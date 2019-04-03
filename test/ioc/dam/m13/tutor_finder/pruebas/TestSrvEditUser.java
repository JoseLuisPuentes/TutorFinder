package ioc.dam.m13.tutor_finder.pruebas;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.util.Scanner;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvEditUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TestSrvEditUser prg = new TestSrvEditUser();
        prg.inici();

    }
    
    public void inici(){
        
        TFClient client = new TFClientImple();
        UserDTO userDTO = null;
        boolean ret = false;
        
        
        System.out.println("---Edit User --- ");
        System.out.println("User to edit: ");
        Scanner scanner = new Scanner(System.in);
        String user = scanner.nextLine();
        userDTO = client.userData(user);
        
        
        if (userDTO.getUserId() == 0) {
            System.out.println("L'usuari no existeix!!");
        } else {
            
            //Mostrem les dades de l'usuari que hi ha a la BBDD
            System.out.println(userDTO.toString());
            //Demanem les dades a canviar
            System.out.println("Name: ");
            String name = scanner.nextLine();
            System.out.println("Mail: ");
            String mail = scanner.nextLine();
            System.out.println("Role: ");
            String role = scanner.nextLine();


            ret = client.editUser(userDTO.getUserId(), name, mail, role);

            if (ret) {
                userDTO = client.userData(name);

                System.out.println(userDTO.toString());

            } else {

                System.out.println("No s'ha pugut actualitzar l'usuari");

            }
        }
        
    }

}
