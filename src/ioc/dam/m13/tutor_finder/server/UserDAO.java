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
                ConnectionPool.getPool().releaseConnection(con);
                
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
                ConnectionPool.getPool().releaseConnection(con);
                
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
        int result;
        
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
            //Busquem el id del rol
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
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                throw new RuntimeException(e);
                
            }
        }
        return ret;
    }
    
    /**
     * Modifica les dades d'un usuari a la BBDD
     * @param userId Int amb el id de l'usuari
     * @param userName String amb el nou nom de l'usuari
     * @param userMail String amb el nou mail de l'usuari
     * @param userRole String amd el nou role de l'usuari
     * @return Retorna true si s'han canviat les dades correctament
     */
    public boolean editUser(int userId, String userName, String userMail, String userRole){
        
        boolean ret = false;        
        int result;
        
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            //String sql = "update users set user_name = ?, user_mail = ?, user_role_id = ? where user_id = ?";
            String sql = "";
            sql += "UPDATE users ";
            sql += "SET user_name = ?, user_mail = ?, user_role_id = ? ";
            sql += "WHERE user_id = ? ";
            
            //Busquem el id del rol
            int roleId = getUserRoles(userRole);
                        
            //preparem la inserció
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userName);
            pstm.setString(2, userMail);
            pstm.setInt(3, roleId);
            pstm.setInt(4, userId);

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
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                throw new RuntimeException(e);
                
            }
        }
        
        return ret;
    }
    
    /**
     * Elimina un usuari de la BBDD
     * @param userName String amb el nom de l'usuari a eliminar
     * @return Retorna True si s'ha eliminat
     */
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
                ConnectionPool.getPool().releaseConnection(con);
                
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
            sql += "WHERE users.user_role_id = roles.role_id ";
            sql += "ORDER BY user_id ";
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
                ConnectionPool.getPool().releaseConnection(con);
                
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
            sql += "ORDER BY user_id ";
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
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                throw new RuntimeException(e);
                
            }
        }
        return users;
    }
    

    /**
     * Modifica el password de l'usuari
     * @param userName String amb el nom de l'usuari
     * @param newPswd Srting amb el password nou a cambiar
     * @return Retorna true si s'ha cambiat correctament
     */
    public boolean editUserPswd(String userName, String newPswd){
        
        boolean ret = false;
        int result;
        
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "UPDATE users ";
            sql += "SET user_pswd = ? ";
            sql += "WHERE user_name = ?";
                                   
            //preparem la inserció
            pstm = con.prepareStatement(sql);
            pstm.setString(1, newPswd);
            pstm.setString(2, userName);
            
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
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                throw new RuntimeException(e);
                
            }
        }
        return ret;
    }
    /**
     * Llista els rols amd els seus id's
     * @return Retorna un HashMap amb tots els rols i els seus id's
     */
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
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                throw new RuntimeException(e);
                
            }
        }
        
        return userRoles;
    }
    /**
     * Demana quin id te el rol pel seu nom
     * @param roleName String amb el nom del rol
     * @return Retorna un int amb l'id del rol demanat
     */
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
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                throw new RuntimeException(e);
                
            }
        }
        
        return ret;
    }
    /**
     * Demana quin nom te el rol pel seu id
     * @param roleId int amb l'id del rol
     * @return Retorna un String amb el nom del rol demanat
     */
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
                ConnectionPool.getPool().releaseConnection(con);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                throw new RuntimeException(e);
                
            }
        }
        
        return ret;
    }
}
