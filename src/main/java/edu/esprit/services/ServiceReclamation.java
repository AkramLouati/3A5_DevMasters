package edu.esprit.services;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import edu.esprit.entities.Reclamation;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ServiceReclamation implements IService<Reclamation> {

    Connection cnx = DataSource.getInstance().getCnx();
    ServiceUser serviceUser = new ServiceUser();
    ServiceMuni serviceMuni = new ServiceMuni();

    public boolean validateReclamation(Reclamation reclamation) {
        return reclamation.getUser() != null &&
                reclamation.getMuni() != null &&
                !reclamation.getSujet_reclamation().isEmpty() &&
                reclamation.getDate_reclamation() != null &&
                !reclamation.getType_reclamation().isEmpty() &&
                !reclamation.getDescription_reclamation().isEmpty() &&
                !reclamation.getAdresse_reclamation().isEmpty();
    }
    @Override
    public void ajouter(Reclamation reclamation) throws SQLException {
        if (!validateReclamation(reclamation)) {
            throw new SQLException("Certains champs requis sont vides. Veuillez remplir tous les champs obligatoires.");
        }
        String req = "INSERT INTO `reclamation`(`id_user`, `id_muni`, `sujet_reclamation`,`date_reclamation`, `type_reclamation`, `description_reclamation`, `status_reclamation`, `image_reclamation`, `adresse_reclamation`) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, reclamation.getUser().getId());
            ps.setInt(2, reclamation.getMuni().getId());
            ps.setString(3, reclamation.getSujet_reclamation());
            ps.setDate(4, (java.sql.Date) reclamation.getDate_reclamation());
            ps.setString(5, reclamation.getType_reclamation());
            ps.setString(6, reclamation.getDescription_reclamation());
            ps.setString(7, "non traité");
            ps.setString(8, reclamation.getImage_reclamation());
            ps.setString(9, reclamation.getAdresse_reclamation());
            ps.executeUpdate();
            System.out.println("Réclamation ajoutée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la réclamation : " + e.getMessage());
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
                return count > 0; // Retourne true si l'ID existe
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false; // Par défaut, retourne false en cas d'exception
    }

    @Override
    public void modifier(Reclamation reclamation) {
        if (!validateReclamation(reclamation)) {
            System.out.println("Certains champs requis sont vides. Veuillez remplir tous les champs obligatoires.");
            return;
        }
        if (reclamationExists(reclamation.getId_reclamation())) {
            String req = "UPDATE `reclamation` SET `id_user`=?, `id_muni`=?, `sujet_reclamation`=?,`date_reclamation`=?, `type_reclamation`=?, `description_reclamation`=?, `status_reclamation`=?,`image_reclamation`=?, `adresse_reclamation`=? WHERE `id_reclamation`=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, reclamation.getUser().getId());
                ps.setInt(2, reclamation.getMuni().getId());
                ps.setString(3, reclamation.getSujet_reclamation());
                ps.setDate(4, (java.sql.Date) reclamation.getDate_reclamation());
                ps.setString(5, reclamation.getType_reclamation());
                ps.setString(6, reclamation.getDescription_reclamation());
                ps.setString(7, "non traité");
                ps.setString(8, reclamation.getImage_reclamation());
                ps.setString(9, reclamation.getAdresse_reclamation());
                ps.setInt(10, reclamation.getId_reclamation());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Réclamation avec ID " + reclamation.getId_reclamation() + " modifiée avec succès !");
                } else {
                    System.out.println("Échec de la modification de la réclamation avec ID " + reclamation.getId_reclamation() + ". Réclamation non trouvée.");
                }
            } catch (SQLException e) {
                System.out.println("Échec de la modification de la réclamation avec ID " + reclamation.getId_reclamation() + ". Erreur : " + e.getMessage());
            }
        } else {
            System.out.println("Réclamation avec ID " + reclamation.getId_reclamation() + " n'existe pas.");
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
                    System.out.println("Réclamation avec ID " + id + " supprimée avec succès !");
                } else {
                    System.out.println("Échec de la suppression de la réclamation avec ID " + id + ". Aucune ligne affectée.");
                }
            } catch (SQLException e) {
                System.out.println("Échec de la suppression de la réclamation avec ID " + id + ". Erreur : " + e.getMessage());
            }
        } else {
            System.out.println("Réclamation avec ID " + id + " n'existe pas.");
        }
    }

    @Override
    public Set<Reclamation> getAll() {
        Set<Reclamation> reclamations = new HashSet<>();

        String req = "SELECT * FROM `reclamation`";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_reclamation = rs.getInt("id_reclamation");
                int id_muni = rs.getInt("id_muni");
                int id_user = rs.getInt("id_user");
                String sujet_reclamation = rs.getString("sujet_reclamation");
                Date date_reclamation = rs.getDate("date_reclamation");
                String type_reclamation = rs.getString("type_reclamation");
                String description_reclamation = rs.getString("description_reclamation");
                String status_reclamation = rs.getString("status_reclamation");
                String image_reclamation = rs.getString("image_reclamation");
                String adresse_reclamation = rs.getString("adresse_reclamation");

                EndUser user = serviceUser.getOneByID(id_user);
                Muni muni = serviceMuni.getOneByID(id_muni);

                Reclamation r = new Reclamation(id_reclamation, user, muni, sujet_reclamation,date_reclamation, type_reclamation, description_reclamation, status_reclamation, image_reclamation, adresse_reclamation);
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
                String sujet_reclamation = rs.getString("sujet_reclamation");
                Date date_reclamation = rs.getDate("date_reclamation");
                String type_reclamation = rs.getString("type_reclamation");
                String description_reclamation = rs.getString("description_reclamation");
                String status_reclamation = rs.getString("status_reclamation");
                String image_reclamation = rs.getString("image_reclamation");
                String adresse_reclamation = rs.getString("adresse_reclamation");

                EndUser user = serviceUser.getOneByID(id_user);
                Muni muni = serviceMuni.getOneByID(id_muni);

                reclamation = new Reclamation(id_reclamation, user, muni, sujet_reclamation,date_reclamation, type_reclamation, description_reclamation, status_reclamation, image_reclamation, adresse_reclamation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamation;
    }
    public Set<Reclamation> getReclamationsByUser(EndUser user) {
        Set<Reclamation> userReclamations = new HashSet<>();
        String req = "SELECT * FROM `reclamation` WHERE `id_user`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_reclamation = rs.getInt("id_reclamation");
                int id_muni = rs.getInt("id_muni");
                String sujet_reclamation = rs.getString("sujet_reclamation");
                Date date_reclamation = rs.getDate("date_reclamation");
                String type_reclamation = rs.getString("type_reclamation");
                String description_reclamation = rs.getString("description_reclamation");
                String status_reclamation = rs.getString("status_reclamation");
                String image_reclamation = rs.getString("image_reclamation");
                String adresse_reclamation = rs.getString("adresse_reclamation");

                Muni muni = serviceMuni.getOneByID(id_muni);

                Reclamation r = new Reclamation(id_reclamation, user, muni, sujet_reclamation, date_reclamation, type_reclamation, description_reclamation, status_reclamation, image_reclamation, adresse_reclamation);
                userReclamations.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userReclamations;
    }



}
