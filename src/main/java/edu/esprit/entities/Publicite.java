package edu.esprit.entities;

import java.util.Objects;

public class Publicite {
    int id_user;
    int id_a;
    private int id_pub;
    private String titre_pub;
    private String Description_pub;
    private int contact_pub;
    private String localisation_pub;
    private String image_pub;

    public Publicite() {
    }

    ;

    public Publicite(String titre_pub, String description_pub, int contact_pub, String localisation_pub, String image_pub, int id_user, int id_a) {
        this.titre_pub = titre_pub;
        this.Description_pub = description_pub;
        this.contact_pub = contact_pub;
        this.localisation_pub = localisation_pub;
        this.image_pub = image_pub;
        this.id_user = id_user;
        this.id_a = id_a;
    }

    public Publicite(int id_pub, String titre_pub, String description_pub, int contact_pub, String localisation_pub, String image_pub, int id_user, int id_a) {
        this.id_pub = id_pub;
        this.titre_pub = titre_pub;
        Description_pub = description_pub;
        this.contact_pub = contact_pub;
        this.localisation_pub = localisation_pub;
        this.image_pub = image_pub;
        this.id_user = id_user;
        this.id_a = id_a;
    }

    public int getId_pub() {
        return id_pub;
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

    public String getTitre_pub() {
        return titre_pub;
    }

    public void setTitre_pub(String titre_pub) {
        this.titre_pub = titre_pub;
    }

    public String getDescription_pub() {
        return Description_pub;
    }

    public void setDescription_pub(String description_pub) {
        Description_pub = description_pub;
    }

    public int getContact_pub() {
        return contact_pub;
    }

    public void setContact_pub(int contact_pub) {
        this.contact_pub = contact_pub;
    }

    public String getLocalisation_pub() {
        return localisation_pub;
    }

    public void setLocalisation_pub(String localisation_pub) {
        this.localisation_pub = localisation_pub;
    }

    public String getImage_pub() {
        return image_pub;
    }

    public void setImage_pub(String image_pub) {
        this.image_pub = image_pub;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_a() {
        return id_a;
    }

    public void setId_a(int id_a) {
        this.id_a = id_a;
    }

    @Override
    public String toString() {
        return "Publicite{" +
                "titre_pub='" + titre_pub + '\'' +
                ", Description_pub='" + Description_pub + '\'' +
                ", contact_pub=" + contact_pub +
                ", localisation_pub='" + localisation_pub + '\'' +
                ", image_pub='" + image_pub + '\'' +
                ", id_user=" + id_user +
                ", id_a=" + id_a +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publicite publicite = (Publicite) o;
        return id_pub == publicite.id_pub;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_pub);
    }
}