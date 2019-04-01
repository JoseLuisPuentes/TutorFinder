package ioc.dam.m13.tutor_finder.server;

import ioc.dam.m13.tutor_finder.dtos.UserDTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            
            //TODO: Mostra les provas de connexió
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
            
            //TODO: Mostra entrades del client per proves
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
                        
            //Enviem la resposta
            dos.writeInt(userDTO.getUserId());
            dos.writeUTF(userDTO.getUserName());
            dos.writeUTF(userDTO.getUserMail());
            dos.writeUTF(userDTO.getUserPswd());
            dos.writeUTF(userDTO.getUserRole());
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
            
        }
    }

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

    private void _editUser(DataInputStream dis, DataOutputStream dos) {
        //TODO: _editUser
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
                        //TODO: enviar datos por separado
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
                        //TODO: enviar datos por separado
                        dos.writeInt(userDTO.getUserId());
                        dos.writeUTF(userDTO.getUserName());
                        dos.writeUTF(userDTO.getUserMail());
                        dos.writeUTF(userDTO.getUserPswd());
                        dos.writeUTF(userDTO.getUserRole());
                    }
                    break;                    
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
    }

    private void _editUserPswd(DataInputStream dis, DataOutputStream dos) {
        //TODO: _editUserPswd
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void _getUserRoles(DataInputStream dis, DataOutputStream dos) {
        try {
            
            int nRoles;
            HashMap<Integer, String> roles = new HashMap<>();
            UserDAO role = new UserDAO();
            
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
}
