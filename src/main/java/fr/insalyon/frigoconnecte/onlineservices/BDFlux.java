package fr.insalyon.frigoconnecte.onlineservices;

import fr.insalyon.frigoconnecte.model.Recette;

import java.sql.*;
import java.util.*;
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
    private Traduction translator;

    public BDFlux(String bd, String compte, String motDePasse) {
        this.foodAPI = new FoodAPIManager();
        this.translator = new Traduction();
        try {

            //Enregistrement de la classe du driver par le driverManager
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver trouvé...");

            //Création d'une connexion sur la base de donnée
            this.conn = DriverManager.getConnection("jdbc:mysql://PC-TP-MYSQL.insa-lyon.fr:3306/" + bd, compte, motDePasse);
            //this.conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/" + bd, compte, motDePasse);
            System.out.println("Connexion établie...");

            // Prepared Statement
            this.insertMesureStatement = this.conn.prepareStatement("INSERT INTO Mesure (idRefrigerateur,idCapteur,valeur,dateMesure, idTypeCapteur) VALUES (?,?,?,?,?) ;");
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

        public String getIngredientsFor(int idRecette) {
            PreparedStatement ps = null;
            try {
                ps = BDFlux.conn.prepareStatement("SELECT nomIngredients FROM Recette WHERE idRecette=?;");
                ps.setInt(1, idRecette);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String s = "";
                    String liste[] = rs.getString("nomIngredients").split(";");
                    for (int i = 0; i < liste.length; i++) {
                        s+="-" + liste[i]+"\n";
                    }
                    return s;
                }
                return null;
            } catch (SQLException e) {
                return null;
            }
        }


    public ArrayList<Recette> getRecettesFor(int idFrigo) {
        PreparedStatement ps = null;
        ArrayList<Recette> recettes = new ArrayList<>();
        try {
            ps = BDFlux.conn.prepareStatement("SELECT categorie FROM Produit p, ListeProduit lp WHERE p.codeBarre = lp.codeBarre AND p.idRefrigerateur=?;");
            ps.setInt(1, idFrigo);
            ResultSet rs = ps.executeQuery();
            String s = "";
            while (rs.next()) s+=rs.getString("categorie");
            String[] liste = s.split(";");
            Set<String> ingredients = new HashSet<>(Arrays.asList(liste));
            liste = ingredients.toArray(new String[ingredients.size()]);
            System.out.println(s);
            for (int i = 0; i < liste.length; i++){
                recettes.addAll(getRecettesWith(liste[i]));
            }

            setRecetteIterationsInList(recettes);

            Set<Recette> recettesHash = new HashSet<Recette>(recettes); //Enlèvement des doublons
            recettes.clear();
            recettes.addAll(recettesHash);

            Collections.sort(recettes, (r1, r2) -> r2.getRatio() - r1.getRatio());


            while (recettes.size() > 25) recettes.remove(recettes.size()-1);

            return  recettes;
        } catch (SQLException e) {
            return recettes;
        }
    }

    private void setRecetteIterationsInList(ArrayList<Recette> recetteArrayList) {
        for (Recette recetteA : recetteArrayList) {
            int i = 0;
            for (Recette recetteB : recetteArrayList) {
                if (recetteA.equals(recetteB)) i++;
            }
            recetteA.iterations = i;
        }
    }

    private ArrayList<Recette> getRecettesWith(String ingredient) {
        PreparedStatement ps = null;
        ArrayList<Recette> recettes = new ArrayList<>();
        if (ingredient.equals(""))return recettes;
        try {
            ingredient = "%" + ingredient + "%";
            ps = BDFlux.conn.prepareStatement("SELECT nomRecette, lienImage, idRecette, nbIngredients FROM Recette WHERE nomIngredients LIKE ?;");
            ps.setString(1,ingredient);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                recettes.add(new Recette(rs.getInt("idRecette"), rs.getString("lienImage"), rs.getString("nomRecette"), rs.getInt("nbIngredients")));
            }
            return recettes;
        } catch (SQLException e) {
            return recettes;
        }
    }

    public ResultSet getEchangeProductsInRecette(int idFrigo, int idRecette) {
        PreparedStatement ps = null;
        ArrayList<Integer> buffProducts = new ArrayList<>();
        try {
            ps = BDFlux.conn.prepareStatement("SELECT * FROM Produit p, ListeProduit lp WHERE p.codeBarre = lp.codeBarre AND p.idRefrigerateur<>? and p.EstEchangeable=1;");
            ps.setInt(1, idFrigo);
            ResultSet rs = ps.executeQuery();
            String s = "";
            while (rs.next()) {
                String[] liste =rs.getString("categorie").split(";");
                int n = 0;
                for (int i = 0; i < liste.length; i++) {
                    if (!liste[i].equals("")) {
                        String ingredient = "%" + liste[i] + "%";
                        ps = BDFlux.conn.prepareStatement("SELECT * FROM Recette WHERE idRecette=? AND nomIngredients LIKE ?;");
                        ps.setInt(1, idRecette);
                        ps.setString(2, ingredient);
                        ResultSet rs2 = ps.executeQuery();
                        if (rs2.next()) n++; //Si il y a une réponse, on ajoute 1
                    }
                }
                if (n == liste.length) buffProducts.add(rs.getInt("idProduit"));
            }
            String endStatement = "";
            for (Integer id : buffProducts) {
                if (buffProducts.indexOf(id) == buffProducts.size() -1) endStatement+= ("idProduit="+id);
                else endStatement+= ("idProduit="+id+" OR ");
            }
            if (buffProducts.size() == 0) return null;
            ps = BDFlux.conn.prepareStatement("SELECT * FROM Produit WHERE " + endStatement);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getProductsInRecette(int idFrigo, int idRecette) {
        PreparedStatement ps = null;
        ArrayList<Integer> buffProducts = new ArrayList<>();
        try {
            ps = BDFlux.conn.prepareStatement("SELECT * FROM Produit p, ListeProduit lp WHERE p.codeBarre = lp.codeBarre AND p.idRefrigerateur=?;");
            ps.setInt(1, idFrigo);
            ResultSet rs = ps.executeQuery();
            String s = "";
            while (rs.next()) {
                String[] liste =rs.getString("categorie").split(";");
                int n = 0;
                for (int i = 0; i < liste.length; i++) {
                    if (!liste[i].equals("")) {
                        String ingredient = "%" + liste[i] + "%";
                        ps = BDFlux.conn.prepareStatement("SELECT * FROM Recette WHERE idRecette=? AND nomIngredients LIKE ?;");
                        ps.setInt(1, idRecette);
                        ps.setString(2, ingredient);
                        ResultSet rs2 = ps.executeQuery();
                        if (rs2.next()) n++; //Si il y a une réponse, on ajoute 1
                    }
                }
                if (n == liste.length) buffProducts.add(rs.getInt("idProduit"));
            }
            String endStatement = "";
            for (Integer id : buffProducts) {
                if (buffProducts.indexOf(id) == buffProducts.size() -1) endStatement+= ("idProduit="+id);
                else endStatement+= ("idProduit="+id+" OR ");
            }
            if (buffProducts.size() == 0) return null;
            ps = BDFlux.conn.prepareStatement("SELECT * FROM Produit WHERE " + endStatement);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            return null;
        }
    }

    public String getPendingExchanges(int idFrigo) {
        PreparedStatement ps = null;
        String s = "";
        try {
            ps = BDFlux.conn.prepareStatement("SELECT nomProduit, idEchange FROM ListeProduit lp, Produit p, Echange e, Refrigerateur r WHERE lp.codeBarre=p.codeBarre AND r.idRefrigerateur=p.idRefrigerateur AND (e.Produit1=p.idProduit OR e.Produit2=p.idProduit) AND p.idRefrigerateur=?;");
            ps.setInt(1, idFrigo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               ps = BDFlux.conn.prepareStatement("SELECT nomProprietaire, adresse, nomProduit FROM ListeProduit lp, Produit p, Echange e, Refrigerateur r WHERE lp.codeBarre=p.codeBarre AND r.idRefrigerateur=p.idRefrigerateur AND (e.Produit1=p.idProduit OR e.Produit2=p.idProduit) AND p.idRefrigerateur<>? AND idEchange=?;");
                ps.setInt(1, idFrigo);
                ps.setInt(2, rs.getInt("idEchange"));
                ResultSet rs2 = ps.executeQuery();
                rs2.next();
                s += (rs2.getString("nomProprietaire") + " qui habite "+ rs2.getString("adresse") +" va échanger son " + rs2.getString("nomProduit") + " contre votre ");
                s+= (rs.getString("nomProduit") + ". \n");
            }
        } catch (SQLException e) {
        }

        return s;

    }

    public void createNewExchange(int a, int b) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("INSERT INTO Echange(Produit1, Produit2) VALUES(?,?)");
            System.out.println("a = " + a);
            System.out.println("b = " + b);
            ps.setInt(1, a);
            ps.setInt(2, b);
            ps.executeUpdate();
            ps = BDFlux.conn.prepareStatement("UPDATE Produit SET estEchangeable=0, estDemandePar=null WHERE idProduit=? OR idProduit=?");
            ps.setInt(1, a);
            ps.setInt(2, b);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getMesureFrom(int idFrigo, int idCapteur) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT * FROM Mesure m, TypeCapteur tc WHERE m.idTypeCapteur=tc.idTypeCapteur AND m.idRefrigerateur=? AND idCapteur=? order by dateMesure;");
            ps.setInt(1, idFrigo);
            ps.setInt(2, idCapteur);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean isRefrgierateurExistant(int id) {
        try {
            PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM Refrigerateur WHERE idRefrigerateur = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return (rs.next());
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        return false;
    }

    public void addRefrigerateur(int ID, String name, String adress) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("INSERT INTO Refrigerateur(idRefrigerateur, nomProprietaire, adresse) VALUES(?,?,?)");
            ps.setInt(1, ID);
            ps.setString(2, name);
            ps.setString(3, adress);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
    public String getRefrigerateurOwnerName(int idFrigo) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT nomProprietaire FROM Refrigerateur WHERE idRefrigerateur = ?");
            ps.setInt(1, idFrigo);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("nomProprietaire");
        } catch (SQLException e) {
            return null;
        }
    }
    public int getProductAsker(int idProduit) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT estDemandePar FROM Produit WHERE idProduit=?;");
            ps.setInt(1, idProduit);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("estDemandePar");
        } catch (SQLException e) {
            return -1;
        }
    }
    public int getProductOwner(int idProduit) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT idRefrigerateur FROM Produit WHERE idProduit=?");
            ps.setInt(1, idProduit);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("idRefrigerateur");
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

    public ResultSet getSharedProductsInRefrigerateur(int idFrigo) {
        PreparedStatement ps = null;
        try {
            ps = BDFlux.conn.prepareStatement("SELECT * FROM Produit WHERE idRefrigerateur=? AND estEchangeable=1 ORDER BY codeBarre;");
            ps.setInt(1, idFrigo);
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
            this.insertListeProduitStatement.setString(4, this.translator.getCategoriesFrom(this.foodAPI.getProductName()));
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
    public int ajouterMesure(int idRefrigerateur,int idTypeCapteur, int idCapteur, double valeur) {
        try {
            Date dateTime=new Date();
            this.insertMesureStatement.setInt(1, idRefrigerateur);
            this.insertMesureStatement.setInt(2, idCapteur);
            this.insertMesureStatement.setDouble(3, valeur);
            this.insertMesureStatement.setTimestamp(4,new Timestamp(dateTime.getTime()));
            this.insertMesureStatement.setInt(5, idTypeCapteur);
            return this.insertMesureStatement.executeUpdate();
        } catch (SQLException ex) {
            return -1;
        }
    }


}
