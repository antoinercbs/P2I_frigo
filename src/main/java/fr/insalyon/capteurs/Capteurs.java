/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.capteurs;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import fr.insalyon.capteurs.view.Fenetre;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.json.JSONObject;

public class Capteurs {
    
    public static Fenetre F;
    
    public static void main(String[] args) {
         F = new Fenetre();
    }
    
    static void displayPopupFromGtin(String gtin) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://fr.openfoodfacts.org/api/v0/produit/" + gtin + ".json")
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            String productDescription = response.body().string();
            JSONObject product = new JSONObject(productDescription);

            String name = product.getJSONObject("product").getString("product_name");
            //String description = product.getJSONObject("product").getString("generic_name");

            String url = product.getJSONObject("product").getString("image_front_url");
            BufferedImage img = null;
            img = ImageIO.read(new URL(url));
            ImageIcon icon = new ImageIcon(img);

            JOptionPane.showMessageDialog(
                    null,
                    "Nom : " + name  /*"\nDescription : " + description*/,
                    "PUTAIN LES GARS C'EST TROP BIEN JE VAIS CHIALLER !!!!!!", JOptionPane.INFORMATION_MESSAGE,
                    icon);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
