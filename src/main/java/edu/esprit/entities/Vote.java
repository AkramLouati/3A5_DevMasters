package edu.esprit.entities;

import java.util.Objects;

public class Vote {
    private int id;
    private int id_user;
    private String description;
    private String dateSoummission;

    public Vote(){

    }

    public Vote(int id, int id_user, String description, String dateSoummission) {
        this.id = id;
        this.id_user = id_user;
        this.description = description;
        this.dateSoummission = dateSoummission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateSoummission() {
        return dateSoummission;
    }

    public void setDateSoummission(String dateSoummission) {
        this.dateSoummission = dateSoummission;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "description='" + description + '\'' +
                ", dateSoummission='" + dateSoummission + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote vote)) return false;
        return getId() == vote.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
