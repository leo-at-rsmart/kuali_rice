<%--
 Copyright 2006-2007 The Kuali Foundation.
 
 Licensed under the Educational Community License, Version 1.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl1.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/jsp/core/tldHeader.jsp"%>
<c:set var="salarySettingAttributes"
	value="${DataDictionary['KualiSalarySettingDocument'].attributes}" />

<kul:page showDocumentInfo="false"
	htmlFormAction="budgetSalarySetting" renderMultipart="true"
	showTabButtons="true"
	docTitle="Salary Setting"
    transactionalDocument="false"
	>

	<kul:hiddenDocumentFields
		isFinancialDocument="false"
		isTransactionalDocument="false" />
    <bc:salarySetting />
	<kul:panelFooter />

</kul:page>



