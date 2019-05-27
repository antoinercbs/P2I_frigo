package fr.insalyon.p2i2.javaarduino;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BDFlux {

    private Connection conn;
    private PreparedStatement insertMesureStatement;
    private PreparedStatement insertProduitStatement;

    public static void main(String[] args) {

        BDFlux bdFlux = new BDFlux("<base-de-donnees>", "<login>", "<mot-de-passe>");

    }

    public BDFlux(String bd, String compte, String motDePasse) {
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
            this.insertProduitStatement = this.conn.prepareStatement("INSERT INTO Produit (codeBarre,idRefrigerateur,dateInsertion) VALUES (?,?,?) ;");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
    }

    
    public int ajouterProduit(int idRefrigerateur,int codeBarre) { // méthode pas encore utilisée
        try {
            Date dateTime=new Date();
            this.insertProduitStatement.setInt(2, idRefrigerateur);
            this.insertProduitStatement.setInt(1, codeBarre);
            this.insertProduitStatement.setTimestamp(3,new Timestamp(dateTime.getTime()));
            return this.insertProduitStatement.executeUpdate();
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
