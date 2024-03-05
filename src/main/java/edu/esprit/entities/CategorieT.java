package edu.esprit.entities;

import java.io.Serializable;
import java.util.Objects;

public class CategorieT implements Serializable {
    private int id_Cat;
    private String nom_Cat;

    public CategorieT(String nom_Cat) {
        this.nom_Cat = nom_Cat;
    }

    public CategorieT(int id_Cat) {
        this.id_Cat = id_Cat;
    }

    // Constructor
    public CategorieT(int id_Cat, String nom_Cat) {
        this.id_Cat = id_Cat;
        this.nom_Cat = nom_Cat;
    }

    public CategorieT() {

    }

    // Getters and Setters
    public int getId_Cat() {
        return id_Cat;
    }

    public void setId_Cat(int id_Cat) {
        this.id_Cat = id_Cat;
    }

    public String getNom_Cat() {
        return nom_Cat;
    }

    public void setNom_Cat(String nom_Cat) {
        this.nom_Cat = nom_Cat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategorieT that)) return false;
        return getId_Cat() == that.getId_Cat() && Objects.equals(getNom_Cat(), that.getNom_Cat());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId_Cat(), getNom_Cat());
    }

    // toString method for easy debugging

    @Override
    public String toString() {
        return nom_Cat;
    }
}
