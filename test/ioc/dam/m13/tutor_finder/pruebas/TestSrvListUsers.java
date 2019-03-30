package ioc.dam.m13.tutor_finder.pruebas;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.util.ArrayList;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvListUsers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TestSrvListUsers prg = new TestSrvListUsers();
        prg.inici();

    }
    
    public void inici(){
        
        TFClient client = new TFClientImple();
        
        
        
        ArrayList<UserDTO> users = client.listUsers();
        
        for (UserDTO user : users) {
            System.out.println(user.toString());
            System.out.println("--------------------");
        }
    }

}
