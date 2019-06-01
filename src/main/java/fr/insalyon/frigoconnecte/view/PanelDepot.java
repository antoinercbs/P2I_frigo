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
    public final SubPanelRetraitDepot subPanelDepot;
    public JTextArea labelDepot;
    public final JTextArea labelListeDepot;
    public final JScrollPane scrollPane;
    
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

        labelDepot = new JTextArea("Votre réfrigérateur est en mode Dépôt." + StandardPanel.newLine + "Vous pouvez scanner vos articles!");
        labelDepot.setBounds(50, 10, 900, 150);
        labelDepot.setFont(StandardPanel.POLICE_BOUTON);

        labelListeDepot = new JTextArea(" Vos derniers ajouts : ");
        labelListeDepot.setBounds(50, 150, 700, 400);
        labelListeDepot.setFont(StandardPanel.POLICE);

        this.subPanelDepot = new SubPanelRetraitDepot(Main.eventsHandler, Main.ID_FRIGO);
        this.scrollPane = new JScrollPane(subPanelDepot, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setBounds(50,200, 850,480);
        this.add(scrollPane);

        this.add(boutonRetour);
        this.add(labelDepot);
        this.add(labelListeDepot);
    }
}
