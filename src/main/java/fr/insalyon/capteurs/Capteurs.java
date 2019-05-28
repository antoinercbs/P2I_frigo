/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.capteurs;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import fr.insalyon.capteurs.view.Events;
import fr.insalyon.capteurs.view.Fenetre;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import fr.insalyon.p2i2.javaarduino.BDFlux;
import org.json.JSONObject;

public class Capteurs {
    
    public static Fenetre F;
    public static Events eventsHandler;
    public static BDFlux maBD;
    
    public static void main(String[] args) {
        maBD =  new BDFlux("G221_C_BD2","G221_C","G221_C");
        eventsHandler = new Events();
         F = new Fenetre(eventsHandler);
    }
    
   
    
}
