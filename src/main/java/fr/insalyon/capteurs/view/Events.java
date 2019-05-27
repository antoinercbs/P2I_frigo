package fr.insalyon.capteurs.view;

import fr.insalyon.capteurs.Capteurs;
import fr.insalyon.capteurs.view.Fenetre;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.Timer;

public class Events implements MouseListener, ChangeListener, ItemListener, ActionListener {

    public Fenetre F;

    public Events() {
        this.F = fr.insalyon.capteurs.Capteurs.F;

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == Capteurs.F.BoutonDepot) {
            Capteurs.F.remove(Capteurs.F.principal);
            Capteurs.F.setContentPane(Capteurs.F.panelDepot);
            Capteurs.F.revalidate();
        }
        if (e.getSource() == Capteurs.F.BoutonRetrait) {
            Capteurs.F.remove(Capteurs.F.principal);
            Capteurs.F.setContentPane(Capteurs.F.panelRetrait);
            Capteurs.F.revalidate();
        }
        if (e.getSource() == Capteurs.F.BoutonMonFrigo) {
            Capteurs.F.remove(Capteurs.F.principal);
            Capteurs.F.setContentPane(Capteurs.F.tab);
            Capteurs.F.revalidate();
        }
        if (e.getSource() == Capteurs.F.BoutonRetourInventaire || e.getSource() == Capteurs.F.BoutonRetourRecettes || e.getSource() == Capteurs.F.BoutonRetourStats) {
            Capteurs.F.remove(Capteurs.F.tab);
            Capteurs.F.setContentPane(Capteurs.F.principal);
            Capteurs.F.revalidate();
        }
        if (e.getSource() == Capteurs.F.boutonRetourDepot) {
            Capteurs.F.remove(Capteurs.F.panelDepot);
            Capteurs.F.setContentPane((Capteurs.F.principal));
            Capteurs.F.revalidate();
        }
        if (e.getSource() == Capteurs.F.BoutonRetourRetrait) {
            Capteurs.F.remove(Capteurs.F.panelRetrait);
            Capteurs.F.setContentPane((Capteurs.F.principal));
            Capteurs.F.revalidate();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
