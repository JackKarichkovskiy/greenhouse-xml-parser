/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ua.plants.generated.GreenHouse;
import ua.plants.parser.exceptions.InvalidDocumentException;
import static ua.plants.utils.ProjectUtils.checkNotNull;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public abstract class AbstractXMLParser implements XMLParser<GreenHouse> {

    protected static final String STALK_COLOR = "stalkColor";
    protected static final String PLANTS = "plants";
    protected static final String PLANT = "plant";
    protected static final String NAME = "name";
    protected static final String SOIL = "soil";
    protected static final String ORIGIN = "origin";
    protected static final String VISUAL_PARAMS = "visualParams";
    protected static final String GROWING_TIPS = "growingTips";
    protected static final String MULTIPLYING = "multiplying";
    protected static final String ID = "id";
    protected static final String AVERAGE_SIZE = "averageSize";
    protected static final String LEAF_COLOR = "leafColor";
    protected static final String WATERING = "watering";
    protected static final String LIGHTING = "lighting";
    protected static final String TEMPERATURE = "temparature";
    
    protected InputStream xmlis;
    protected InputStream xsdis;
    protected GreenHouse greenHouse;
    protected Document doc;

    @Override
    public GreenHouse parse(InputStream xmlis, InputStream xsdis) throws Exception {
        this.xmlis = checkNotNull(xmlis);
        this.xsdis = checkNotNull(xsdis);

        //validate(xmlis, xsdis);

        return parseFile(xmlis);
    }

    protected abstract GreenHouse parseFile(InputStream xmlis) throws Exception;

    private void validate(InputStream xmlis, InputStream xsdis) throws SAXException, IOException, InvalidDocumentException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlis);
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(xsdis);
        Schema schema = factory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        try {
            validator.validate(new DOMSource(doc));
        } catch (SAXException e) {
            System.out.println(e);
            throw new InvalidDocumentException();
        }
    }

}
