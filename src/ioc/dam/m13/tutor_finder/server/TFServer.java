package ioc.dam.m13.tutor_finder.server;

import ioc.dam.m13.tutor_finder.dtos.AdDTO;
import ioc.dam.m13.tutor_finder.dtos.UserDTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servidor que s'encarrega de rebre les solicituts dels clients
 * connectar amb el servidor de BBDD i retornar les dades als clients
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TFServer extends Thread{
    
    public static final int SERVER_PORT = 7474;
    public static final int LOGIN = 0;
    public static final int USER_DATA = 1;
    public static final int NEW_USER = 2;
    public static final int EDIT_USER = 3;
    public static final int DEL_USER = 4;
    public static final int LIST_USERS = 5;
    public static final int EDIT_USER_PSWD = 6;
    public static final int GET_USER_ROLES = 7;
    public static final int CRATE_AD = 8;
    public static final int LIST_ADS = 9;
    public static final int LIST_ADS_BY_USER = 10;
    public static final int LIST_ADS_BY_ROLE = 11;
    public static final int LIST_ADS_BY_TYPE = 12;
    public static final int EDIT_AD = 13;
    public static final int DEL_AD = 14;
    public static final int GET_AD_TYPES = 15;
    public static final int GET_AD_TYPE_BY_ID = 16;
    public static final int GET_AD_TYPE_BY_NAME = 17;
    
    
    private Socket socket = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    
        
    public TFServer( Socket s) {
        
        this.socket = s;        
    }
    
    public static void main(String[] args) throws Exception {
        
        ServerSocket ss = new ServerSocket(SERVER_PORT);
        Socket s;
        
        System.out.println("Server is running ...");
        
        while (true) {            
            
            s = ss.accept();
            new TFServer(s).start();
            
        }
    }
    
    @Override
    public void run() {
        
        try {
            
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
                        
            //llegim el codi de servei
            int srvCod = dis.readInt();
            
            //Mostra les provas de connexió
            System.out.println("cliente pide: " + srvCod);
            
            
            switch(srvCod) {
                case LOGIN:
                    _login(dis, dos);
                    break;
                    
                case USER_DATA:
                    _userData(dis, dos);
                    break;
                    
                case NEW_USER:
                    _newUser(dis, dos);
                    break;
                
                case EDIT_USER:
                    _editUser(dis, dos);
                    break;
                    
                case DEL_USER:
                    _delUser(dis, dos);
                    break;
                
                case LIST_USERS:
                    _listUsers(dis, dos);
                    break;
                
                case EDIT_USER_PSWD:
                    _editUserPswd(dis, dos);
                    break;
                    
                case GET_USER_ROLES:
                    _getUserRoles(dis, dos);
                    break;
                
                case CRATE_AD:
                    _createAd(dis, dos);
                    break;
                    
                case LIST_ADS:
                    _listAds(dis, dos);
                    break;
                    
                case LIST_ADS_BY_USER:
                    _listAdsByUser(dis, dos);
                    break;
                    
                case LIST_ADS_BY_ROLE:
                    _listAdsByRole(dis, dos);
                    break;
                    
                case LIST_ADS_BY_TYPE:
                    _listAdsByType(dis, dos);
                    break;
                    
                case EDIT_AD:
                    _editAd(dis, dos);
                    break;
                    
                case DEL_AD:
                    _delAd(dis, dos);
                    break;
                    
                case GET_AD_TYPES:
                    _getAdTypes(dis, dos);
                    break;
                    
                case GET_AD_TYPE_BY_ID:
                    _getAdTypeById(dis, dos);
                    break;
                    
                case GET_AD_TYPE_BY_NAME:
                    _getAdTypeByName(dis, dos);
                    break;
                    
    
                    
            }
            
                                   
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }    
        
    }
    /**
     * Comprova si l'usuari i la contrasenya demanades per la connexió amb el client
     * son correctes al servidor de BBDD, i retorna la resposta per la mateixa connexió 
     * @param dis InputStream del client
     * @param dos OutputStream del client
     */
    private void _login(DataInputStream dis, DataOutputStream dos) {
        
        try {
            
            UserDAO dao =  (UserDAO) TFFactory.getInstance("USER");
            
            //Llegin l'usuari i la contrasenya del client
            String usr = dis.readUTF();
            String pwd = dis.readUTF();
            
            // Mostra entrades del client per proves
            System.out.println("user: " + usr);
            System.out.println("password: " + pwd);
            
            //Preparem la resposta
            boolean ret = dao.login(usr, pwd);
            
            //Enviem la resposta
            dos.writeBoolean(ret);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
            
        } 
    }
    /**
     * Demana al servidor de BBDD les dades de l'usuari demanat per la 
     * connexió del client, i retorna la resposta per la mateixa connexió 
     * @param dis InputStream del client
     * @param dos OutputStream del client
     */
    private void _userData(DataInputStream dis, DataOutputStream dos) {
        try {
            
            UserDAO dao = (UserDAO) TFFactory.getInstance("USER");
            
            //Llegin l'usuari i la contrasenya del client
            String usr = dis.readUTF();
                        
            //Preparem la resposta
            UserDTO userDTO = dao.userData(usr);
            
            System.out.println(userDTO.toString());
            if (userDTO.getUserId() == 0) {
                //enviem l'id
                dos.writeInt(userDTO.getUserId());
                dos.writeUTF("");
                dos.writeUTF("");
                dos.writeUTF("");
                dos.writeUTF("");
                
            } else {
                //Enviem la resposta
            
                dos.writeInt(userDTO.getUserId());
                dos.writeUTF(userDTO.getUserName());
                dos.writeUTF(userDTO.getUserMail());
                dos.writeUTF(userDTO.getUserPswd());
                dos.writeUTF(userDTO.getUserRole());
            
            }
            
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
            
        }
    }
    /**
     * Crea un nou usuari a la BBDD amb les dades que rep del client
     * @param dis DataInputStream del client
     * @param dos DataOutputStream del client
     */
    private void _newUser(DataInputStream dis, DataOutputStream dos) {
        
        try {
            UserDAO dao = (UserDAO) TFFactory.getInstance("USER");
            
            //Llegim les dades del client per crear un nou usari
            String userName = dis.readUTF();
            String userMail = dis.readUTF();
            String userPswd = dis.readUTF();
            String userRole = dis.readUTF();
            
            //Creem el nou usuari
            boolean ret = dao.newUser(userName, userMail, userPswd, userRole);
            
            //Retornem al client el resultat
            dos.writeBoolean(ret);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * Modifica les dades d'un ususari amb les dades que rep del client
     * @param dis DataInputStream del client
     * @param dos DataOutputStream del client
     */
    private void _editUser(DataInputStream dis, DataOutputStream dos) {
        
        try {
            
            UserDAO dao = (UserDAO) TFFactory.getInstance("USER");
            
            //Llegim les dades del client a canviar 
            int userId = dis.readInt();
            String userName = dis.readUTF();
            String userMail = dis.readUTF();
            String userRole = dis.readUTF();
            
            //Canviem les dades de l'usari
            boolean ret = dao.editUser(userId, userName, userMail, userRole);
            
            //Retornem al client el resultat
            dos.writeBoolean(ret);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * Esborra un ususari de la BBDD
     * @param dis DataInputStream del client
     * @param dos DataOutputStream del client
     */
    private void _delUser(DataInputStream dis, DataOutputStream dos) {
        try {
            UserDAO dao = (UserDAO) TFFactory.getInstance("USER");
            
            //Llegim les dades del client per eliminar l'usari
            String userName = dis.readUTF();
            
            
            //Eliminem l'usuari
            boolean ret = dao.delUser(userName);
            
            //Retornem al client el resultat
            dos.writeBoolean(ret);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * Llista els ususaris de la BBDD
     * @param dis DataInputStream del client
     * @param dos DataOutputStream del client
     */
    private void _listUsers(DataInputStream dis, DataOutputStream dos) {
        
        try {
            UserDAO dao = (UserDAO) TFFactory.getInstance("USER");
            ArrayList<UserDTO> users = new ArrayList<>();
            int nUsers;
            
            //Llegim quin mètode sobre carrgat del UserDAO es vol fer servir 
            switch(dis.readUTF()){
                //Mètode listUsers()
                case "listUsers":
                    //Demanen a la BBDD la llista d'usuaris
                    users = dao.listUsers();
                    
                    //Enviem el nombre d'usuaris que hi ha de resposta
                    nUsers = users.size();
                    dos.writeInt(nUsers);
                    
                    for (UserDTO userDTO : users) {
                        //Retornem els objectes per separat al client
                        dos.writeInt(userDTO.getUserId());
                        dos.writeUTF(userDTO.getUserName());
                        dos.writeUTF(userDTO.getUserMail());
                        dos.writeUTF(userDTO.getUserPswd());
                        dos.writeUTF(userDTO.getUserRole());
                        
                    }
                    break;
                //Mètode listUsers(String roleName)
                case "listUsersRole":
                    //Llegim el nom del rol que es vol llistar
                    String roleName = dis.readUTF();
                    //Demanem a la BBDD la llista d'usuaris per rol
                    users = dao.listUsers(roleName);
                    nUsers = users.size();
                    //Enviem la quantitat d'usuaris que hi ha de resposta
                    dos.writeInt(nUsers);
                    
                    for (UserDTO userDTO : users) {
                        //Retornem els objectes per separat al client
                        dos.writeInt(userDTO.getUserId());
                        dos.writeUTF(userDTO.getUserName());
                        dos.writeUTF(userDTO.getUserMail());
                        dos.writeUTF(userDTO.getUserPswd());
                        dos.writeUTF(userDTO.getUserRole());
                    }
                    break;                    
            }
            
        } catch (Exception e) {
            
            
        }
        
    }
    /**
     * Mododifica la contrasenya de l'usuari
     * @param dis DataInputStream del client
     * @param dos DataOutputStream del client
     */
    private void _editUserPswd(DataInputStream dis, DataOutputStream dos) {

        try {
            
            UserDAO dao = (UserDAO) TFFactory.getInstance("USER");
            
            //Llegim les dades del client per canbiar la contrasenya
            String userName = dis.readUTF();
            String newPswd = dis.readUTF();
            
            //Canviem la contrasenya de l'usari
            boolean ret = dao.editUserPswd(userName, newPswd);
            
            //Retornem al client el resultat
            dos.writeBoolean(ret);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * Llista els rols que hi ha a la BBDD
     * @param dis DataInputStream del client
     * @param dos DataOutputStream del client
     */
    private void _getUserRoles(DataInputStream dis, DataOutputStream dos) {
        try {
            
            int nRoles;
            HashMap<Integer, String> roles = new HashMap<>();
            UserDAO role = (UserDAO) TFFactory.getInstance("USER");
            
            
            //Llegim quin mètode sobre carrgat del UserDAO es vol fer servir 
            switch(dis.readUTF()){
                //Mètode getUserRoles()
                case "getUserRoles":
                    //Demanen a la BBDD la llista d'usuaris
                    roles = role.getUserRoles();
                    
                    //Enviem el nombre de rols que hi ha de resposta
                    nRoles = roles.size();
                    dos.writeInt(nRoles);
                    
                    for (Map.Entry<Integer, String> entry : roles.entrySet()) {
                        //Agafem les dades del hashMap
                        Integer roleId = entry.getKey();
                        String roleName = entry.getValue();
                        //Les enviem al client
                        dos.writeInt(roleId);
                        dos.writeUTF(roleName);
                    }
                    
                    break;
                    
                //Mètode getUserRoleId(String roleName)
                case "getUserRoleId":
                    //Llegim el nom del rol que es vol llistar
                    String roleName = dis.readUTF();
                    //Demanem a la BBDD per l'Id del rol
                    int id = role.getUserRoles(roleName);
                    
                    //Enviem l'id del rol demanat
                    dos.writeInt(id);
                    
                    break;   
                    
                //Mètode getUserRoleName(int roleId)
                case "getUserRoleName":
                    //Llegim l'Id del rol que es vol llistar
                    int roleId = dis.readInt();
                    //Demanem a la BBDD pel nom del rol
                    String name = role.getUserRoles(roleId);
                    
                    //Enviem el nom del rol demanat
                    dos.writeUTF(name);
                    
                    break;    
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //TODO: documentar createAD
    private void _createAd(DataInputStream dis, DataOutputStream dos) {
        
        try {
            AdDAO dao = (AdDAO) TFFactory.getInstance("AD");
            
            //Llegim les dades del client per crear un nou anunci
            int userId = dis.readInt();
            String tittle = dis.readUTF();
            String description = dis.readUTF();
            int adTypeId = dis.readInt();
            int price = dis.readInt();
            
            System.out.println("user: " + userId);
            System.out.println("tittle: " + tittle);
            System.out.println("description: " + description);
            System.out.println("type: " + adTypeId);
            System.out.println("price: " + price);
            
            
            //Creem el nou usuari
            boolean ret = dao.createAd(userId, tittle, description, adTypeId, price);
            
            //Retornem al client el resultat
            dos.writeBoolean(ret);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //TODO: documentar listAds
    private void _listAds(DataInputStream dis, DataOutputStream dos) {
        
        try {
            AdDAO dao = (AdDAO) TFFactory.getInstance("AD");
            ArrayList<AdDTO> ads = new ArrayList<>();
            int nAds;
            
            //Demanem a la BBDD la llista de tots els anuncis
            ads = dao.listAds();
            //Enviem el nombre d'anuncis que hi ha de resposta
            nAds = ads.size();
            dos.writeInt(nAds);
            
            //TODO: prova de resposta adslist
            for (AdDTO ad : ads) {
                System.out.println(ad.toString());
            }
            
            
            for (AdDTO ad : ads) {
                //Retornem els objectes per separat al client
                dos.writeInt(ad.getAdId());
                dos.writeInt(ad.getAdUserId());
                dos.writeUTF(ad.getUserName());
                dos.writeUTF(ad.getAdTittle());
                dos.writeUTF(ad.getAdDescription());
                dos.writeInt(ad.getAdTypeId());
                dos.writeUTF(ad.getTypesName());
                dos.writeInt(ad.getAdPrice());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //TODO: documentar listAdByUser
    private void _listAdsByUser(DataInputStream dis, DataOutputStream dos) {
        
        try {
            AdDAO dao = (AdDAO) TFFactory.getInstance("AD");
            ArrayList<AdDTO> ads = new ArrayList<>();
            int nAds;
            //Llegim l'id d'usuari
            int userId = dis.readInt();
            
            //Demanem a la BBDD la llista de tots els anuncis
            ads = dao.listAdsByUser(userId);
            //Enviem el nombre d'anuncis que hi ha de resposta
            nAds = ads.size();
            dos.writeInt(nAds);
            
            for (AdDTO ad : ads) {
                //Retornem els objectes per separat al client
                dos.writeInt(ad.getAdId());
                dos.writeInt(ad.getAdUserId());
                dos.writeUTF(ad.getUserName());
                dos.writeUTF(ad.getAdTittle());
                dos.writeUTF(ad.getAdDescription());
                dos.writeInt(ad.getAdTypeId());
                dos.writeUTF(ad.getTypesName());
                dos.writeInt(ad.getAdPrice());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //TODO: documentar listAdsByRole
    private void _listAdsByRole(DataInputStream dis, DataOutputStream dos) {
        
        try {
            AdDAO dao = (AdDAO) TFFactory.getInstance("AD");
            ArrayList<AdDTO> ads = new ArrayList<>();
            int nAds;
            //Llegim l'id del rol
            int roleId = dis.readInt();
            //Demanem a la BBDD la llista de tots els anuncis
            ads = dao.listAdsByRole(roleId);
            //Enviem el nombre d'anuncis que hi ha de resposta
            nAds = ads.size();
            dos.writeInt(nAds);
            
            for (AdDTO ad : ads) {
                //Retornem els objectes per separat al client
                dos.writeInt(ad.getAdId());
                dos.writeInt(ad.getAdUserId());
                dos.writeUTF(ad.getUserName());
                dos.writeUTF(ad.getAdTittle());
                dos.writeUTF(ad.getAdDescription());
                dos.writeInt(ad.getAdTypeId());
                dos.writeUTF(ad.getTypesName());
                dos.writeInt(ad.getAdPrice());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //TODO: documentar lsitAdsByType
    private void _listAdsByType(DataInputStream dis, DataOutputStream dos) {
        
        try {
            AdDAO dao = (AdDAO) TFFactory.getInstance("AD");
            ArrayList<AdDTO> ads = new ArrayList<>();
            int nAds;
            //Llegim l'id del tipus
            int typeId = dis.readInt();
            //Demanem a la BBDD la llista de tots els anuncis
            ads = dao.listAdsByType(typeId);
            //Enviem el nombre d'anuncis que hi ha de resposta
            nAds = ads.size();
            dos.writeInt(nAds);
            
            for (AdDTO ad : ads) {
                //Retornem els objectes per separat al client
                dos.writeInt(ad.getAdId());
                dos.writeInt(ad.getAdUserId());
                dos.writeUTF(ad.getUserName());
                dos.writeUTF(ad.getAdTittle());
                dos.writeUTF(ad.getAdDescription());
                dos.writeInt(ad.getAdTypeId());
                dos.writeUTF(ad.getTypesName());
                dos.writeInt(ad.getAdPrice());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //TODO: documentar editAd
    private void _editAd(DataInputStream dis, DataOutputStream dos) {
        try {
            
            AdDAO dao = (AdDAO) TFFactory.getInstance("AD");
            
            //Llegim les dades de l'anunci a canviar 
            int adId = dis.readInt();
            String tittle = dis.readUTF();
            String description = dis.readUTF();
            int adTypeId = dis.readInt();
            int price = dis.readInt();
            
            //Canviem les dades de l'usari
            boolean ret = dao.editAd(adId, tittle,description,adTypeId, price);
            
            //Retornem al client el resultat
            dos.writeBoolean(ret);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //TODO: documentar delAd
    private void _delAd(DataInputStream dis, DataOutputStream dos) {
        try {
            AdDAO dao = (AdDAO) TFFactory.getInstance("AD");
            
            //Llegim l'id de l'anunci a eliminar
            int adId = dis.readInt();
            
            
            //Eliminem l'anunci
            boolean ret = dao.delAd(adId);
            
            //Retornem al client el resultat
            dos.writeBoolean(ret);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //TODO: documentar _getAdTypes
    private void _getAdTypes(DataInputStream dis, DataOutputStream dos) {
        try{
            int nTypes;
            HashMap<Integer, String> ads = new HashMap<>();
            AdDAO ad = (AdDAO) TFFactory.getInstance("AD");
            
            //Demanen a la BBDD la llista de tipus
            ads = ad.getAdTypes();
            
            //Enviem el nombre de tipus que hi ha a la resposta
            nTypes = ads.size();
            dos.writeInt(nTypes);
            
            for (Map.Entry<Integer, String> entry : ads.entrySet()) {
                //Agafem les dades del hashMap
                Integer adTypeId = entry.getKey();
                String adTypeName = entry.getValue();
                //Les enviem al client
                dos.writeInt(adTypeId);
                dos.writeUTF(adTypeName);
            }
            
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //TODO: documentar _getAdTypeById 
    private void _getAdTypeById(DataInputStream dis, DataOutputStream dos) {
        
        try {
            
            String adTypeName = null;
            AdDAO ad = (AdDAO) TFFactory.getInstance("AD");
            //Agafem del client el id a buscar
            int adTypeId = dis.readInt();
            //Demanem a la BBDD el nom del tipus pel seu id
            adTypeName = ad.getAdTypeById(adTypeId);
            //Retonem al client la resposta
            dos.writeUTF(adTypeName);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    //TODO: documentar _getAdTypeByNAme
    private void _getAdTypeByName(DataInputStream dis, DataOutputStream dos) {
        try {
            
            int adTypeId = -1;
             
            AdDAO ad = (AdDAO) TFFactory.getInstance("AD");
            //Agafem del client el id a buscar
            String adTypeName= dis.readUTF();
            //Demanem a la BBDD el nom del tipus pel sue nom
            adTypeId = ad.getAdTypeByName(adTypeName);
            //Retonem al client la resposta
            dos.writeInt(adTypeId);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
