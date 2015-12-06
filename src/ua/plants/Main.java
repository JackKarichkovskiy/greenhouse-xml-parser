/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import ua.plants.generated.GreenHouse;
import ua.plants.parser.XMLParser;
import ua.plants.parser.XMLParserFactory;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        XMLParserFactory factory = XMLParserFactory.getInstance(XMLParserFactory.XMLParserType.DOM);
        XMLParser xmlParser = factory.getXMLParser();
        
        //PARSING
        GreenHouse parsedGreenHouse = xmlParser.parse(
                new FileInputStream("src/xmlFiles/greenhouse.xml"),
                new FileInputStream("src/xmlFiles/greenhouse.xsd"));
        System.out.println(parsedGreenHouse);
        
        //CHANGING ROOT NAME
        xmlParser.renameRootElement("flower",
                new FileInputStream("src/xmlFiles/greenhouse.xml"),
                new FileInputStream("src/xmlFiles/greenhouse.xsd"),
                new FileOutputStream("src/xmlFiles/greenhouse(newRoot).xml"));
    }
    
}
