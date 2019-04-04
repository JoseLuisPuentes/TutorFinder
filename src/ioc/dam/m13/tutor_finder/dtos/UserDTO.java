package ioc.dam.m13.tutor_finder.dtos;

/**
 * Objecte usuari
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class UserDTO {
    
    //Atributs
    private int userId;
    private String userName;
    private String userMail;
    private String userRole;
    private String userPswd;
    
    //Getters
    public String getUserPswd() {
        return userPswd;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getUserRole() {
        return userRole;
    }
    
    //Setters
    public void setUserPswd(String userPswd) {
        this.userPswd = userPswd;
    }
    
    public void setUserId(int userId) {
        
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
//toString()
    @Override
    public String toString() {
        return "Id = " + userId + 
                "\t Name = " + userName + 
                "\t Mail = " + userMail + 
                "\t Password = " + userPswd +
                "\t Role = " + userRole;
    }
    

}
