package fr.insalyon.frigoconnecte.view;

import fr.insalyon.frigoconnecte.Main;
import fr.insalyon.frigoconnecte.model.Produit;
import fr.insalyon.frigoconnecte.model.Recette;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MiniPanelRecette extends JPanel {
    public Recette r;
    BufferedImage img;
    public Color color;
    int idFrigo;


    public MiniPanelRecette(Recette recette) {
        super();
        this.setLayout(null);
        this.setSize(200, 300);

        try {
            URL url = new URL(recette.imgAdress);
            this.img = resize(ImageIO.read(url), 160, 180);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.r = recette;
        this.addMouseListener(Main.eventsHandler);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.lightGray);
        g2.fillRoundRect(2, 2, this.getWidth() - 3, this.getHeight() - 3, 50, 50);
        g2.setColor(Color.gray);
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(2, 2, this.getWidth() - 3, this.getHeight() - 3, 50, 50);

        g2.drawImage(this.img, 20, 20, null);

        g2.setColor(Color.BLACK);
        drawCenteredString(g2, this.r.nom, new Rectangle(0, 200, 200, 30), new Font("Candara", Font.BOLD, 20));

        g2.setColor(Color.BLACK);
        drawCenteredString(g2, this.r.getRatio() + "% des ingrédients", new Rectangle(0, 250, 200, 30), new Font("Candara", Font.BOLD, 15));



    }


    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        if (img == null) return null;
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
        while (metrics.stringWidth(text) > this.getWidth() - 10) {
            font = new Font(font.getName(), font.getStyle(), font.getSize() - 1);
            metrics = g.getFontMetrics(font);
        }
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