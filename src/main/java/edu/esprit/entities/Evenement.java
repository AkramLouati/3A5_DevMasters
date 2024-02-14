package edu.esprit.entities;

import java.util.Objects;

public class Evenement {
    private int id;
    private String nomEvent;
    private int id_user;
    private String dateEtHeureDeb;
    private String dateEtHeureFin;
    private int capaciteMax;
    private String categorie;

    public Evenement(){

    }

    public Evenement(int id, String nomEvent, int id_user, String dateEtHeureDeb, String dateEtHeureFin, int capaciteMax, String categorie) {
        this.id = id;
        this.nomEvent = nomEvent;
        this.id_user = id_user;
        this.dateEtHeureDeb = dateEtHeureDeb;
        this.dateEtHeureFin = dateEtHeureFin;
        this.capaciteMax = capaciteMax;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getDateEtHeureDeb() {
        return dateEtHeureDeb;
    }

    public void setDateEtHeureDeb(String dateEtHeureDeb) {
        this.dateEtHeureDeb = dateEtHeureDeb;
    }

    public String getDateEtHeureFin() {
        return dateEtHeureFin;
    }

    public void setDateEtHeureFin(String dateEtHeureFin) {
        this.dateEtHeureFin = dateEtHeureFin;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                ", nomEvent='" + nomEvent + '\'' +
                ", dateEtHeureDeb='" + dateEtHeureDeb + '\'' +
                ", dateEtHeureFin='" + dateEtHeureFin + '\'' +
                ", capaciteMax=" + capaciteMax +
                ", categorie='" + categorie + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evenement evenement)) return false;
        return getId() == evenement.getId() && Objects.equals(getNomEvent(), evenement.getNomEvent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNomEvent());
    }
}
