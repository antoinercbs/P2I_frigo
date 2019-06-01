package fr.insalyon.frigoconnecte.model;

import fr.insalyon.frigoconnecte.Main;


public class Produit {
    public int idProduit;
    public String nom;
    public String imgAdress;
    public String categorie;
    public double contenance;
    public double pourcentageRestant;
    public String datePeremption;
    public int estDemandePar;

    public boolean isShared = false;

    public Produit(int idProduit, String nom, String imgAdress, String categorie, double contenance, double pourcentageRestant, String datePeremption) {
        this.idProduit=idProduit;
        this.nom = nom;
        this.imgAdress = imgAdress;
        this.categorie = categorie;
        this.contenance = contenance;
        this.pourcentageRestant = pourcentageRestant;
        this.datePeremption = datePeremption;
        this.isShared = Main.maBD.getShareStatueFrom(idProduit);
        this.estDemandePar = Main.maBD.getProductAsker(idProduit);
    }

    public boolean changeSharedStatue() {
        this.isShared=!this.isShared;
        Main.maBD.setSharedSatueFor(this.isShared, this.idProduit);
        return this.isShared;
    }

    public void changeExchangeStatue(int estDemandePar) {
        if (this.estDemandePar==estDemandePar) {
            Main.maBD.setExchangeSatueFor(-1, this.idProduit);
            return;
        }
        this.estDemandePar=estDemandePar;
        Main.maBD.setExchangeSatueFor(this.estDemandePar, this.idProduit);
    }
}
