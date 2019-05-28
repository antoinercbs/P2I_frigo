package fr.insalyon.capteurs.view;

import fr.insalyon.capteurs.Capteurs;

import java.awt.event.*;
import javax.swing.event.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Events implements ActionListener {

    public Events() {
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Capteurs.F.panelPrincipal.boutonDepot) {
            Capteurs.F.remove(Capteurs.F.panelPrincipal);
            Capteurs.F.setContentPane(Capteurs.F.panelDepot);
            Capteurs.F.revalidate();
        }
        if (e.getSource() == Capteurs.F.panelPrincipal.boutonRetrait) {
            Capteurs.F.remove(Capteurs.F.panelPrincipal);
            Capteurs.F.setContentPane(Capteurs.F.panelRetrait);
            Capteurs.F.revalidate();
        }
        if (e.getSource() == Capteurs.F.panelPrincipal.boutonMonFrigo) {
            Capteurs.F.remove(Capteurs.F.panelPrincipal);
            Capteurs.F.setContentPane(Capteurs.F.panelMonFrigo);
            Capteurs.F.revalidate();
        }
        if (e.getSource() == Capteurs.F.panelInventaire.boutonRetour || e.getSource() == Capteurs.F.panelRecettes.boutonRetour || e.getSource() == Capteurs.F.panelStatistiques.boutonRetour) {
            Capteurs.F.remove(Capteurs.F.panelMonFrigo);
            Capteurs.F.setContentPane(Capteurs.F.panelPrincipal);
            Capteurs.F.revalidate();
        }
        if (e.getSource() == Capteurs.F.panelDepot.boutonRetour) {
            Capteurs.F.remove(Capteurs.F.panelDepot);
            Capteurs.F.setContentPane((Capteurs.F.panelPrincipal));
            Capteurs.F.revalidate();
        }
        if (e.getSource() == Capteurs.F.panelRetrait.boutonRetour) {
            Capteurs.F.remove(Capteurs.F.panelRetrait);
            Capteurs.F.setContentPane((Capteurs.F.panelPrincipal));
            Capteurs.F.revalidate();
        }
    }

}
