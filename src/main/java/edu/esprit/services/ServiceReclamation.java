package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ServiceReclamation implements IService<Reclamation> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Reclamation reclamation) {
        String req = "INSERT INTO `reclamation`(`id_user`, `id_muni`, `date_reclamation`, `type_reclamation`, `description_reclamation`, `status_reclamation`, `image_reclamation`, `adresse_reclamation`) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, reclamation.getId_user());
            ps.setInt(2, reclamation.getId_muni());
            ps.setDate(3, reclamation.getDate_reclamation());
            ps.setString(4, reclamation.getType_reclamation());
            ps.setString(5, reclamation.getDescription_reclamation());
            ps.setString(6, "non traité");
            ps.setString(7, reclamation.getImage_reclamation());
            ps.setString(8, reclamation.getAdresse_reclamation());
            ps.executeUpdate();
            System.out.println("Reclamation ajoutee !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de lajout de la reclamation : " + e.getMessage());
        }
    }
    private boolean reclamationExists(int id_reclamation) {
        String req = "SELECT COUNT(*) FROM `reclamation` WHERE `id_reclamation`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id_reclamation);
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
    public void modifier(Reclamation reclamation) {
        if (reclamationExists(reclamation.getId_reclamation())) {
            String req = "UPDATE `reclamation` SET `id_user`=?, `id_muni`=?, `date_reclamation`=?, `type_reclamation`=?, `description_reclamation`=?, `status_reclamation`=?, `image_reclamation`=?, `adresse_reclamation`=? WHERE `id_reclamation`=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, reclamation.getId_user());
                ps.setInt(2, reclamation.getId_muni());
                ps.setDate(3, reclamation.getDate_reclamation());
                ps.setString(4, reclamation.getType_reclamation());
                ps.setString(5, reclamation.getDescription_reclamation());
                ps.setString(6, reclamation.getStatus_reclamation());
                ps.setString(7, reclamation.getImage_reclamation());
                ps.setString(8, reclamation.getAdresse_reclamation());
                ps.setInt(9, reclamation.getId_reclamation());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Reclamation with ID " + reclamation.getId_reclamation() + " modified successfully!");
                } else {
                    System.out.println("Failed to modify reclamation with ID " + reclamation.getId_reclamation() + ". Reclamation not found.");
                }
            } catch (SQLException e) {
                System.out.println("Failed to modify reclamation with ID " + reclamation.getId_reclamation() + ". Error: " + e.getMessage());
            }
        } else {
            System.out.println("Reclamation with ID " + reclamation.getId_reclamation() + " does not exist.");
        }
    }


    @Override
    public void supprimer(int id) {
        if (reclamationExists(id)) {
            String req = "DELETE FROM `reclamation` WHERE `id_reclamation`=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, id);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Reclamation with ID " + id + " deleted successfully!");
                } else {
                    System.out.println("Failed to delete reclamation with ID " + id + ". No rows affected.");
                }
            } catch (SQLException e) {
                System.out.println("Failed to delete reclamation with ID " + id + ". Error: " + e.getMessage());
            }
        } else {
            System.out.println("Reclamation with ID " + id + " does not exist.");
        }
    }

    @Override
    public Set<Reclamation> getAll() {
        Set<Reclamation> reclamations = new HashSet<>();

        String req = "Select * from reclamation";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                int id_reclamation = rs.getInt(1);
                int id_muni = rs.getInt(2);
                int id_user = rs.getInt(3);
                Date date_reclamation = rs.getDate(4);
                String type_reclamation = rs.getString(5);
                String description_reclamation = rs.getString(6);
                String status_reclamation = rs.getString(7);
                String image_reclamation = rs.getString(8);
                String adresse_reclamation = rs.getString(9);
                Reclamation r = new Reclamation(id_reclamation,id_muni,id_user,date_reclamation,type_reclamation,description_reclamation,status_reclamation,image_reclamation,adresse_reclamation);
                reclamations.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamations;
    }

    @Override
    public Reclamation getOneByID(int id) {
        Reclamation reclamation = null;
        String req = "SELECT * FROM `reclamation` WHERE `id_reclamation`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_reclamation = rs.getInt("id_reclamation");
                int id_muni = rs.getInt("id_muni");
                int id_user = rs.getInt("id_user");
                Date date_reclamation = rs.getDate("date_reclamation");
                String type_reclamation = rs.getString("type_reclamation");
                String description_reclamation = rs.getString("description_reclamation");
                String status_reclamation = rs.getString("status_reclamation");
                String image_reclamation = rs.getString("image_reclamation");
                String adresse_reclamation = rs.getString("adresse_reclamation");
                reclamation = new Reclamation(id_reclamation, id_muni, id_user, date_reclamation, type_reclamation, description_reclamation, status_reclamation, image_reclamation, adresse_reclamation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamation;
    }
}
