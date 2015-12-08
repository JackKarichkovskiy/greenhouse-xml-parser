/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser.stax;

import ua.plants.parser.XMLParser;
import ua.plants.parser.XMLParserFactory;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class StAXParserFactory extends XMLParserFactory {

    @Override
    public XMLParser getXMLParser() {
        return new StAXParser();
    }

}
