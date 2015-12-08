/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public abstract class XMLParserFactory {

    public abstract XMLParser getXMLParser();

    public enum XMLParserType {

        DOM("ua.plants.parser.dom.DOMParserFactory"),
        SAX("ua.plants.parser.sax.SAXParserFactory"),
        STAX("ua.plants.parser.stax.StAXParserFactory");

        private final String clazz;

        private XMLParserType(String clazz) {
            this.clazz = clazz;
        }
    }

    public static XMLParserFactory getInstance(XMLParserType type) {
        switch (type) {
            case DOM:
            case SAX:
            case STAX:
                try {
                    Class clazz = Class.forName(type.clazz);
                    return (XMLParserFactory) clazz.newInstance();
                } catch (Exception e) {
                    System.out.println(e.toString());
                    return null;
                }
            default:
                return null;
        }
    }
}
