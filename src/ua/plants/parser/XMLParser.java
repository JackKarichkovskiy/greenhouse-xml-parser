/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser;

import java.io.InputStream;
import java.io.OutputStream;
import ua.plants.generated.GreenHouse;

/**
 *
 * @author Karichkovskiy Yevhen
 * @param <T> - type of parsed element
 */
public interface XMLParser<T extends GreenHouse> {

    T parse(String xmlFile, String xsdFile) throws Exception;
    
    void renameRootElement(String newName, String xmlInFile, String xsdFile, String xmlOutFile) throws Exception;
}
