package ioc.dam.m13.tutor_finder.client;

import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.util.ArrayList;

/**
 * Interface per crear les diferents implementacions 
 * de clients amb els serveis del TFserver
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public interface TFClient {
    /**
     * Demana per fer login al servidor
     * @param userName Nom d'usuari al servidor
     * @param pswd Contrasenya de l'usuari
     * @return True si l'usuari és al server
     */    
    public boolean login(String userName, String pswd);
    
    /**
     * Demana les dades de l'usuari
     * @param userName Nom de l'usuari
     * @return Retorna el objecte amb les dades d'usuari
     */
    public UserDTO userData(String userName);
    
    /**
     * Crea un usuari nou
     * @param userName String amb el Nom de l'usuari
     * @param userMail String amb el Mail de l'usuari
     * @param userPswd String amb la Contrasenya de l'usuari
     * @param userRole String amb el Role de l'usuari
     * @return Retorna true si s'ha inserit correctament
     */
    public boolean newUser(String userName, String userMail, String userPswd, String userRole);
    /*
    //TODO: codificar editUser TFC
    public boolean editUser(String userName){
        boolean ret = false;
        
        return ret;
    }
    
    //TODO: codificar delUser TFC
    public boolean delUser(String userName){
        boolean ret = false;
        
        return ret;
    }
    */
    //TODO: codificar listUsers TFC
    /**
     * Llista tots els usuaris
     * @return Retorna un array de UserDTO's amb les dades dels usuaris
     */
    public ArrayList<UserDTO> listUsers();
    
    /**
     * Llista els usuaris que siguin del mateix rol
     * @param roleName String amb el nom del rol
     * @return Retorna un array de UserDTO's amb les dades dels usuaris amb el mateix rol 
     */
    public ArrayList<UserDTO> listUsers(String roleName);
    
    /*
    //TODO: codificar editUserPswd TFC
    public boolean editUserPswd(String userName, String pswd){
        boolean ret = false;
        
        return ret;
    }
    
    
    //TODO: codificar getUserRoles TFC
    public HashMap<Integer, String> getUserRoles(){
        HashMap<Integer, String> userRoles = new HashMap<>();
        
        return userRoles;
    }
    
    public int getUserRoles(String userName){
        int ret = -1;
        
        return ret;
    }
    
    public String getUserRoles(int roleId){
        String ret = null;
        
        return ret;
    */
}
