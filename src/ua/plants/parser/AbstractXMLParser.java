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
 */
public abstract class AbstractXMLParser implements XMLParser<GreenHouse> {

    protected GreenHouse greenHouse;
    
    @Override
    public GreenHouse parse(InputStream is) throws Exception {
        //validate(is);
        
        return parseFile(is);
    }

    protected abstract GreenHouse parseFile(InputStream is) throws Exception;

    private void validate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
