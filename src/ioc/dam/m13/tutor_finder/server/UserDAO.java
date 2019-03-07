package ioc.dam.m13.tutor_finder.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public abstract class UserDAO {
    
    //TODO: SQL login i SQL userData
    public boolean login(String userName, String pswd) {
        
        boolean ret = false;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            // Agafem una connexio del pool
            con = ConnectionPool.getPool().geConnection();
            
            //SQL
            String sql = "";
            sql += "SELECT userName, userPswd ";
            sql += "FROM user ";
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
        }
        return ret;
    }
    
    public UserDTO userData(String userName) {
        
        UserDTO user = new UserDTO();
        
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().geConnection();
            //SQL
            String sql = "";
            sql += "SELECT * ";
            sql += "FROM user ";
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
                user.setUserRol(rs.getString("userRol"));
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
