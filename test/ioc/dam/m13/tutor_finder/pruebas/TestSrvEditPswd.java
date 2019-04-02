package ioc.dam.m13.tutor_finder.pruebas;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.util.Scanner;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvEditPswd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TestSrvEditPswd prg = new TestSrvEditPswd();
        prg.inici();

    }
    
    public void inici(){
        TFClient client = new TFClientImple();
        UserDTO userDTO = null;
        
        System.out.println("---Edit User Password--- ");
        System.out.println("User: ");
        Scanner scanner = new Scanner(System.in);
        String user = scanner.nextLine();
        System.out.println("Password: ");
        String pwd = scanner.nextLine();
        
        boolean ret = client.editUserPswd(user, pwd);
        
        if (ret) {
            userDTO = client.userData(user);
            
            System.out.println(userDTO.toString());
            
        } else {
            
            System.out.println("No s'ha pugut canviar la contrasenya");
            
        }
    }
}
