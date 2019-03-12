package ioc.dam.m13.tutor_finder.server;

import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TFServer extends Thread{
    
    public static final int SERVER_PORT = 7474;
    public static final int LOGIN = 0;
    public static final int USER_DATA = 1;
    
    private Socket socket = null;
    private DataInputStream ois = null;
    private DataOutputStream oos = null;
        
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
            
            ois = new DataInputStream(socket.getInputStream());
            oos = new DataOutputStream(socket.getOutputStream());
            
            //Para probar como hacerlo enviando objetos
            //Faltaria crear un objeto Login o un objeto User
            
            /*
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            Object obj = in.readObject();
            
            if (obj.getClass().equals(UserDAO)) {
                
            }*/
            
            //llegim el codi de servei
            int srvCod = ois.readInt();
            
            //TODO: Mostra les provas de connexió
            System.out.println("cliente pide: " + srvCod);
            
            
            switch(srvCod) {
                case LOGIN:
                    _login(ois, oos);
                    break;
                    
                case USER_DATA:
                    _userData(ois, oos);
                    break;
                    
            }
            
                        
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }        
        
    }

    private void _login(DataInputStream ois, DataOutputStream oos) {
        
        try {
            
            UserDAO dao =  (UserDAO) TFFactory.getInstance("USER");
            
            //Llegin l'usuari i la contrasenya del client
            String usr = ois.readUTF();
            String pwd = ois.readUTF();
            
            //TODO: Mostra entrades del client per proves
            System.out.println("user: " + usr);
            System.out.println("password: " + pwd);
            
            //Preparem la resposta
            boolean ret = dao.login(usr, pwd);
            
            //Enviem la resposta
            oos.writeBoolean(ret);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
            
        }
    }
    
    private void _userData(DataInputStream ois, DataOutputStream oos) {
        try {
            
            UserDAO dao = (UserDAO) TFFactory.getInstance("USER");
            
            //Llegin l'usuari i la contrasenya del client
            String usr = ois.readUTF();
                        
            //Preparem la resposta
            UserDTO userDTO = dao.userData(usr);
                        
            //Enviem la resposta
            oos.writeInt(userDTO.getUserId());
            oos.writeUTF(userDTO.getUserName());
            oos.writeUTF(userDTO.getUserMail());
            oos.writeUTF(userDTO.getUserRole());
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
            
        }
    }
}
