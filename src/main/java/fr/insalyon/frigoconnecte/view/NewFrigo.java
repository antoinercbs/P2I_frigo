package fr.insalyon.frigoconnecte.view;

import fr.insalyon.frigoconnecte.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFrigo extends JDialog implements ActionListener{
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField nameTextField;
    private JTextField adressTextField;

    public NewFrigo() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(this);
        this.pack();
        this.setVisible(true);

    }

    public void onOK() {
        Main.nomFrigo = nameTextField.getText();
        Main.adresseFrigo =adressTextField.getText();
        System.out.println(Main.nomFrigo);
        Main.maBD.addRefrigerateur(Main.ID_FRIGO, Main.nomFrigo, Main.adresseFrigo);
        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        onOK();
    }
}
