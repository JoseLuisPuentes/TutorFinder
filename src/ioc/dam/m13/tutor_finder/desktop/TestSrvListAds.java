/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ioc.dam.m13.tutor_finder.desktop;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import ioc.dam.m13.tutor_finder.dtos.AdDTO;
import java.util.ArrayList;

/**
 *
 * @author Alexandre
 */
public class TestSrvListAds {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        TestSrvListAds prg = new TestSrvListAds();
        prg.inici();

    }
    
    public void inici(){
        
        TFClient client = new TFClientImple();
        
        
        System.out.println("----- ListAds -----");
        ArrayList<AdDTO> ads = client.listAds();
        
        for (AdDTO ad : ads) {
            System.out.println("----- Ads -----");
            System.out.println(ad.toString());
            System.out.println("--------------------");
        }
        
    }

}