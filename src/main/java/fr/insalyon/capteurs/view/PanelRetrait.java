/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.capteurs.view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Antoine Rochebois
 */
public class PanelRetrait extends StandardPanel {

    private final JButton boutonRetourRetrait;
    private final JTextArea labelRetrait;
    private final JTextArea labelListeRetrait;
    

    public PanelRetrait() {
        super();
        boutonRetourRetrait = new JButton("Retour");
        boutonRetourRetrait.setBounds(800, 600, 150, 50);
        boutonRetourRetrait.setBackground(super.COULEUR);
        boutonRetourRetrait.setForeground(Color.WHITE);
        boutonRetourRetrait.setFont(super.POLICE);
        boutonRetourRetrait.addMouseListener(super.handler);

        labelRetrait = new JTextArea("Votre réfrigérateur est en mode Retrait." + newLine + "" + newLine + "Vous pouvez scanner vos articles!");
        labelRetrait.setBounds(50, 150, 900, 200);
        labelRetrait.setFont(super.POLICE_BOUTON);

        labelListeRetrait = new JTextArea(" Vos derniers retraits : ");
        labelListeRetrait.setBounds(50, 350, 700, 400);
        labelListeRetrait.setFont(super.POLICE);

        this.add(boutonRetourRetrait);
        this.add(labelRetrait);
        this.add(labelListeRetrait);
    }
}
