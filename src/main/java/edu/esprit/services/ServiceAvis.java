package edu.esprit.services;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.Avis;
import edu.esprit.entities.Muni;
import edu.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ServiceAvis implements IService<Avis> {


    Connection cnx = DataSource.getInstance().getCnx();
    ServiceEquipement serviceEquipement = new ServiceEquipement();
    ServiceUser serviceEndUser = new ServiceUser();
    ServiceMuni serviceMuni = new ServiceMuni();


    @Override
    public void ajouter(Avis avis) {
        String req = "INSERT INTO avis (id_equipement, id_user, id_muni, note_avis, commentaire_avis, date_avis) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, avis.getEquipement().getId_equipement());
            ps.setInt(2, avis.getUser().getId());
            ps.setInt(3, avis.getMuni().getId());
            ps.setInt(4, avis.getNote_avis());
            ps.setString(5, avis.getCommentaire_avis());
            ps.setDate(6, new java.sql.Date(avis.getDate_avis().getTime()));
            ps.executeUpdate();
            System.out.println("Avis ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'avis : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Avis avis) {
        String req = "UPDATE avis SET id_user=?, id_muni=?, id_eq=?, note_avis=?, commentaire_avis=?, date_avis=? WHERE id_avis=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, avis.getUser().getId());
            ps.setInt(2, avis.getMuni().getId());
            ps.setInt(3, avis.getEquipement().getId_equipement());
            ps.setInt(4, avis.getNote_avis());
            ps.setString(5, avis.getCommentaire_avis());
            ps.setDate(6, new java.sql.Date(avis.getDate_avis().getTime()));
            ps.setInt(7, avis.getId_avis());
            ps.executeUpdate();
            System.out.println("Avis modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'avis : " + e.getMessage());
        }
    }


    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM avis WHERE id_avis=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Avis supprimé avec succès !");
            } else {
                System.out.println("Aucun avis trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'avis : " + e.getMessage());
        }
    }


    @Override
    public Set<Avis> getAll() {
        Set<Avis> avisList = new HashSet<>();
        String req = "SELECT * FROM avis";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_avis = rs.getInt("id_avis");
                int id_user = rs.getInt("id_user");
                int id_muni = rs.getInt("id_muni");
                int id_eq = rs.getInt("id_eq");
                int note_avis = rs.getInt("note_avis");
                String commentaire_avis = rs.getString("commentaire_avis");
                java.util.Date date_avis = rs.getDate("date_avis");

                // Récupération de l'utilisateur associé à l'avis
                EndUser user = serviceEndUser.getOneByID(id_user);
                Muni muni = serviceMuni.getOneByID(id_muni);

                // Récupération de l'équipement associé à l'avis
                Equipement equipement = serviceEquipement.getOneByID(id_eq);

                // Création d'un nouvel objet Avis
                Avis avis = new Avis(id_avis, user, equipement, muni, note_avis, commentaire_avis, date_avis);
                avisList.add(avis);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des avis : " + e.getMessage());
        }
        return avisList;
    }

    @Override
    public Avis getOneByID(int id) {
        Avis avis = null;
        String req = "SELECT * FROM avis WHERE id_avis=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_avis = rs.getInt("id_avis");
                int id_user = rs.getInt("id_user");
                int id_muni = rs.getInt("id_muni");
                int id_eq = rs.getInt("id_eq");
                int note_avis = rs.getInt("note_avis");
                String commentaire_avis = rs.getString("commentaire_avis");
                java.util.Date date_avis = rs.getDate("date_avis");

                // Récupération de l'utilisateur associé à l'avis
                EndUser user = serviceEndUser.getOneByID(id_user);
                Muni muni = serviceMuni.getOneByID(id_muni);

                // Récupération de l'équipement associé à l'avis
                Equipement equipement = serviceEquipement.getOneByID(id_eq);

                // Création d'un nouvel objet Avis
                avis = new Avis(id_avis, user, equipement, muni,note_avis, commentaire_avis, date_avis);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'avis : " + e.getMessage());
        }
        return avis;
    }
}

