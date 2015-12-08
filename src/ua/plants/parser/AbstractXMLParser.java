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
import java.util.Comparator;
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
import ua.plants.generated.GreenHouse.Plants.Plant;
import ua.plants.parser.exceptions.InvalidDocumentException;
import static ua.plants.utils.ProjectUtils.checkNotNull;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public abstract class AbstractXMLParser implements XMLParser<GreenHouse> {

    protected GreenHouse greenHouse;

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
        checkNotNull(xsdFile);

        validate(new FileInputStream(xmlFile), new FileInputStream(xsdFile)); //Validation

        return parseFile(new FileInputStream(xmlFile));
    }

    @Override
    public void renameRootElement(String newName, String xmlInFile, String xsdFile, String xmlOutFile) throws Exception {
        checkNotNull(newName);
        checkNotNull(xmlInFile);
        checkNotNull(xsdFile);
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

    protected void writeDocument(Document document, String xmlOutFile) throws TransformerException, TransformerFactoryConfigurationError, FileNotFoundException, TransformerConfigurationException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new FileOutputStream(xmlOutFile));
        Source input = new DOMSource(document);

        transformer.transform(input, output);
    }

    @Override
    public void sortPlants(String xmlInFile, String xsdFile, String xmlOutFile, Comparator<Plant> comparator) throws Exception {
        checkNotNull(xmlInFile);
        checkNotNull(xsdFile);
        checkNotNull(xmlOutFile);
        checkNotNull(comparator);

        GreenHouse greenhouse = parse(xmlInFile, xsdFile);
        greenhouse.getPlants().getPlant().sort(comparator);

    }

    protected abstract GreenHouse parseFile(InputStream xmlis) throws Exception;

    private void validate(InputStream xmlis, InputStream xsdis) throws SAXException, IOException, InvalidDocumentException, ParserConfigurationException {
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
            throw new InvalidDocumentException();
        }
        System.out.println("Validation successful...");
    }

}
