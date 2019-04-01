package ioc.dam.m13.tutor_finder.client;

import ioc.dam.m13.tutor_finder.dtos.UserDTO;
import java.util.ArrayList;
import java.util.HashMap;

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
    */
    /**
     * Elimina un usuari
     * @param userName String amb el nom de l'usuari a eliminar
     * @return Retorna True si s'ha eliminat
     */
    public boolean delUser(String userName);
    
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
    */
    
    //TODO: codificar getUserRoles TFC
    /**
     * Llista tots els rols
     * @return Retorna un HashMap<Integer,String> amb tots els rols
     */
    public HashMap<Integer, String> getUserRoles();
    
    /**
     * Lista el id del del rol demanat pel seu nom
     * @param roleName Stringa amb el nom del rol
     * @return Retorna un Int amb el Id del rol
     */
    public int getUserRoleId(String roleName);
    
    /**
     * Llista el nom del rol demanta pel seu id
     * @param roleId Int amb el id 
     * @return 
     */
    public String getUserRoleName(int roleId);
}
