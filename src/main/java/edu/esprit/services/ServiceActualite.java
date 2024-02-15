package edu.esprit.services;

import edu.esprit.entities.Actualite;
import edu.esprit.entities.Publicite;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ServiceActualite implements IService<Actualite>{
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Actualite actualite) {
        String req = "INSERT INTO `actualite`(`titre_a`, `description_a`, `date_a`, `image_a`, `id_muni`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, actualite.getTitre_a());
            ps.setString(2, actualite.getDescription_a());
            ps.setDate(3, actualite.getDate_a());
            ps.setString(4, actualite.getImage_a());
            ps.setInt(5, actualite.getId_muni());
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
        if (actualiteExists(id)) {
            String req = "DELETE FROM `actualite` WHERE `id_a`=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("Actualite with ID " + id + " deleted!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Actualite with ID " + id + " does not exist.");
        }
    }
    }

    @Override
    public Set<Actualite> getAll() {
        Set<Actualite> actualites = new HashSet<>();

        String req = "Select * from actualite";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_a = rs.getInt("id_a");
                String titre_a = rs.getString("titre_a");
                String description_a = rs.getString("description_a");

                Date date_a = rs.getDate("date_a");
                String image_a = rs.getString("image_a");
                int id_muni = rs.getInt("id_muni");

                Actualite act = new Actualite(id_a, titre_a, description_a, date_a, image_a, id_muni);
                actualites.add(act);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return actualites;
    }

    @Override
    public Actualite getOneByID(int id) {
        return null;
    }


}
