package ioc.dam.m13.tutor_finder.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;

import ioc.dam.m13.tutor_finder.server.TFServer;
import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Classe que encapsula la connexió amb el servidor,
 * enviar i rebre les dades i desconnexió
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class ServiceLocator {
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i comprova si el usuari introduït es correcte.
     * @param userName El nom de l'usuari a comprovar.
     * @param pswd La contrasenya de l'usuari.
     * @return Retorna "true" si l'usuari és a la base de dades.
     */
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
            ResourceBundle rb = ResourceBundle.getBundle("ioc.dam.m13.tutor_finder.client.config");
            serverIp = rb.getString("server_ip");
            port = Integer.parseInt(rb.getString("port"));
            
            // Instanciem el Socket i els Input i Output 
            // per comunicar amb el server
            s = new Socket(serverIp, port);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem el login al servidor
            dos.writeInt(TFServer.LOGIN);
            dos.writeUTF(userName);
            dos.writeUTF(pswd);
            
            
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
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        
        return ret;
    }
    
    /**
     * Connecta amb el servidor agafant la configuració de 
     * l'arxiu "config.properties" i demana les dades de l'usuari introduït
     * @param userName Nom de l'usuari 
     * @return Retorna un objecte "UserDTO" amb les dades de l'usuari.
     */
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
            ResourceBundle rb = ResourceBundle.getBundle("ioc.dam.m13.tutor_finder.client.config");
            serverIp = rb.getString("server_ip");
            port = Integer.parseInt(rb.getString("port"));
            
            // Instanciem el Socket i els Input i Output 
            // per comunicar amb el server
            s = new Socket(serverIp, port);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem les dades de l'usuari al servidor
            dos.writeInt(TFServer.USER_DATA);
            dos.writeUTF(userName);
            
            // Llegim la resposta i creem la resposta
            user.setUserId(dis.readInt());
            user.setUserName(dis.readUTF());
            user.setUserPswd(dis.readUTF());
            user.setUserMail(dis.readUTF());
            user.setUserRole(dis.readUTF());
                        
            
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
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        
        return user;
    }
    //TODO: provar newUser SL
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i crea un usuari nou amb les dades passdes.
     * @param userName String del nom de l'usuari
     * @param userMail String de l'email de l'usuari
     * @param userPswd String amb la contrasenya
     * @param userRole String amb el rol de l'usuari
     * @return true si s'ha inserit el nou usuari
     */
    public static boolean newUser(String userName, String userMail, String userPswd, String userRole ) {
        
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
            ResourceBundle rb = ResourceBundle.getBundle("ioc.dam.m13.tutor_finder.client.config");
            serverIp = rb.getString("server_ip");
            port = Integer.parseInt(rb.getString("port"));
            
            // Instanciem el Socket i els Input i Output 
            // per comunicar amb el server
            s = new Socket(serverIp, port);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem el enviar el nou usuari al servidor
            dos.writeInt(TFServer.NEW_USER);
            //Enviem les dades del nou usari
            dos.writeUTF(userName);
            dos.writeUTF(userMail);
            dos.writeUTF(userPswd);
            dos.writeUTF(userRole);
            
            
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
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        
        return ret;
    }
    
    //TODO: codificar editUser SL
    public boolean editUser(String userName){
        boolean ret = false;
        
        return ret;
    }
    
    //TODO: codificar delUser SL
    public boolean delUser(String userName){
        boolean ret = false;
        
        return ret;
    }
    
    //TODO: codificar listUsers SL
    public ArrayList<UserDTO> listUsers(){
        ArrayList<UserDTO> users = null;
        
        return users;
    }
    
    public ArrayList<UserDTO> listUsers(String roleName){
        ArrayList<UserDTO> users = new ArrayList<>();
        
        return users;
    }
    
    //TODO: codificar editUserPswd SL
    public boolean editUserPswd(String userName, String pswd){
        boolean ret = false;
        
        return ret;
    }
    
    
    //TODO: codificar getUserRoles SL
    public HashMap<Integer, String> getUserRoles(){
        HashMap<Integer, String> userRoles = new HashMap<>();
        
        return userRoles;
    }
    
    public int getUserRoles(String userName){
        int ret = -1;
        
        return ret;
    }
    
    public String getUserRoles(int roleId){
        String ret = null;
        
        return ret;
    }
}
