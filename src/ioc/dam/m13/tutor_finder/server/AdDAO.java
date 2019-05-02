package ioc.dam.m13.tutor_finder.server;

import ioc.dam.m13.tutor_finder.dtos.AdDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Accedeix a les dades d'un anunci que hi ha a la BBDD
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class AdDAO {
    
    /**
     * Crea un anunci nou
     * @param userId int amb l'id de l'usuari que crea l'anunci
     * @param tittle String amb el titol de l'anunci
     * @param description String amb el text de l'anunci
     * @param adTypeId int amd el id del tipus d'anunci que es vol publicar
     * @param price int amb el preu del servei que ofereix
     * @return retorna true si es crea correctament
     */
    public boolean createAd (int userId, String tittle, String description, int adTypeId, int price){
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
            sql += "INSERT INTO ads (ad_user_id, ad_tittle, ad_description, ad_type_id, ad_price)";
            sql += "VALUES (?, ?, ?, ?, ?) ";
            
                        
            //preparem la inserció
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, userId);
            pstm.setString(2, tittle);
            pstm.setString(3, description);
            pstm.setInt(4, adTypeId);
            pstm.setInt(5, price);

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
     * Llista tots els anuncis 
     * @return Retorna un ArrayList de objectes AdDTO 
     */
    public ArrayList<AdDTO> listAds(){
        ArrayList<AdDTO> ads = new ArrayList<>();
        
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT ad_id, "
                    + "ad_user_id, "
                    + "(select user_name from users where ad_user_id = user_id), "
                    + "ad_tittle, "
                    + "ad_description, "
                    + "ad_type_id, "
                    + "(select ad_types_name from ad_types where ad_type_id = ad_types_id), "
                    + "ad_price ";
            sql += "FROM ads ";
            sql += "ORDER BY ad_id ";
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                // Construïm l'usuari amb la resposta
                AdDTO ad = new AdDTO();
                
                ad.setAdId(rs.getInt("ad_id"));
                ad.setAdUserId(rs.getInt("ad_user_id"));
                ad.setUserName(rs.getString("user_name"));
                ad.setAdTittle(rs.getString("ad_tittle"));
                ad.setAdDescription(rs.getString("ad_description"));
                ad.setAdTypeId(rs.getInt("ad_type_id"));
                ad.setTypesName(rs.getString("ad_types_name"));
                ad.setAdPrice(rs.getInt("ad_price"));
                
                ads.add(ad);
                
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
        return ads;
    }
    
    /**
     * Llista tots els anuncis que ha creat un usuari
     * @param userId int amb el id de l'usuari
     * @return Retorna un ArrayList de objectes AdDTO 
     */
    public ArrayList<AdDTO> listAdsByUser(int userId) {
        ArrayList<AdDTO> ads = new ArrayList<>();
        
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT ad_id, "
                    + "ad_user_id, "
                    + "(select user_name from users where ad_user_id = user_id), "
                    + "ad_tittle, "
                    + "ad_description, "
                    + "ad_type_id, "
                    + "(select ad_types_name from ad_types where ad_type_id = ad_types_id), "
                    + "ad_price ";
            sql += "FROM ads ";
            sql += "WHERE ad_user_id = ? ";
            sql += "ORDER BY ad_id ";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, userId);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                // Construïm l'usuari amb la resposta
                AdDTO ad = new AdDTO();
                
                ad.setAdId(rs.getInt("ad_id"));
                ad.setAdUserId(rs.getInt("ad_user_id"));
                ad.setUserName(rs.getString("user_name"));
                ad.setAdTittle(rs.getString("ad_tittle"));
                ad.setAdDescription(rs.getString("ad_description"));
                ad.setAdTypeId(rs.getInt("ad_type_id"));
                ad.setTypesName(rs.getString("ad_types_name"));
                ad.setAdPrice(rs.getInt("ad_price"));
                
                ads.add(ad);
                
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
        return ads;
    }
    
    /**
     * Llista els tots els anuncis del mateix tipus
     * @param roleId int amb l'id del tipus de rol
     * @return Retorna un ArrayList de objectes AdDTO 
     */
    public ArrayList<AdDTO> listAdsByRole(int roleId){
        ArrayList<AdDTO> ads = new ArrayList<>();
        
        
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT ad_id, "
                    + "ad_user_id, "
                    + "(select user_name from users where ad_user_id = user_id), "
                    + "ad_tittle, "
                    + "ad_description, "
                    + "ad_type_id, "
                    + "(select ad_types_name from ad_types where ad_type_id = ad_types_id), "
                    + "ad_price ";
            sql += "FROM ads, users ";
            sql += "WHERE ad_user_id = user_id AND user_role_id = ? ";
            sql += "ORDER BY ad_id ";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, roleId);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                // Construïm l'usuari amb la resposta
                AdDTO ad = new AdDTO();
                
                ad.setAdId(rs.getInt("ad_id"));
                ad.setAdUserId(rs.getInt("ad_user_id"));
                ad.setUserName(rs.getString("user_name"));
                ad.setAdTittle(rs.getString("ad_tittle"));
                ad.setAdDescription(rs.getString("ad_description"));
                ad.setAdTypeId(rs.getInt("ad_type_id"));
                ad.setTypesName(rs.getString("ad_types_name"));
                ad.setAdPrice(rs.getInt("ad_price"));
                
                ads.add(ad);
                
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
        return ads;
    }
    
    /**
     * Llista tots els anuncis del mateix tipus
     * @param typeId int amb l'id del tipus d'anunci
     * @return Retorna un ArrayList de objectes AdDTO 
     */
    public ArrayList<AdDTO> listAdsByType(int typeId){
        ArrayList<AdDTO> ads = new ArrayList<>();
        
        
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT ad_id, "
                    + "ad_user_id, "
                    + "(select user_name from users where ad_user_id = user_id), "
                    + "ad_tittle, "
                    + "ad_description, "
                    + "ad_type_id, "
                    + "(select ad_types_name from ad_types where ad_type_id = ad_types_id), "
                    + "ad_price ";
            sql += "FROM ads ";
            sql += "WHERE ad_type_id = ? ";
            sql += "ORDER BY ad_id ";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, typeId);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                // Construïm l'usuari amb la resposta
                AdDTO ad = new AdDTO();
                
                ad.setAdId(rs.getInt("ad_id"));
                ad.setAdUserId(rs.getInt("ad_user_id"));
                ad.setUserName(rs.getString("user_name"));
                ad.setAdTittle(rs.getString("ad_tittle"));
                ad.setAdDescription(rs.getString("ad_description"));
                ad.setAdTypeId(rs.getInt("ad_type_id"));
                ad.setTypesName(rs.getString("ad_types_name"));
                ad.setAdPrice(rs.getInt("ad_price"));
                
                ads.add(ad);
                
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
        return ads;
    }
    
    /**
     * Modifica les dades s'un anunci
     * @param adId int amb l'id de l'anunci
     * @param tittle String amb la modificació del títol 
     * @param description String amb la modificació de la descripció
     * @param adTypeId int amb l'id del tipus d'anunci al que es vol modificar
     * @param price int amb el nou preu
     * @return Retorna true si s'ha modificat correctament
     */
    public boolean editAd(int adId, String tittle, String description, int adTypeId, int price) {
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
            sql += "UPDATE ads ";
            sql += "SET ad_tittle = ?, ad_description = ?, ad_type_id = ?, ad_price = ? ";
            sql += "WHERE ad_id = ? ";
                        
            //preparem la inserció
            pstm = con.prepareStatement(sql);
            pstm.setString(1, tittle);
            pstm.setString(2, description);
            pstm.setInt(3, adTypeId);
            pstm.setInt(4, price);
            pstm.setInt(5, adId);

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
     * Esborra un anunci
     * @param adId int amb l'id de l'anunci que es vol esborrar
     * @return Retorna true si s'ha esborrat correctament
     */
    public boolean delAd(int adId) {
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
            sql += "DELETE FROM ads ";
            sql += "WHERE ad_id = ?";
            
            //preparem la eliminació
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, adId);
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
     * Llista tots el tipus d'anunci 
     * @return HashMap amb l'id i el lnom del tipus d'anunci
     */
    public HashMap<Integer, String> getAdTypes(){
        HashMap<Integer, String> adTypes = new HashMap<>();
        
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT ad_types_id, ad_types_name ";
            sql += "FROM ad_types ";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                
                int id = rs.getInt("ad_types_id");
                String name = rs.getString("ad_types_name");                
                adTypes.put(id, name);
                                
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
        return adTypes;
    }
    
    /**
     * Retorna el nom del tipus d'anunci demanat pel seu id
     * @param adTypeId int amb l'id del tipus d'anunci
     * @return Retorna un String amb el nom del tipus d'anunci
     */
    public String getAdTypeById(int adTypeId){
        
        String ret = null;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT ad_types_name ";
            sql += "FROM ad_types ";
            sql += "WHERE ad_types_id = ?";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, adTypeId);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                
                ret = rs.getString("ad_types_name");
                                
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
     * Retorna l'id del tipus d'anunci demanat pel seu nom
     * @param adTypeName String amb el nom del tipus d'anunci
     * @return Retorna un int amb l'id del tipus d'anunci
     */
    public int getAdTypeByName(String adTypeName){
        
        int ret = -1;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
               
        try {
            // Agafem una connexió del pool
            con = ConnectionPool.getPool().getConnection();
            //SQL
            String sql = "";
            sql += "SELECT ad_types_id ";
            sql += "FROM ad_types ";
            sql += "WHERE ad_types_name = ?";
            
            //Fem la consulta
            pstm = con.prepareStatement(sql);
            pstm.setString(1, adTypeName);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                
                ret = rs.getInt("ad_types_id");
                                
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
