/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kim.test;

import org.kuali.rice.core.api.lifecycle.BaseLifecycle;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.api.type.KimType;
import org.kuali.rice.kim.impl.permission.PermissionTemplateBo;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.test.BaselineTestCase;
import org.kuali.rice.test.BaselineTestCase.BaselineMode;
import org.kuali.rice.test.BaselineTestCase.Mode;
import org.kuali.rice.test.SQLDataLoader;
import org.kuali.rice.test.lifecycles.KEWXmlDataLoaderLifecycle;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * This is test base that should be used for all KIM unit tests. All non-web unit tests for KIM should extend this base
 * class.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@BaselineMode(Mode.ROLLBACK_CLEAR_DB)
public abstract class KIMTestCase extends BaselineTestCase {

	private static final String KIM_MODULE_NAME = "kim";
	
	public KIMTestCase() {
		super(KIM_MODULE_NAME);
	}
	
	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> suiteLifecycles = super.getSuiteLifecycles();
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/kuali/rice/kim/test/DefaultSuiteTestData.xml"));
		return suiteLifecycles;
	}
	
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/kuali/rice/kim/test/DefaultSuiteTestData.sql", "/").runSql();
		new SQLDataLoader("classpath:org/kuali/rice/kim/test/CircularRolesTestData.sql", "/").runSql();
		new SQLDataLoader("classpath:org/kuali/rice/kim/test/CircularGroupsTestData.sql", "/").runSql();
        new SQLDataLoader("classpath:org/kuali/rice/kim/test/DefaultSuiteLDAPTestData.sql", "/").runSql();
	}
	
	@Override
	protected Lifecycle getLoadApplicationLifecycle() {
    	SpringResourceLoader springResourceLoader = new SpringResourceLoader(new QName("KIMTestHarnessApplicationResourceLoader"), "classpath:KIMTestHarnessSpringBeans.xml", null);
    	springResourceLoader.setParentSpringResourceLoader(getTestHarnessSpringResourceLoader());
    	return springResourceLoader;
	}
	
	/**
	 * Override the standard per-test lifecycles to prepend ClearDatabaseLifecycle and ClearCacheLifecycle
	 * @see org.kuali.rice.test.RiceTestCase#getPerTestLifecycles()
	 */
	@Override
	protected List<Lifecycle> getPerTestLifecycles() {
		List<Lifecycle> lifecycles = super.getPerTestLifecycles();
		lifecycles.add(new ClearCacheLifecycle());
		return lifecycles;
	}
	
	public class ClearCacheLifecycle extends BaseLifecycle {
		@Override
		public void stop() throws Exception {
			//KimApiServiceLocator.getIdentityManagementService().flushAllCaches();
			//KimApiServiceLocator.getRoleService().flushRoleCaches();
			super.stop();
		}

	}
	
	protected List<String> getPerTestTablesNotToClear() {
		List<String> tablesNotToClear = new ArrayList<String>();
		tablesNotToClear.add("KRIM_.*");
		tablesNotToClear.add("KRNS_.*");
        tablesNotToClear.add("KRCR_.*");
        tablesNotToClear.add("KREW_.*");
		return tablesNotToClear;
	}


	/**
     * @see org.kuali.rice.test.RiceTestCase#getModuleName()
     */
	@Override
	protected String getModuleName() {
		return KIM_MODULE_NAME;
	}

}