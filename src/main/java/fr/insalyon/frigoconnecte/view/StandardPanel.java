/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.frigoconnecte.view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

/**
 *
 * @author Antoine Rochebois
 */
public class StandardPanel extends JPanel {

    public static Color COULEUR;

    protected static Font POLICE_BOUTON;
    protected static Font POLICE;

    public static String newLine = System.getProperty("line.separator");

    protected StandardPanel() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        POLICE_BOUTON = new Font("Cambria", 1, 40);
        POLICE = new Font("Cambria", 1, 25);
        COULEUR = new Color(248, 7, 80);
    }
}
