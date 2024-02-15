package edu.esprit.services;

import edu.esprit.entities.Actualite;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ServiceActualite implements IService<Actualite> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Actualite actualite) {
        // Basic input validation to ensure all required fields are filled
        if (isValidActualite(actualite)) {
            String req = "INSERT INTO `actualite`(`titre_a`, `description_a`, `date_a`, `image_a`, `id_muni`) VALUES (?,?,?,?,?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, actualite.getTitre_a());
                ps.setString(2, actualite.getDescription_a());
                ps.setDate(3, actualite.getDate_a());
                ps.setString(4, actualite.getImage_a());
                ps.setInt(5, actualite.getId_muni());
                ps.executeUpdate();
                System.out.println("Actualite added!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("remplir tous les champs");
        }
    }

    // Check if the Actualite object has all required fields filled
    private boolean isValidActualite(Actualite actualite) {
        return actualite != null &&
                actualite.getTitre_a() != null && !actualite.getTitre_a().isEmpty() &&
                actualite.getDescription_a() != null && !actualite.getDescription_a().isEmpty() &&
                actualite.getDate_a() != null && // You may want to adjust this based on your requirements
                actualite.getImage_a() != null && !actualite.getImage_a().isEmpty() &&
                actualite.getId_muni() > 0; // You may want to adjust this based on your requirements
    }


    @Override
    public void modifier(Actualite actualite) {
        // Check if the specified ID exists before attempting to update
        if (actualiteExists(actualite.getId_a())) {
            // Check if the new id_a value exists in the actualite table
            if (actualiteExists(actualite.getId_a())) {
                String req = "UPDATE `actualite` SET `titre_a`=?, `description_a`=?, `date_a`=?, `image_a`=? , `id_muni`=? WHERE `id_a`=?";
                try {
                    PreparedStatement ps = cnx.prepareStatement(req);
                    ps.setString(1, actualite.getTitre_a());
                    ps.setString(2, actualite.getDescription_a());
                    ps.setDate(3, new java.sql.Date(actualite.getDate_a().getTime())); // Use new Date object
                    ps.setString(4, actualite.getImage_a());
                    ps.setInt(5, actualite.getId_muni());
                    ps.setInt(6, actualite.getId_a());
                    ps.executeUpdate();
                    System.out.println("Actualite with ID " + actualite.getId_a() + " modified!");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Actualite with ID " + actualite.getId_a() + " does not exist.");
            }
        } else {
            System.out.println("Actualite with ID " + actualite.getId_a() + " does not exist.");
        }
    }


    // Check if the specified actualite ID exists
    private boolean MuniExists(int id_muni) {
        String req = "SELECT COUNT(*) FROM `muni` WHERE `id_muni` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id_muni);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public void supprimer(int id) {
        if (actualiteExists(id)) {
            String req = "DELETE FROM `actualite` WHERE `id_a`=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("Actualite with ID " + id + " deleted!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Actualite with ID " + id + " does not exist.");
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

                Date date_a = rs.getDate("date_a");
                String image_a = rs.getString("image_a");
                int id_muni = rs.getInt("id_muni");

                Actualite act = new Actualite(id_a, titre_a, description_a, date_a, image_a, id_muni);
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
                int id_a = rs.getInt("id_a");
                String titre_a = rs.getString("titre_a");
                String description_a = rs.getString("description_a");

                Date date_a = rs.getDate("date_a");
                String image_a = rs.getString("image_a");
                int id_muni = rs.getInt("id_muni");
                return new Actualite(id_a, titre_a, description_a, date_a, image_a, id_muni);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
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

}
