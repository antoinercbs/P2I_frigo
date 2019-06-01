package fr.insalyon.frigoconnecte.view;

import fr.insalyon.frigoconnecte.Main;

import java.awt.event.*;

public class Events implements ActionListener, MouseListener {

    public Events() {
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Main.F.tm) {
            Main.F.repaint();
        }
        if (e.getSource() == Main.F.panelPrincipal.boutonDepot) {
            Main.F.remove(Main.F.panelPrincipal);
            Main.F.setContentPane(Main.F.panelDepot);
            Main.F.revalidate();
            Main.estEnModeDepot=true;
        }
        if (e.getSource() == Main.F.panelPrincipal.boutonRetrait) {
            Main.F.remove(Main.F.panelPrincipal);
            Main.F.setContentPane(Main.F.panelRetrait);
            Main.F.revalidate();
            Main.estEnModeRetrait=true;
        }
        if (e.getSource() == Main.F.panelPrincipal.boutonMonFrigo) {
            Main.F.remove(Main.F.panelPrincipal);
            Main.F.monFrigoContainer.remove(Main.F.panelMonFrigo);
            Main.F.panelMonFrigo = new PanelMonFrigo(this);
            Main.F.monFrigoContainer.add(Main.F.panelMonFrigo);
            Main.F.setContentPane(Main.F.monFrigoContainer);
            Main.F.revalidate();
        }
        if (e.getSource() == Main.F.boutonRetour) {
            Main.F.remove(Main.F.monFrigoContainer);
            Main.F.setContentPane(Main.F.panelPrincipal);
            Main.F.revalidate();
        }
        if (e.getSource() == Main.F.panelDepot.boutonRetour) {
            Main.F.remove(Main.F.panelDepot);
            Main.F.setContentPane((Main.F.panelPrincipal));
            Main.F.revalidate();
            Main.estEnModeDepot=false;
        }
        if (e.getSource() == Main.F.panelRetrait.boutonRetour) {
            Main.F.remove(Main.F.panelRetrait);
            Main.F.setContentPane((Main.F.panelPrincipal));
            Main.F.revalidate();
            Main.estEnModeRetrait=false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().getClass().equals(MiniPanelProduit.class)) {
            MiniPanelProduit mpp = (MiniPanelProduit) e.getSource();
            if (mpp.getParent().getClass().equals(Main.F.panelMonFrigo.panelInventaire.getClass())) {
                mpp.changeInventaryColor();
                mpp.p.changeSharedStatue();
            } else if (mpp.getParent().getClass().equals(Main.F.panelMonFrigo.panelDemanderEchange.getClass())) {
                mpp.changeExchangeColor();
                mpp.p.changeSharedStatue();
                mpp.p.changeExchangeStatue(mpp.idFrigo);
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
}
