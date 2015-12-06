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
 *
 * @author Karichkovskiy Yevhen
 */
public class SAXParser extends AbstractXMLParser {

    private String temp;

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
                case PLANT:
                    Plant plant = ObjectFactory.createGreenHousePlantsPlant();
                    plant.setId(Integer.parseInt(atts.getValue(ID)));
                    greenHouse.getPlants().getPlant().add(plant);
                    break;
                case VISUAL_PARAMS:
                    plants.
                            get(plants.size()-1)
                            .setVisualParams(
                            ObjectFactory.createGreenHousePlantsPlantVisualParams());
                    break;
                case GROWING_TIPS:
                    plants.get(plants.size()-1).setGrowingTips(
                            ObjectFactory.createGreenHousePlantsPlantGrowingTips());
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            List<Plant> plants = greenHouse.getPlants().getPlant();
            Plant lastPlant = plants.get(plants.size() - 1);
            switch(localName){
                case NAME:
                    lastPlant.setName(temp);
                    break;
                case SOIL:
                    lastPlant.setSoil(temp);
                    break;
                case ORIGIN:
                    lastPlant.setOrigin(temp);
                    break;
                case STALK_COLOR:
                    lastPlant.getVisualParams().setStalkColor(temp);
                    break;
                case LEAF_COLOR:
                    lastPlant.getVisualParams().setLeafColor(temp);
                    break;
                case AVERAGE_SIZE:
                    lastPlant.getVisualParams().setAverageSize(Float.parseFloat(temp));
                    break;
                case TEMPERATURE:
                    lastPlant.getGrowingTips().setTemparature(Byte.parseByte(temp));
                    break;
                case LIGHTING:
                    lastPlant.getGrowingTips().setLighting(Boolean.parseBoolean(temp));
                    break;
                case WATERING:
                    lastPlant.getGrowingTips().setWatering(Integer.parseInt(temp));
                    break;
                case MULTIPLYING:
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
