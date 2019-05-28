/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.capteurs.view;


import javax.swing.*;

public class Fenetre extends JFrame {

    public PanelPrincipal panelPrincipal;
    public PanelDepot panelDepot;
    public PanelRetrait panelRetrait;
    public PanelRecettes panelRecettes;
    public PanelInventaire panelInventaire;
    public PanelStatistiques panelStatistiques;
    public PanelMonFrigo panelMonFrigo;



    public Fenetre(Events eventsHandler) {

        this.panelPrincipal = new PanelPrincipal(eventsHandler);
        this.panelRetrait = new PanelRetrait(eventsHandler);

        panelDepot = new PanelDepot(eventsHandler);

        panelMonFrigo = new PanelMonFrigo(eventsHandler);
        panelRecettes = new PanelRecettes(eventsHandler);
        panelMonFrigo.addTab("             Mes Recettes             ", panelRecettes);
        panelInventaire = new PanelInventaire(eventsHandler);
        panelMonFrigo.addTab("             Mon Inventaire             ", new JScrollPane(panelInventaire, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        panelStatistiques = new PanelStatistiques(eventsHandler);
        panelMonFrigo.addTab("              Statistiques             ", panelStatistiques);



        //this.add(panelMonFrigo);
        setSize(1000, 800);
        setTitle("Frigo Connecté");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        //this.setResizable(false);
    }

}
