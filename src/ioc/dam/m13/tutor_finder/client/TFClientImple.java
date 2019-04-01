package ioc.dam.m13.tutor_finder.client;

import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Clase que implemeta el client per demanar els serveis del 
 *  servidor
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class TFClientImple implements TFClient{

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
    

}
