package edu.esprit.services;

import edu.esprit.entities.Messagerie;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.Set;

public class ServiceMessagerie implements IService<Messagerie> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Messagerie messagerie) {
        String req = "INSERT INTO `messagerie`(`date_message`, `contenu_message`, `receiverId_message`, `senderId_message`, `type_message`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDate(1, messagerie.getDate_message());
            ps.setString(2, messagerie.getContenu_message());
            ps.setInt(3, messagerie.getReceiverId_message());
            ps.setInt(4, messagerie.getSenderId_message());
            ps.setString(5, messagerie.getType_message());
            ps.executeUpdate();
            System.out.println("Message ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du message : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Messagerie messagerie) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Set<Messagerie> getAll() {
        return null;
    }

    @Override
    public Messagerie getOneByID(int id) {
        return null;
    }
}
