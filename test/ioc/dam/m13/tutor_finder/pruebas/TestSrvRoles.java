package ioc.dam.m13.tutor_finder.pruebas;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import java.util.HashMap;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrvRoles {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        TestSrvRoles prg = new TestSrvRoles();
        prg.inici();

    }
    
    public void inici(){
        
        TFClient client = new TFClientImple();
        System.out.println("-----Roles-----");
        HashMap<Integer,String> roles = client.getUserRoles();
        System.out.println(roles.toString());
        
        System.out.println("-----Roles by id-----");
        System.out.println(client.getUserRoleName(1));
        System.out.println(client.getUserRoleName(2));
        System.out.println(client.getUserRoleName(3));
        
        System.out.println("-----Roles by name-----");
        System.out.println(client.getUserRoleId("admin"));
        System.out.println(client.getUserRoleId("tutor"));
        System.out.println(client.getUserRoleId("student"));
    
    }

}
