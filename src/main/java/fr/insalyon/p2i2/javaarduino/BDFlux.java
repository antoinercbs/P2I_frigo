package fr.insalyon.p2i2.javaarduino;

import fr.insalyon.capteurs.onlineservices.FoodAPIManager;

import java.sql.*;
import java.util.Date;

public class BDFlux {

    private Connection conn;
    private PreparedStatement insertMesureStatement;
    private PreparedStatement insertProduitStatement;
    private PreparedStatement insertListeProduitStatement;

    private FoodAPIManager foodAPI;

    public BDFlux(String bd, String compte, String motDePasse) {
        this.foodAPI = new FoodAPIManager();
        try {

            //Enregistrement de la classe du driver par le driverManager
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver trouvé...");

            //Création d'une connexion sur la base de donnée
            this.conn = DriverManager.getConnection("jdbc:mysql://PC-TP-MYSQL.insa-lyon.fr:3306/" + bd, compte, motDePasse);
            //this.conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/" + bd, compte, motDePasse);
            System.out.println("Connexion établie...");

            // Prepared Statement
            this.insertMesureStatement = this.conn.prepareStatement("INSERT INTO Mesure (idRefrigerateur,idCapteur,valeur,dateMesure) VALUES (?,?,?,?) ;");
            this.insertProduitStatement = this.conn.prepareStatement("INSERT INTO Produit (codeBarre,idRefrigerateur,dateInsertion, pourcentageRestant) VALUES (?,?,?, 99) ;");
            this.insertListeProduitStatement = this.conn.prepareStatement("INSERT INTO ListeProduit (codeBarre, nomProduit, lienImage, categorie, contenance, uniteContenace, dureeValide) VALUES (?, ?, ?, ?, ?, ?, ?);");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
    }

    public boolean isProductInProductList(double gtin) {
       try {
           PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM ListeProduit WHERE codeBarre = ?;");
            ps.setDouble(1, gtin);
           ResultSet rs = ps.executeQuery();
           return (rs.next()); //s'il n'y a pas ce produit dans la liste, on retourne faux
       } catch (Exception ex) {
           ex.printStackTrace(System.err);
       }

       return false;
    }

    
    public int ajouterProduit(int idRefrigerateur,double codeBarre) {
        try {
            Date dateTime=new Date();
            this.insertProduitStatement.setInt(2, idRefrigerateur);
            this.insertProduitStatement.setDouble(1, codeBarre);
            this.insertProduitStatement.setTimestamp(3,new Timestamp(dateTime.getTime()));
            if (!isProductInProductList(codeBarre)) {
                ajouterListeProduitDepuisAPI(codeBarre);
            }
            return this.insertProduitStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return -1;
        }
    }

    public int ajouterListeProduitDepuisAPI(double gtin) {
        this.foodAPI.fetchDataFromGtin(String.valueOf((long) gtin));
        try {
            this.insertListeProduitStatement.setDouble(1, gtin);
            this.insertListeProduitStatement.setString(2, this.foodAPI.getProductName());
            this.insertListeProduitStatement.setString(3, this.foodAPI.getProductImageURL());
            this.insertListeProduitStatement.setString(4, this.foodAPI.getProductCategorie());
            this.insertListeProduitStatement.setDouble(5, this.foodAPI.getContenance());
            this.insertListeProduitStatement.setString(6, this.foodAPI.getContenanceUnit());
            this.insertListeProduitStatement.setInt(7, this.foodAPI.getProductDuration());
            return this.insertListeProduitStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return -1;
        }
    }

    public int ajouterMesure(int idRefrigerateur,int idCapteur, double valeur) {
        try {
            Date dateTime=new Date();
            this.insertMesureStatement.setInt(1, idRefrigerateur);
            this.insertMesureStatement.setInt(2, idCapteur);
            this.insertMesureStatement.setDouble(3, valeur);
            this.insertMesureStatement.setTimestamp(4,new Timestamp(dateTime.getTime()));
            return this.insertMesureStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return -1;
        }
    }


}
