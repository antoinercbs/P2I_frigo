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

public class PanelAccepterEchange extends StandardPanel {
    ArrayList<MiniPanelProduit> miniPanelProduits = new ArrayList<>();
    ArrayList<String> askersNames = new ArrayList<>();
    ArrayList<Integer> askersX = new ArrayList<>();
    ArrayList<Integer> askersY = new ArrayList<>();
    int idFrigo;



    public PanelAccepterEchange(Events eventsHandler, int idFrigo) {
        super();
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(1000, 600));
        this.idFrigo = idFrigo;


        try {
            int nbEchangistes = Main.maBD.getAskerCountFor(idFrigo);
            drawExchangeInventaryFromResultSet(Main.maBD.getExchangeProductsAskedFrom(idFrigo));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dessine l'inventaire dans ce panneau d'inventaire à partir d'une liste de produits donnée
     * @param rs liste de produits
     * @throws SQLException
     */
    public void drawExchangeInventaryFromResultSet(ResultSet rs) throws SQLException {
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
        if (produits.size() == 0) return;
        int i = 0;
        int j = 0;
        int currentAsker = 0;
        for (i = 0; (i-j) < produits.size(); i++) {
            if (currentAsker !=produits.get(i-j).estDemandePar) {
                currentAsker = produits.get(i-j).estDemandePar;
                j++;
                String name  = Main.maBD.getRefrigerateurOwnerName(currentAsker);
                System.out.println(name);
                String label = ((name != null)?name:"Inconnu") + " : ";
                askersX.add(10+(210*i)%(210*4));
                askersY.add( 10+(310*(i/4)));
                askersNames.add(label);
            } else {
                MiniPanelProduit mpp = new MiniPanelProduit(produits.get(i-j), true, currentAsker);
                mpp.color = Color.lightGray;
                mpp.setLocation(10+(210*i)%(210*4), 10+(310*(i/4)));
                this.miniPanelProduits.add(mpp);
                this.add(mpp);
            }

        }
        this.setPreferredSize(new Dimension(1000,320+(310*(i/4))));

    }

    public void paintComponent(Graphics g) {
        for (int i = 0; i < this.askersNames.size(); i++) {
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString(this.askersNames.get(i), this.askersX.get(i)+20, this.askersY.get(i)+135);
        }
    }


}
