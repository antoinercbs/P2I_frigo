package fr.insalyon.frigoconnecte.model;

import java.util.Objects;

public class Recette {
    public String imgAdress;
    public String nom;
    public int idRecette;
    public int nbIngredients;
    public int iterations;

    public Recette(int idRecette, String imgAdress, String nom, int nbIngredients) {
        this.imgAdress = "http://img.antoine-rcbs.fr/p2i/" + imgAdress.substring(imgAdress.indexOf(".com/")+4);
        this.nom = nom;
        this.idRecette = idRecette;
        this.nbIngredients = nbIngredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recette recette = (Recette) o;
        return idRecette == recette.idRecette;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRecette);
    }

    public int getRatio() {
        return (int) ((double) iterations/(double)nbIngredients);
    }
}
