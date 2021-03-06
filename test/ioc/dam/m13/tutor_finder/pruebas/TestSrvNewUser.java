package ioc.dam.m13.tutor_finder.pruebas;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.util.Scanner;

/**
 * Prova de crear un usuari nou
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvNewUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        TestSrvNewUser prg = new TestSrvNewUser();
        prg.inici();

    }
    
    public void inici(){
        
        TFClient client = new TFClientImple();
        UserDTO userDTO = null;
        
        System.out.println("---Login--- ");
        System.out.println("User: ");
        Scanner scanner = new Scanner(System.in);
        String user = scanner.nextLine();
        System.out.println("Password: ");
        String pwd = scanner.nextLine();
        
        boolean ret = client.login(user, pwd);
        
        if (ret) {
            userDTO = client.userData(user);
            
            System.out.println(userDTO.toString());
            
        } else {
            
            
            System.out.println("Aquest ususari no existeix!!");
            System.out.println("Crei un nou usuari..."
                    + "\nNom: ");
            String nom = scanner.nextLine();
            System.out.println("Email: ");
            String email = scanner.nextLine();
            System.out.println("Password: ");
            String pswd = scanner.nextLine();
            System.out.println("Rol: ");
            String role = scanner.nextLine();
            
            boolean resposta = client.newUser(nom, email, pswd, role);
            
            if (resposta) {
                System.out.println("S'ha creat un nou usuari");
            } else {
                System.out.println("No s'ha creat un nou usuari");
            }
            
            
        }
    }

}
