/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.frigoconnecte.view;


import fr.insalyon.frigoconnecte.Main;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {

    public final JButton boutonRetour;
    public PanelPrincipal panelPrincipal;
    public PanelDepot panelDepot;
    public PanelRetrait panelRetrait;
    public PanelMonFrigo panelMonFrigo;
    public JPanel monFrigoContainer;
    Timer tm;



    public Fenetre(Events eventsHandler) {

        this.panelPrincipal = new PanelPrincipal(eventsHandler);
        this.panelRetrait = new PanelRetrait(eventsHandler);
        this.panelDepot = new PanelDepot(eventsHandler);

        this.monFrigoContainer = new JPanel();
        monFrigoContainer.setLayout(null);
        boutonRetour = new JButton("Retour");
        boutonRetour.setBounds(800, 700, 150, 50);
        boutonRetour.setBackground(StandardPanel.COULEUR);
        boutonRetour.setForeground(Color.WHITE);
        boutonRetour.setFont(StandardPanel.POLICE);
        boutonRetour.addActionListener(eventsHandler);
        this.monFrigoContainer.add(boutonRetour);
        this.panelMonFrigo = new PanelMonFrigo(eventsHandler);
        this.monFrigoContainer.add(this.panelMonFrigo);
        this.panelMonFrigo.setFocusable(true);
        this.monFrigoContainer.setFocusable(false);




        setSize(1000, 800);
        setTitle("Frigo Connecté");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        this.setResizable(false);
        this.tm = new Timer(20, Main.eventsHandler);
        tm.start();

    }

}
