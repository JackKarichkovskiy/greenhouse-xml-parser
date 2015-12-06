/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser;

import java.io.InputStream;
import java.io.OutputStream;
import ua.plants.generated.GreenHouse;
import ua.plants.parser.exceptions.NotParsedDocumentException;

/**
 *
 * @author Karichkovskiy Yevhen
 * @param <T> - type of parsed element
 */
public interface XMLParser<T extends GreenHouse> {

    T parse(InputStream xmlis, InputStream xsdis) throws Exception;
    
    void renameRootElement(String newName, InputStream xmlis, InputStream xsdis, OutputStream xmlos) throws Exception;
}
