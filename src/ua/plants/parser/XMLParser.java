/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser;

import java.io.InputStream;
import ua.plants.generated.GreenHouse;

/**
 *
 * @author Karichkovskiy Yevhen
 * @param <T> - type of parsed element
 */
public interface XMLParser<T extends GreenHouse> {

    T parse(InputStream xmlis, InputStream xsdis) throws Exception;
}
