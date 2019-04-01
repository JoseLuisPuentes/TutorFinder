package ioc.dam.m13.tutor_finder.server;

import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Accedeix a les dades d'un usuari que hi han a la BBDD
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
    /**
     * Connecta a la BBDD i crea un usuari nou
     * @param userName String amb el nom de l'usuari
     * @param userMail String amd el mail de l'usuari
     * @param userPswd String amd la contrasenya de l'usuari
     * @param roleName String amb rol de l'usuari
     * @return retorna true si s'ha inserit a la BBDD
     */
    
    public boolean newUser(String userName, String userMail, String userPswd, String roleName){
        boolean ret = false;
        int result = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "INSERT INTO users (user_name, user_mail, user_pswd, user_role_id)";
            sql += "VALUES (?, ?, ?, ?) ";
            //TODO: prova de ficar role
            int roleId = getUserRoles(roleName);
                        
            //preparem la inserció
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userName);
            pstm.setString(2, userMail);
            pstm.setString(3, userPswd);
            pstm.setInt(4, roleId);

            result = pstm.executeUpdate();
            con.commit();
            
            if (result > 0) {
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
    
    //TODO: codificar editUser
    public boolean editUser(String userName){
        boolean ret = false;
        
        return ret;
    }
    
    //TODO: codificar delUser
    public boolean delUser(String userName){
        
        boolean ret = false;
        int result = 0;
        
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "DELETE FROM users ";
            sql += "WHERE users.user_name = ?";
            
            //preparem la eliminació
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userName);
            //Executem
            result = pstm.executeUpdate();
            con.commit();
            
            if (result > 0) {
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
     * Llista tots els usuaris que hi ha a la BBDD
     * @return Retorna un array de UserDTO's amb les dades dels usuaris
     */
    public ArrayList<UserDTO> listUsers(){
        ArrayList<UserDTO> users = new ArrayList<>();
                
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT user_id, user_name, user_mail, user_pswd, role_name ";
            sql += "FROM users, roles ";
            sql += "WHERE users.user_role_id = roles.role_id";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                // Construïm l'usuari amb la resposta
                UserDTO user = new UserDTO();
                
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserMail(rs.getString("user_mail"));
                user.setUserPswd(rs.getString("user_pswd"));
                user.setUserRole(rs.getString("role_name"));
                
                users.add(user);
                
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
        
        return users;
    }
    
    /**
     * Llista els usuaris que siguin del mateix rol que hi ha a la BBDD
     * @param roleName String amb el nom del rol
     * @return Retorna un array de UserDTO's amb les dades dels usuaris amb el mateix rol 
     */
    public ArrayList<UserDTO> listUsers(String roleName){
        ArrayList<UserDTO> users = new ArrayList<>();
        
        
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT user_id, user_name, user_mail, user_pswd, role_name ";
            sql += "FROM users, roles ";
            sql += "WHERE users.user_role_id = roles.role_id AND roles.role_name = ?";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            pstm.setString(1, roleName);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                
                // Construïm l'usuari amb la resposta
                UserDTO user = new UserDTO();
                
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserMail(rs.getString("user_mail"));
                user.setUserPswd(rs.getString("user_pswd"));
                user.setUserRole(rs.getString("role_name"));
                
                users.add(user);
                
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
        return users;
    }
    
    //TODO: codificar editUserPswd
    public boolean editUserPswd(String userName, String pswd){
        boolean ret = false;
        
        return ret;
    }
    
    
    //TODO: codificar getUserRoles
    public HashMap<Integer, String> getUserRoles(){
        
        HashMap<Integer, String> userRoles = new HashMap<>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT role_id, role_name ";
            sql += "FROM roles ";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                
                int id = rs.getInt("role_id");
                String name = rs.getString("role_name");                
                userRoles.put(id, name);
                                
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
        
        return userRoles;
    }
    
    public int getUserRoles(String roleName){
        int ret = -1;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT role_id ";
            sql += "FROM roles ";
            sql += "WHERE roles.role_name = ?";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            pstm.setString(1, roleName);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                
                ret = rs.getInt("role_id");
                                
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
    
    public String getUserRoles(int roleId){
        
        String ret = null;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT role_name ";
            sql += "FROM roles ";
            sql += "WHERE roles.role_id = ?";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, roleId);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                
                ret = rs.getString("role_name");
                                
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
}
