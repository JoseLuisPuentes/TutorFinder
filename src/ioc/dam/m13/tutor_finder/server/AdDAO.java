package ioc.dam.m13.tutor_finder.server;

import ioc.dam.m13.tutor_finder.dtos.AdDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Accedeix a les dades d'un anunci que hi ha a la BBDD
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class AdDAO {
    
    //TODO: crear anunci
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
    
    //TODO: llistar anuncis
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
    
    public ArrayList<AdDTO> listAdsByUser(int userId) {
        ArrayList<AdDTO> ads = new ArrayList<>();
        
        return ads;
    }
    
    public ArrayList<AdDTO> listAdsByRole(int roleId){
        ArrayList<AdDTO> ads = new ArrayList<>();
        
        return ads;
    }
    
    public ArrayList<AdDTO> listAdsByType(int typeId){
        ArrayList<AdDTO> ads = new ArrayList<>();
        
        return ads;
    }
    //TODO: editar anuncis
    public boolean editAd(int adId, String tittle, String description, int adTypeId, int price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //TODO: esborrar anuncis
    public boolean delAd(int adId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
    
        
}
