package edu.esprit.services;

import edu.esprit.entities.Actualite;
import edu.esprit.entities.Publicite;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceActualite implements IService<Actualite>{
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Actualite actualite) {
        String req = "INSERT INTO `actualite`(`titre_a`, `description_a`, `date_a`, `image_a`) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, actualite.getTitre_a());
            ps.setString(2, actualite.getDescription_a());
            ps.setDate(3, actualite.getDate_a());
            ps.setString(4, actualite.getImage_a());
            ps.executeUpdate();
            System.out.println("actualite added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Actualite actualite) {
        if (actualiteExists(actualite.getId_a())) {
            String req = "UPDATE `actualite` SET `titre_a`=?, `description_a`=?, `image_a`=?,`date_a`=?, `id_muni`=? , `id_a`=?WHERE `id_pub`=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, actualite.getTitre_a());
                ps.setString(2, actualite.getDescription_a());
                ps.setString(3, actualite.getImage_a());
                ps.setDate(4, actualite.getDate_a());
                ps.setInt(5, actualite.getId_muni());

                ps.executeUpdate();
                System.out.println("actualite with ID " + actualite.getId_a() + " modified!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("actualite with ID " + actualite.getId_a() + " does not exist.");
        }
    }
    private boolean actualiteExists(int id_a) {
        String req = "SELECT COUNT(*) FROM `actualite` WHERE `id_a`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id_a);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Returns true if the ID exists
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false; // Default to false in case of an exception
    }
    @Override
    public void supprimer(int id) {
        if (actualiteExists(id)) {
            String req = "DELETE FROM `actualite` WHERE `id_a`=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("Publicite with ID " + id + " deleted!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("actualite with ID " + id + " does not exist.");
        }

    }

    @Override
    public Set<Actualite> getAll() {
        Set<Actualite> actualites = new HashSet<>();

        String req = "Select * from actualite";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_a = rs.getInt("id_a");
                String titre_a = rs.getString("titre_a");
                String description_a = rs.getString("description_a");
                String  image_a = rs.getString("image_a");
                Date date_a = rs.getDate("date_a");
                int id_muni = rs.getInt("id_muni");
                Actualite act = new Publicite(id_a, titre_a, description_a, image_a, date_a,id_muni);
                actualites.add(act);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return actualites;

    }

    @Override
    public Actualite getOneByID(int id) {
        String req = "SELECT * FROM `actualite` WHERE `id_a`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String titre_a = rs.getString("titre_a");
                String description_a = rs.getString("description_a");
                int contact_pub = rs.getInt("contact_pub");
                String localisation_pub = rs.getString("localisation_pub");
                String image_pub = rs.getString("image_pub");
                int id_user = rs.getInt("id_user");
                int id_a = rs.getInt("id_a");
                return new Publicite(id, titre_pub, description_pub, contact_pub, localisation_pub,image_pub,id_user,id_a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
}
