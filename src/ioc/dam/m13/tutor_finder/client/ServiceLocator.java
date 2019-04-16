package ioc.dam.m13.tutor_finder.client;



import ioc.dam.m13.tutor_finder.dtos.AdDTO;
import ioc.dam.m13.tutor_finder.server.TFServer;
import ioc.dam.m13.tutor_finder.dtos.UserDTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.Socket;
import java.util.ResourceBundle;
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
            user.setUserMail(dis.readUTF());
            user.setUserPswd(dis.readUTF());
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
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i modificar les dades d'un usuari amb les dades passdes.
     * @param userId Int amb id d'usuari
     * @param userName String amb el nom d'usuari
     * @param userMail String amb el mail d'usuari
     * @param userRole String amb el rol d'usuari
     * @return 
     */
    public static boolean editUser(int userId, String userName, String userMail, String userRole){
        
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
            dos.writeInt(TFServer.EDIT_USER);
            //Enviem les dades del nou usari
            dos.writeInt(userId);
            dos.writeUTF(userName);
            dos.writeUTF(userMail);
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
    
    public static boolean delUser(String userName){
        
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
            dos.writeInt(TFServer.DEL_USER);
            //Enviem les dades del nou usari
            dos.writeUTF(userName);

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
    
    
    public static ArrayList<UserDTO> listUsers(){
        
        // Dades de configuració del servidor
        String serverIp;
        int port;
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        ArrayList<UserDTO> users = new ArrayList<>();
        
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
            
            // Solicitem el llistat de tots els usuaris al servidor
            dos.writeInt(TFServer.LIST_USERS);
            dos.writeUTF("listUsers");
            
            //Revem el nombre d'ususaris que hi haurà de resposta
            int nUsers = dis.readInt();
            
            for (int i = 0; i < nUsers; i++) {
                //Rebem los dades del servidor i construï un UserDTO 
                //i el fiquem l'ArrayList
                UserDTO user = new UserDTO();
                user.setUserId(dis.readInt());
                user.setUserName(dis.readUTF());
                user.setUserMail(dis.readUTF());
                user.setUserPswd(dis.readUTF());
                user.setUserRole(dis.readUTF());
                
                users.add(user);
            }
                        
                        
            
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
        
        return users;
    }
    
    public static ArrayList<UserDTO> listUsers(String roleName){
        
        // Dades de configuració del servidor
        String serverIp;
        int port;
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        ArrayList<UserDTO> users = new ArrayList<>();
        
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
            
            // Solicitem el llistat de tots els usuaris al servidor
            dos.writeInt(TFServer.LIST_USERS);
            dos.writeUTF("listUsersRole");
            //Enviem el rol que volem llistar
            dos.writeUTF(roleName);
            
            //Revem el nombre d'ususaris que hi haurà de resposta
            int nUsers = dis.readInt();
            
            for (int i = 0; i < nUsers; i++) {
                //Rebem los dades del servidor i construï un UserDTO 
                //i el fiquem l'ArrayList
                UserDTO user = new UserDTO();
                user.setUserId(dis.readInt());
                user.setUserName(dis.readUTF());
                user.setUserMail(dis.readUTF());
                user.setUserPswd(dis.readUTF());
                user.setUserRole(dis.readUTF());
                
                users.add(user);
            }
                        
                        
            
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
        
        return users;
    }
    

    /**
     * Connecta amb el TFServer agafant les dades de l'arxiu "config.properties"
     * i canvia la contrasenya de l'usuari
     * @param userName String amb el nom de l'usuari
     * @param newPswd String amb la nova contrasenya
     * @return Retorna true si s'ha cenviat correctament
     */
    public static boolean editUserPswd(String userName, String newPswd){
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
            
            // Solicitem el canvi de contrasenya al servidor
            dos.writeInt(TFServer.EDIT_USER_PSWD);
            dos.writeUTF(userName);
            dos.writeUTF(newPswd);
            
            
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
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i demana la llista de id's i roles que hi ha a la BBDD
     * @return Retrona un HashMap amb la taula de roles
     */
    public static HashMap<Integer, String> getUserRoles(){
        
        // Dades de configuració del servidor
        String serverIp;
        int port;
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        HashMap<Integer, String> userRoles = new HashMap<>();
        
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
            
            // Solicitem el llistat de tots els usuaris al servidor
            dos.writeInt(TFServer.GET_USER_ROLES);
            dos.writeUTF("getUserRoles");
            
            //Revem el nombre d'ususaris que hi haurà de resposta
            int nRoles = dis.readInt();
            
            for (int i = 0; i < nRoles; i++) {
                //Rebem los dades del servidor i construïm 
                //el HashMap de resposta
                int id = dis.readInt();
                String name = dis.readUTF();
                userRoles.put(id, name);           
                
            }
            
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
        
        return userRoles;
    }
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i demana el nom del rol per l'id que hi ha a la BBDD
     * @param userName String amb el nom del rol
     * @return retorna un int amb l'id del rol
     */
    public static int getUserRoles(String roleName){
        
        // Dades de configuració del servidor
        String serverIp;
        int port;
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        int ret = -1;
        
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
            
            // Solicitem l'id del rol
            dos.writeInt(TFServer.GET_USER_ROLES);
            dos.writeUTF("getUserRoleId");
            dos.writeUTF(roleName);
            
            //Revem la resposta
            ret = dis.readInt();
            
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
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i demana l'id del rol pel nom que hi ha a la BBDD
     * @param roleId Int amd l'Id del rol demanat
     * @return Retorna un String amd el nom del rol
     */
    public static String getUserRoles(int roleId){
        
        // Dades de configuració del servidor
        String serverIp;
        int port;
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        String ret = null;
        
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
            
            // Solicitem l'id del rol
            dos.writeInt(TFServer.GET_USER_ROLES);
            dos.writeUTF("getUserRoleName");
            dos.writeInt(roleId);
            
            //Revem la resposta
            ret = dis.readUTF();
            
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
    
    public static boolean createAd (int userId, String tittle, String description, int adTypeId, int price) {
        
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
            dos.writeInt(TFServer.CRATE_AD);
            //Enviem les dades del nou usari
            dos.writeInt(userId);
            dos.writeUTF(tittle);
            dos.writeUTF(description);
            dos.writeInt(adTypeId);
            dos.writeInt(price);
            
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
    
    public static ArrayList<AdDTO> listAds(){
        // Dades de configuració del servidor
        String serverIp;
        int port;
        
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        ArrayList<AdDTO> ads = new ArrayList<>();
        
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
            
            // Solicitem el llistat de tots els anuncis al servidor
            dos.writeInt(TFServer.LIST_ADS);
            
            
            //Revem el nombre d'anuncis que hi haurà de resposta
            int nAds = dis.readInt();
            
            for (int i = 0; i < nAds; i++) {
                //Rebem los dades del servidor i construï un UserDTO 
                //i el fiquem l'ArrayList
                AdDTO ad = new AdDTO();
                ad.setAdId(dis.readInt());
                ad.setAdUserId(dis.readInt());
                ad.setUserName(dis.readUTF());
                ad.setAdTittle(dis.readUTF());
                ad.setAdDescription(dis.readUTF());
                ad.setAdTypeId(dis.readInt());
                ad.setTypesName(dis.readUTF());
                ad.setAdPrice(dis.readInt());
                                
                ads.add(ad);
            }
                        
                        
            
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
        
        return ads;
    
    }
    //TODO: listAdsByUser ServiceLocator
    public static ArrayList<AdDTO> listAdsByUser(int userId){
        
        ArrayList<AdDTO> ads = new ArrayList<>();
        return ads;
        
    }
    //TODO: listAdsByRole ServiceLocator        
    public static ArrayList<AdDTO> listAdsByRole(int roleId){
        ArrayList<AdDTO> ads = new ArrayList<>();
        return ads;
    }
    //TODO: listAdsByType ServiceLocator        
    public static ArrayList<AdDTO> listAdsByType(int typeId){
        ArrayList<AdDTO> ads = new ArrayList<>();
        return ads;
    }
    //TODO: editAd ServiceLocator
    public static boolean editAd(int adId, String tittle, String description, int adTypeId, int price){
        boolean ret = false;
        return ret;
    }
    //TODO: delAd ServiceLocator
    public static boolean delAd(int adId){
        boolean ret = false;
        return ret;
    }
    //TODO: getAdTypes ServiceLocator
    public static HashMap<Integer, String> getAdTypes(){
        HashMap<Integer, String> adTypes = new HashMap<>();
        return adTypes;
    }
    //TODO: getAdTypeById ServiceLocator
    public static String getAdTypeById(int adTypeId){
        String ret = null;
        return ret;
    }
    //TODO: getAdTypeByNAme ServiceLocator
    public static int getAdTypeByName(String AdTypeName){
        int ret = 0 ;
        return ret;
    }
}
