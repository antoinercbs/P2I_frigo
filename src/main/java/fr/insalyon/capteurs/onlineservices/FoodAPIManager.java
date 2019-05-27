/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.capteurs.onlineservices;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 * @author Antoine Rochebois
 */
public class FoodAPIManager {

    private OkHttpClient client;
    JSONObject currentItem;

    public FoodAPIManager() {
        client = new OkHttpClient();
    }

    public void fetchDataFromGtin(String gtin) {
        Request request = new Request.Builder()
                .url("https://fr.openfoodfacts.org/api/v0/produit/" + gtin + ".json")
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            String productDescription = response.body().string();
            this.currentItem = new JSONObject(productDescription);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getProductName() {
        if (this.currentItem != null) {
            return this.currentItem.getJSONObject("product").getString("product_name");
        } 
        return null;
    }

    public String getProductImageURL() {
        if (this.currentItem != null) {
            return this.currentItem.getJSONObject("product").getString("image_front_url");
        } 
        return null;
    }
    
    public String getProductCategorie() {
        if (this.currentItem != null) {
            return this.currentItem.getJSONObject("product").getString("categories");
        } 
        return null;
    }
    
    public double getContenance() {
        if (this.currentItem != null) {
            return this.currentItem.getJSONObject("product").getDouble("product_quantity");
        } 
        return -1;
    }

    public int getProductDuration() {
        return (int) (Math.random()*7+7);
    }
    
    public String getContenanceUnit() {
        return "g";
    }
}
