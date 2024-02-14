package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class CommentaireTache {
    int id_C, id_user, id_T;
    String text_C;
    Date date_C, date_FT;

    public CommentaireTache() {
    }

    public CommentaireTache(int id_C, int id_user, int id_T, String text_C, Date date_C, Date date_FT) {
        this.id_C = id_C;
        this.id_user = id_user;
        this.id_T = id_T;
        this.text_C = text_C;
        this.date_C = date_C;
        this.date_FT = date_FT;
    }

    public int getId_C() {
        return id_C;
    }

    public void setId_C(int id_C) {
        this.id_C = id_C;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_T() {
        return id_T;
    }

    public void setId_T(int id_T) {
        this.id_T = id_T;
    }

    public String getText_C() {
        return text_C;
    }

    public void setText_C(String text_C) {
        this.text_C = text_C;
    }

    public Date getDate_C() {
        return date_C;
    }

    public void setDate_C(Date date_C) {
        this.date_C = date_C;
    }

    public Date getDate_FT() {
        return date_FT;
    }

    public void setDate_FT(Date date_FT) {
        this.date_FT = date_FT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentaireTache that)) return false;
        return getId_C() == that.getId_C();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_C());
    }

    @Override
    public String toString() {
        return "CommentaireTache{" +
                "text_C='" + text_C + '\'' +
                ", date_C=" + date_C +
                ", date_FT=" + date_FT +
                '}';
    }
}

