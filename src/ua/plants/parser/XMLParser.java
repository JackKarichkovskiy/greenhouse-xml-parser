/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser;

import java.util.Comparator;
import ua.plants.generated.GreenHouse;
import ua.plants.generated.GreenHouse.Plants.Plant;

/**
 *
 * @author Karichkovskiy Yevhen
 * @param <T> - type of parsed element
 */
public interface XMLParser<T extends GreenHouse> {

    T parse(String xmlFile, String xsdFile) throws Exception;
    
    void renameRootElement(String newName, String xmlInFile, String xsdFile, String xmlOutFile) throws Exception;
    
    void sortPlants(String xmlInFile, String xsdFile, String xmlOutFile, Comparator<Plant> comparator) throws Exception;
}
