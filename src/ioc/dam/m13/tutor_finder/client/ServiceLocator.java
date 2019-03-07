package ioc.dam.m13.tutor_finder.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;

import ioc.dam.m13.tutor_finder.server.TFServer;
import ioc.dam.m13.tutor_finder.server.UserDTO;
/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class ServiceLocator {
    
    public static boolean login(String userName, String pswd) {
        
        boolean ret = false;
        
        // Dades de configuració del servidor
        String serverIp = null;
        int port;
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Agafem les dades de conexió al server
            // del arxiu de configuració "config.properties"
            ResourceBundle rb = ResourceBundle.getBundle("config");
            serverIp = rb.getString("server_ip");
            port = Integer.parseInt(rb.getString("port"));
            
            // Instanciem el Socket i els Input i Output 
            // per comunicar amb el server
            s = new Socket(serverIp, port);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem el login al servidor
            dos.write(TFServer.LOGIN);
            
            // Llegim la resposta
            ret = dis.readBoolean();
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
            
        } finally {
            
            try {
                // Tanquem connexions
                if (dis != null) { dis.close();}
                if (dos != null) { dos.close();}
                if (s != null) { s.close();}
                
            } catch (Exception e) {
            }
        }
        
        return ret;
    }
    
    public static UserDTO userData(String userName) {
        
        UserDTO user = new UserDTO();
        
         // Dades de configuració del servidor
        String serverIp = null;
        int port;
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Agafem les dades de conexió al server
            // del arxiu de configuració "config.properties"
            ResourceBundle rb = ResourceBundle.getBundle("config");
            serverIp = rb.getString("server_ip");
            port = Integer.parseInt(rb.getString("port"));
            
            // Instanciem el Socket i els Input i Output 
            // per comunicar amb el server
            s = new Socket(serverIp, port);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem el login al servidor
            dos.write(TFServer.USER_DATA);
            
            // Llegim la resposta i creem la resposta
            user.setUserId(dis.readInt());
            user.setUserName(dis.readUTF());
            user.setUserMail(dis.readUTF());
            user.setUserRol(dis.readUTF());
                        
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
            
        } finally {
            
            try {
                // Tanquem connexions
                if (dis != null) { dis.close();}
                if (dos != null) { dos.close();}
                if (s != null) { s.close();}
                
            } catch (Exception e) {
            }
        }
        
        return user;
    }
    
}
