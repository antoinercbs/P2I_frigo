package fr.insalyon.p2i2.javaarduino;

import fr.insalyon.p2i2.javaarduino.usb.ArduinoManager;
import fr.insalyon.p2i2.javaarduino.util.Console;
import java.io.IOException;
import java.util.ArrayList;

public class TestArduino 
{
    
    public static void main(String[] args) {

        // Objet matérialisant la console d'exécution (Affichage Écran / Lecture Clavier)
        final Console console = new Console();
        final BDFlux  maBD= new BDFlux("G221_C_BD2","G221_C","G221_C");

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

                // Cette méthode est appelée AUTOMATIQUEMENT lorsque l'Arduino envoie des données
                // Affichage sur la Console de la ligne transmise par l'Arduino
                 
                console.println("ARDUINO >> " + line);
                String[] data= line.split("!");
                
                if(data.length<8){ // cas d'une donnée erronée pour protéger la BD
                    console.println("OUPS");
                    for(int i=0;i<data.length;i++)  
                        console.println(data[i]);
                }
                else{
                    ArrayList<String[]> dataFinal=new ArrayList<String[]>();
                    for(int j=0 ; j<data.length; j++){
                            dataFinal.add(data[j].split(";"));
                    }
     
               /* for(String[] tab: dataFinal){   ( une autre méthode pas utilisée)
                for(int j=0;j<tab.length;j++){
                    if(j%6==0)
                        maBD.ajouterMesure(1,2,Double.parseDouble(tab[j]));
                    if(j%6==1)
                        maBD.ajouterMesure(1,3,Double.parseDouble(tab[j]));
                    if(j%6==2)
                        maBD.ajouterMesure(1,4,Double.parseDouble(tab[j]));
                    if(j%6==3){
                        maBD.ajouterMesure(1,1,Double.parseDouble(tab[j]));
                        maBD.ajouterProduit(1,Integer.parseInt(tab[j]));
                    }
                }     
             }
*/
                for(String[] tab: dataFinal){
                       maBD.ajouterMesure(1, Integer.parseInt(tab[0]), Double.parseDouble(tab[1]));
                   /*if(Integer.parseInt(tab[0])==1) // permet l'insertion dans la table Produit
                       maBD.ajouterProduit(1,Integer.parseInt(tab[1]));
                       */
                       
                }

                String s="-- " ;
                    
                for(String[] tab: dataFinal){
                 s = s + "\n\t"+ tab.length + " -- \n";   
                for(int j=0;j<tab.length;j++)        // Permet d'afficher sur la console les données transmis sous forme "idCapteur;val"
                    s=s+(" - " + tab[j]);            
             }
               console.println(s);
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
