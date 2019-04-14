package ioc.dam.m13.tutor_finder.dtos;

/**
 * Objecte Anunci
 * @author José Luis Puentes Jiménez <jlpuentes74@gmail.com>
 */
public class AdDTO {
    
    //Atributs
    private int adId;
    private int adUserId;
    private String userName;    
    private String adTittle;
    private String adDescription;
    private int adTypeId;
    private String typesName;
    private int adPrice;
    
    //Getters
    public int getAdId() {
        return adId;
    }

    public int getAdUserId() {
        return adUserId;
    }
    
    public String getUserName() {
        return userName;
    }

    public String getAdTittle() {
        return adTittle;
    }

    public String getAdDescription() {
        return adDescription;
    }

    public int getAdTypeId() {
        return adTypeId;
    }

    public String getTypesName() {
        return typesName;
    }
    
    public int getAdPrice() {
        return adPrice;
    }
    
    //Setters
    public void setAdId(int adId) {
        this.adId = adId;
    }

    public void setAdUserId(int adUserId) {
        this.adUserId = adUserId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setAdTittle(String adTittle) {
        this.adTittle = adTittle;
    }

    public void setAdDescription(String adDescription) {
        this.adDescription = adDescription;
    }

    public void setAdTypeId(int adTypeId) {
        this.adTypeId = adTypeId;
    }

    public void setTypesName(String typesName) {
        this.typesName = typesName;
    }

    public void setAdPrice(int adPrice) {
        this.adPrice = adPrice;
    }

    @Override
    public String toString() {
        return "Ad:" + "adId=" + adId + ", adUserId=" + adUserId + ", userName=" + userName + ", adTittle=" + adTittle + ", adDescription=" + adDescription + ", adTypeId=" + adTypeId + ", typesName=" + typesName + ", adPrice=" + adPrice;
    }

    
    
    
    
       
}
