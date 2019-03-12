package ioc.dam.m13.tutor_finder.server;

import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class UserDAO {
    
    
    public boolean login(String userName, String pswd) {
        
        boolean ret = false;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
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
        
        //TODO: Codi comentat per les proves sense servidor BBDD
        /*
        try {
            // Agafem una connexio del pool
            con = ConnectionPool.getPool().geConnection();
            
            //SQL
            String sql = "";
            sql += "SELECT userName, userPswd ";
            sql += "FROM users ";
            sql += "WHERE userName = ?";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userName);
            rs = pstm.executeQuery();
            
            String dbPswd = rs.getString("userPswd");
            
            // Comparem la contrasenya amb el resultat de la consulta
            if (pswd == dbPswd) {                
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
        }*/
        return ret;
    }
    
    public UserDTO userData(String userName) {
        
        UserDTO user = new UserDTO();
        
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        //TODO: Fake DTO's
        
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


        
        /*
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().geConnection();
            //SQL
            String sql = "";
            sql += "SELECT * ";
            sql += "FROM users ";
            sql += "WHERE userName = ?";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userName);
            rs = pstm.executeQuery();
            
            if (rs != null) {
                // Construïm l'usuari amb la resposta
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setUserMail(rs.getString("userMail"));
                user.setUserRole(rs.getString("userRole"));
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
        */
        return user;
    }
    
}
