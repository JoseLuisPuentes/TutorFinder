package ioc.dam.m13.tutor_finder.server;

import java.util.ResourceBundle;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TFFactory {
    
    public static Object getInstance(String objName) {
        try {
            //Llegim el factory.properties
            ResourceBundle rb = ResourceBundle.getBundle("factory");
            //Agafem la classe
            String className = rb.getString(objName);
            //Retornem una instancia de la classe que hem agafat
            Object ret = Class.forName(className).newInstance();
            
            return ret;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
            
        }
    }

}
