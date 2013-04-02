/**
 * Copyright 2005-2011 The Kuali Foundation
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
package edu.samplu.mainmenu.test;

import org.junit.Test;

import edu.samplu.common.ITUtil;
import edu.samplu.common.WebDriverLegacyITBase;

/**
 * tests that user 'admin' can display the Context lookup screen, search,
 * initiate an Context maintenance document via an edit action on the search results and
 * finally cancel the maintenance document
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class ContextLookUpWDIT extends WebDriverLegacyITBase {
    public static final String TEST_URL = ITUtil.PORTAL + "?channelTitle=Context%20Lookup&channelUrl=" + ITUtil.getBaseUrlString() +
            "/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.rice.krms.impl.repository.ContextBo&showMaintenanceLinks=true&returnLocation=" +
            ITUtil.PORTAL_URL + "&hideReturnLink=true";
   
    @Override
    public String getTestUrl() {
        return TEST_URL;
    }
    @Test
    public void contextLookupAssertions() throws Exception{
        super.contextLookupAssertions();
    }
}