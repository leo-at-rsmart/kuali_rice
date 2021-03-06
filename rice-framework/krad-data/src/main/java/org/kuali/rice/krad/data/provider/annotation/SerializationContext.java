/*
 * Copyright 2006-2014 The Kuali Foundation
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

package org.kuali.rice.krad.data.provider.annotation;

/**
 * Defines different context(s) for serialization in Rice.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public enum SerializationContext {

    /**
     * Matches all the other contexts defined in {@link SerializationContext}.
     */
    ALL,

    /**
     * The context for serializing within the maintenance document framework.
     */
    MAINTENANCE,

// TODO: implement respect for @Serialized in workflow doc serialization
//    /**
//     * The context for serializing to workflow document contents.
//     */
//    WORKFLOW;
}
