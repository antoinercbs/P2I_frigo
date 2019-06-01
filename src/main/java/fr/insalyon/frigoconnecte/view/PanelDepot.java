/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.frigoconnecte.view;

import fr.insalyon.frigoconnecte.Main;

import java.awt.Color;
import javax.swing.*;

/**
 *
 * @author Antoine Rochebois
 */
public class PanelDepot  extends StandardPanel{

    public final JButton boutonRetour;
    public JTextArea labelDepot;
    public final JTextArea labelListeDepot;
    
    
    public PanelDepot(Events eventsHandler) {
        super();
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        boutonRetour = new JButton("Retour");
        boutonRetour.setBounds(800, 700, 150, 50);
        boutonRetour.setBackground(StandardPanel.COULEUR);
        boutonRetour.setForeground(Color.WHITE);
        boutonRetour.setFont(StandardPanel.POLICE);
        boutonRetour.addActionListener(Main.eventsHandler);

        labelDepot = new JTextArea("Votre réfrigérateur est en mode Dépôt." + StandardPanel.newLine + "" + StandardPanel.newLine + "Vous pouvez scanner vos articles!");
        labelDepot.setBounds(50, 150, 900, 200);
        labelDepot.setFont(StandardPanel.POLICE_BOUTON);

        labelListeDepot = new JTextArea(" Vos derniers ajouts : ");
        labelListeDepot.setBounds(50, 350, 700, 400);
        labelListeDepot.setFont(StandardPanel.POLICE);

        this.add(boutonRetour);
        this.add(labelDepot);
        this.add(labelListeDepot);
    }
}
