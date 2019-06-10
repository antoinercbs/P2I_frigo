package fr.insalyon.frigoconnecte.view;

import fr.insalyon.frigoconnecte.Main;
import fr.insalyon.frigoconnecte.model.Produit;
import fr.insalyon.frigoconnecte.onlineservices.BDFlux;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class NewExchange extends JDialog {

    ArrayList<MiniPanelProduit> miniPanelProduits = new ArrayList<>();

    private JPanel contentPane;
    public JPanel selectionPanel;
    public int fromProduct;

    public int idForeignFrigo;


    public NewExchange(int idForeignFrigo, int fromProduct) {
        this.fromProduct=fromProduct;
        this.idForeignFrigo = idForeignFrigo;
        this.selectionPanel.setLayout(null);
        setContentPane(contentPane);
        setModal(true);



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        try {

            drawInventaryFromResultSet(Main.maBD.getSharedProductsInRefrigerateur(idForeignFrigo));
        } catch (SQLException e) {
            e.printStackTrace();
        }


        this.pack();
        this.setVisible(true);
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
            MiniPanelProduit mpp = new MiniPanelProduit(produits.get(i), false, this.idForeignFrigo);
            mpp.setLocation(10+(210*i)%(210*4), 10+(310*(i/4)));
            mpp.color = Color.lightGray;
            this.miniPanelProduits.add(mpp);
            this.selectionPanel.add(mpp);
        }
        this.selectionPanel.setPreferredSize(new Dimension(1000,320+(310*(i/4))));

    }



    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
