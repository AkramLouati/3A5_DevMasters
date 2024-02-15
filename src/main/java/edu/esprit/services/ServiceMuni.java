package edu.esprit.services;

import edu.esprit.entities.Muni;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class ServiceMuni implements IService<Muni>{

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Muni muni) {
        String req = "INSERT INTO `muni`(`nom_muni`, `email_muni`, `password_muni`, `imagee_user`) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, muni.getNom_muni());
            ps.setString(2, muni.getEmail_muni());
            ps.setString(3, muni.getPassword_muni());
            ps.setString(4, muni.getImage());
            ps.executeUpdate();
            System.out.printf("Muni added !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier(Muni muni) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Set<Muni> getAll() {
        return null;
    }

    @Override
    public Muni getOneByID(int id) {
        return null;
    }
}
