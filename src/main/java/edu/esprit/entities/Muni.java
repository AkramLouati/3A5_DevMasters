package edu.esprit.entities;

import java.util.Objects;

public class Muni {
    private int id;
    private String nom_muni;
    private String email_muni;
    private String password_muni;
    private String id_user;
    private String image;

    public Muni(String nom_muni) {
        this.nom_muni = nom_muni;
    }

    public Muni(String nom_muni, String email_muni, String password_muni, String id_user, String image) {
        this.nom_muni = nom_muni;
        this.email_muni = email_muni;
        this.password_muni = password_muni;
        this.id_user = id_user;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Muni{" +
                "nom_muni='" + nom_muni + '\'' +
                ", email_muni='" + email_muni + '\'' +
                ", password_muni='" + password_muni + '\'' +
                ", id_user='" + id_user + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Muni muni = (Muni) o;
        return id == muni.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
