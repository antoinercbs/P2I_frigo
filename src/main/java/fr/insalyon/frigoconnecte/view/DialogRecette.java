package fr.insalyon.frigoconnecte.view;

import fr.insalyon.frigoconnecte.Main;
import fr.insalyon.frigoconnecte.model.Produit;
import fr.insalyon.frigoconnecte.model.Recette;
import fr.insalyon.frigoconnecte.onlineservices.BDFlux;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class DialogRecette extends JDialog {
    private JPanel contentPane;
    private JPanel ingredientsPanel;
    private JPanel exchangePanel;

    ArrayList<MiniPanelProduit> miniPanelProduits = new ArrayList<>();


    public DialogRecette(Recette recette) {
        this.ingredientsPanel.setLayout(null);
        this.exchangePanel.setLayout(null);
        setContentPane(contentPane);
        setModal(true);


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        try {

            drawProduitsFromResultSet(Main.maBD.getProductsInRecette(Main.ID_FRIGO, recette.idRecette), ingredientsPanel);
            drawProduitsFromResultSet(Main.maBD.getEchangeProductsInRecette(Main.ID_FRIGO, recette.idRecette), exchangePanel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.pack();
        this.setVisible(true);
    }


    public void drawProduitsFromResultSet(ResultSet rs, JPanel jp) throws SQLException {
        LinkedList<Produit> produits = new LinkedList<>();
        if (rs == null) return;
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
            MiniPanelProduit mpp = new MiniPanelProduit(produits.get(i), false, Main.ID_FRIGO);
            mpp.setLocation(10+(210*i)%(210*4), 10+(310*(i/4)));
            mpp.color = Color.lightGray;
            this.miniPanelProduits.add(mpp);
            jp.add(mpp);
        }
        this.ingredientsPanel.setPreferredSize(new Dimension(1000,320+(310*(i/4))));
        this.exchangePanel.setPreferredSize(new Dimension(1000,320+(310*(i/4))));

    }



    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
