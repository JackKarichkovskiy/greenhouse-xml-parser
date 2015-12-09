/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants;

import ua.plants.generated.GreenHouse;
import ua.plants.parser.XMLParser;
import ua.plants.parser.XMLParserFactory;

/**
 * Main class that calls parser methods.
 * @author Karichkovskiy Yevhen
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        XMLParserFactory factory = XMLParserFactory.getInstance(XMLParserFactory.XMLParserType.SAX);
        XMLParser xmlParser = factory.getXMLParser();

        String xmlPath = "src/xmlFiles/greenhouse.xml";
        String xsdPath = "src/xmlFiles/greenhouse.xsd";

        //PARSING
        GreenHouse parsedGreenHouse = xmlParser.parse(
                xmlPath,
                xsdPath);
        System.out.println(parsedGreenHouse);

        //CHANGING ROOT NAME
        xmlParser.renameRootElement("flower",
                                    xmlPath,
                                    xsdPath,
                                    "src/xmlFiles/greenhouse(newRoot).xml");
    }

}
