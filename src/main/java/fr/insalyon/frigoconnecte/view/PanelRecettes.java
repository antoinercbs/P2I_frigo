package fr.insalyon.frigoconnecte.view;

import fr.insalyon.frigoconnecte.Main;
import fr.insalyon.frigoconnecte.model.Produit;
import fr.insalyon.frigoconnecte.model.Recette;
import fr.insalyon.frigoconnecte.onlineservices.BDFlux;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class PanelRecettes extends JPanel {
    ArrayList<MiniPanelRecette> miniPanelRecettes = new ArrayList<>();


    /**
     * Construit le panneau d'affichage de l'inventaire d'un frigo
     * @param eventsHandler pour interractions boutons
     */
    public PanelRecettes(Events eventsHandler) {
        super();
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(1000, 600));
        //System.setProperty("http.agent", "Chrome");

        try {
            drawInventaryFromResultSet(Main.maBD.getRecettesFor(Main.ID_FRIGO));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drawInventaryFromResultSet(ArrayList<Recette> recettes) throws SQLException {
        int i;
        for (i = 0; i < recettes.size(); i++) {
            MiniPanelRecette mpr = new MiniPanelRecette(recettes.get(i));
            mpr.setLocation(10+(210*i)%(210*4), 10+(310*(i/4)));
            this.miniPanelRecettes.add(mpr);
            this.add(mpr);
        }
        this.setPreferredSize(new Dimension(1000,320+(310*(i/4))));

    }

}
