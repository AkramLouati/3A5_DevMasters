package edu.esprit.services;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Messagerie;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceMessagerie implements IService<Messagerie> {
    Connection cnx = DataSource.getInstance().getCnx();
    ServiceUser serviceUser = new ServiceUser();
    ServiceMuni serviceMuni = new ServiceMuni();
    public boolean validateMessagerie(Messagerie messagerie) {
        return messagerie.getDate_message() != null &&
                !messagerie.getContenu_message().isEmpty() &&
                messagerie.getReceiver_message() != null &&
                messagerie.getSender_message() != null &&
                !messagerie.getType_message().isEmpty();
    }
    @Override
    public void ajouter(Messagerie messagerie) {
        if (!validateMessagerie(messagerie)) {
            System.out.println("Certains champs requis sont vides. Veuillez remplir tous les champs obligatoires.");
            return;
        }
        String req = "INSERT INTO `messagerie`(`date_message`, `contenu_message`, `receiverId_message`, `senderId_message`, `type_message`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDate(1, messagerie.getDate_message());
            ps.setString(2, messagerie.getContenu_message());
            ps.setInt(3, messagerie.getReceiver_message().getId());
            ps.setInt(4, messagerie.getSender_message().getId());
            ps.setString(5, messagerie.getType_message());
            ps.executeUpdate();
            System.out.println("Message ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du message : " + e.getMessage());
        }
    }
    private boolean messageExists(int id_message) {
        String req = "SELECT COUNT(*) FROM `messagerie` WHERE `id_message`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id_message);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'existence du message avec l'ID " + id_message + " : " + e.getMessage());
        }
        return false;
    }
    @Override
    public void modifier(Messagerie messagerie) {
        if (!validateMessagerie(messagerie)) {
            System.out.println("Certains champs requis sont vides. Veuillez remplir tous les champs obligatoires.");
            return;
        }
        if (messageExists(messagerie.getId_message())) {
            String req = "UPDATE `messagerie` SET `date_message`=?, `contenu_message`=?, `receiverId_message`=?, `senderId_message`=?, `type_message`=? WHERE `id_message`=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setDate(1, messagerie.getDate_message());
                ps.setString(2, messagerie.getContenu_message());
                ps.setInt(3, messagerie.getReceiver_message().getId());
                ps.setInt(4, messagerie.getSender_message().getId());
                ps.setString(5, messagerie.getType_message());
                ps.setInt(6, messagerie.getId_message());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Message avec l'ID " + messagerie.getId_message() + " modifié avec succès !");
                } else {
                    System.out.println("Échec de la modification du message avec l'ID " + messagerie.getId_message() + ". Message non trouvé.");
                }
            } catch (SQLException e) {
                System.out.println("Échec de la modification du message avec l'ID " + messagerie.getId_message() + ". Erreur : " + e.getMessage());
            }
        } else {
            System.out.println("Le message avec l'ID " + messagerie.getId_message() + " n'existe pas.");
        }
    }

    @Override
    public void supprimer(int id) {

        if (messageExists(id)) {
            String req = "DELETE FROM `messagerie` WHERE `id_message`=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, id);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Message avec l'ID " + id + " supprimé avec succès !");
                } else {
                    System.out.println("Échec de la suppression du message avec l'ID " + id + ". Aucune ligne affectée.");
                }
            } catch (SQLException e) {
                System.out.println("Échec de la suppression du message avec l'ID " + id + ". Erreur : " + e.getMessage());
            }
        } else {
            System.out.println("Le message avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public Set<Messagerie> getAll() {

        Set<Messagerie> messages = new HashSet<>();

        String req = "SELECT * FROM `messagerie`";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_message = rs.getInt("id_message");
                Date date_message = rs.getDate("date_message");
                String contenu_message = rs.getString("contenu_message");
                int receiverId_message = rs.getInt("receiverId_message");
                int senderId_message = rs.getInt("senderId_message");
                String type_message = rs.getString("type_message");

                EndUser receiver = serviceUser.getOneByID(receiverId_message);
                EndUser sender = serviceUser.getOneByID(senderId_message);

                Messagerie messagerie = new Messagerie(id_message, date_message, contenu_message, receiver, sender, type_message);
                messages.add(messagerie);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des messages : " + e.getMessage());
        }

        return messages;
    }

    @Override
    public Messagerie getOneByID(int id) {
        Messagerie messagerie = null;
        String req = "SELECT * FROM `messagerie` WHERE `id_message`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Date date_message = rs.getDate("date_message");
                String contenu_message = rs.getString("contenu_message");
                int receiverId_message = rs.getInt("receiverId_message");
                int senderId_message = rs.getInt("senderId_message");
                String type_message = rs.getString("type_message");

                EndUser receiver = serviceUser.getOneByID(receiverId_message);
                EndUser sender = serviceUser.getOneByID(senderId_message);

                messagerie = new Messagerie(id, date_message, contenu_message, receiver, sender, type_message);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du message avec l'ID " + id + " : " + e.getMessage());
        }
        return messagerie;
    }



}
