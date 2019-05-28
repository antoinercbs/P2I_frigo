/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.capteurs.view;

import fr.insalyon.capteurs.Capteurs;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 *
 * @author Antoine Rochebois
 */
public class PanelRetrait extends StandardPanel {

    public final JButton boutonRetour;
    public final JTextArea labelRetrait;
    public final JTextArea labelListeRetrait;
    

    public PanelRetrait(Events eventsHandler) {
        super();
        boutonRetour = new JButton("Retour");
        boutonRetour.setBounds(800, 600, 150, 50);
        boutonRetour.setBackground(super.COULEUR);
        boutonRetour.setForeground(Color.WHITE);
        boutonRetour.setFont(super.POLICE);
        boutonRetour.addActionListener(eventsHandler);

        labelRetrait = new JTextArea("Votre réfrigérateur est en mode Retrait." + newLine + "" + newLine + "Vous pouvez scanner vos articles!");
        labelRetrait.setBounds(50, 150, 900, 200);
        labelRetrait.setFont(super.POLICE_BOUTON);

        labelListeRetrait = new JTextArea(" Vos derniers retraits : ");
        labelListeRetrait.setBounds(50, 350, 700, 400);
        labelListeRetrait.setFont(super.POLICE);

        this.add(boutonRetour);
        this.add(labelRetrait);
        this.add(labelListeRetrait);
    }
}
