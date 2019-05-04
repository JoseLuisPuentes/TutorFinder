package ioc.dam.m13.tutor_finder.pruebas;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import java.util.Scanner;

/**
 * Prova de crear un usuari nou
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvCreateAd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestSrvCreateAd prg = new TestSrvCreateAd();
        prg.inici();

    }
    
    public void inici(){
        TFClient client = new TFClientImple();
        
        
        System.out.println("---Create Ad--- ");
        System.out.println("User: ");
        Scanner scanner = new Scanner(System.in);
        int userId = Integer.parseInt(scanner.nextLine());;
        System.out.println("Tittle: ");
        String tittle = scanner.nextLine();
        System.out.println("Description: ");
        String description = scanner.nextLine();
        System.out.println("Type: ");
        int type = Integer.parseInt(scanner.nextLine());
        System.out.println("Price: ");
        int price= Integer.parseInt(scanner.nextLine());;
        
        boolean ret = client.createAd(userId, tittle, description, type, price);
        
        if (ret) {
            System.out.println("S'ha creat un nou anunci");
            
        } else {            
            System.out.println("No s'ha creat un nou usuari");
            
        }    
    }

}
