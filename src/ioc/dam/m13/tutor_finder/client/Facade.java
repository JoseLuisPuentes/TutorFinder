/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ioc.dam.m13.tutor_finder.client;

import ioc.dam.m13.tutor_finder.server.UserDTO;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public interface Facade {
    
    /**
     * Demana per fer login al servidor
     * @param userName Nom d'usuari al servidor
     * @param pswd Contrasenya de l'usuari
     * @return True si l'usuari és al server
     */    
    public boolean login(String userName, String pswd);
    
    /**
     * Demana les dades de l'usuari
     * @param userId Nom de l'usuari
     * @return Retorna el objecte amb les dades d'usuari
     */
    public UserDTO userData(String userName);
    
}
