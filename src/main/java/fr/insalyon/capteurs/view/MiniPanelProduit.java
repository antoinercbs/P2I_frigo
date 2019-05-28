package fr.insalyon.capteurs.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MiniPanelProduit extends JPanel {
    String nom;
    BufferedImage img;
    String categorie;
    double contenance;
    double pourcentageRestant;
    String datePeremption;

    public MiniPanelProduit(String nom, String imgAdress, String categorie, double contenance, double pourcentageRestant, String datePeremption) {
        super();
        this.setLayout(null);
        this.setSize(200,300);

        try {
            this.img = resize(ImageIO.read(new URL(imgAdress)), 160, 180);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.nom=nom;
        this.categorie=categorie;
        this.contenance=contenance;
        this.pourcentageRestant=pourcentageRestant;
        this.datePeremption=datePeremption;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.lightGray);
        g2.fillRoundRect(2,2,this.getWidth()-3, this.getHeight()-3, 50, 50);
        g2.setColor(Color.gray);
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(2,2,this.getWidth()-3, this.getHeight()-3, 50, 50);

        g2.drawImage(this.img, 20,20, null);

        g2.setColor(Color.BLACK);
        drawCenteredString(g2, this.nom, new Rectangle(0,200, 200,30), new Font("Arial",Font.BOLD,20));

        drawCenteredString(g2, this.datePeremption, new Rectangle(0,270, 200,20), new Font("Arial",Font.PLAIN,15));

        //dessin de la zone de remplissage
        g2.drawString(this.contenance + " g", 10, 245);
        g2.setColor(StandardPanel.COULEUR);
        g2.fillRect(70, 230, (int) (110*this.pourcentageRestant/100.0), 20);
        g2.setColor(Color.BLACK);
        g2.drawRect(70, 230, 110, 20);




    }


    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }


    public void drawCenteredString(Graphics2D g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }
}
