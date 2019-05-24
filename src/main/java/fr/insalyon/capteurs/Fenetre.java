/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.capteurs;
import fr.insalyon.capteurs.BDFlux;
import java.awt.*;
import javax.swing.*;

public class Fenetre extends JFrame{
    public JPanel principal;
    public JPanel PanelDepot;
    public JPanel PanelRetrait;
    public JPanel PanelMonFrigo;
    public JPanel PanelRecettes;
    public JPanel PanelInventaire;
    public JPanel PanelStatistiques;
    public JTabbedPane tab;
    public JButton BoutonDepot;
    public JButton BoutonRetrait;
    public JButton BoutonMonFrigo;
    public JButton BoutonRetourRetrait;
    public JButton BoutonRetourDepot;
    public JButton BoutonRetourStats;
    public JButton BoutonRetourInventaire;
    public JButton BoutonRetourRecettes;
    public JLabel LabelHaut;
    public JLabel LabelBas;
    public JTextArea LabelRetrait;
    public JTextArea LabelDepot;
    public JTextArea LabelListeRetrait;
    public JTextArea LabelListeDepot;
   
   
    public Font policeBouton;
    public Font police;
    
    String Newligne=System.getProperty("line.separator");
   
    
 public Fenetre(){     
     Events Handler = new Events();
     BDFlux BD = new BDFlux("G221_C_BD2","G221_C","G221_C");
     
     policeBouton = new Font("Grobold",1,40);
     police = new Font("Dosis",1,25);
     Color couleur = new Color(248,7,80);
     
     principal = new JPanel();
     principal.setLayout(null);
     principal.setBackground(Color.WHITE);
       
     LabelHaut = new JLabel("                                                            Température : " +BD.Temp+" °C                          Humidité : " +BD.Humi+" %"        );
     LabelHaut.setBounds(0,0,1000,100);
     LabelHaut.setFont(police);
     LabelHaut.setBackground(couleur);
     LabelHaut.setForeground(Color.WHITE);
     LabelHaut.setOpaque(true);
     
     LabelBas = new JLabel("                                             Numéro de Série : ");
     LabelBas.setBounds(0,710,1000,50);
     LabelBas.setFont(police);
     LabelBas.setBackground(couleur);
     LabelBas.setForeground(Color.WHITE);
     LabelBas.setOpaque(true);
     
     BoutonDepot = new JButton("Dépôt");
     BoutonDepot.setBounds(550,180,300,150);
     BoutonDepot.setFont(policeBouton);
     BoutonDepot.setBackground(couleur);
     BoutonDepot.setForeground(Color.WHITE);
     BoutonDepot.addMouseListener(Handler);
     
     BoutonRetrait = new JButton("Retrait");
     BoutonRetrait.setBounds(550,420,300,150);
     BoutonRetrait.setFont(policeBouton);
     BoutonRetrait.setBackground(couleur);
     BoutonRetrait.setForeground(Color.WHITE);
     BoutonRetrait.addMouseListener(Handler);
     
     BoutonMonFrigo = new JButton("Mon Frigo");
     BoutonMonFrigo.setBounds(100,180,300,390);
     BoutonMonFrigo.setFont(policeBouton);
     BoutonMonFrigo.setBackground(couleur);
     BoutonMonFrigo.setForeground(Color.WHITE);
     BoutonMonFrigo.addMouseListener(Handler);
     
     principal.add(LabelHaut);
     principal.add(LabelBas);
     principal.add(BoutonDepot);
     principal.add(BoutonRetrait);
     principal.add(BoutonMonFrigo);
     
     //-----------------------------------------------PANEL RETRAIT --------------------------------------------------
     
     PanelRetrait = new JPanel();
     PanelRetrait.setLayout(null);
     PanelRetrait.setBackground(Color.WHITE);
     
     
     BoutonRetourRetrait= new JButton("Retour");
     BoutonRetourRetrait.setBounds(800,600,150,50);
     BoutonRetourRetrait.setBackground(couleur);
     BoutonRetourRetrait.setForeground(Color.WHITE);
     BoutonRetourRetrait.setFont(police);
     BoutonRetourRetrait.addMouseListener(Handler);
     
     LabelRetrait=new JTextArea("Votre réfrigérateur est en mode Retrait."+Newligne+""+Newligne+"Vous pouvez scanner vos articles!");
     LabelRetrait.setBounds(50,150,900,200);
     LabelRetrait.setFont(policeBouton);
     
     LabelListeRetrait = new JTextArea(" Vos derniers retraits : ");
     LabelListeRetrait.setBounds(50,350,700,400);
     LabelListeRetrait.setFont(police);
    
     PanelRetrait.add(BoutonRetourRetrait);
     PanelRetrait.add(LabelRetrait);
     PanelRetrait.add(LabelListeRetrait);
     
     //-----------------------------------------------PANEL DEPOT --------------------------------------------------
     
     PanelDepot = new JPanel();
     PanelDepot.setLayout(null);
     PanelDepot.setBackground(Color.WHITE);
     
     BoutonRetourDepot= new JButton("Retour");
     BoutonRetourDepot.setBounds(800,600,150,50);
     BoutonRetourDepot.setBackground(couleur);
     BoutonRetourDepot.setForeground(Color.WHITE);
     BoutonRetourDepot.setFont(police);
     BoutonRetourDepot.addMouseListener(Handler);
     
     LabelDepot=new JTextArea("Votre réfrigérateur est en mode Dépôt."+Newligne+""+Newligne+"Vous pouvez scanner vos articles!");
     LabelDepot.setBounds(50,150,900,200);
     LabelDepot.setFont(policeBouton);
     
     LabelListeDepot = new JTextArea(" Vos derniers ajouts : ");
     LabelListeDepot.setBounds(50,350,700,400);
     LabelListeDepot.setFont(police);
    
     PanelDepot.add(BoutonRetourDepot);
     PanelDepot.add(LabelDepot);
     PanelDepot.add(LabelListeDepot);
     
      //-----------------------------------------------PANEL MON FRIGO --------------------------------------------------      
      tab = new JTabbedPane();
      tab.setFont(police);
      tab.setBackground(Color.WHITE);
      tab.setForeground(couleur);
      
      PanelRecettes = new JPanel();
      PanelRecettes.setLayout(null);
      PanelRecettes.setBackground(Color.WHITE);
      tab.addTab("             Mes Recettes             ",PanelRecettes);
      
      BoutonRetourRecettes= new JButton("Retour");
      BoutonRetourRecettes.setBounds(800,600,150,50);
      BoutonRetourRecettes.setBackground(couleur);
      BoutonRetourRecettes.setForeground(Color.WHITE);
      BoutonRetourRecettes.setFont(police);
      BoutonRetourRecettes.addMouseListener(Handler);
      
      PanelInventaire = new JPanel();
      PanelInventaire.setLayout(null);
      PanelInventaire.setBackground(Color.WHITE);
      tab.addTab("             Mon Inventaire             ", PanelInventaire);
       
      BoutonRetourInventaire= new JButton("Retour");
      BoutonRetourInventaire.setBounds(800,600,150,50);
      BoutonRetourInventaire.setBackground(couleur);
      BoutonRetourInventaire.setForeground(Color.WHITE);
      BoutonRetourInventaire.setFont(police);
      BoutonRetourInventaire.addMouseListener(Handler);
      
      PanelStatistiques = new JPanel();
      PanelStatistiques.setLayout(null);
      PanelStatistiques.setBackground(Color.WHITE);
      tab.addTab("              Statistiques             ", PanelStatistiques);
      
      BoutonRetourStats= new JButton("Retour");
      BoutonRetourStats.setBounds(800,600,150,50);
      BoutonRetourStats.setBackground(couleur);
      BoutonRetourStats.setForeground(Color.WHITE);
      BoutonRetourStats.setFont(police);
      BoutonRetourStats.addMouseListener(Handler);
      
     PanelRecettes.add(BoutonRetourRecettes);
     PanelInventaire.add(BoutonRetourInventaire);
     PanelStatistiques.add(BoutonRetourStats);
      
     //this.add(tab);
     setSize(1000,800);
     setTitle("Frigo Connecté");
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setVisible(true);
     setContentPane(principal);
     setLocationRelativeTo(null);
     
     
 
 }
    
}
