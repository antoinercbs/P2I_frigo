package fr.insalyon.frigoconnecte.view;

import fr.insalyon.frigoconnecte.Main;
import fr.insalyon.frigoconnecte.model.Produit;
import fr.insalyon.frigoconnecte.onlineservices.BDFlux;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class PanelInventaire extends JPanel {
    ArrayList<MiniPanelProduit> miniPanelProduits = new ArrayList<>();
    private int idFrigo;


    /**
     * Construit le panneau d'affichage de l'inventaire d'un frigo
     * @param eventsHandler pour interractions boutons
     * @param idFrigo identifiant du frigo dont on veut dresser l'inventaire
     */
    public PanelInventaire(Events eventsHandler, int idFrigo) {
        super();
        this.idFrigo=idFrigo;
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(1000, 600));

        try {

            drawInventaryFromResultSet(Main.maBD.getProductsInRefrigerateur(idFrigo));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dessine l'inventaire dans ce panneau d'inventaire à partir d'une liste de produits donnée
     * @param rs liste de produits
     * @throws SQLException
     */
    public void drawInventaryFromResultSet(ResultSet rs) throws SQLException {
        LinkedList<Produit> produits = new LinkedList<>();

        while(rs.next()) {
            try {
                int id=rs.getInt("idProduit");
                double pr = rs.getDouble("pourcentageRestant");
                String dp = "NaN"; //TODO : à changer pour appel à BD (il faudra mettre la date dans la BD)

                PreparedStatement ps = BDFlux.conn.prepareStatement("SELECT * FROM ListeProduit WHERE codeBarre = ?;");
                ps.setDouble(1, rs.getDouble("codeBarre"));
                ResultSet rsListe = ps.executeQuery();
                double cont = 0;
                String nom = "NaN", imgAdress = "NaN", categorie = "NaN";
                if (rsListe.next()) {

                    nom = rsListe.getString("nomProduit");
                    imgAdress=rsListe.getString("lienImage");
                    categorie=rsListe.getString("categorie");
                    cont=rsListe.getDouble("contenance");
                }

                produits.add(new Produit(id, nom, imgAdress, categorie, cont, pr, dp));

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        int i;
        for (i = 0; i < produits.size(); i++) {
            MiniPanelProduit mpp = new MiniPanelProduit(produits.get(i), false, this.idFrigo);
            mpp.setLocation(10+(210*i)%(210*4), 10+(310*(i/4)));
            this.miniPanelProduits.add(mpp);
            this.add(mpp);
        }
        this.setPreferredSize(new Dimension(1000,320+(310*(i/4))));

    }

}
