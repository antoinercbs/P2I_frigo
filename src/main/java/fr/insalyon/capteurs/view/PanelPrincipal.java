/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.capteurs.view;

import fr.insalyon.capteurs.BDFlux;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Antoine Rochebois
 */
public class PanelPrincipal extends StandardPanel {

    private final JLabel LabelHaut;
    private final JLabel LabelBas;
    private JButton BoutonDepot;
    private JButton BoutonRetrait;
    private JButton BoutonMonFrigo;
    public PanelPrincipal(BDFlux bd) {

        LabelHaut = new JLabel("                                      Température : " + bd.Temp + " °C                          Humidité : " + bd.Humi + " %");
        LabelHaut.setBounds(0, 0, 1000, 100);
        LabelHaut.setFont(StandardPanel.POLICE);
        LabelHaut.setBackground(StandardPanel.COULEUR);
        LabelHaut.setForeground(Color.WHITE);
        LabelHaut.setOpaque(true);

        LabelBas = new JLabel("                                             Numéro de Série : ");
        LabelBas.setBounds(0, 710, 1000, 50);
        LabelBas.setFont(StandardPanel.POLICE);
        LabelBas.setBackground(StandardPanel.COULEUR);
        LabelBas.setForeground(Color.WHITE);
        LabelBas.setOpaque(true);

        BoutonDepot = new JButton("Dépôt");
        BoutonDepot.setBounds(550, 180, 300, 150);
        BoutonDepot.setFont(StandardPanel.POLICE_BOUTON);
        BoutonDepot.setBackground(StandardPanel.COULEUR);
        BoutonDepot.setForeground(Color.WHITE);
        BoutonDepot.addMouseListener(handler);

        BoutonRetrait = new JButton("Retrait");
        BoutonRetrait.setBounds(550, 420, 300, 150);
        BoutonRetrait.setFont(StandardPanel.POLICE_BOUTON);
        BoutonRetrait.setBackground(StandardPanel.COULEUR);
        BoutonRetrait.setForeground(Color.WHITE);
        BoutonRetrait.addMouseListener(handler);

        BoutonMonFrigo = new JButton("Mon Frigo");
        BoutonMonFrigo.setBounds(100, 180, 300, 390);
        BoutonMonFrigo.setFont(StandardPanel.POLICE_BOUTON);
        BoutonMonFrigo.setBackground(StandardPanel.COULEUR);
        BoutonMonFrigo.setForeground(Color.WHITE);
        BoutonMonFrigo.addMouseListener(handler);

        this.add(LabelHaut);
        this.add(LabelBas);
        this.add(BoutonDepot);
        this.add(BoutonRetrait);
        this.add(BoutonMonFrigo);
    }
}
