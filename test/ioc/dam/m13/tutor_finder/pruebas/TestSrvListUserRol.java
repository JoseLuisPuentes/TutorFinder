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
public class TestSrvListUserRol {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TestSrvListUserRol prg = new TestSrvListUserRol();
        prg.inici();

    }
    
    public void inici(){
        TFClient client = new TFClientImple();
        
        System.out.println("---List users role--- ");
        System.out.println("Role: ");
        Scanner scanner = new Scanner(System.in);
        String roles = scanner.nextLine();
        
        ArrayList<UserDTO> users = client.listUsers(roles);
        
        for (UserDTO user : users) {
            System.out.println(user.toString());
            System.out.println("--------------------");
        }
    }

}
