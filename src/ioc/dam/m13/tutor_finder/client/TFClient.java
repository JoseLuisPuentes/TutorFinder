package ioc.dam.m13.tutor_finder.client;

import ioc.dam.m13.tutor_finder.dtos.AdDTO;
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
    
    /**
     * Modifica les dades d'un usuari 
     * @param userId Int amb el id de l'usuari
     * @param userName String amb el nou nom de l'usuari
     * @param userMail String amb el nou mail de l'usuari
     * @param userRole String amd el nou role de l'usuari
     * @return Retorna true si s'han canviat les dades correctament
     */
    public boolean editUser(int userId, String userName, String userMail, String userRole);
    
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
    
    
    /**
     * Modifica el password de l'usuari
     * @param userName String amb el nom de l'usuari
     * @param newPswd Srting amb el password nou a cambiar
     * @return Retorna true si s'ha cambiat correctament
     */
    public boolean editUserPswd(String userName, String newPswd);
    
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
    
    /**
     * Crea un anunci nou
     * @param userId int amb l'id de l'usuari que crea l'anunci
     * @param tittle String amb el titol de l'anunci
     * @param description String amb el text de l'anunci
     * @param adTypeId int amd el id del tipus d'anunci que es vol publicar
     * @param price int amb el preu del servei que ofereix
     * @return retorna true si es crea correctament
     */
    public boolean createAd (int userId, String tittle, String description, int adTypeId, int price);
    
    /**
     * Llista tots els anuncis 
     * @return Retorna un ArrayList de objectes AdDTO 
     */
    public ArrayList<AdDTO> listAds();
    
    /**
     * Llista tots els anuncis que ha creat un usuari
     * @param userId int amb el id de l'usuari
     * @return Retorna un ArrayList de objectes AdDTO 
     */
    public ArrayList<AdDTO> listAdsByUser(int userId);
    
    /**
     * Llista els tots els anuncis del mateix tipus
     * @param roleId int amb l'id del tipus de rol
     * @return Retorna un ArrayList de objectes AdDTO 
     */
    public ArrayList<AdDTO> listAdsByRole(int roleId);
    
    /**
     * Llista tots els anuncis del mateix tipus
     * @param typeId int amb l'id del tipus d'anunci
     * @return Retorna un ArrayList de objectes AdDTO 
     */
    public ArrayList<AdDTO> listAdsByType(int typeId);
    
    /**
     * Modifica les dades s'un anunci
     * @param adId int amb l'id de l'anunci
     * @param tittle String amb la modificació del títol 
     * @param description String amb la modificació de la descripció
     * @param adTypeId int amb l'id del tipus d'anunci al que es vol modificar
     * @param price int amb el nou preu
     * @return Retorna true si s'ha modificat correctament
     */
    public boolean editAd(int adId, String tittle, String description, int adTypeId, int price);
    
    /**
     * Esborra un anunci
     * @param adId int amb l'id de l'anunci que es vol esborrar
     * @return Retorna true si s'ha esborrat correctament
     */
    public boolean delAd(int adId);
    
    /**
     * Llista tots el tipus d'anunci 
     * @return HashMap amb l'id i el lnom del tipus d'anunci
     */
    public HashMap<Integer, String> getAdTypes();
    
    /**
     * Retorna el nom del tipus d'anunci demanat pel seu id
     * @param adTypeId int amb l'id del tipus d'anunci
     * @return Retorna un String amb el nom del tipus d'anunci
     */
    public String getAdTypeById(int adTypeId);
    
    /**
     * Retorna l'id del tipus d'anunci demanat pel seu nom
     * @param adTypeName String amb el nom del tipus d'anunci
     * @return Retorna un int amb l'id del tipus d'anunci
     */
    public int getAdTypeByName(String adTypeName);
    
}
