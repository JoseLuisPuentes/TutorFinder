
import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        
        String typeName = client.getAdTypeById(5);
        
        System.out.println("TypeId 5 is : " + typeName );
        
        
        System.out.println("----- getTypesByName -----");
        
        int typeId = client.getAdTypeByName("Castellà");
        System.out.println("TypeName Catellà is: " + typeId);
        
    
    }

}