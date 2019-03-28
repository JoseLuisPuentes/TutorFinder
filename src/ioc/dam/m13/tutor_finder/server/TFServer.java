package ioc.dam.m13.tutor_finder.server;

import ioc.dam.m13.tutor_finder.dtos.UserDTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
    
    private Socket socket = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private ObjectOutputStream oos = null;
        
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
            oos = new ObjectOutputStream(socket.getOutputStream());
            //Para probar como hacerlo enviando objetos
            //Faltaria crear un objeto Login o un objeto User
            
            /*
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            Object obj = in.readObject();
            
            if (obj.getClass().equals(UserDAO)) {
                
            }*/
            
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
                    _listUsers(dis, oos);
                    break;
                
                case EDIT_USER_PSWD:
                    _editUserPswd(dis, dos);
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
        //TODO: provar _newUser
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
        //TODO: _delUser
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void _listUsers(DataInputStream dis, ObjectOutputStream oos) {
        //TODO: provar _listUsers
        try {
            UserDAO dao = (UserDAO) TFFactory.getInstance("USER");
            ArrayList<UserDTO> users = new ArrayList<>();
            
            //Llegim quin mètode sobre carrgat del UserDAO es vol fer servir 
            switch(dis.readUTF()){
                //Mètode listUsers()
                case "listUsers":
                    //Demanen a la BBDD la llista d'usuaris
                    users = dao.listUsers();
                    for (UserDTO user : users) {
                    //Retornem els objectes per separat al client
                        oos.writeObject(user);
                    }
                    break;
                //Mètode listUsers(String roleName)
                case "listUsersRole":
                    //Llegim el nom del rol que es vol llistar
                    String roleName = dis.readUTF();
                    //Demanem a la BBDD la llista d'usuaris per rol
                    users = dao.listUsers(roleName);
                    int nUsers = users.size();
                    //Enviem la quantitat d'usuaris que hi ha de resposta
                    oos.writeInt(nUsers);
                    
                    for (UserDTO user : users) {
                        //Retornem els objectes per separat al client
                        oos.writeObject(user);
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
}
