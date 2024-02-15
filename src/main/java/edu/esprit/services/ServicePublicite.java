package edu.esprit.services;

import edu.esprit.entities.Publicite;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServicePublicite implements IService<Publicite> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Publicite publicite) {
        // Basic input validation to ensure all required fields are filled
        if (isValidPublicite(publicite)) {
            String req = "INSERT INTO `publicite`(`titre_pub`, `description_pub`, `contact_pub`, `localisation_pub`, `image_pub`,`id_user`,`id_a`) VALUES (?,?,?,?,?,?,?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, publicite.getTitre_pub());
                ps.setString(2, publicite.getDescription_pub());
                ps.setInt(3, publicite.getContact_pub());
                ps.setString(4, publicite.getLocalisation_pub());
                ps.setString(5, publicite.getImage_pub());
                ps.setInt(6, publicite.getId_user());
                ps.setInt(7, publicite.getId_a());
                ps.executeUpdate();
                System.out.println("Publicite added!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("remplir tous les champs");
        }
    }

    // Check if the Publicite object has all required fields filled
    private boolean isValidPublicite(Publicite publicite) {
        return publicite != null &&
                publicite.getTitre_pub() != null && !publicite.getTitre_pub().isEmpty() &&
                publicite.getDescription_pub() != null && !publicite.getDescription_pub().isEmpty() &&
                publicite.getContact_pub() > 0 && // You may want to adjust this based on your requirements
                publicite.getLocalisation_pub() != null && !publicite.getLocalisation_pub().isEmpty() &&
                publicite.getImage_pub() != null && !publicite.getImage_pub().isEmpty() &&
                publicite.getId_user() > 0 && // You may want to adjust this based on your requirements
                publicite.getId_a() > 0; // You may want to adjust this based on your requirements
    }


    @Override
    public void modifier(Publicite publicite) {
        // Check if all the required fields are filled
        if (isValidPublicite(publicite)) {
            // Check if the specified Publicite ID exists before attempting to update
            if (publiciteExists(publicite.getId_pub())) {
                // Check if the associated Actualite ID exists in the actualite table
                if (actualiteExists(publicite.getId_a())) {
                    String req = "UPDATE `publicite` SET `titre_pub`=?, `description_pub`=?, `contact_pub`=?, `localisation_pub`=? , `image_pub`=?, `id_user`=?, `id_a`=? WHERE `id_pub`=?";
                    try {
                        PreparedStatement ps = cnx.prepareStatement(req);
                        ps.setString(1, publicite.getTitre_pub());
                        ps.setString(2, publicite.getDescription_pub());
                        ps.setInt(3, publicite.getContact_pub());
                        ps.setString(4, publicite.getLocalisation_pub());
                        ps.setString(5, publicite.getImage_pub());
                        ps.setInt(6, publicite.getId_user());
                        ps.setInt(7, publicite.getId_a());
                        ps.setInt(8, publicite.getId_pub());
                        ps.executeUpdate();
                        System.out.println("Publicite with ID " + publicite.getId_pub() + " modified!");
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Associated Actualite with ID " + publicite.getId_a() + " does not exist.");
                }
            } else {
                System.out.println("Publicite with ID " + publicite.getId_pub() + " does not exist.");
            }
        } else {
            System.out.println("Invalid input. Please fill all required fields.");
        }
    }


    // Check if the specified actualite ID exists
    private boolean actualiteExists(int id_a) {
        String req = "SELECT COUNT(*) FROM `actualite` WHERE `id_a` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id_a);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    @Override
    public Set<Publicite> getAll() {
        Set<Publicite> publicites = new HashSet<>();

        String req = "Select * from publicite";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_pub = rs.getInt("id_pub");
                String titre_pub = rs.getString("titre_pub");
                String description_pub = rs.getString("description_pub");
                int contact_pub = rs.getInt("contact_pub");
                String localisation_pub = rs.getString("localisation_pub");
                String image_pub = rs.getString("image_pub");
                int id_user = rs.getInt("id_user");
                int id_a = rs.getInt("id_a");
                Publicite pub = new Publicite(id_pub, titre_pub, description_pub, contact_pub, localisation_pub, image_pub, id_user, id_a);
                publicites.add(pub);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return publicites;

    }

    @Override
    public Publicite getOneByID(int id) {
        String req = "SELECT * FROM `publicite` WHERE `id_pub`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String titre_pub = rs.getString("titre_pub");
                String description_pub = rs.getString("description_pub");
                int contact_pub = rs.getInt("contact_pub");
                String localisation_pub = rs.getString("localisation_pub");
                String image_pub = rs.getString("image_pub");
                int id_user = rs.getInt("id_user");
                int id_a = rs.getInt("id_a");
                return new Publicite(id, titre_pub, description_pub, contact_pub, localisation_pub, image_pub, id_user, id_a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }


    private boolean publiciteExists(int id_pub) {
        String req = "SELECT COUNT(*) FROM `publicite` WHERE `id_pub`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id_pub);
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
        // Check if the specified ID exists before attempting to delete
        if (publiciteExists(id)) {
            String req = "DELETE FROM `publicite` WHERE `id_pub`=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("Publicite with ID " + id + " deleted!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Publicite with ID " + id + " does not exist.");
        }
    }

}
