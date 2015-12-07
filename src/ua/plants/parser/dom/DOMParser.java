/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser.dom;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ua.plants.generated.GreenHouse;
import ua.plants.generated.GreenHouse.Plants;
import ua.plants.generated.GreenHouse.Plants.Plant;
import ua.plants.generated.GreenHouse.Plants.Plant.GrowingTips;
import ua.plants.generated.GreenHouse.Plants.Plant.VisualParams;
import ua.plants.generated.ObjectFactory;
import ua.plants.parser.AbstractXMLParser;

/**
 *
 * @author Karichkovskiy Yevhen
 */
class DOMParser extends AbstractXMLParser {

    @Override
    protected GreenHouse parseFile(InputStream xmlis) throws Exception {
        greenHouse = ObjectFactory.createGreenHouse();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = this.doc = db.parse(xmlis);

        Element root = document.getDocumentElement();

        Element plants = (Element) root.getElementsByTagName(PLANTS).item(0);

        greenHouse.setPlants(parsePlants(plants));

        return greenHouse;
    }

    private Plants parsePlants(Element plants) {
        Plants parsedPlants = ObjectFactory.createGreenHousePlants();
        NodeList plantList = plants.getElementsByTagName(PLANT);
        for (int i = 0; i < plantList.getLength(); i++) {
            Element plant = (Element) plantList.item(i);
            parsedPlants.getPlant().add(parsePlant(plant));
        }
        return parsedPlants;
    }

    private Plant parsePlant(Element plant) {
        Plant parsedPlant = ObjectFactory.createGreenHousePlantsPlant();
        parsedPlant.setName(plant.getElementsByTagName(NAME).item(0).getTextContent());
        parsedPlant.setSoil(plant.getElementsByTagName(SOIL).item(0).getTextContent());
        parsedPlant.setOrigin(plant.getElementsByTagName(ORIGIN).item(0).getTextContent());
        parsedPlant.setVisualParams(parseVisualParams((Element) plant.getElementsByTagName(VISUAL_PARAMS).item(0)));
        parsedPlant.setGrowingTips(parseGrowingTips((Element) plant.getElementsByTagName(GROWING_TIPS).item(0)));
        parsedPlant.setMultiplying(plant.getElementsByTagName(MULTIPLYING).item(0).getTextContent());
        parsedPlant.setId(Integer.parseInt(plant.getAttribute(ID)));
        return parsedPlant;
    }

    private VisualParams parseVisualParams(Element params) {
        VisualParams parsedParams = ObjectFactory.createGreenHousePlantsPlantVisualParams();
        parsedParams.setStalkColor(params.getElementsByTagName(STALK_COLOR).item(0).getTextContent());
        parsedParams.setLeafColor(params.getElementsByTagName(LEAF_COLOR).item(0).getTextContent());
        parsedParams.setAverageSize(Float.parseFloat(params.getElementsByTagName(AVERAGE_SIZE).item(0).getTextContent()));
        return parsedParams;
    }

    private GrowingTips parseGrowingTips(Element tips) {
        GrowingTips parsedTips = ObjectFactory.createGreenHousePlantsPlantGrowingTips();
        parsedTips.setTemparature(Byte.parseByte(tips.getElementsByTagName(TEMPERATURE).item(0).getTextContent()));
        parsedTips.setLighting(Boolean.parseBoolean(tips.getElementsByTagName(LIGHTING).item(0).getTextContent()));
        parsedTips.setWatering(Integer.parseInt(tips.getElementsByTagName(WATERING).item(0).getTextContent()));
        return parsedTips;
    }
}
