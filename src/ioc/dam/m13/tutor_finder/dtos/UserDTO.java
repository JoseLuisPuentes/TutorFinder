package ioc.dam.m13.tutor_finder.dtos;

/**
 *
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class UserDTO {

    public int userId;
    private String userName;
    private String userMail;
    private String userRole;
    private String userPswd;

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

    @Override
    public String toString() {
        return " --- UserDTO --- " + 
                "\n userId = " + userId + 
                "\n userName = " + userName + 
                "\n userMail = " + userMail + 
                "\n userRole = " + userRole + 
                "\n userPswd = " + userPswd;
    }
    

}
