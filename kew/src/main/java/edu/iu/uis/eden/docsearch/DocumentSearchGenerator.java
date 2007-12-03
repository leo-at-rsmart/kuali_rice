/*
 * Copyright 2005-2006 The Kuali Foundation.
 * 
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
package edu.iu.uis.eden.docsearch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import edu.iu.uis.eden.WorkflowServiceError;
import edu.iu.uis.eden.exception.EdenUserNotFoundException;
import edu.iu.uis.eden.user.WorkflowUser;

/**
 * TODO delyea - documentation
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
public interface DocumentSearchGenerator {
	public void setSearchableAttributes(List searchableAttributes);
	public void setSearchingUser(WorkflowUser searchingUser);
    public List<WorkflowServiceError> performPreSearchConditions(WorkflowUser user, DocSearchCriteriaVO searchCriteria);
    public List<WorkflowServiceError> validateSearchableAttributes(DocSearchCriteriaVO searchCriteria);
    public String generateSearchSql(DocSearchCriteriaVO searchCriteria) throws EdenUserNotFoundException;
    public List<DocSearchVO> processResultSet(Statement searchAttributeStatement, ResultSet resultSet,DocSearchCriteriaVO searchCriteria) throws EdenUserNotFoundException, SQLException;
    public DocSearchCriteriaVO clearSearch(DocSearchCriteriaVO searchCriteria);
}
