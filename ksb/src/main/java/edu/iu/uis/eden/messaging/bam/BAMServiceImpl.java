/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.iu.uis.eden.messaging.bam;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.rice.definition.ObjectDefinition;

import edu.iu.uis.eden.messaging.ServiceInfo;
import edu.iu.uis.eden.messaging.bam.dao.BAMConfigDAO;
import edu.iu.uis.eden.messaging.bam.dao.BAMDAO;

public class BAMServiceImpl implements BAMService {

	private static final Logger LOG = Logger.getLogger(BAMServiceImpl.class);

	private BAMDAO dao;

	private BAMConfigDAO configDao;

	public BAMTargetEntry recordClientInvocation(ServiceInfo serviceDefinition, Object target, Method method, Object[] params) {
		if (isEnabled()) {
			try {
				LOG.debug("A call was received... for service: " + serviceDefinition.getQname().toString() + " method: " + method.getName());
				BAMTargetEntry bamTargetEntry = getBAMTargetEntry(Boolean.FALSE, serviceDefinition, target, method, params);
				this.dao.save(bamTargetEntry);
				return bamTargetEntry;
			} catch (Throwable t) {
				LOG.error("BAM Failed to record client invocation", t);
				return null;
			}
		}
		return null;
	}

	public BAMTargetEntry recordServerInvocation(Object target, ServiceInfo entry, Method method, Object[] params) {
		if (isEnabled()) {
			try {
				LOG.debug("A call was received... for service: " + target.getClass().getName() + " method: " + method.getName());
				BAMTargetEntry bamTargetEntry = getBAMTargetEntry(Boolean.TRUE, entry, target, method, params);
				this.dao.save(bamTargetEntry);
			} catch (Throwable t) {
				LOG.error("BAM Failed to record server invocation", t);
			}
		}
		return null;
	}

	public BAMTargetEntry recordClientInvocationError(Throwable throwable, BAMTargetEntry bamTargetEntry) {
		if (bamTargetEntry != null) {
			try {
				setThrowableOnBAMTargetEntry(throwable, bamTargetEntry);
				this.dao.save(bamTargetEntry);
				return bamTargetEntry;
			} catch (Exception e) {
				LOG.error("BAM Failed to record client invocation error", e);
			}
		}
		return null;
	}

	public BAMTargetEntry recordServerInvocationError(Throwable throwable, BAMTargetEntry bamTargetEntry) {
		if (bamTargetEntry != null) {
			try {
				setThrowableOnBAMTargetEntry(throwable, bamTargetEntry);
				this.dao.save(bamTargetEntry);
				return bamTargetEntry;
			} catch (Exception e) {
				LOG.error("BAM Failed to record service invocation error", e);
			}
		}
		return null;
	}

	private void setThrowableOnBAMTargetEntry(Throwable throwable, BAMTargetEntry bamTargetEntry) {
		if (throwable != null) {
			bamTargetEntry.setExceptionMessage(throwable.getMessage());
			bamTargetEntry.setExceptionToString(makeStringfit(throwable.toString()));
		}
	}

	private BAMTargetEntry getBAMTargetEntry(Boolean serverInd, ServiceInfo entry, Object target, Method method, Object[] params) {
		BAMTargetEntry bamEntry = new BAMTargetEntry();
		bamEntry.setServerInvocation(serverInd);
		bamEntry.setServiceName(entry.getQname().toString());
		bamEntry.setServiceURL(entry.getEndpointUrl());
		bamEntry.setTargetToString(makeStringfit(target.toString()));
		bamEntry.setMethodName(method.getName());
		bamEntry.setThreadName(Thread.currentThread().getName());
		bamEntry.setCallDate(new Timestamp(System.currentTimeMillis()));
		setBamParams(params, bamEntry);
		return bamEntry;
	}

	private void setBamParams(Object[] params, BAMTargetEntry bamEntry) {
		if (params == null) {
			return;
		}
		for (int i = 0; i < params.length; i++) {
			BAMParam bamParam = new BAMParam();
			bamParam.setBamTargetEntry(bamEntry);
			bamParam.setParam(params[i].toString());
			bamEntry.addBamParam(bamParam);
		}
	}

	private String makeStringfit(String string) {
		if (string.length() > 1999) {
			return string.substring(0, 1999);
		}
		return string;
	}

	public boolean isEnabled() {
		return getConfigDao().getEnabledState();
	}

	public BAMDAO getDao() {
		return this.dao;
	}

	public void setDao(BAMDAO dao) {
		this.dao = dao;
	}

	public BAMConfigDAO getConfigDao() {
		return this.configDao;
	}

	public void setConfigDao(BAMConfigDAO configDao) {
		this.configDao = configDao;
	}

	public List<BAMTargetEntry> getCallsForService(QName serviceName) {
		return getDao().getCallsForService(serviceName);
	}

	public List<BAMTargetEntry> getCallsForRemotedClasses(ObjectDefinition objDef) {
		return getDao().getCallsForRemotedClasses(objDef);
	}

	public void clearBAMTables() {
		getDao().clearBAMTables();
	}

	public List<BAMTargetEntry> getCallsForService(QName serviceName, String methodName) {
		return getDao().getCallsForService(serviceName, methodName);
	}

	public List<BAMTargetEntry> getCallsForRemotedClasses(ObjectDefinition objDef, String methodName) {
		return getDao().getCallsForRemotedClasses(objDef, methodName);
	}
}