package ioc.dam.m13.tutor_finder.pruebas;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvDelUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TestSrvDelUser prg = new TestSrvDelUser();
        prg.inici();

    }
    
    public void inici(){
        
        TFClient client = new TFClientImple();
        ArrayList<UserDTO> users = client.listUsers();
        
        for (Object user : users) {
            System.out.println(user.toString());
        }
        System.out.println("----Esborrar usuari----");
        System.out.println("Nom Usuari: ");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();
        
        boolean ret = client.delUser(userName);
        
        if (ret) {
            System.out.println("S'ha esborrat satisfactoriament");
        } else {
            System.out.println("No s'ha pugut esborrar");
        }
    
    }

}
