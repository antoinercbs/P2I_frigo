package fr.insalyon.frigoconnecte.view;

import fr.insalyon.frigoconnecte.Main;

import javax.swing.*;
import java.awt.*;

public class PanelMonFrigo extends JTabbedPane {

    public final PanelRecettes panelRecettes;
    public final PanelInventaire panelInventaire;
    public final PanelDemanderEchange panelDemanderEchange;
    public final PanelStatistiques panelStatistiques;
    public final PanelAccepterEchange panelAccepterEchange;

    public PanelMonFrigo(Events eventsHandler) {
        super();
        this.setFont(StandardPanel.POLICE);
        this.setBackground(Color.WHITE);
        this.setForeground(StandardPanel.COULEUR);
        this.setBounds(0,0, 1000,800);



        panelRecettes = new PanelRecettes(Main.eventsHandler);
        this.addTab("Recettes", panelRecettes);
        panelInventaire = new PanelInventaire(eventsHandler, 2);
        this.addTab("Inventaire", new JScrollPane(panelInventaire, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        panelDemanderEchange = new PanelDemanderEchange(eventsHandler, 1);
        this.addTab("Echanges disponibles", new JScrollPane(panelDemanderEchange, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        panelAccepterEchange = new PanelAccepterEchange(eventsHandler, 2);
        this.addTab("Echanges demandés", new JScrollPane(panelAccepterEchange, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        panelStatistiques = new PanelStatistiques(eventsHandler);
        this.addTab("Statistiques", panelStatistiques);



    }
}
