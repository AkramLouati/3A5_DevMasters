package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Avis {
    private int id_avis;
    private int id_eq;
    private int note_avis;
    private String commentaire_avis;
    private Date date_avis;

    public Avis() {
    }

    public Avis(int id_eq, int note_avis, String commentaire_avis, Date date_avis) {
        this.id_avis = id_avis;
        this.id_eq = id_eq;
        this.note_avis = note_avis;
        this.commentaire_avis = commentaire_avis;
        this.date_avis = date_avis;
    }

    public Avis(int id_avis, int id_eq, int note_avis, String commentaire_avis, Date date_avis) {
        this.id_avis = id_avis;
        this.id_eq = id_eq;
        this.note_avis = note_avis;
        this.commentaire_avis = commentaire_avis;
        this.date_avis = date_avis;
    }

    public int getId_avis() {
        return id_avis;
    }

    public void setId_avis(int id_avis) {
        this.id_avis = id_avis;
    }

    public int getId_eq() {
        return id_eq;
    }

    public void setId_eq(int id_eq) {
        this.id_eq = id_eq;
    }

    public int getNote_avis() {
        return note_avis;
    }

    public void setNote_avis(int note_avis) {
        this.note_avis = note_avis;
    }

    public String getCommentaire_avis() {
        return commentaire_avis;
    }

    public void setCommentaire_avis(String commentaire_avis) {
        this.commentaire_avis = commentaire_avis;
    }

    public Date getDate_avis() {
        return date_avis;
    }

    public void setDate_avis(Date date_avis) {
        this.date_avis = date_avis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avis avis = (Avis) o;
        return id_avis == avis.id_avis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_avis);
    }

    @Override
    public String toString() {
        return "Avis{" +
                "id_avis=" + id_avis +
                ", id_eq=" + id_eq +
                ", note_avis=" + note_avis +
                ", commentaire_avis='" + commentaire_avis + '\'' +
                ", date_avis=" + date_avis +
                '}';

    }

}

