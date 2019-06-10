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
        this.addTab("Recettes", new JScrollPane(panelRecettes, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        panelInventaire = new PanelInventaire(eventsHandler, Main.ID_FRIGO);
        this.addTab("Inventaire", new JScrollPane(panelInventaire, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        panelDemanderEchange = new PanelDemanderEchange(eventsHandler, Main.ID_FRIGO);
        this.addTab("Echanges disponibles", new JScrollPane(panelDemanderEchange, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        panelAccepterEchange = new PanelAccepterEchange(eventsHandler, Main.ID_FRIGO);
        this.addTab("Echanges demandés", new JScrollPane(panelAccepterEchange, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        panelStatistiques = new PanelStatistiques(eventsHandler, Main.ID_FRIGO);
        this.addTab("Statistiques", panelStatistiques);



    }
}
