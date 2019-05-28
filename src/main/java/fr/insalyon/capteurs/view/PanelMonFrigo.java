package fr.insalyon.capteurs.view;

import javax.swing.*;
import java.awt.*;

public class PanelMonFrigo extends JTabbedPane {

    public PanelMonFrigo(Events eventsHandler) {
        super();
        this.setFont(StandardPanel.POLICE);
        this.setBackground(Color.WHITE);
        this.setForeground(StandardPanel.COULEUR);

    }
}
