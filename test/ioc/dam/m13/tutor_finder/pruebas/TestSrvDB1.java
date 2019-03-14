package ioc.dam.m13.tutor_finder.pruebas;



import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import ioc.dam.m13.tutor_finder.server.TFFactory;
import ioc.dam.m13.tutor_finder.server.UserDAO;
import java.util.Scanner;

/**
 * Test per comprovar l'accés a les deades de l'usuari des del servidor 
 * al servidor de BBDD.
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
