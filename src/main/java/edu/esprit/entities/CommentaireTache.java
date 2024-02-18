package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class CommentaireTache {
    private int id_C, id_T;
    private EndUser user;
    private Date date_C;
    private String text_C;


    public CommentaireTache() {
    }

    public CommentaireTache(int id_C, EndUser user, int id_T, Date date_C, String text_C) {
        this.id_C = id_C;
        this.user = user;
        this.id_T = id_T;
        this.date_C = date_C;
        this.text_C = text_C;
    }

    public CommentaireTache(EndUser user, int id_T, Date date_C, String text_C) {
        this.user = user;
        this.id_T = id_T;
        this.date_C = date_C;
        this.text_C = text_C;
    }

    public CommentaireTache(int id_C, String text_C) {
        this.id_C = id_C;
        this.text_C = text_C;
    }


    public int getId_C() {
        return id_C;
    }

    public void setId_C(int id_C) {
        this.id_C = id_C;
    }

    public EndUser getUser() {
        return user;
    }

    public void setUser(EndUser user) {
        this.user = user;
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
                "date_C=" + date_C + "|" +
                "text_C='" + text_C + "|" +
                '}' +
                System.lineSeparator();
    }
}

