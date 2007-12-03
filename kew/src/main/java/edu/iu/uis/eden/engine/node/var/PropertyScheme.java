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
package edu.iu.uis.eden.engine.node.var;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import edu.iu.uis.eden.engine.RouteContext;
import edu.iu.uis.eden.engine.node.var.schemes.LiteralScheme;
import edu.iu.uis.eden.engine.node.var.schemes.ResourceScheme;
import edu.iu.uis.eden.engine.node.var.schemes.URLScheme;
import edu.iu.uis.eden.engine.node.var.schemes.VariableScheme;
import edu.iu.uis.eden.engine.node.var.schemes.XPathScheme;

/**
 * Interface representing an implementation that can resolve Property objects to
 * values.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
public interface PropertyScheme {
    public static final PropertyScheme VARIABLE_SCHEME = new VariableScheme();
    public static final PropertyScheme LITERAL_SCHEME = new LiteralScheme();
    public static final PropertyScheme RESOURCE_SCHEME = new ResourceScheme();
    public static final PropertyScheme URL_SCHEME = new URLScheme();
    public static final PropertyScheme XPATH_SCHEME = new XPathScheme();

    /**
     * Collection/enumeration of PropertyScheme types
     */
    /* interfaces can't have static initializers */
    public static final Collection SCHEMES = Collections.unmodifiableCollection(
                                                             Arrays.
                                                                 asList(new PropertyScheme[] {
                                                                    VARIABLE_SCHEME,
                                                                    LITERAL_SCHEME,
                                                                    RESOURCE_SCHEME,
                                                                    URL_SCHEME,
                                                                    XPATH_SCHEME}));

    /**
     * Scheme name
     * @return scheme name
     */
    public String getName();
    /**
     * Short scheme name
     * @return short scheme name
     */
    public String getShortName();
    /**
     * Loads/resolves a Property
     * @param property the property to resolve
     * @param context the current RouteContext
     * @return the loaded/resolved value
     */
    public Object load(Property property, RouteContext context);
}