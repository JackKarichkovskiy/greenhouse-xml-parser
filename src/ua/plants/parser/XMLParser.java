/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.plants.parser;

import ua.plants.generated.GreenHouse;

/**
 * Interface represents XML parser and its functionality.
 *
 * @author Karichkovskiy Yevhen
 */
public interface XMLParser {

    /**
     * Method validates mentioned xml file by xsd file and then parses and
     * returns obtained object.
     *
     * @param xmlFile - file path of xml
     * @param xsdFile - file path of xsd (for validation)
     * @return parsed object
     * @throws Exception - if parsing was failed or some IO problems
     */
    GreenHouse parse(String xmlFile, String xsdFile) throws Exception;

    /**
     * Method firstly validates existed xml file by xsd file.<br>
     * And then renames the root element and writes the result to the output
     * file.
     *
     * @param newName - new name of the root
     * @param xmlInFile - existed xml path that needs to be changed
     * @param xsdFile - existed xsd path connected with xml
     * @param xmlOutFile - output file path where it needs to be saved the
     * result
     * @throws Exception - if parsing was failed or some IO problems
     */
    void renameRootElement(String newName, String xmlInFile, String xsdFile, String xmlOutFile) throws Exception;

}
