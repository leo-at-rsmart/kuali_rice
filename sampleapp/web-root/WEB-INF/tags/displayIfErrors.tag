<%--
 Copyright 2006 The Kuali Foundation.
 
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
<%@ tag body-content="scriptless" %> 

<%@ taglib prefix="c" uri="/tlds/c.tld"%>
<%@ taglib prefix="fn" uri="/tlds/fn.tld"%>

<%@ attribute name="keyMatch" required="true" %>

<%-- determine if there are any error messages matching the given key --%>
<c:set var="hasErrors" value="false" />
<c:forEach items="${fn:split(keyMatch,',')}" var="prefix">
    <c:forEach items="${ErrorPropertyList}" var="key">
        <c:if test="${(fn:endsWith(prefix,'*') && fn:startsWith(key,fn:replace(prefix,'*',''))) || (key eq prefix)}">
            <c:set var="hasErrors" value="true"/>
        </c:if>
    </c:forEach>
</c:forEach>

<%-- if there are matching errors, process the contained JSP --%>
<c:if test="${hasErrors}">
    <jsp:doBody/>
</c:if>
