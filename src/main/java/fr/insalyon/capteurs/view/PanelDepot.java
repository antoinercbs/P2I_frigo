/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.capteurs.view;

import java.awt.Color;
import javax.swing.*;

/**
 *
 * @author Antoine Rochebois
 */
public class PanelDepot  extends StandardPanel{

    private final JButton boutonRetourDepot;
    private JTextArea labelDepot;
    private final JTextArea labelListeDepot;
    
    
    public PanelDepot() {
        super();
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        boutonRetourDepot = new JButton("Retour");
        boutonRetourDepot.setBounds(800, 600, 150, 50);
        boutonRetourDepot.setBackground(StandardPanel.COULEUR);
        boutonRetourDepot.setForeground(Color.WHITE);
        boutonRetourDepot.setFont(StandardPanel.POLICE);
        boutonRetourDepot.addMouseListener(handler);

        labelDepot = new JTextArea("Votre réfrigérateur est en mode Dépôt." + StandardPanel.newLine + "" + StandardPanel.newLine + "Vous pouvez scanner vos articles!");
        labelDepot.setBounds(50, 150, 900, 200);
        labelDepot.setFont(StandardPanel.POLICE_BOUTON);

        labelListeDepot = new JTextArea(" Vos derniers ajouts : ");
        labelListeDepot.setBounds(50, 350, 700, 400);
        labelListeDepot.setFont(StandardPanel.POLICE);

        this.add(boutonRetourDepot);
        this.add(labelDepot);
        this.add(labelListeDepot);
    }
}
