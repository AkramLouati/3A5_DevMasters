package edu.esprit.services;

import edu.esprit.entities.EndUser;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class ServiceUser implements IService<EndUser> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(EndUser endUser) {
        String req = "INSERT INTO `enduser`(`email_user`, `nom_user`, `type_user`, `phoneNumber_user`, `id_muni`, `location_user`, `image_user`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, endUser.getEmail());
            ps.setString(2, endUser.getNom());
            ps.setString(3, endUser.getType());
            ps.setString(4, endUser.getPhoneNumber());
            ps.setString(5, endUser.getId_muni());
            ps.setString(6, endUser.getLocation());
            ps.setString(7, endUser.getImage());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void modifier(EndUser endUser) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Set<EndUser> getAll() {
        return null;
    }

    @Override
    public EndUser getOneByID(int id) {
        return null;
    }
}
