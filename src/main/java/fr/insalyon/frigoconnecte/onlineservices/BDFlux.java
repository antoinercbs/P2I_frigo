package fr.insalyon.frigoconnecte.onlineservices;

import fr.insalyon.frigoconnecte.onlineservices.FoodAPIManager;

import java.sql.*;
import java.util.Date;

public class BDFlux {

    public static Connection conn;
    private PreparedStatement insertMesureStatement;
    private PreparedStatement insertProduitStatement;
    private PreparedStatement insertListeProduitStatement;
    private PreparedStatement insertEchangeStatement;
    private PreparedStatement insertListeProduitEchangeStatement;
    private PreparedStatement deleteProduitStatement;

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
            this.insertEchangeStatement=this.conn.prepareStatement("INSERT INTO Echange(idEchange,refrigerateur1,refrigerateur2,prixVente) VALUES(?,?,?,?); ");
            this.insertListeProduitEchangeStatement=this.conn.prepareStatement("INSERT INTO ListeProduitEchange(idEchange,idProduit,idRefrigerateur,nbProduits) VALUES(?,?,?,?); ");
            this.deleteProduitStatement=this.conn.prepareStatement("DELETE from Produit where idProduit=? and idRefrigerateur=? ;");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
    }

    public ResultSet enleverProduit(int idFrigo, double codeBarre) {
        PreparedStatement ps = null;
        try {
            int idToDel = 0;
            ps = BDFlux.conn.prepareStatement("SELECT idProduit FROM Produit WHERE idRefrigerateur=? AND codeBarre=?");
            ps.setInt(1, idFrigo);
            ps.setDouble(2, codeBarre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) idToDel = rs.getInt("idProduit");

            ps =  BDFlux.conn.prepareStatement("select * from Produit where idProduit=? and idRefrigerateur=? ;");
            ps.setInt(1, idToDel);
            ps.setInt(2, idFrigo);
            ResultSet rs2 = ps.executeQuery();
            ps = this.deleteProduitStatement;
            ps.setInt(1, idToDel);
            ps.setInt(2, idFrigo);
            ps.executeUpdate();
            return rs2;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getLatestAddedProductIn(int idFrigo) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT DISTINCT * FROM Produit p, ListeProduit lp WHERE p.idRefrigerateur=? AND  lp.codeBarre=p.codeBarre AND dateInsertion IN (SELECT max(dateInsertion) FROM Produit);");
            ps.setInt(1, idFrigo);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            return null;
        }
    }

    public ResultSet getExchangeProductsAskedFrom(int idFrigo) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT * FROM Produit WHERE idRefrigerateur=? AND estDemandePar <> -1 ORDER BY estDemandePar");
            ps.setInt(1, idFrigo);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            return null;
        }
    }

    public int getAskerCountFor(int idFrigo) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT DISTINCT estDemandePar FROM Produit  WHERE idRefrigerateur=? AND estDemandePar <> -1;");
            ps.setInt(1, idFrigo);
            ResultSet rs = ps.executeQuery();
            int i= 0;
            while (rs.next()) i++;
            return i;
        } catch (SQLException e) {
            return -1;
        }
    }
    public int getProductAsker(int idProduit) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT estDemandePar FROM Produit WHERE idProduit=? AND estDemandePar <> NULL AND estDemandePar <> -1;");
            ps.setInt(1, idProduit);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("estDemandePar");
        } catch (SQLException e) {
            return -1;
        }
    }

    public void setExchangeSatueFor(int value, int idProduit) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("UPDATE Produit SET estDemandePar=? WHERE idProduit=?");
            ps.setInt(1, value);
            ps.setInt(2, idProduit);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean getShareStatueFrom(int idProduit) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT estEchangeable FROM Produit WHERE idProduit=?;");
            ps.setInt(1, idProduit);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getBoolean("estEchangeable");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void setSharedSatueFor(boolean value, int idProduit) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("UPDATE Produit SET estEchangeable=? WHERE idProduit=?");
            ps.setBoolean(1, value);
            ps.setInt(2, idProduit);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ResultSet getExchangeProductsFor(int idFrigo) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT * FROM Produit WHERE idRefrigerateur <> ? AND estEchangeable=?;");
            ps.setInt(1, idFrigo);
            ps.setBoolean(2, true);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public ResultSet getProductsInRefrigerateur(int idFrigo) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT * FROM Produit WHERE idRefrigerateur=? ORDER BY codeBarre;");
            ps.setInt(1, idFrigo);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    /** Vérifie qu'un produit est présent dan la liste des produits auquels le frigo a déjà été connecté (Table ListeProduit)
     *
     * @param gtin le code barre du produit que l'on veut tester
     * @return vrai si le produit est dans la base
     */
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

    /** Cette méthode (ajouterEchange) gère tout le système du troque.
     * On choisit 2 refrigerateurs et une liste de produit de chacun représentée par un tableau de idProduit.
     * Tous les produits échangés sont insérés dans la table ListeProduitEchange.
     * Les produits sont ensuite supprimés d’un réfrigérateur et ajoutés dans l’autre.
     * @param idEchange id de cet échange
     * @param refrigerateur1 réfrigérateur 1 concerné par l'échange
     * @param refrigerateur2 réfrigérateur 1 concerné par l'échange
     * @param listeIdProduit1 Liste des produits de l'échange dans le réfrigérateur 1
     * @param listeIdProduit2 Liste des produits de l'échange dans le réfrigérateur 2
     * @param prixVente prix de l'échange
     */
    /*public int ajouterEchange(int idEchange,int refrigerateur1,int refrigerateur2,int[]listeIdProduit1,int[]listeIdProduit2,double prixVente){
        try{
            this.insertEchangeStatement.setInt(1,idEchange); // le idEchange on peut le faire aléatoire dans le main
            // , pas de auto_increment pour gérer les foreign key
            this.insertEchangeStatement.setInt(2,refrigerateur1);
            this.insertEchangeStatement.setInt(3,refrigerateur2);
            this.insertEchangeStatement.setDouble(4,prixVente);
            this.insertEchangeStatement.executeUpdate();
            double[] codeBarre1; // à ajouter: une requete select permettant de trouver pour chaque idProduit son codeBarre et de mettre dans
            // un tableau de codeBarre
            double[] codeBarre2; // utilisable après pour inserer les produits du ref1 dans ref2 et vice versa (voir ligne 90)

            for( int i=0; i<listeIdProduit1.length;i++){
                this.insertListeProduitEchangeStatement.setInt(1,idEchange);
                this.insertListeProduitEchangeStatement.setInt(2,listeIdProduit1[i])  ;
                this.insertListeProduitEchangeStatement.setInt(3,refrigerateur1)  ;
                this.insertListeProduitEchangeStatement.setInt(4,listeIdProduit1.length)  ;
                this.insertListeProduitEchangeStatement.executeUpdate();
                this.deleteProduitStatement.setInt(2,refrigerateur1);
                this.deleteProduitStatement.setInt(1,listeIdProduit1[i]);
                this.deleteProduitStatement.executeUpdate();
            }
            for( int i=0; i<listeIdProduit2.length;i++){
                this.insertListeProduitEchangeStatement.setInt(1,idEchange);
                this.insertListeProduitEchangeStatement.setInt(2,listeIdProduit2[i])  ;
                this.insertListeProduitEchangeStatement.setInt(3,refrigerateur2)  ;
                this.insertListeProduitEchangeStatement.setInt(4,listeIdProduit2.length) ;
                this.insertListeProduitEchangeStatement.executeUpdate();
                this.deleteProduitStatement.setInt(2,refrigerateur2);
                this.deleteProduitStatement.setInt(1,listeIdProduit2[i]); // Enleve le produit échangé de la BD.
                this.deleteProduitStatement.executeUpdate();
            }
            for(int i=0; i<codeBarre1.legnth;i++) {
                ajouterProduit(2, codeBarre1[i]);
            }
            for(int i=0; i<codeBarre2.legnth;i++) {
                ajouterProduit(1, codeBarre2[i]);
            }
            return 1;

        }catch (SQLException ex){
            ex.printStackTrace(System.err);
            return -1;
        }

    }*/



    /** Ajoute les infos d'un produit à ListeProduit à l'aide de l'API OpenFoodFact sur base d'un code barre.
     *
     * @param gtin le code barre du produit à traiter
     * @return -1 si problème avec SQL, 1 sinon
     */
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
    /**
     * ajoute un produit dans le réfrigérateur correspondant. Le cas échéant, ajoute ce produit à ListeProduits si le système
     * y est confronté pour la première fois en allant chercher les infos grâce au code barre.
     * @param idRefrigerateur id du frigo où l'on veut ajouter un produit
     * @param codeBarre code barre du produit à ajouter
     * @return
     */
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


    /**
     * Ajoute une mesure à l'ensemble des mesures dans la BDD
     * @param idRefrigerateur frigo dont vient la mesure
     * @param idCapteur identifiant du capteur ayant pris la mesure
     * @param valeur valeur numérique de la mesure
     */
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
