/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ua.plants.generated.GreenHouse;
import ua.plants.parser.exceptions.InvalidDocumentException;
import static ua.plants.utils.ProjectUtils.checkNotNull;

/**
 * Abstract class that have some default realization and defines template of
 * parse method.
 *
 * @author Karichkovskiy Yevhen
 */
public abstract class AbstractXMLParser implements XMLParser {

    /**
     * Object that contains the parsed entity after parsing.
     */
    protected GreenHouse greenHouse;

    /**
     * Inner interface that contains xml tags names. Used during parsing.
     */
    protected interface GreenHouseTags {

        String STALK_COLOR = "stalkColor";
        String PLANTS = "plants";
        String PLANT = "plant";
        String NAME = "name";
        String SOIL = "soil";
        String ORIGIN = "origin";
        String VISUAL_PARAMS = "visualParams";
        String GROWING_TIPS = "growingTips";
        String MULTIPLYING = "multiplying";
        String ID = "id";
        String AVERAGE_SIZE = "averageSize";
        String LEAF_COLOR = "leafColor";
        String WATERING = "watering";
        String LIGHTING = "lighting";
        String TEMPERATURE = "temparature";
    }

    @Override
    public GreenHouse parse(String xmlFile, String xsdFile) throws Exception {
        checkNotNull(xmlFile);

        if (xsdFile != null) {
            boolean xmlValidated = validate(new FileInputStream(xmlFile), new FileInputStream(xsdFile)); //Validation
            if(!xmlValidated)
                throw new InvalidDocumentException();
        }

        return parseFile(new FileInputStream(xmlFile));
    }

    @Override
    public void renameRootElement(String newName, String xmlInFile, String xsdFile, String xmlOutFile) throws Exception {
        checkNotNull(newName);
        checkNotNull(xmlInFile);
        checkNotNull(xmlOutFile);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.parse(new FileInputStream(xmlInFile));

        Element oldRoot = document.getDocumentElement();

        Element newRoot = document.createElement(newName);

        NamedNodeMap attributes = oldRoot.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = document.importNode(attributes.item(i), true);
            newRoot.getAttributes().setNamedItem(attr);
        }

        while (oldRoot.hasChildNodes()) {
            newRoot.appendChild(oldRoot.getFirstChild());
        }

        oldRoot.getParentNode().replaceChild(newRoot, oldRoot);

        writeDocument(document, xmlOutFile);
    }

    /**
     * Method write the xml represented by document to file using xmlOutFile
     * path.
     *
     * @param document - xml structure document
     * @param xmlOutFile - path to the output file
     * @throws TransformerException - if has some writing problems
     * @throws TransformerFactoryConfigurationError - if has problems with factory creation
     * @throws FileNotFoundException - if xml output file isn't exists
     * @throws TransformerConfigurationException - if has some problems with transformer creation
     */
    protected void writeDocument(Document document, String xmlOutFile)
            throws TransformerException, TransformerFactoryConfigurationError, FileNotFoundException, TransformerConfigurationException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new FileOutputStream(xmlOutFile));
        Source input = new DOMSource(document);

        transformer.transform(input, output);
    }

    /**
     * Method that parses the xml read from xmlis input stream and returns parsed object.
     * @param xmlis - input stream from xml file
     * @return parsed object
     * @throws Exception - if some parsing problems occur
     */
    protected abstract GreenHouse parseFile(InputStream xmlis) throws Exception;

    /**
     * Method that validates the existed xml using appropriate xsd file.
     * @param xmlis - xml input stream
     * @param xsdis - xsd input stream
     * @return true - if validation was successful, otherwise - false.
     * @throws SAXException - if problems with parsing xml or xsd
     * @throws IOException - if some IO problems
     * @throws ParserConfigurationException - if some problems with DocumentBuilder creation
     */
    private boolean validate(InputStream xmlis, InputStream xsdis) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(xmlis);
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(xsdis);
        Schema schema = factory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        try {
            validator.validate(new DOMSource(document));
        } catch (SAXException e) {
            System.out.println(e);
            return false;
        }
        System.out.println("Validation successful...");
        return true;
    }

}
