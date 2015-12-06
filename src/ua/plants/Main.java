/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants;

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
    public static void main(String[] args) {
        XMLParserFactory factory = XMLParserFactory.getInstance(XMLParserFactory.XMLParserType.DOM);
        XMLParser xmlParser = factory.getXMLParser();
        System.out.println(xmlParser);
    }
    
}
