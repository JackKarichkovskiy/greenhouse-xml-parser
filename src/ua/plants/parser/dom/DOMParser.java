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
import org.w3c.dom.NodeList;
import ua.plants.generated.GreenHouse;
import ua.plants.generated.GreenHouse.Plants;
import ua.plants.generated.GreenHouse.Plants.Plant;
import ua.plants.generated.GreenHouse.Plants.Plant.GrowingTips;
import ua.plants.generated.GreenHouse.Plants.Plant.VisualParams;
import ua.plants.generated.ObjectFactory;
import ua.plants.parser.AbstractXMLParser;

/**
 * DOM realization of xml parser.
 *
 * @author Karichkovskiy Yevhen
 */
class DOMParser extends AbstractXMLParser {

    @Override
    protected GreenHouse parseFile(InputStream xmlis) throws Exception {
        greenHouse = ObjectFactory.createGreenHouse();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.parse(xmlis);

        Element root = document.getDocumentElement();

        Element plants = (Element) root.getElementsByTagName(GreenHouseTags.PLANTS).item(0);

        greenHouse.setPlants(parsePlants(plants));

        return greenHouse;
    }

    /**
     * Parses plants tag in xml.
     *
     * @param plants - plants element of xml
     * @return parsed object
     */
    private Plants parsePlants(Element plants) {
        Plants parsedPlants = ObjectFactory.createGreenHousePlants();
        NodeList plantList = plants.getElementsByTagName(GreenHouseTags.PLANT);
        for (int i = 0; i < plantList.getLength(); i++) {
            Element plant = (Element) plantList.item(i);
            parsedPlants.getPlant().add(parsePlant(plant));
        }
        return parsedPlants;
    }

    /**
     * Parses plant tag in xml.
     *
     * @param plant - plant element of xml
     * @return parsed object
     */
    private Plant parsePlant(Element plant) {
        Plant parsedPlant = ObjectFactory.createGreenHousePlantsPlant();
        parsedPlant.setName(plant.getElementsByTagName(GreenHouseTags.NAME).item(0).getTextContent());
        parsedPlant.setSoil(plant.getElementsByTagName(GreenHouseTags.SOIL).item(0).getTextContent());
        parsedPlant.setOrigin(plant.getElementsByTagName(GreenHouseTags.ORIGIN).item(0).getTextContent());
        parsedPlant.setVisualParams(parseVisualParams((Element) plant.getElementsByTagName(GreenHouseTags.VISUAL_PARAMS).item(0)));
        parsedPlant.setGrowingTips(parseGrowingTips((Element) plant.getElementsByTagName(GreenHouseTags.GROWING_TIPS).item(0)));
        parsedPlant.setMultiplying(plant.getElementsByTagName(GreenHouseTags.MULTIPLYING).item(0).getTextContent());
        parsedPlant.setId(Integer.parseInt(plant.getAttribute(GreenHouseTags.ID)));
        return parsedPlant;
    }

    /**
     * Parses visual parameters tag in xml.
     *
     * @param params - visual parameters element of xml
     * @return parsed object
     */
    private VisualParams parseVisualParams(Element params) {
        VisualParams parsedParams = ObjectFactory.createGreenHousePlantsPlantVisualParams();
        parsedParams.setStalkColor(params.getElementsByTagName(GreenHouseTags.STALK_COLOR).item(0).getTextContent());
        parsedParams.setLeafColor(params.getElementsByTagName(GreenHouseTags.LEAF_COLOR).item(0).getTextContent());
        parsedParams.setAverageSize(Float.parseFloat(params.getElementsByTagName(GreenHouseTags.AVERAGE_SIZE).item(0).getTextContent()));
        return parsedParams;
    }

    /**
     * Parses growing tips tag in xml.
     *
     * @param tips - growing tips element of xml
     * @return parsed object
     */
    private GrowingTips parseGrowingTips(Element tips) {
        GrowingTips parsedTips = ObjectFactory.createGreenHousePlantsPlantGrowingTips();
        parsedTips.setTemparature(Byte.parseByte(tips.getElementsByTagName(GreenHouseTags.TEMPERATURE).item(0).getTextContent()));
        parsedTips.setLighting(Boolean.parseBoolean(tips.getElementsByTagName(GreenHouseTags.LIGHTING).item(0).getTextContent()));
        parsedTips.setWatering(Integer.parseInt(tips.getElementsByTagName(GreenHouseTags.WATERING).item(0).getTextContent()));
        return parsedTips;
    }
}
