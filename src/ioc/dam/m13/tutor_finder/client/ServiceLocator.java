package ioc.dam.m13.tutor_finder.client;



import ioc.dam.m13.tutor_finder.dtos.AdDTO;
import ioc.dam.m13.tutor_finder.server.TFServer;
import ioc.dam.m13.tutor_finder.dtos.UserDTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
/**
 * Classe que encapsula la connexió amb el servidor,
 * enviar i rebre les dades i desconnexió
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class ServiceLocator {
    
    private static SSLSocket _getSSLSocket(){
        SSLSocket ret = null;
        
        
        
        String serverIp = null;
        int port;
        ResourceBundle rb = ResourceBundle.getBundle("ioc.dam.m13.tutor_finder.client.config");
        serverIp = rb.getString("server_ip");
        port = Integer.parseInt(rb.getString("port"));
        
        SSLSocketFactory sslFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        
        try {
            
            ret = (SSLSocket) sslFactory.createSocket(serverIp, port);
        
        } catch (IOException ex) {
            
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        
        return ret;
    }
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i comprova si el usuari introduït es correcte.
     * @param userName El nom de l'usuari a comprovar.
     * @param pswd La contrasenya de l'usuari.
     * @return Retorna "true" si l'usuari és a la base de dades.
     */
    public static boolean login(String userName, String pswd) {
        
        boolean ret = false;
        SSLSocket s = null;
        
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            
            s = (SSLSocket) _getSSLSocket();
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

        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
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
        
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
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
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
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
        
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
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
        
        ArrayList<UserDTO> users = new ArrayList<>();
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
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
        
        ArrayList<UserDTO> users = new ArrayList<>();
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
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
        
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
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
        HashMap<Integer, String> userRoles = new HashMap<>();
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
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
        int ret = -1;
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
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
        String ret = null;
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
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
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i crea un anunci a la BBDD
     * @param userId int amb l'id de l'usuari que crea l'anunci
     * @param tittle String amb el titol de l'anunci
     * @param description String amb el text de l'anunci
     * @param adTypeId int amd el id del tipus d'anunci que es vol publicar
     * @param price int amb el preu del servei que ofereix
     * @return retorna true si es crea correctament
     */
    public static boolean createAd (int userId, String tittle, String description, int adTypeId, int price) {
        
        boolean ret = false;
        
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
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
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i llista tots els anuncis que hi ha al BBDD
     * @return Retorna un ArrayList de objectes AdDTO 
     */
    public static ArrayList<AdDTO> listAds(){
        ArrayList<AdDTO> ads = new ArrayList<>();
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
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
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i llista tots els anuncis que ha creat un usuari a la BBDD
     * @param userId int amb el id de l'usuari
     * @return Retorna un ArrayList de objectes AdDTO 
     */
    public static ArrayList<AdDTO> listAdsByUser(int userId){
        ArrayList<AdDTO> ads = new ArrayList<>();
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem el llistat de tots els anuncis al servidor
            dos.writeInt(TFServer.LIST_ADS_BY_USER);
            
            //Enviem el id de l'usuari del que volem rebre els anuncis
            dos.writeInt(userId);
            
            
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
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i llista els tots els anuncis del mateix tipus
     * @param roleId int amb l'id del tipus de rol
     * @return Retorna un ArrayList de objectes AdDTO 
     */    
    public static ArrayList<AdDTO> listAdsByRole(int roleId){
        ArrayList<AdDTO> ads = new ArrayList<>();
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem el llistat de tots els anuncis al servidor
            dos.writeInt(TFServer.LIST_ADS_BY_ROLE);
            
            //Enviem el id del rol del que volem rebre els anuncis
            dos.writeInt(roleId);
            
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
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i llista tots els anuncis del mateix tipus que hi ha a la BBDD
     * @param typeId int amb l'id del tipus d'anunci
     * @return Retorna un ArrayList de objectes AdDTO 
     */
    public static ArrayList<AdDTO> listAdsByType(int typeId){
        ArrayList<AdDTO> ads = new ArrayList<>();
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem el llistat de tots els anuncis al servidor
            dos.writeInt(TFServer.LIST_ADS_BY_TYPE);
            
            //Enviem el id del tipus de clasificació del que volem rebre els anuncis
            dos.writeInt(typeId);
            
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
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i modifica les dades s'un anunci a la BBDD
     * @param adId int amb l'id de l'anunci
     * @param tittle String amb la modificació del títol 
     * @param description String amb la modificació de la descripció
     * @param adTypeId int amb l'id del tipus d'anunci al que es vol modificar
     * @param price int amb el nou preu
     * @return Retorna true si s'ha modificat correctament
     */
    public static boolean editAd(int adId, String tittle, String description, int adTypeId, int price){
        boolean ret = false;
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem el editar un nou anunci al servidor
            dos.writeInt(TFServer.EDIT_AD);
            //Enviem les dades del nou usari
            dos.writeInt(adId);
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
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i esborra un anunci de la BBDD
     * @param adId int amb l'id de l'anunci que es vol esborrar
     * @return Retorna true si s'ha esborrat correctament
     */
    public static boolean delAd(int adId){
        boolean ret = false;
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem el nom del tipus pel seu id
            dos.writeInt(TFServer.DEL_AD);
            
            dos.writeInt(adId);
            
            //Revem la resposta
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
     * i llista tots el tipus d'anunci que hi ha a la BBDD
     * @return HashMap amb l'id i el lnom del tipus d'anunci
     */
    public static HashMap<Integer, String> getAdTypes(){
        HashMap<Integer, String> adTypes = new HashMap<>();
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem el llistat de tots els tipus d'anuncis que hi ha al servidor
            dos.writeInt(TFServer.GET_AD_TYPES);
                        
            //Revem el nombre de tipus que hi haurà de resposta
            int nRoles = dis.readInt();
            
            for (int i = 0; i < nRoles; i++) {
                //Rebem los dades del servidor i construïm 
                //el HashMap de resposta
                int id = dis.readInt();
                String name = dis.readUTF();
                adTypes.put(id, name);           
                
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
        
        return adTypes;
    }
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i retorna el nom del tipus d'anunci demanat pel seu id
     * @param adTypeId int amb l'id del tipus d'anunci
     * @return Retorna un String amb el nom del tipus d'anunci
     */
    public static String getAdTypeById(int adTypeId){
        String ret = null;
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem el nom del tipus pel seu id
            dos.writeInt(TFServer.GET_AD_TYPE_BY_ID);
            
            dos.writeInt(adTypeId);
            
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
    
    /**
     * Connecta amb el TFserver agafant les dades de l'arxiu "config.properties"
     * i retorna l'id del tipus d'anunci demanat pel seu nom
     * @param adTypeName String amb el nom del tipus d'anunci
     * @return Retorna un int amb l'id del tipus d'anunci
     */
    public static int getAdTypeByName(String adTypeName){
        int ret = -1;
        SSLSocket s = null;
        DataInputStream dis = null;
        DataOutputStream dos =null;
        
        try {
            // Instanciem el SSLSocket i els Input i Output 
            // per comunicar amb el server
            s = _getSSLSocket();
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // Solicitem l'id del tipus pel seu nom
            dos.writeInt(TFServer.GET_AD_TYPE_BY_NAME);
            dos.writeUTF(adTypeName);
            
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
}
