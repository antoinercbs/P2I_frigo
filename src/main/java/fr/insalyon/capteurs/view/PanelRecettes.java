package fr.insalyon.capteurs.view;

import fr.insalyon.capteurs.Capteurs;

import javax.swing.*;
import java.awt.*;

public class PanelRecettes extends JPanel {
    public JButton boutonRetour;

    public PanelRecettes(Events eventsHandler) {
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        boutonRetour = new JButton("Retour");
        boutonRetour.setBounds(800, 600, 150, 50);
        boutonRetour.setBackground(StandardPanel.COULEUR);
        boutonRetour.setForeground(Color.WHITE);
        boutonRetour.setFont(StandardPanel.POLICE);
        this.add(boutonRetour);
       boutonRetour.addActionListener(eventsHandler);
    }
}
