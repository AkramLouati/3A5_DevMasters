package edu.esprit.entities;

import edu.esprit.services.EtatTache;

import java.util.Date;
import java.util.Objects;

public class Tache {
    int id_T, id_user;
    String categorie_T, titre_T, pieceJointe_T, desc_T;
    Date date_DT, date_FT;
    EtatTache etat_T;

    public Tache() {
    }

    public Tache(int id_T, String categorie_T, String titre_T, String pieceJointe_T, Date date_DT, Date date_FT, String desc_T, EtatTache etat_T, int id_user) {
        this.id_T = id_T;
        this.categorie_T = categorie_T;
        this.titre_T = titre_T;
        this.pieceJointe_T = pieceJointe_T;
        this.date_DT = date_DT;
        this.date_FT = date_FT;
        this.desc_T = desc_T;
        this.etat_T = etat_T;
        this.id_user = id_user;
    }

    public Tache(String categorie_T, String titre_T, String pieceJointe_T, Date date_DT, Date date_FT, String desc_T, EtatTache etat_T, int id_user) {
        this.categorie_T = categorie_T;
        this.titre_T = titre_T;
        this.pieceJointe_T = pieceJointe_T;
        this.date_DT = date_DT;
        this.date_FT = date_FT;
        this.desc_T = desc_T;
        this.etat_T = etat_T;
        this.id_user = id_user;
    }


    public int getId_T() {
        return id_T;
    }

    public void setId_T(int id_T) {
        this.id_T = id_T;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getCategorie_T() {
        return categorie_T;
    }

    public void setCategorie_T(String categorie_T) {
        this.categorie_T = categorie_T;
    }

    public String getTitre_T() {
        return titre_T;
    }

    public void setTitre_T(String titre_T) {
        this.titre_T = titre_T;
    }

    public String getPieceJointe_T() {
        return pieceJointe_T;
    }

    public void setPieceJointe_T(String pieceJointe_T) {
        this.pieceJointe_T = pieceJointe_T;
    }

    public String getDesc_T() {
        return desc_T;
    }

    public void setDesc_T(String desc_T) {
        this.desc_T = desc_T;
    }

    public Date getDate_DT() {
        return date_DT;
    }

    public void setDate_DT(Date date_DT) {
        this.date_DT = date_DT;
    }

    public Date getDate_FT() {
        return date_FT;
    }

    public void setDate_FT(Date date_FT) {
        this.date_FT = date_FT;
    }

    public EtatTache getEtat_T() {
        return etat_T;
    }

    public void setEtat_T(EtatTache etat_T) {
        this.etat_T = etat_T;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tache tache)) return false;
        return getId_T() == tache.getId_T() && Objects.equals(getTitre_T(), tache.getTitre_T());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_T(), getTitre_T());
    }

    @Override
    public String toString() {
        return "Tache{" +
                "Categorie=" + categorie_T + "|" +
                "Titre=" + titre_T + "|" +
                "Piece Jointe=" + pieceJointe_T + "|" +
                "Description=" + desc_T + "|" +
                "Date Debut=" + date_DT + "|" +
                "Date Fin=" + date_FT + "|" +
                "Etat=" + etat_T + "|" +
                '}' +
                // Add this instead of the closing curly brace
                System.lineSeparator();
    }

}
