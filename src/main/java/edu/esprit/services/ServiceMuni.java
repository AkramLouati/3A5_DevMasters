package edu.esprit.services;

import edu.esprit.entities.Muni;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;


public class ServiceMuni implements IService<Muni> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Muni muni) {

    }

    @Override
    public void modifier(Muni muni) {

    }

    @Override
    public void supprimer(int id) {
        if (id <= 0) {
            System.out.println("Invalid Muni ID. Please provide a positive integer.");
            return;
        }
        String req = "DELETE FROM `muni` WHERE `id_muni` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Muni with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No Muni found with ID " + id + ". Nothing deleted.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Set<Muni> getAll() {
        return null;
    }

    @Override
    public Muni getOneByID(int id) {
        String req = "SELECT * FROM `muni` WHERE `id_muni` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Muni(id); // Créer une instance Muni avec seulement l'ID
            } else {
                System.out.println("Muni with ID " + id + " not found.");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}