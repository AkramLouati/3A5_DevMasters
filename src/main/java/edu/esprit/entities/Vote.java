package edu.esprit.entities;

import edu.esprit.services.IService;

import java.util.Objects;
import java.util.Set;

public class Vote {
private int id_V;
private int id_user;
private String desc_E;
private String date_SV;

    public Vote() {
    }

    public Vote(int id_V, int id_user, String desc_E, String date_SV) {
        this.id_V = id_V;
        this.id_user = id_user;
        this.desc_E = desc_E;
        this.date_SV = date_SV;
    }

    public Vote(int id_user, String desc_E, String date_SV) {
        this.id_user = id_user;
        this.desc_E = desc_E;
        this.date_SV = date_SV;
    }

    public int getId_V() {
        return id_V;
    }

    public void setId_V(int id_V) {
        this.id_V = id_V;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getDesc_E() {
        return desc_E;
    }

    public void setDesc_E(String desc_E) {
        this.desc_E = desc_E;
    }

    public String getDate_SV() {
        return date_SV;
    }

    public void setDate_SV(String date_SV) {
        this.date_SV = date_SV;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id_V=" + id_V +
                ", id_user=" + id_user +
                ", desc_E='" + desc_E + '\'' +
                ", date_SV='" + date_SV + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote vote)) return false;
        return getId_V() == vote.getId_V();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_V());
    }
}
