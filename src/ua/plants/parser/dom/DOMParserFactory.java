/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser.dom;

import ua.plants.parser.XMLParserFactory;
import ua.plants.parser.XMLParser;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class DOMParserFactory extends XMLParserFactory{

    @Override
    public XMLParser getXMLParser() {
        return new DOMParser();
    }
    
}
