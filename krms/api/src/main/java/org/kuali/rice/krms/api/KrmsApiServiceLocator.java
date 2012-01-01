/**
 * Copyright 2005-2012 The Kuali Foundation
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
package org.kuali.rice.krms.api;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.engine.Engine;
import org.kuali.rice.krms.api.engine.expression.ComparisonOperatorService;
import org.kuali.rice.krms.api.repository.RuleRepositoryService;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;

import javax.xml.namespace.QName;

/**
 * A static service locator which aids in locating the various remotable services that form the Kuali Rule Management System API.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class KrmsApiServiceLocator {

	public static final String ENGINE = "rice.krms.engine";
	public static final QName RULE_REPOSITORY_SERVICE = new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleRepositoryService");
    public static final QName KRMS_TYPE_REPOSITORY_SERVICE = new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "krmsTypeRepositoryService");
    public static final QName COMPARISON_SERVICE = new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "comparisonOperatorRegistration");

    static <T> T getService(String serviceName) {
        return GlobalResourceLoader.<T>getService(serviceName);
    }

    static <T> T getService(QName serviceName) {
        return GlobalResourceLoader.<T>getService(serviceName);
    }

    public static Engine getEngine() {
        return getService(ENGINE);
    }
    
    public static RuleRepositoryService getRuleRepositoryService() {
    	return getService(RULE_REPOSITORY_SERVICE);
    }

    public static KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        return getService(KRMS_TYPE_REPOSITORY_SERVICE);
    }

    public static ComparisonOperatorService getComparisonOperatorService() {
        return getService(COMPARISON_SERVICE);
    }
}
