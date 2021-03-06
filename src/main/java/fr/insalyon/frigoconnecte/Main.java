/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.frigoconnecte;

import fr.insalyon.frigoconnecte.arduino.usb.ArduinoManager;
import fr.insalyon.frigoconnecte.arduino.util.Console;
import fr.insalyon.frigoconnecte.view.Events;
import fr.insalyon.frigoconnecte.view.Fenetre;

import fr.insalyon.frigoconnecte.onlineservices.BDFlux;
import fr.insalyon.frigoconnecte.view.NewFrigo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main  {

    public static int ID_FRIGO =66;
    public static String nomFrigo = "";
    public static String adresseFrigo = "";

    public static NewFrigo nf;

    public static Fenetre F;
    public static Events eventsHandler;
    public static BDFlux maBD;
    public static final Console console = new Console();
    public static boolean estEnModeRetrait = false;
    public static boolean estEnModeDepot = false;

    public static int buffProduct;


    public static void main(String[] args) {

        maBD = new BDFlux("G221_C_BD2", "G221_C", "G221_C");
        eventsHandler = new Events();
        if (!maBD.isRefrgierateurExistant(ID_FRIGO)) nf= new NewFrigo();
        F = new Fenetre(eventsHandler);



        // Affichage sur la console
        console.log("DÉBUT du programme TestArduino");

        console.log("TOUS les Ports COM Virtuels:");
        for (String port : ArduinoManager.listVirtualComPorts()) {
            console.log(" - " + port);
        }
        console.log("----");

        // Recherche d'un port disponible (avec une liste d'exceptions si besoin)
        String myPort = ArduinoManager.searchVirtualComPort("COM0", "/dev/tty.usbserial-FTUS8LMO", "<autre-exception>");

        console.log("CONNEXION au port " + myPort);

        ArduinoManager arduino = new ArduinoManager(myPort) {
            @Override
            protected void onData(String line) {
                int a = 0;

                // Cette méthode est appelée AUTOMATIQUEMENT lorsque l'Arduino envoie des données
                // Affichage sur la Console de la ligne transmise par l'Arduino

                console.println("ARDUINO >> " + line);
                String[] data = line.split("!");

                if (data.length < 7) { // cas d'une donnée erronée pour protéger la BD
                    console.println("OUPS" + data.length);
                    for (int i = 0; i < data.length; i++)
                        console.println(data[i]);
                } else {
                    ArrayList<String[]> dataFinal = new ArrayList<String[]>();
                    for (int j = 0; j < data.length; j++) {
                        dataFinal.add(data[j].split(";"));
                    }

                    for (int k = 0; k < dataFinal.size(); k++) {
                        String[] tab = dataFinal.get(k);
                        if (tab.length > 1) {
                            a = Integer.parseInt(tab[0]);
                            if (a == 1) // permet l'insertion dans la table Produit
                                if (estEnModeDepot) {
                                    maBD.ajouterProduit(ID_FRIGO, Long.parseLong(tab[1]));
                                    try {
                                        F.panelDepot.subPanelDepot.updateSelectionFromResultSet(maBD.getLatestAddedProductIn(ID_FRIGO));
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } else if (estEnModeRetrait) {
                                    try {
                                        F.panelRetrait.subPanelRetrait.updateSelectionFromResultSet(maBD.enleverProduit(ID_FRIGO, Long.parseLong(tab[1])));
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            if (a != 0) {
                                System.out.println(a);
                                int i = maBD.ajouterMesure(ID_FRIGO, a, k, Double.parseDouble(tab[1]));
                                console.log("ajoutMesure: " + i);
                            }
                        }
                    }

                    /*String s = "-- ";

                    for (String[] tab : dataFinal) {
                        s = s + "\n\t" + tab.length + " -- \n";
                        for (int j = 0; j < tab.length; j++)        // Permet d'afficher sur la console les données transmis sous forme "idCapteur;val"
                            s = s + (" - " + tab[j]);
                    }
                    console.println(s);*/
                }
            }

        };


        try {
            console.log("DÉMARRAGE de la connexion");
            // Connexion à l'Arduino
            arduino.start();

            console.log("BOUCLE infinie en attente du Clavier");
            // Boucle d'ecriture sur l'arduino (execution concurrente au thread)
            boolean exit = false;

            while (!exit) {

                // Lecture Clavier de la ligne saisie par l'Utilisateur
                String line = console.readLine("Envoyer une ligne (ou 'stop') > ");

                if (line.length() != 0) {

                    // Affichage sur l'écran
                    console.log("CLAVIER >> " + line);

                    // Test de sortie de boucle
                    exit = line.equalsIgnoreCase("stop");

                    if (!exit) {
                        // Envoi sur l'Arduino du texte saisi au Clavier
                        arduino.write(line);
                    }
                }
            }

            console.log("ARRÊT de la connexion");
            // Fin de la connexion à l'Arduino
            arduino.stop();

        } catch (IOException ex) {
            // Si un problème a eu lieu...
            console.log(ex);
        }



    }


}
