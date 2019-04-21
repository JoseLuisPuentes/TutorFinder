package ioc.dam.m13.tutor_finder.pruebas;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import ioc.dam.m13.tutor_finder.server.AdDAO;
import java.util.Scanner;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvEditAD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TestSrvEditAD prg = new TestSrvEditAD();
        prg.inici();

    }
    
    public void inici(){
        
        TFClient client = new TFClientImple();
        
        //AdDAO ad = new AdDAO();
        
        System.out.println("------ Edit Ad -------");
        System.out.println("Ad Id: ");
        Scanner scanner = new Scanner(System.in);
        int adId = Integer.parseInt(scanner.nextLine());
        //ad = client.listAdById(adId);
        System.out.println("Tittle : ");
        String tittle = scanner.nextLine();
                
        System.out.println("Description: ");
        String description = scanner.nextLine();
        
        System.out.println("Type Ad: ");
        int adTypeId = Integer.parseInt(scanner.nextLine());
        
        System.out.println("Price: ");
        int price = Integer.parseInt(scanner.nextLine());
        
        boolean ret = client.editAd(adId, tittle, description, adTypeId, price);
        
        if (ret) {
            System.out.println("L'anunci s'ha modificat correctament");
        } else {
            System.out.println("L'anunci NO s'ha modificat correctament");
        }
        
    }

}
