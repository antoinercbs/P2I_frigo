package fr.insalyon.capteurs.view;

import fr.insalyon.capteurs.Capteurs;

import javax.swing.*;
import java.awt.*;

public class PanelStatistiques extends JPanel {
    public JButton boutonRetour;

    public PanelStatistiques(Events eventsHandler) {
        super();
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        boutonRetour = new JButton("Retour");
        boutonRetour.setBounds(800, 600, 150, 50);
        boutonRetour.setBackground(StandardPanel.COULEUR);
        boutonRetour.setForeground(Color.WHITE);
        boutonRetour.setFont(StandardPanel.POLICE);
       boutonRetour.addActionListener(eventsHandler);
        this.add(boutonRetour);
    }
}
