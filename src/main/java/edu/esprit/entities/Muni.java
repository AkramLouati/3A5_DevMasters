package edu.esprit.entities;

import java.util.Objects;

public class Muni {
    private int id_muni;
    private String nom_muni;
    private String email_muni;
    private String password_muni;
    private String image;

    public Muni(String nom_muni, String email_muni, String password_muni, String image) {
        this.nom_muni = nom_muni;
        this.email_muni = email_muni;
        this.password_muni = password_muni;
        this.image = image;
    }

    public Muni(int id_muni, String nom_muni, String email_muni, String password_muni, String id_user, String image) {
        this.id_muni = id_muni;
        this.nom_muni = nom_muni;
        this.email_muni = email_muni;
        this.password_muni = password_muni;
        this.image = image;
    }

    public String getNom_muni() {
        return nom_muni;
    }

    public void setNom_muni(String nom_muni) {
        this.nom_muni = nom_muni;
    }

    public String getEmail_muni() {
        return email_muni;
    }

    public void setEmail_muni(String email_muni) {
        this.email_muni = email_muni;
    }

    public String getPassword_muni() {
        return password_muni;
    }

    public void setPassword_muni(String password_muni) {
        this.password_muni = password_muni;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Muni{" +
                "nom_muni='" + nom_muni + '\'' +
                ", email_muni='" + email_muni + '\'' +
                ", password_muni='" + password_muni + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Muni muni = (Muni) o;
        return id_muni == muni.id_muni;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_muni);
    }
}
