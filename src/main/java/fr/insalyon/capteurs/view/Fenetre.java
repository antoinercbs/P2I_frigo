/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.capteurs.view;


import fr.insalyon.p2i2.javaarduino.BDFlux;
import java.awt.*;
import javax.swing.*;

public class Fenetre extends JFrame {

    public JPanel principal;
    public JPanel panelDepot;
    public JPanel panelRetrait;
    public JPanel PanelMonFrigo;
    public JPanel PanelRecettes;
    public JPanel PanelInventaire;
    public JPanel PanelStatistiques;
    public JTabbedPane tab;

    public JButton BoutonDepot;
    public JButton BoutonRetrait;
    public JButton BoutonMonFrigo;
    public JButton BoutonRetourRetrait;
    public JButton boutonRetourDepot;
    public JButton BoutonRetourStats;
    public JButton BoutonRetourInventaire;
    public JButton BoutonRetourRecettes;

    public JLabel LabelHaut;
    public JLabel LabelBas;
    public JTextArea LabelRetrait;
    public JTextArea labelDepot;
    public JTextArea LabelListeRetrait;
    public JTextArea labelListeDepot;
    
    public static Events handler;

    public Fenetre() {
        BDFlux bd = new BDFlux("G221_C_BD2", "G221_C", "G221_C");

        this.principal = new PanelPrincipal(bd);
        //-----------------------------------------------PANEL RETRAIT --------------------------------------------------
        panelRetrait = new PanelRetrait();

        //-----------------------------------------------PANEL DEPOT --------------------------------------------------
        panelDepot = new JPanel();
        panelDepot.setLayout(null);
        panelDepot.setBackground(Color.WHITE);

        boutonRetourDepot = new JButton("Retour");
        boutonRetourDepot.setBounds(800, 600, 150, 50);
        boutonRetourDepot.setBackground(StandardPanel.COULEUR);
        boutonRetourDepot.setForeground(Color.WHITE);
        boutonRetourDepot.setFont(StandardPanel.POLICE);
        boutonRetourDepot.addMouseListener(handler);

        labelDepot = new JTextArea("Votre réfrigérateur est en mode Dépôt." + StandardPanel.newLine + "" + StandardPanel.newLine + "Vous pouvez scanner vos articles!");
        labelDepot.setBounds(50, 150, 900, 200);
        labelDepot.setFont(StandardPanel.POLICE_BOUTON);

        labelListeDepot = new JTextArea(" Vos derniers ajouts : ");
        labelListeDepot.setBounds(50, 350, 700, 400);
        labelListeDepot.setFont(StandardPanel.POLICE);

        panelDepot.add(boutonRetourDepot);
        panelDepot.add(labelDepot);
        panelDepot.add(labelListeDepot);

        //-----------------------------------------------PANEL MON FRIGO --------------------------------------------------      
        tab = new JTabbedPane();
        tab.setFont(StandardPanel.POLICE);
        tab.setBackground(Color.WHITE);
        tab.setForeground(StandardPanel.COULEUR);

        PanelRecettes = new JPanel();
        PanelRecettes.setLayout(null);
        PanelRecettes.setBackground(Color.WHITE);
        tab.addTab("             Mes Recettes             ", PanelRecettes);

        BoutonRetourRecettes = new JButton("Retour");
        BoutonRetourRecettes.setBounds(800, 600, 150, 50);
        BoutonRetourRecettes.setBackground(StandardPanel.COULEUR);
        BoutonRetourRecettes.setForeground(Color.WHITE);
        BoutonRetourRecettes.setFont(StandardPanel.POLICE);
        BoutonRetourRecettes.addMouseListener(handler);

        PanelInventaire = new JPanel();
        PanelInventaire.setLayout(null);
        PanelInventaire.setBackground(Color.WHITE);
        tab.addTab("             Mon Inventaire             ", PanelInventaire);

        BoutonRetourInventaire = new JButton("Retour");
        BoutonRetourInventaire.setBounds(800, 600, 150, 50);
        BoutonRetourInventaire.setBackground(StandardPanel.COULEUR);
        BoutonRetourInventaire.setForeground(Color.WHITE);
        BoutonRetourInventaire.setFont(StandardPanel.POLICE);
        BoutonRetourInventaire.addMouseListener(handler);

        PanelStatistiques = new JPanel();
        PanelStatistiques.setLayout(null);
        PanelStatistiques.setBackground(Color.WHITE);
        tab.addTab("              Statistiques             ", PanelStatistiques);

        BoutonRetourStats = new JButton("Retour");
        BoutonRetourStats.setBounds(800, 600, 150, 50);
        BoutonRetourStats.setBackground(StandardPanel.COULEUR);
        BoutonRetourStats.setForeground(Color.WHITE);
        BoutonRetourStats.setFont(StandardPanel.POLICE);
        BoutonRetourStats.addMouseListener(handler);

        PanelRecettes.add(BoutonRetourRecettes);
        PanelInventaire.add(BoutonRetourInventaire);
        PanelStatistiques.add(BoutonRetourStats);

        //this.add(tab);
        setSize(1000, 800);
        setTitle("Frigo Connecté");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(principal);
        setLocationRelativeTo(null);

    }

}
