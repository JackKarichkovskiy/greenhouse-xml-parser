/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
     */
    public static void main(String[] args) throws Exception {
        XMLParserFactory factory = XMLParserFactory.getInstance(XMLParserFactory.XMLParserType.DOM);
        XMLParser xmlParser = factory.getXMLParser();
        
        InputStream is = new FileInputStream("src/xmlFiles/greenhouse.xml");
        GreenHouse parsedGreenHouse = xmlParser.parse(is);
        System.out.println(parsedGreenHouse);
    }
    
}
