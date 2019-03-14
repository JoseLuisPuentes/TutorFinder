package ioc.dam.m13.tutor_finder.server;

import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Accedeix a les dades dun usuari que hi han a la BBDD
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class UserDAO {
    
    /**
     * Comprova si l'usuari i la contrasenya son correctes a la BBDD
     * @param userName Nom de l'usuari
     * @param pswd Contrasenya de l'usuari
     * @return Retorna true si hi es a la BBDD
     */
    public boolean login(String userName, String pswd) {
        
        boolean ret = false;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        /*
        //TODO: Fake test login
        String uName = "jose";
        String pass = "tutorfinder";
        if (userName.equals(uName)) {
            
            if (pass.equals(pswd)) {
                ret = true;
            }            
        }
        
        String uName2 = "alex";
        String pass2 = "tutorfinder";
        if (userName.equals(uName2)) {
            
            if (pass2.equals(pswd)) {
                ret = true;
            }            
        }
        
        String uName3 = "jaime";
        String pass3 = "tutorfinder";
        if (userName.equals(uName3)) {
            
            if (pass3.equals(pswd)) {
                ret = true;
            }            
        }
        */
        //TODO: Codi comentat per les proves sense servidor BBDD
       
        try {
            // Agafem una connexio del pool
            con = ConnectionPool.getPool().getConnection();
            
            //SQL
            String sql = "";
            sql += "SELECT user_name, user_pswd ";
            sql += "FROM users ";
            sql += "WHERE user_name = ?";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userName);
            rs = pstm.executeQuery();
            String dbPswd = null;
            
            while (rs.next()) {  
                
                dbPswd = rs.getString("user_pswd");
                System.out.println("user password dao: " + dbPswd);
            }
            
            
            // Comparem la contrasenya amb el resultat de la consulta
            if (pswd.equals(dbPswd)) {                
                ret = true;
            }
                    
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
            
        } finally {
            
            try {
                // Tanquem connexions
                if (rs != null) { rs.close();}
                if (pstm != null) { pstm.close();}
                
            } catch (Exception e) {
                
                e.printStackTrace();
                throw new RuntimeException(e);
                
            }
        }
        
        return ret;
    }
    /**
     * Demana les dades que hi ha d'un usuari a la BBDD 
     * @param userName Nom de l'usuari
     * @return Retorna un objecte UserDTO amb les dades de l'usuari
     */
    public UserDTO userData(String userName) {
        
        UserDTO user = new UserDTO();
        
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        //TODO: Fake DTO's
        /*
        if (userName == "jose") {
            user.setUserId(01);
            user.setUserName("jose");
            user.setUserMail("jose@gmail.com");
            user.setUserRole("admin");
        }

        if (userName == "alex") {
            user.setUserId(01);
            user.setUserName("alex");
            user.setUserMail("alex@gmail.com");
            user.setUserRole("tutor");
        }
        
        if (userName == "jaime") {
            user.setUserId(01);
            user.setUserName("jaime");
            user.setUserMail("jaime@gmail.com");
            user.setUserRole("alumne");
        }
        */


        
        
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT user_id, user_name, user_mail, user_pswd, role_name ";
            sql += "FROM users, roles ";
            sql += "WHERE users.user_name = ? AND users.user_role_id = roles.role_id";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userName);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                
                // Construïm l'usuari amb la resposta
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserMail(rs.getString("user_mail"));
                user.setUserPswd(rs.getString("user_pswd"));
                user.setUserRole(rs.getString("role_name"));
                System.out.println(user.toString());
                
            }
           
            
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            
            try {
                // Tanquem connexions
                if (rs != null) { rs.close();}
                if (pstm != null) { pstm.close();}
                
            } catch (Exception e) {
                
                e.printStackTrace();
                throw new RuntimeException(e);
                
            }
        }
        
        return user;
    }
    
}
