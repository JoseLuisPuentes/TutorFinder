package ioc.dam.m13.tutor_finder.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
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
        
        while (true) {            
            
            s = ss.accept();
            new TFServer(s).start();
            
        }
    }
    //TODO: run()
    @Override
    public void run() {
        
        try {
            
            ois = new DataInputStream(socket.getInputStream());
            oos = new DataOutputStream(socket.getOutputStream());
            
            //Para probar como hacerlo enviando objetos
            //Crear objeto Login o objeto User
            /*
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            Object obj = in.readObject();
            
            if (obj.getClass().equals(UserDAO)) {
                
            }*/
            
            //llegim el codi de servei
            int srvCod = ois.readInt();
            
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
    
    //TODO: login i userData

    private void _login(DataInputStream ois, DataOutputStream oos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void _userData(DataInputStream ois, DataOutputStream oos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
