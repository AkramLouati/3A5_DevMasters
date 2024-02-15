package edu.esprit.entities;

import java.util.Objects;

public class EndUser {
    private int id;
    private String nom;
    private String email;
    private String password;
    private String type;
    private String phoneNumber;
    private String id_muni;
    private String location;
    private String image;

    public EndUser() {
    }

    public EndUser(String email, String nom, String password, String type, String phoneNumber, String id_muni, String location, String image) {
        this.email = email;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.id_muni = id_muni;
        this.location = location;
        this.image = image;
    }

    public EndUser(int id, String email, String nom, String password, String type, String phoneNumber, String id_muni, String location, String image) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.id_muni = id_muni;
        this.location = location;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId_muni() {
        return id_muni;
    }

    public void setId_muni(String id_muni) {
        this.id_muni = id_muni;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "EndUser{" +
                "nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", id_muni='" + id_muni + '\'' +
                ", location='" + location + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndUser endUser = (EndUser) o;
        return id == endUser.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
