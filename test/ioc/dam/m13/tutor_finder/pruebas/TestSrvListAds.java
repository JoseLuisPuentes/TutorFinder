package ioc.dam.m13.tutor_finder.pruebas;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import ioc.dam.m13.tutor_finder.dtos.AdDTO;
import java.util.ArrayList;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvListAds {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TestSrvListAds prg = new TestSrvListAds();
        prg.inici();

    }
    
    public void inici(){
        
        TFClient client = new TFClientImple();
        
        
        
        ArrayList<AdDTO> ads = client.listAds();
        
        for (AdDTO ad : ads) {
            System.out.println(ad.toString());
            System.out.println("--------------------");
        }
    }

}
