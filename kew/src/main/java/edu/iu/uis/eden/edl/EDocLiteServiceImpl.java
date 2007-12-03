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
package edu.iu.uis.eden.edl;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.kuali.rice.core.Core;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.iu.uis.eden.EdenConstants;
import edu.iu.uis.eden.KEWServiceLocator;
import edu.iu.uis.eden.WorkflowServiceErrorException;
import edu.iu.uis.eden.WorkflowServiceErrorImpl;
import edu.iu.uis.eden.edl.dao.EDocLiteDAO;
import edu.iu.uis.eden.exception.WorkflowRuntimeException;
import edu.iu.uis.eden.export.ExportDataSet;
import edu.iu.uis.eden.routeheader.DocumentRouteHeaderValue;
import edu.iu.uis.eden.routetemplate.RuleAttribute;
import edu.iu.uis.eden.user.WorkflowUser;
import edu.iu.uis.eden.util.XmlHelper;
import edu.iu.uis.eden.xml.EDocLiteXmlParser;
import edu.iu.uis.eden.xml.export.EDocLiteXmlExporter;

/**
 * DAO-based EDocLiteService implementation
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
public class EDocLiteServiceImpl implements EDocLiteService {	
    private static final Logger LOG = Logger.getLogger(EDocLiteServiceImpl.class);

	private EDLGlobalConfig edlGlobalConfig;
    /**
     * The Spring-wired DAO bean
     */
    private EDocLiteDAO dao;
    /**
     * Spring wired StyleService bean
     */
    private StyleService styleService;

    // ---- Spring DAO setters

    public void setEDocLiteDAO(EDocLiteDAO dao) {
        this.dao = dao;
    }

    public void setStyleService(StyleService styleService) {
        this.styleService = styleService;
    }

	public EDLController getEDLController(String edlName) {
		EDocLiteAssociation edlAssociation = this.dao.getEDocLiteAssociation(edlName);
        if (edlAssociation == null) {
            throw new WorkflowRuntimeException("No document association active for EDL: " + edlName);
        }
		if (edlGlobalConfig == null) {
			initEDLGlobalConfig();
		}
		return EDLControllerFactory.createEDLController(edlAssociation, edlGlobalConfig);
	}
	
	public EDLController getEDLController(Long documentId) {
		DocumentRouteHeaderValue document = KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentId);
		String edlName = document.getAppDocId();//components working with workflow docs will need to know this, perhaps through a document utils.
		if (edlName == null) {
			edlName = document.getDocumentType().getName();
		}
		EDocLiteAssociation edlAssociation = this.dao.getEDocLiteAssociation(edlName);
        if (edlAssociation == null) {
            throw new WorkflowRuntimeException("No document association active for EDL: " + edlName);
        }
		if (edlGlobalConfig == null) {
			initEDLGlobalConfig();
		}
		return EDLControllerFactory.createEDLController(edlAssociation, edlGlobalConfig, document);
	}
	
	public void initEDLGlobalConfig() {
		try {
			this.edlGlobalConfig = EDLGlobalConfigFactory.createEDLGlobalConfig(Core.getCurrentContextConfig().getEDLConfigLocation());	
		} catch (Exception e) {
			throw new WorkflowRuntimeException(e);
		}
	}
	
	public Document getDefinitionXml(EDocLiteAssociation edlAssociation) {
		try {
			Document def = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(
			new StringReader(getEDocLiteDefinition(edlAssociation.getDefinition()).getXmlContent())));
			return def;
		} catch (Exception e) {
			throw new WorkflowRuntimeException("Caught exception parsing EDL definition " + edlAssociation.getDefinition(), e);
		}
	}
	
	private static WorkflowServiceErrorException generateException(String error, Throwable cause) {
        WorkflowServiceErrorException wsee = new WorkflowServiceErrorException(error, new WorkflowServiceErrorImpl(error, EdenConstants.XML_FILE_PARSE_ERROR));
        if (cause != null) {
            wsee.initCause(cause);
        }
        return wsee;
    }

    private static WorkflowServiceErrorException generateMissingAttribException(String element, String attrib) {
        return generateException("EDocLite '" + element + "' element must contain a '" + attrib + "' attribute", null);
    }

    private static WorkflowServiceErrorException generateMissingChildException(String element, String child) {
        return generateException("EDocLite '" + element + "' element must contain a '" + child + "' child element", null);
    }

    private static WorkflowServiceErrorException generateSerializationException(String element, TransformerException cause) {
        return generateException("Error serializing EDocLite '" + element + "' element", cause);
    }

    /**
     * Parses an arbitrary XML stream
     * 
     * @param stream
     *            stream containing XML doc content
     * @return parsed Document object
     */
    private static Document parse(InputStream stream) {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
        } catch (Exception e) {
            WorkflowServiceErrorException wsee = new WorkflowServiceErrorException("Error parsing EDocLite XML file", new WorkflowServiceErrorImpl("Error parsing XML file.", EdenConstants.XML_FILE_PARSE_ERROR));
            wsee.initCause(e);
            throw wsee;
        }
    }

    /**
     * Parses an EDocLiteAssocation
     * 
     * @param e
     *            element to parse
     * @return an EDocLiteAssocation
     */
    private static EDocLiteAssociation parseEDocLiteAssociation(Element e) {
        String docType = EDLXmlUtils.getChildElementTextValue(e, "docType");
        if (docType == null) {
            throw generateMissingChildException("association", "docType");
        }
        EDocLiteAssociation assoc = new EDocLiteAssociation();
        assoc.setEdlName(docType);
        assoc.setDefinition(EDLXmlUtils.getChildElementTextValue(e, "definition"));
        assoc.setStyle(EDLXmlUtils.getChildElementTextValue(e, "style"));
        assoc.setActiveInd(Boolean.valueOf(EDLXmlUtils.getChildElementTextValue(e, "active")));
        return assoc;
    }

    /**
     * Parses an EDocLiteDefinition
     * 
     * @param e
     *            element to parse
     * @return an EDocLiteDefinition
     */
    private static EDocLiteDefinition parseEDocLiteDefinition(Element e) {
        EDocLiteDefinition def = new EDocLiteDefinition();
        String name = e.getAttribute("name");
        if (name == null || name.length() == 0) {
            throw generateMissingAttribException(EDLXmlUtils.EDL_E, "name");
        }
        def.setName(name);

        // do some validation to ensure that any attributes referenced actually exist
        // blow up if there is a problem

        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList fields;
        try {
            fields = (NodeList) xpath.evaluate("fieldDef", e, XPathConstants.NODESET);
        } catch (XPathExpressionException xpee) {
            throw new RuntimeException("Invalid EDocLiteDefinition", xpee);
        }

        if (fields != null) {
            Collection invalidAttributes = new ArrayList(5);
            for (int i = 0; i < fields.getLength(); i++) {
                Node node = (Node) fields.item(i);
                // they should all be Element...
                if (node instanceof Element) {
                    Element field = (Element) node;
                    // rely on XML validation to ensure this is present
                    String fieldName = field.getAttribute("name");
                    String attribute = field.getAttribute("attributeName");
                    if (attribute != null && attribute.length() > 0) {
                        RuleAttribute ruleAttrib = KEWServiceLocator.getRuleAttributeService().findByName(attribute);
                        if (ruleAttrib == null) {
                            LOG.error("Invalid attribute referenced in EDocLite definition: " + attribute);
                            invalidAttributes.add("Attribute '" + attribute + "' referenced in field '" + fieldName + "' not found");
                        }
                    }
                }
            }
            if (invalidAttributes.size() > 0) {
                LOG.error("Invalid attributes referenced in EDocLite definition");
                StringBuffer message = new StringBuffer("EDocLite definition contains references to non-existent attributes;\n");
                Iterator it = invalidAttributes.iterator();
                while (it.hasNext()) {
                    message.append(it.next());
                    message.append("\n");
                }
                throw new RuntimeException(message.toString());
            }
        }

        try {
            def.setXmlContent(XmlHelper.writeNode(e, true));
        } catch (TransformerException te) {
            throw generateSerializationException(EDLXmlUtils.EDL_E, te);
        }
        return def;
    }

    // ---- helper methods

    public void saveEDocLiteStyle(EDocLiteStyle data) {
        styleService.saveStyle(data);
    }

    public void saveEDocLiteDefinition(EDocLiteDefinition data) {
        EDocLiteDefinition existingData = getEDocLiteDefinition(data.getName());
        if (existingData != null) {
            existingData.setActiveInd(Boolean.FALSE);
            dao.saveEDocLiteDefinition(existingData);
        }
        // if not specified (from xml), mark it as active
        if (data.getActiveInd() == null) {
            data.setActiveInd(Boolean.TRUE);
        }
        dao.saveEDocLiteDefinition(data);
        removeDefinitionFromCache(data.getName());
    }

    public void saveEDocLiteAssociation(EDocLiteAssociation assoc) {
        EDocLiteAssociation existingData = getEDocLiteAssociation(assoc.getEdlName());
        if (existingData != null) {
            existingData.setActiveInd(Boolean.FALSE);
            dao.saveEDocLiteAssociation(existingData);
        }
        // if not specified (from xml), mark it as active
        if (assoc.getActiveInd() == null) {
            assoc.setActiveInd(Boolean.TRUE);
        }
        dao.saveEDocLiteAssociation(assoc);
    }

    // ---- EDocLiteService interface implementation

    public void saveEDocLiteStyle(InputStream xml) {
        styleService.saveStyle(xml);
    }

    public void saveEDocLiteDefinition(InputStream xml) {
        // convert xml to EDocLiteDefinition
        EDocLiteDefinition data = parseEDocLiteDefinition(parse(xml).getDocumentElement());
        saveEDocLiteDefinition(data);        
    }

    public void saveEDocLiteAssociation(InputStream xml) {
        // convert xml to EDocLiteAssociation
        EDocLiteAssociation assoc = parseEDocLiteAssociation(parse(xml).getDocumentElement());
        saveEDocLiteAssociation(assoc);
    }

    public EDocLiteStyle getEDocLiteStyle(String styleName) {
        return styleService.getStyle(styleName);
    }

    public EDocLiteDefinition getEDocLiteDefinition(String definitionName) {
        return dao.getEDocLiteDefinition(definitionName);
    }

    public EDocLiteAssociation getEDocLiteAssociation(String docTypeName) {
        return dao.getEDocLiteAssociation(docTypeName);
    }

    public List<String> getEDocLiteStyles() {
        return styleService.getStyleNames();
    }

    public List getEDocLiteDefinitions() {
        return dao.getEDocLiteDefinitions();
    }

    public List getEDocLiteAssociations() {
        return dao.getEDocLiteAssociations();
    }

    public Templates getStyleAsTranslet(String name) throws TransformerConfigurationException {
        if (name == null || "null".equals(name)) { //"name".equals(name) - from a null value in the lookupable
            name = "Default";
        }

        return styleService.getStyleAsTranslet(name);
    }

    public void removeStyleFromCache(String styleName) {
        styleService.removeStyleFromCache(styleName);
    }
    
    public void removeDefinitionFromCache(String defName) {
        LOG.info("Removing definition " + defName + " from cache");
        EDLControllerFactory.flushDefinitionFromConfigCache(defName);
    }

    public List search(EDocLiteAssociation edocLite) {
        return this.dao.search(edocLite);
    }

    public EDocLiteAssociation getEDocLiteAssociation(Long associationId) {
        return dao.getEDocLiteAssociation(associationId);
    }
    
    // ---- XmlLoader interface implementation

    public void loadXml(InputStream inputStream, WorkflowUser user) {
    	EDocLiteXmlParser.loadXml(inputStream, user);
    }

    // ---- XmlExporter interface implementation
	public org.jdom.Element export(ExportDataSet dataSet) {
		return new EDocLiteXmlExporter().export(dataSet);
	}
  
}