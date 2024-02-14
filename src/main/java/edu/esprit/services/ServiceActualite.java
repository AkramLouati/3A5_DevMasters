package edu.esprit.services;

import edu.esprit.entities.Actualite;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class ServiceActualite implements IService<Actualite>{
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Actualite actualite) {
        String req = "INSERT INTO `actualite`(`titre_a`, `description_a`, `date_a`, `image_a`) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, actualite.getTitre_a());
            ps.setString(2, actualite.getDescription_a());
            ps.setDate(3, actualite.getDate_a());
            ps.setString(4, actualite.getImage_a());
            ps.executeUpdate();
            System.out.println("actualite added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Actualite actualite) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Set<Actualite> getAll() {
        return null;
    }

    @Override
    public Actualite getOneByID(int id) {
        return null;
    }
}
