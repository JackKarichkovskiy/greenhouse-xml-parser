/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser.sax;

import ua.plants.parser.XMLParser;
import ua.plants.parser.XMLParserFactory;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class SAXParserFactory extends XMLParserFactory{

    @Override
    public XMLParser getXMLParser() {
        return new SAXParser();
    }
    
}
