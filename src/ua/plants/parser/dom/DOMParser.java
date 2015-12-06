/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser.dom;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
    protected GreenHouse parseFile(InputStream is) throws Exception {
        greenHouse = ObjectFactory.createGreenHouse();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.parse(is);
        Element root = document.getDocumentElement();

        Element plants = (Element) root.getElementsByTagName("plants").item(0);

        greenHouse.setPlants(parsePlants(plants));

        return greenHouse;
    }

    private Plants parsePlants(Element plants) {
        Plants parsedPlants = ObjectFactory.createGreenHousePlants();
        NodeList plantList = plants.getElementsByTagName("plant");
        for (int i = 0; i < plantList.getLength(); i++) {
            Element plant = (Element) plantList.item(i);
            parsedPlants.getPlant().add(parsePlant(plant));
        }
        return parsedPlants;
    }

    private Plant parsePlant(Element plant) {
        Plant parsedPlant = ObjectFactory.createGreenHousePlantsPlant();
        parsedPlant.setName(plant.getElementsByTagName("name").item(0).getTextContent());
        parsedPlant.setSoil(plant.getElementsByTagName("soil").item(0).getTextContent());
        parsedPlant.setOrigin(plant.getElementsByTagName("origin").item(0).getTextContent());
        parsedPlant.setVisualParams(parseVisualParams((Element) plant.getElementsByTagName("visualParams").item(0)));
        parsedPlant.setGrowingTips(parseGrowingTips((Element) plant.getElementsByTagName("growingTips").item(0)));
        parsedPlant.setMultiplying(plant.getElementsByTagName("multiplying").item(0).getTextContent());
        parsedPlant.setId(Integer.parseInt(plant.getAttribute("id")));
        return parsedPlant;
    }

    private VisualParams parseVisualParams(Element params) {
        VisualParams parsedParams = ObjectFactory.createGreenHousePlantsPlantVisualParams();
        parsedParams.setStalkColor(params.getElementsByTagName(STALK_COLOR).item(0).getTextContent());
        parsedParams.setLeafColor(params.getElementsByTagName("leafColor").item(0).getTextContent());
        parsedParams.setAverageSize(Float.parseFloat(params.getElementsByTagName("averageSize").item(0).getTextContent()));
        return parsedParams;
    }
    private static final String STALK_COLOR = "stalkColor";

    private GrowingTips parseGrowingTips(Element tips) {
        GrowingTips parsedTips = ObjectFactory.createGreenHousePlantsPlantGrowingTips();
        parsedTips.setTemparature(Byte.parseByte(tips.getElementsByTagName("temparature").item(0).getTextContent()));
        parsedTips.setLighting(Boolean.parseBoolean(tips.getElementsByTagName("temparature").item(0).getTextContent()));
        parsedTips.setWatering(Integer.parseInt(tips.getElementsByTagName("temparature").item(0).getTextContent()));
        return parsedTips;
    }
}
