package ioc.dam.m13.tutor_finder.server;

import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.util.Scanner;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvDB1 {
    
    public static void main(String[] args) {
        
        UserDAO dao = (UserDAO) TFFactory.getInstance("USER");
        
        
        System.out.println("---Login--- ");
        System.out.println("User: ");
        Scanner scanner = new Scanner(System.in);
        String user = scanner.nextLine();
        //System.out.println("Password: ");
        //String pwd = scanner.nextLine();
        
        UserDTO dto = dao.userData(user);
        
        System.out.println("Retorn: " + dto.toString());
    }

}
