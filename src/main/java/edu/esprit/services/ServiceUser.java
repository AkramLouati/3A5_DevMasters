package edu.esprit.services;

import edu.esprit.entities.EndUser;
import edu.esprit.utils.DataSource;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceUser implements IService<EndUser> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(EndUser endUser) {
        String req = "INSERT INTO `enduser`(`email_user`, `nom_user`, `type_user`, `phoneNumber_user`, `id_muni`, `location_user`, `image_user`,`password`) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, endUser.getEmail());
            ps.setString(2, endUser.getNom());
            ps.setString(3, endUser.getType());
            ps.setString(4, endUser.getPhoneNumber());
            ps.setString(5, endUser.getId_muni());
            ps.setString(6, endUser.getLocation());
            ps.setString(7, endUser.getImage());
            ps.setString(8, endUser.getPassword());
            ps.executeUpdate();
            System.out.printf("User added!!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void modifier(EndUser endUser) {
        String req = "UPDATE `enduser` SET `nom_user`=?, `email_user`=?, `type_user`=?, `phoneNumber_user`=?, `id_muni`=?, `location_user`=?, `image_user`=?, `password`=? WHERE `id_user`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, endUser.getNom());
            ps.setString(2, endUser.getEmail());
            ps.setString(3, endUser.getType());
            ps.setString(4, endUser.getPhoneNumber());
            ps.setString(5, endUser.getId_muni());
            ps.setString(6, endUser.getLocation());
            ps.setString(7, endUser.getImage());
            ps.setString(8, endUser.getPassword());
            ps.setInt(9, endUser.getId()); // Assuming `id_user` is the primary key
            ps.executeUpdate();
            System.out.println("User updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void supprimer(int id) {
        String deleteQuery = "DELETE FROM `enduser` WHERE `id_user` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(deleteQuery);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("User not found or already deleted.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Set<EndUser> getAll() {
        Set<EndUser> users = new HashSet<>();
        String req = "Select * from endUser";

        Statement st = null;
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                int id = rs.getInt("id_user");
                String nom = rs.getString("nom_user");
                String email = rs.getString("email_user");
                String password = rs.getString("password");
                String type = rs.getString("type_user");
                String phoneNumber = rs.getString("phoneNumber_user");
                String id_muni = rs.getString("id_muni");
                String location = rs.getString("location_user");
                String image = rs.getString("image_user");
                EndUser p = new EndUser(id,email,nom,password,type,phoneNumber,id_muni,location,image);
                users.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public EndUser getOneByID(int id) {
        return null;
    }
}
