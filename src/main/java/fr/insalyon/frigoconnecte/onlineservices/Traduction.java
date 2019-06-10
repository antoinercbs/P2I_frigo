package fr.insalyon.frigoconnecte.onlineservices;

import java.io.*;
import java.util.ArrayList; //Pour les liste

public class Traduction {


    public Traduction() {
    }

    public String getCategoriesFrom(String name) {
        ArrayList<String> cat = traductionFrAn(name);
        String s = "";
        for (String c : cat) {
            s+=c+";";
        }
        return s;
    }

    public ArrayList<String> traductionFrAn(String motATraduireFr) {

        ArrayList<String> motTraduitAng = new ArrayList<String>();

        try {
            File directory = new File("src/resources/traduction.txt");
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(directory)));

            String line;
            String[] mot;
            int nMot = 0;

            while ((line = input.readLine()) != null) {

                mot = line.split(";");

                if (mot.length > 1) {

                    if ( containsIgnoreCase(motATraduireFr, mot[0]) ) {

                        motTraduitAng.add(mot[1]);
                        nMot++;
                    }
                }
            }
            input.close();

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
        return motTraduitAng;
    }

    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

}

