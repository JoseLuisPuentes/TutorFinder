package ioc.dam.m13.tutor_finder.pruebas;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import ioc.dam.m13.tutor_finder.dtos.AdDTO;
import java.util.ArrayList;

/**
 * Prova les quatre maneres de llistar anuncis
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvListAds {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        TestSrvListAds prg = new TestSrvListAds();
        prg.inici();

    }
    
    public void inici(){
        
        TFClient client = new TFClientImple();
        
        
        System.out.println("----- ListAds -----");
        ArrayList<AdDTO> ads = client.listAds();
        
        for (AdDTO ad : ads) {
            System.out.println(ad.toString());
            System.out.println("--------------------");
        }
        
        System.out.println("----- ListAdsByRole -----");
        ads = client.listAdsByRole(2);
        
        for (AdDTO ad : ads) {
            System.out.println(ad.toString());
            System.out.println("--------------------");
        }
        System.out.println("----- ListAdsByType -----");
        ads = client.listAdsByType(5);
        
        for (AdDTO ad : ads) {
            System.out.println(ad.toString());
            System.out.println("--------------------");
        }
        
        System.out.println("----- ListAdsByUser -----");
        ads = client.listAdsByUser(2);
        
        for (AdDTO ad : ads) {
            System.out.println(ad.toString());
            System.out.println("--------------------");
        }
        
    }

}
