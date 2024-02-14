package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            ps.setString(5,reclamation.getDescription_reclamation());
            ps.setString(6,reclamation.getStatus_reclamation());
            ps.setString(7,reclamation.getImage_reclamation());
            ps.setString(8,reclamation.getAdresse_reclamation());
            ps.executeUpdate();
            System.out.println("reclamation added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(Reclamation reclamation) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Set<Reclamation> getAll() {
        return null;
    }

    @Override
    public Reclamation getOneByID(int id) {
        return null;
    }
}
