/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser.sax;

import java.io.InputStream;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import ua.plants.generated.GreenHouse;
import ua.plants.generated.GreenHouse.Plants.Plant;
import ua.plants.generated.ObjectFactory;
import ua.plants.parser.AbstractXMLParser;

/**
 * SAX realization of xml parser.
 *
 * @author Karichkovskiy Yevhen
 */
public class SAXParser extends AbstractXMLParser {

    /**
     * Field that stores temp string of tag content.
     */
    private String temp;

    /**
     * Realization of SAX parsing.
     */
    class SaxHandler implements ContentHandler {

        @Override
        public void setDocumentLocator(Locator locator) {

        }

        @Override
        public void startDocument() throws SAXException {
            greenHouse.setPlants(ObjectFactory.createGreenHousePlants());
        }

        @Override
        public void endDocument() throws SAXException {

        }

        @Override
        public void startPrefixMapping(String prefix, String uri) throws SAXException {

        }

        @Override
        public void endPrefixMapping(String prefix) throws SAXException {

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            List<Plant> plants = greenHouse.getPlants().getPlant();
            switch (localName) {
                case GreenHouseTags.PLANT:
                    Plant plant = ObjectFactory.createGreenHousePlantsPlant();
                    plant.setId(Integer.parseInt(atts.getValue(GreenHouseTags.ID)));
                    greenHouse.getPlants().getPlant().add(plant);
                    break;
                case GreenHouseTags.VISUAL_PARAMS:
                    plants.get(plants.size() - 1)
                            .setVisualParams(ObjectFactory.createGreenHousePlantsPlantVisualParams());
                    break;
                case GreenHouseTags.GROWING_TIPS:
                    plants.get(plants.size() - 1).setGrowingTips(
                            ObjectFactory.createGreenHousePlantsPlantGrowingTips());
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            List<Plant> plants = greenHouse.getPlants().getPlant();
            Plant lastPlant = plants.get(plants.size() - 1);
            switch (localName) {
                case GreenHouseTags.NAME:
                    lastPlant.setName(temp);
                    break;
                case GreenHouseTags.SOIL:
                    lastPlant.setSoil(temp);
                    break;
                case GreenHouseTags.ORIGIN:
                    lastPlant.setOrigin(temp);
                    break;
                case GreenHouseTags.STALK_COLOR:
                    lastPlant.getVisualParams().setStalkColor(temp);
                    break;
                case GreenHouseTags.LEAF_COLOR:
                    lastPlant.getVisualParams().setLeafColor(temp);
                    break;
                case GreenHouseTags.AVERAGE_SIZE:
                    lastPlant.getVisualParams().setAverageSize(Float.parseFloat(temp));
                    break;
                case GreenHouseTags.TEMPERATURE:
                    lastPlant.getGrowingTips().setTemparature(Byte.parseByte(temp));
                    break;
                case GreenHouseTags.LIGHTING:
                    lastPlant.getGrowingTips().setLighting(Boolean.parseBoolean(temp));
                    break;
                case GreenHouseTags.WATERING:
                    lastPlant.getGrowingTips().setWatering(Integer.parseInt(temp));
                    break;
                case GreenHouseTags.MULTIPLYING:
                    lastPlant.setMultiplying(temp);
                    break;
            }
            temp = "";
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            temp = new String(ch, start, length);
        }

        @Override
        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void processingInstruction(String target, String data) throws SAXException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void skippedEntity(String name) throws SAXException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    @Override
    protected GreenHouse parseFile(InputStream xmlis) throws Exception {
        greenHouse = ObjectFactory.createGreenHouse();

        XMLReader reader = XMLReaderFactory.createXMLReader();
        SaxHandler contentHandler = new SaxHandler();
        reader.setContentHandler(contentHandler);
        reader.parse(new InputSource(xmlis));

        return greenHouse;
    }

}
