package ioc.dam.m13.tutor_finder.client;

import java.util.Scanner;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TestSrv1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        TestSrv1 prg = new TestSrv1();
        prg.inici();

    }
    
    public void inici(){
        
        TFClient client = new TFClientImple();
        
        System.out.println("---Login--- ");
        System.out.println("User: ");
        Scanner scanner = new Scanner(System.in);
        String user = scanner.nextLine();
        System.out.println("Password: ");
        String pwd = scanner.nextLine();
        
        boolean ret = client.login(user, pwd);
        
        System.out.println("Retorn: " + ret);
        
    
    }

}
