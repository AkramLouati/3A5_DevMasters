package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
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
        Set<Reclamation> reclamations = new HashSet<>();

        String req = "Select * from reclamation";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                int id_reclamation = rs.getInt(1);
                int id_muni = rs.getInt(2);
                int id_user = rs.getInt(3);
                Date date_reclamation = rs.getDate(4);
                String type_reclamation = rs.getString(5);
                String description_reclamation = rs.getString(6);
                String status_reclamation = rs.getString(7);
                String image_reclamation = rs.getString(8);
                String adresse_reclamation = rs.getString(9);
                Reclamation r = new Reclamation(id_reclamation,id_muni,id_user,date_reclamation,type_reclamation,description_reclamation,status_reclamation,image_reclamation,adresse_reclamation);
                reclamations.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamations;
    }

    @Override
    public Reclamation getOneByID(int id) {
        return null;
    }
}
