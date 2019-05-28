package fr.insalyon.capteurs.view;

import fr.insalyon.capteurs.Capteurs;
import fr.insalyon.p2i2.javaarduino.BDFlux;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class PanelInventaire extends JPanel {
    public JButton boutonRetour;
    public PanelInventaire(Events eventsHandler) {
        super();
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(1000,800));
        boutonRetour = new JButton("Retour");
        boutonRetour.setBounds(800, 600, 150, 50);
        boutonRetour.setBackground(StandardPanel.COULEUR);
        boutonRetour.setForeground(Color.WHITE);
        boutonRetour.setFont(StandardPanel.POLICE);
        boutonRetour.addActionListener(Capteurs.eventsHandler);
        this.add(boutonRetour);
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT * FROM Produit;");
            drawInventaryFromResultSet(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }



       /* MiniPanelProduit test = new MiniPanelProduit("Nutella", "https://static.openfoodfacts.org/images/products/301/762/040/6003/front_fr.130.400.jpg", "en:chocolate-spreads", 600.00, 63, "14/12/1999");
        this.add(test);
        test.setLocation(10,10);*/
    }

    public void drawInventaryFromResultSet(ResultSet rs) throws SQLException {
        LinkedList<String> noms = new LinkedList<>();
        LinkedList<String> imgAdress = new LinkedList<>();
        LinkedList<String> categories = new LinkedList<>();
        LinkedList<Double> contenances = new LinkedList<>();
        LinkedList<Double> pourcentagesRestants = new LinkedList<>();
        LinkedList<String> datesPeremption = new LinkedList<>();

        while(rs.next()) {
            try {
                pourcentagesRestants.add(rs.getDouble("pourcentageRestant"));
                datesPeremption.add("14/12/1999"); //TODO : à changer pour appel à BD (il faudra mettre la date dans la BD)

                PreparedStatement ps = BDFlux.conn.prepareStatement("SELECT * FROM ListeProduit WHERE codeBarre = ?;");
                ps.setDouble(1, rs.getDouble("codeBarre"));
                ResultSet rsListe = ps.executeQuery();
                if (rsListe.next()) {
                    noms.add(rsListe.getString("nomProduit"));
                    imgAdress.add(rsListe.getString("lienImage"));
                    categories.add(rsListe.getString("categorie"));
                    contenances.add(rsListe.getDouble("contenance"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        int i;
        for (i = 0; i < noms.size(); i++) {
            MiniPanelProduit mpp = new MiniPanelProduit(noms.get(i), imgAdress.get(i), categories.get(i), contenances.get(i), pourcentagesRestants.get(i), datesPeremption.get(i));
            mpp.setLocation(10+(210*i)%(210*4), 10+(310*(i/4)));
            this.add(mpp);
        }

    }

}
