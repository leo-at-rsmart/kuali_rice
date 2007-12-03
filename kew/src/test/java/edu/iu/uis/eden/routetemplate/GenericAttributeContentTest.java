/*
 * Copyright 2005-2007 The Kuali Foundation.
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
// Created on Oct 23, 2006

package edu.iu.uis.eden.routetemplate;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import edu.iu.uis.eden.routeheader.DocumentContent;
import edu.iu.uis.eden.routeheader.StandardDocumentContent;

public class GenericAttributeContentTest extends TestCase {
    private static final String ATTRIB1_CONTENT = "    <boringAttribute>" +
                                                  "      <field>" +
                                                  "        <name>color</name>" +
                                                  "        <value>green</value>" +
                                                  "      </field>" +
                                                  "      <field>" +
                                                  "        <name>shape</name>" +
                                                  "        <value>circle</value>" +
                                                  "      </field>" +
                                                  "    </boringAttribute>";
    private static final String ATTRIB2_CONTENT = "    <coolAttribute>" +
                                                  "      <field>" +
                                                  "        <name>car</name>" +
                                                  "        <value>KIT</value>" +
                                                  "      </field>" +
                                                  "      <field>" +
                                                  "        <name>driver</name>" +
                                                  "        <value>hasselhof</value>" +
                                                  "      </field>" +
                                                  "    </coolAttribute>";
    private static final String TEST_CONTENT = "<documentContent>" +
                                               "  <attributeContent>" +
                                               ATTRIB1_CONTENT +
                                               ATTRIB2_CONTENT +
                                               "  </attributeContent>" +
                                               "</documentContent>";
                                            
    @Test public void testGenerateContent() throws Exception {
        DocumentContent dc = new StandardDocumentContent(TEST_CONTENT);
        GenericAttributeContent gac = new GenericAttributeContent("boringAttribute");
        List<Map<String, String>> attrs = gac.parseContent(dc.getAttributeContent());
        assertEquals(1, attrs.size());
        Map<String, String> properties = attrs.get(0);
        assertEquals(2, properties.size());
        assertEquals("green", properties.get("color"));
        assertEquals("circle", properties.get("shape"));
        String content = gac.generateContent(properties);
        assertEquals(content.replaceAll("\\s+", ""), ATTRIB1_CONTENT.replaceAll("\\s+", ""));

        gac = new GenericAttributeContent("coolAttribute");
        attrs = gac.parseContent(dc.getAttributeContent());
        assertEquals(1, attrs.size());
        properties = attrs.get(0);
        assertEquals(2, properties.size());
        assertEquals("hasselhof", properties.get("driver"));
        assertEquals("KIT", properties.get("car"));
        content = gac.generateContent(properties);
        assertEquals(content.replaceAll("\\s+", ""), ATTRIB2_CONTENT.replaceAll("\\s+", ""));
    }
}
