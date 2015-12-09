/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser;

/**
 * Abstract factory that produces xml parsers.
 *
 * @author Karichkovskiy Yevhen
 */
public abstract class XMLParserFactory {

    /**
     * Method returns produced xml parser.
     *
     * @return xml parser
     */
    public abstract XMLParser getXMLParser();

    /**
     * Enumeration of xml parser realizations.
     */
    public enum XMLParserType {

        DOM("ua.plants.parser.dom.DOMParserFactory"),
        SAX("ua.plants.parser.sax.SAXParserFactory"),
        STAX("ua.plants.parser.stax.StAXParserFactory");

        /**
         * Field that contains class declaration of concrete xml parser.
         */
        private final String clazz;

        private XMLParserType(String clazz) {
            this.clazz = clazz;
        }
    }

    /**
     * Method creates concrete xml factory based on xml parser type.
     * @param type - type of xml parser
     * @return xml parser factory
     */
    public static XMLParserFactory getInstance(XMLParserType type) {
        switch (type) {
            case DOM:
            case SAX:
            case STAX:
                try {
                    Class clazz = Class.forName(type.clazz);
                    return (XMLParserFactory) clazz.newInstance();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            default:
                return null;
        }
    }
}
