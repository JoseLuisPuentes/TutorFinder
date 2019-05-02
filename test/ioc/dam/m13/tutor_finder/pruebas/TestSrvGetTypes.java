package ioc.dam.m13.tutor_finder.pruebas;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import java.util.HashMap;
import java.util.Map;

/**
 * Prova les atres maneres de llistar els tipus d'anuncis
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvGetTypes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        TestSrvGetTypes prg = new TestSrvGetTypes();
        prg.inici();

    }
    
    public void inici(){
        
        TFClient client = new TFClientImple();
        
        System.out.println("----- getTypes -----");
        HashMap<Integer, String> types = client.getAdTypes();
        
        for (Map.Entry<Integer, String> entry : types.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Id: " + key + " AdType: " + value);
        }
        
        System.out.println("----- getTypesById -----");
        
        String typeName = client.getAdTypeById(7);
        
        System.out.println("TypeId 17 is : " + typeName );
        
        
        System.out.println("----- getTypesByName -----");
        
        int typeId = client.getAdTypeByName("Electricitat/Mecànica");
        System.out.println("TypeName Electricitat/Mecànica is: " + typeId);
        
    
    }

}
