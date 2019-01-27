package PO61.Geer.wdad.data.managers;

import PO61.Geer.wdad.utils.PreferencesManagerConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class PreferencesManager implements PreferencesManagerInterface, PreferencesManagerConstants {
    private static final String PATH = "src/PO62/Gaydidey/wdad/resources/configuration/appconfig.xml";
    private static final File FILE = new File(PATH);

    private static PreferencesManager ourInstance = new PreferencesManager();

    private Document document;
    private XPath xPath;

    public static PreferencesManager getInstance() {
        return ourInstance;
    }

    private PreferencesManager() {
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(FILE);
            xPath = XPathFactory.newInstance().newXPath();
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getProperty(String key) {
        try {
            return (String) xPath.evaluate(key + "/text()", document, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setProperty(String key, String value) {
        try {
            ((Node) xPath.evaluate(key, document, XPathConstants.NODE))
                    .getFirstChild().setNodeValue(value);

            save();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Properties getProperties() {
        Properties props = new Properties();
        props.put(CREATEREGISTRY, getProperty(CREATEREGISTRY));
        props.put(REGISTRYADDRESS, getProperty(REGISTRYADDRESS));
        props.put(REGISTRYPORT, getProperty(REGISTRYPORT));
        props.put(POLICYPATH, getProperty(POLICYPATH));
        props.put(USECODEBASEONLY, getProperty(USECODEBASEONLY));
        props.put(CLASSPROVIDER, getProperty(CLASSPROVIDER));

        return props;
    }

    @Override
    public void setProperties(Properties prop) {
        for (Object key : prop.keySet()) {
            setProperty(key.toString(), prop.getProperty(key.toString()));
        }
    }

    @Override
    public void addBoundObject(String name, String className) {
        Element el = document.createElement("boundobject");
        el.setAttribute("class", className);
        el.setAttribute("name", name);
        document.getElementsByTagName("server").item(0).appendChild(el);

        save();
    }

    @Override
    public void removeBoundObject(String name) {
        NodeList objects = document.getElementsByTagName("boundobject");
        Node element;
        for (int i = 0; i < objects.getLength(); i++) {
            element = objects.item(i);
            if (name.equals(element.getAttributes().getNamedItem("name").getNodeValue())) {
                element.getParentNode().removeChild(element);
            }
        }

        save();
    }

    @Deprecated
    public String getCreateRegistry() {
        return document.getElementsByTagName("createregistry").item(0).getTextContent();
    }

    @Deprecated
    public void setCreateRegistry(String value) {
        document.getElementsByTagName("createregistry").item(0).setTextContent(value);
        save();
    }

    @Deprecated
    public String getRegistryAddress() {
        return document.getElementsByTagName("registryaddress").item(0).getTextContent();
    }

    @Deprecated
    public void setRegistryAddress(String value) {
        document.getElementsByTagName("registryaddress").item(0).setTextContent(value);
        save();
    }

    @Deprecated
    public String getRegistryPort() {
        return document.getElementsByTagName("registryport").item(0).getTextContent();
    }

    @Deprecated
    public void setRegistryPort(String value) {
        document.getElementsByTagName("registryport").item(0).setTextContent(value);
        save();
    }

    @Deprecated
    public String getPolicyPath() {
        return document.getElementsByTagName("policypath").item(0).getTextContent();
    }

    @Deprecated
    public void setPolicyPath(String value) {
        document.getElementsByTagName("policypath").item(0).setTextContent(value);
        save();
    }

    @Deprecated
    public String getUseCodeBaseOnly() {
        return document.getElementsByTagName("usecodebaseonly").item(0).getTextContent();
    }

    @Deprecated
    public void setUseCodeBaseOnly(String value) {
        document.getElementsByTagName("usecodebaceonly").item(0).setTextContent(value);
        save();
    }

    @Deprecated
    public String getClassProvider() {
        return document.getElementsByTagName("classprovider").item(0).getTextContent();
    }

    @Deprecated
    public void setClassProvider(String value) {
        document.getElementsByTagName("classprovider").item(0).setTextContent(value);
        save();
    }
    
    private void save() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StreamResult output = new StreamResult(FILE);
            DOMSource input = new DOMSource(document);
            transformer.transform(input, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
