package edu.esprit.entities;

import java.util.Objects;

public class EndUser {
    private int id;
    private Muni muni;

    public EndUser() {
    }
    public EndUser(int id) {
        this.id = id;
    }
    public EndUser(int id, Muni muni) {
        this.id = id;
        this.muni = muni;
    }

    public EndUser(Muni muni) {
        this.muni = muni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Muni getMuni() {
        return muni;
    }

    public void setMuni(Muni muni) {
        this.muni = muni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndUser user = (EndUser) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "EndUser{" +
                "id=" + id +
                ", muni=" + muni +
                '}';
    }
}
