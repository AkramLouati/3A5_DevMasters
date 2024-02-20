package edu.esprit.entities;

import java.util.Objects;

public class Muni {
    private int id;

    public Muni() {
    }

    public Muni(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Muni{" +
                "id=" + id +
                '}';
    }
}