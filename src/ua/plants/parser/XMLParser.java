/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser;

import java.io.InputStream;

/**
 *
 * @author Karichkovskiy Yevhen
 * @param <T> - type of parsed element
 */
public interface XMLParser<T> {

    T parse(InputStream is) throws Exception;
}
