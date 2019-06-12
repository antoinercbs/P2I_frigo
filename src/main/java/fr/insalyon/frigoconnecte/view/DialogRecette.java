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
    private JScrollPane ingredientsPanel;
    private JScrollPane exchangePanel;
    private JTextPane textPane1;
    private JLabel textPanelfqfsqf;

    private JPanel buffIngredients;
    private JPanel buffExchanges;




    public DialogRecette(Recette recette) {
        this.ingredientsPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.ingredientsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.exchangePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.exchangePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.textPane1.setEditable(false);

        this.setSize(1000, 1000);
        setContentPane(contentPane);
        setModal(true);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        this.buffIngredients = new JPanel();
        this.buffExchanges = new JPanel();
        this.buffIngredients.setLayout(null);
        this.buffExchanges.setLayout(null);


        try {

            drawProduitsFromResultSet(Main.maBD.getProductsInRecette(Main.ID_FRIGO, recette.idRecette), buffIngredients);
            drawProduitsFromResultSet(Main.maBD.getEchangeProductsInRecette(Main.ID_FRIGO, recette.idRecette), buffExchanges);
            this.textPane1.setText(Main.maBD.getIngredientsFor(recette.idRecette));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.buffIngredients.setVisible(true);
        this.buffExchanges.setVisible(true);
        this.exchangePanel.setViewportView(buffExchanges);
        this.ingredientsPanel.setViewportView(buffIngredients);


        this.exchangePanel.setVisible(true);
        this.ingredientsPanel.setVisible(true);

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
            jp.add(mpp);
        }
        jp.setPreferredSize(new Dimension(900,320+(310*(i/4))));

    }



    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
