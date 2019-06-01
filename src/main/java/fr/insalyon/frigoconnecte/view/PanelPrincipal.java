/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.frigoconnecte.view;

import fr.insalyon.frigoconnecte.Main;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Antoine Rochebois
 */
public class PanelPrincipal extends StandardPanel {

    public final JLabel labelHaut;
    public final JLabel labelBas;
    public JButton boutonDepot;
    public JButton boutonRetrait;
    public JButton boutonMonFrigo;

    public PanelPrincipal(Events eventsHandler) {

        labelHaut = new JLabel("                                      Température : " + " °C                          Humidité : "  + " %");
        labelHaut.setBounds(0, 0, 1000, 100);
        labelHaut.setFont(StandardPanel.POLICE);
        labelHaut.setBackground(StandardPanel.COULEUR);
        labelHaut.setForeground(Color.WHITE);
        labelHaut.setOpaque(true);

        labelBas = new JLabel("                                             Numéro de Série : " + Main.ID_FRIGO);
        labelBas.setBounds(0, 710, 1000, 50);
        labelBas.setFont(StandardPanel.POLICE);
        labelBas.setBackground(StandardPanel.COULEUR);
        labelBas.setForeground(Color.WHITE);
        labelBas.setOpaque(true);

        boutonDepot = new JButton("Dépôt");
        boutonDepot.setBounds(550, 180, 300, 150);
        boutonDepot.setFont(StandardPanel.POLICE_BOUTON);
        boutonDepot.setBackground(StandardPanel.COULEUR);
        boutonDepot.setForeground(Color.WHITE);
        boutonDepot.addActionListener(eventsHandler);

        boutonRetrait = new JButton("Retrait");
        boutonRetrait.setBounds(550, 420, 300, 150);
        boutonRetrait.setFont(StandardPanel.POLICE_BOUTON);
        boutonRetrait.setBackground(StandardPanel.COULEUR);
        boutonRetrait.setForeground(Color.WHITE);
        boutonRetrait.addActionListener(eventsHandler);

        boutonMonFrigo = new JButton("Mon Frigo");
        boutonMonFrigo.setBounds(100, 180, 300, 390);
        boutonMonFrigo.setFont(StandardPanel.POLICE_BOUTON);
        boutonMonFrigo.setBackground(StandardPanel.COULEUR);
        boutonMonFrigo.setForeground(Color.WHITE);
        boutonMonFrigo.addActionListener(eventsHandler);

        this.add(labelHaut);
        this.add(labelBas);
        this.add(boutonDepot);
        this.add(boutonRetrait);
        this.add(boutonMonFrigo);
    }

    public JLabel getLabelHaut() {
        return labelHaut;
    }

    public JLabel getLabelBas() {
        return labelBas;
    }

    public JButton getBoutonDepot() {
        return boutonDepot;
    }

    public JButton getBoutonRetrait() {
        return boutonRetrait;
    }

    public JButton getBoutonMonFrigo() {
        return boutonMonFrigo;
    }
}
