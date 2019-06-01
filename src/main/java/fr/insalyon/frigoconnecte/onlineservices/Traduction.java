/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.frigoconnecte.onlineservices;

public class Traduction {

    public static void main(String[] args) {
        String nomProduitFr = "qosdhamlqeochpouletqsoefdfnl";
        System.out.println(traduction(nomProduitFr));
    }

    public static String traduction(String nomProduitFr) {
        String motAng = " ";


        for (int i = 0; i < nomProduitFr.length(); i++) {
            if (i <= nomProduitFr.length() - 6 && (nomProduitFr.substring(i, i + 6).equals("poulet") || nomProduitFr.substring(i, i + 6).equals("Poulet"))) {
                motAng = "chicken";
            } else if (i <= nomProduitFr.length() - 6 && (nomProduitFr.substring(i, i + 6).equals("tomate") || nomProduitFr.substring(i, i + 6).equals("Tomate"))) {
                motAng = "tomato";
            } else if (i <= nomProduitFr.length() - 3 && (nomProduitFr.substring(i, i + 3).equals("Jambon") || nomProduitFr.substring(i, i + 3).equals("jambon"))) {
                motAng = "ham";
            } else if (i <= nomProduitFr.length() - 4 && (nomProduitFr.substring(i, i + 4).equals("oeuf") || nomProduitFr.substring(i, i + 4).equals("Oeuf"))) {
                motAng = "egg";
            } else if (i <= nomProduitFr.length() - 6 && (nomProduitFr.substring(i, i + 6).equals("farine") || nomProduitFr.substring(i, i + 6).equals("Farine"))) {
                motAng = "flour";
            } else if (i <= nomProduitFr.length() - 6 && (nomProduitFr.substring(i, i + 6).equals("yaourt") || nomProduitFr.substring(i, i + 6).equals("Yaourt"))) {
                motAng = "yogurt";
            } else if (i <= nomProduitFr.length() - 6 && (nomProduitFr.substring(i, i + 6).equals("salade") || nomProduitFr.substring(i, i + 6).equals("Salade"))) {
                motAng = "salade";
            } else if (i <= nomProduitFr.length() - 7 && (nomProduitFr.substring(i, i + 7).equals("fromage") || nomProduitFr.substring(i, i + 7).equals("Fromage"))) {
                motAng = "cheese";
            } else if (i <= nomProduitFr.length() - 9 && (nomProduitFr.substring(i, i + 9).equals("cornichon") || nomProduitFr.substring(i, i + 9).equals("Cornichon"))) {
                motAng = "pickle";
            } else if (i <= nomProduitFr.length() - 4 && (nomProduitFr.substring(i, i + 4).equals("lait") || nomProduitFr.substring(i, i + 4).equals("Lait"))) {
                motAng = "milk";
            } else if (i <= nomProduitFr.length() - 5 && (nomProduitFr.substring(i, i + 5).equals("sauce") || nomProduitFr.substring(i, i + 5).equals("Sauce"))) {
                motAng = "sauce";
            } else if (i <= nomProduitFr.length() - 4 && (nomProduitFr.substring(i, i + 4).equals("meat") || nomProduitFr.substring(i, i + 4).equals("Meat"))) {
                motAng = "meat";
            } else if (i <= nomProduitFr.length() - 5 && (nomProduitFr.substring(i, i + 5).equals("steak") || nomProduitFr.substring(i, i + 5).equals("Steak"))) {
                motAng = "steak";
            } else if (i <= nomProduitFr.length() - 3 && (nomProduitFr.substring(i, i + 3).equals("jam") || nomProduitFr.substring(i, i + 3).equals("Jam"))) {
                motAng = "jam";
            } else if (i <= nomProduitFr.length() - 6 && (nomProduitFr.substring(i, i + 6).equals("banane") || nomProduitFr.substring(i, i + 6).equals("Banane"))) {
                motAng = "banana";
            } else if (i <= nomProduitFr.length() - 3 && (nomProduitFr.substring(i, i + 3).equals("eau") || nomProduitFr.substring(i, i + 3).equals("Eau"))) {
                motAng = "water";
            } else if (i <= nomProduitFr.length() - 8 && (nomProduitFr.substring(i, i + 8).equals("moutarde") || nomProduitFr.substring(i, i + 8).equals("Moutarde"))) {
                motAng = "mustard";
            } else if (i <= nomProduitFr.length() - 7 && (nomProduitFr.substring(i, i + 7).equals("ketchup") || nomProduitFr.substring(i, i + 7).equals("Ketchup"))) {
                motAng = "ketchup";
            } else if (i <= nomProduitFr.length() - 6 && (nomProduitFr.substring(i, i + 6).equals("citron") || nomProduitFr.substring(i, i + 6).equals("Citron"))) {
                motAng = "lemon";
            } else if (i <= nomProduitFr.length() - 5 && (nomProduitFr.substring(i, i + 5).equals("Pomme") || nomProduitFr.substring(i, i + 5).equals("Pomme"))) {
                motAng = "apple";
            } else if (i <= nomProduitFr.length() - 3 && (nomProduitFr.substring(i, i + 3).equals("BBQ"))) {
                motAng = "BBQ";
            }
        }

        return motAng;
    }
}
