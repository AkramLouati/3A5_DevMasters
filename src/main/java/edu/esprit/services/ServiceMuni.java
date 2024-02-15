package edu.esprit.services;

import edu.esprit.entities.Muni;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class ServiceMuni implements IService<Muni>{

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Muni muni) {
        String req = "INSERT INTO `muni`(`nom_muni`, `email_muni`, `password_muni`, `imagee_user`) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, muni.getNom_muni());
            ps.setString(2, muni.getEmail_muni());
            if (!isValidEmail(muni.getEmail_muni())) {
                throw new IllegalArgumentException("Invalid email address");
            }
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
        String req = "UPDATE `muni` SET `nom_muni`=?, `email_muni`=?, `password_muni`=?, `imagee_user`=? WHERE `id_muni`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, muni.getNom_muni());
            ps.setString(2, muni.getEmail_muni());
            if (!isValidEmail(muni.getEmail_muni())) {
                throw new IllegalArgumentException("Invalid email address");
            }
            ps.setString(3, muni.getPassword_muni());
            ps.setString(4, muni.getImage());
            ps.setInt(5, muni.getId_muni()); // Assuming `id_muni` is the primary key
            ps.executeUpdate();
            System.out.println("Muni updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        Set<Muni> munis = new HashSet<>();
        String req = "Select * from muni";

        Statement st = null;
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                int id_muni = rs.getInt("id_muni");
                String nom_muni = rs.getString("nom_muni");
                String email_muni = rs.getString("email_muni");
                String password_muni = rs.getString("password_muni");
                String image = rs.getString("imagee_user");
                Muni p = new Muni(id_muni,nom_muni,email_muni,password_muni,image);
                munis.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return munis;
    }

    @Override
    public Muni getOneByID(int id) {
        String req = "SELECT * FROM `muni` WHERE `id_muni` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_muni = rs.getInt("id_muni");
                String nom_muni = rs.getString("nom_muni");
                String email_muni = rs.getString("email_muni");
                String password_muni = rs.getString("password_muni");
                String image = rs.getString("imagee_user");
                return new Muni(id_muni, nom_muni, email_muni, password_muni, image);
            } else {
                // Handle case where no Muni with the given ID exists
                System.out.println("Muni with ID " + id + " not found.");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean isValidEmail(String email) {
        String regexPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }
}
