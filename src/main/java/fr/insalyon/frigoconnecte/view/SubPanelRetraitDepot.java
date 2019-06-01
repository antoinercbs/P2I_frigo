package fr.insalyon.frigoconnecte.view;

import fr.insalyon.frigoconnecte.Main;
import fr.insalyon.frigoconnecte.model.Produit;
import fr.insalyon.frigoconnecte.onlineservices.BDFlux;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class SubPanelRetraitDepot extends StandardPanel {

    ArrayList<MiniPanelProduit> miniPanelProduits = new ArrayList<>();
    LinkedList<Produit> produits = new LinkedList<>();
    private int idFrigo;
    private int increment = 0;



    public SubPanelRetraitDepot(Events eventsHandler, int idFrigo) {
        super();
        this.idFrigo=idFrigo;
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(1000, 600));
    }

    /**
     * Dessine l'inventaire dans ce panneau d'inventaire à partir d'une liste de produits donnée
     * @param rs liste de produits
     * @throws SQLException
     */
    public void updateSelectionFromResultSet(ResultSet rs) throws SQLException {


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
        for (increment = increment; increment < produits.size(); increment++) {
            MiniPanelProduit mpp = new MiniPanelProduit(produits.get(increment), false, this.idFrigo);
            mpp.setLocation(10+(210*increment)%(210*4), 10+(310*(increment/4)));
            this.miniPanelProduits.add(mpp);
            this.add(mpp);
        }
        this.setPreferredSize(new Dimension(1000,320+(310*(increment/4))));

    }
}
