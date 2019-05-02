package ioc.dam.m13.tutor_finder.client;

import ioc.dam.m13.tutor_finder.dtos.AdDTO;
import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Clase que implemeta el client per demanar els serveis del 
 *  servidor
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TFClientImple implements TFClient{

    public TFClientImple() {
        
        System.setProperty("javax.net.ssl.trustStore", "src/certs/client/ClientKeyStore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "tutorfinder");
        System.getProperties().list(System.out);
        System.setProperty("javax.net.debug", "SSL,handshake");
        
    }
    
    @Override
    public boolean login(String userName, String pswd) {
        
        return ServiceLocator.login(userName, pswd);
        
    }
    
    @Override
    public UserDTO userData(String userName) {
        
        return ServiceLocator.userData(userName);
        
    }

    @Override
    public boolean newUser(String userName, String userMail, String userPswd, String userRole) {
        
        return ServiceLocator.newUser(userName, userMail, userPswd, userRole);
        
    }

    @Override
    public ArrayList<UserDTO> listUsers() {
        
        return ServiceLocator.listUsers();
    }

    @Override
    public ArrayList<UserDTO> listUsers(String roleName) {
        
        return ServiceLocator.listUsers(roleName);
    }

    @Override
    public HashMap<Integer, String> getUserRoles() {
        
        return ServiceLocator.getUserRoles();
    }

    @Override
    public int getUserRoleId(String roleName) {
        
        return ServiceLocator.getUserRoles(roleName);
    }

    @Override
    public String getUserRoleName(int roleId) {
        
        return ServiceLocator.getUserRoles(roleId);
    }

    @Override
    public boolean delUser(String userName) {
        
        return ServiceLocator.delUser(userName);
    }

    @Override
    public boolean editUserPswd(String userName, String newPswd) {
        
        return ServiceLocator.editUserPswd(userName, newPswd);
    }

    @Override
    public boolean editUser(int userId, String userName, String userMail, String userRole) {
        
        return ServiceLocator.editUser(userId, userName, userMail, userRole);
    }

    @Override
    public boolean createAd(int userId, String tittle, String description, int adTypeId, int price) {
        
        return ServiceLocator.createAd(userId, tittle, description, adTypeId, price);
    }

    @Override
    public ArrayList<AdDTO> listAds() {
        
        return ServiceLocator.listAds();        
    }

    @Override
    public ArrayList<AdDTO> listAdsByUser(int userId) {
        
        return ServiceLocator.listAdsByUser(userId);
    }

    @Override
    public ArrayList<AdDTO> listAdsByRole(int roleId) {
        
        return ServiceLocator.listAdsByRole(roleId);
    }

    @Override
    public ArrayList<AdDTO> listAdsByType(int typeId) {
        
        return ServiceLocator.listAdsByType(typeId);
    }

    @Override
    public boolean editAd(int adId, String tittle, String description, int adTypeId, int price) {
        
        return ServiceLocator.editAd(adId, tittle, description, adTypeId, price);
    }

    @Override
    public boolean delAd(int adId) {
        
        return ServiceLocator.delAd(adId);
    }

    @Override
    public HashMap<Integer, String> getAdTypes() {
        
        return ServiceLocator.getAdTypes();
    }

    @Override
    public String getAdTypeById(int adTypeId) {
        
        return ServiceLocator.getAdTypeById(adTypeId);
    }

    @Override
    public int getAdTypeByName(String adTypeName) {
        
        return ServiceLocator.getAdTypeByName(adTypeName);
    }

}
