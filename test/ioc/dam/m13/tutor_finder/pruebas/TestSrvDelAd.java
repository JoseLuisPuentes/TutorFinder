package ioc.dam.m13.tutor_finder.pruebas;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import java.util.Scanner;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvDelAd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TestSrvDelAd prg = new TestSrvDelAd();
        prg.inici();

    }
    
    public void inici(){
        
        TFClient client = new TFClientImple();
        
        System.out.println("------ DelAd ------");
        System.out.println("Id of Ad to delete: ");
        Scanner scanner = new Scanner(System.in);
        int adId = Integer.parseInt(scanner.nextLine());
        boolean ret = client.delAd(adId);
        
        if (ret) {
            System.out.println("L'anunci s'ha esborrat correctament");
            
        } else {
            System.out.println("L'anunci NO s'ha esborrat correctament");
        }
    }

}
