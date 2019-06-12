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
public class PanelRetrait extends StandardPanel {

    public final JButton boutonRetour;
    public final JTextArea labelRetrait;
    public final JTextArea labelListeRetrait;
    public final SubPanelRetraitDepot subPanelRetrait;
    public final JScrollPane scrollPane;
    

    public PanelRetrait(Events eventsHandler) {
        super();
        boutonRetour = new JButton("Retour");
        boutonRetour.setBounds(800, 700, 150, 50);
        boutonRetour.setBackground(super.COULEUR);
        boutonRetour.setForeground(Color.WHITE);
        boutonRetour.setFont(super.POLICE);
        boutonRetour.addActionListener(eventsHandler);

        labelRetrait = new JTextArea("Votre réfrigérateur est en mode Retrait." + newLine + "Vous pouvez scanner vos articles!");
        labelRetrait.setBounds(50, 5, 900, 150);
        labelRetrait.setFont(super.POLICE_BOUTON);

        labelListeRetrait = new JTextArea(" Vos derniers retraits : ");
        labelListeRetrait.setBounds(50, 150, 700, 400);
        labelListeRetrait.setFont(super.POLICE);

        this.subPanelRetrait = new SubPanelRetraitDepot(Main.eventsHandler, Main.ID_FRIGO);
        this.scrollPane = new JScrollPane(subPanelRetrait, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setBounds(50,200, 850,480);
        this.add(scrollPane);



        this.add(boutonRetour);
        this.add(labelRetrait);
        this.add(labelListeRetrait);
    }
}
