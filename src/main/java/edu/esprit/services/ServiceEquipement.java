package edu.esprit.services;


import edu.esprit.entities.EndUser;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.Muni;
import edu.esprit.entities.EndUser;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceEquipement implements IService<Equipement> {

    Connection cnx = DataSource.getInstance().getCnx();
    ServiceUser serviceUser = new ServiceUser();
    ServiceMuni serviceMuni = new ServiceMuni();

    @Override
    public void ajouter(Equipement equipement) {
        String req = "INSERT INTO equipement (reference_eq, nom_eq, categorie_eq, date_ajouteq, quantite_eq, image_eq, description_eq, id_user,id_muni) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, equipement.getReference_eq());
            ps.setString(2, equipement.getNom_eq());
            ps.setString(3, equipement.getCategorie_eq());
            ps.setString(4, String.valueOf(equipement.getDate_ajouteq()));
            ps.setInt(5, equipement.getQuantite_eq());
            ps.setString(6, equipement.getImage_eq());
            ps.setString(7, equipement.getDescription_eq());
            ps.setInt(8, equipement.getUser().getId());
            ps.setInt(9, equipement.getMuni().getId());

            ps.executeUpdate();
            System.out.println("Equipement ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'équipement : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Equipement equipement) {
        String req = "UPDATE equipement SET `reference_eq`=?, `nom_eq`=?, `categorie_eq`=?, `date_ajouteq`=?, `quantite_eq`=?, `image_eq`=?, `description_eq`=?, `id_user`=?, `id_muni`=? WHERE `id_equipement`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, equipement.getReference_eq());
            ps.setString(2, equipement.getNom_eq());
            ps.setString(3, equipement.getCategorie_eq());
            ps.setString(4, String.valueOf(equipement.getDate_ajouteq()));
            ps.setInt(5, equipement.getQuantite_eq());
            ps.setString(6, equipement.getImage_eq());
            ps.setString(7, equipement.getDescription_eq());
            ps.setInt(8, equipement.getUser().getId());
            ps.setInt(9, equipement.getMuni().getId());
            ps.executeUpdate();
            System.out.println("Equipement modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'équipement : " + e.getMessage());
        }
    }


    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM equipement WHERE id_equipement=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Equipement supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'équipement : " + e.getMessage());
        }
    }


    @Override
    public Set<Equipement> getAll() {
        Set<Equipement> equipements = new HashSet<>();

        String req = "SELECT * FROM equipement";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_equipement = rs.getInt("id_equipement");
                String reference_eq = rs.getString("reference_eq");
                String nom_eq = rs.getString("nom_eq");
                String categorie_eq = rs.getString("categorie_eq");
                Date date_ajouteq = rs.getDate("date_ajouteq");
                int quantite_eq = rs.getInt("quantite_eq");
                String image_eq = rs.getString("image_eq");
                String description_eq = rs.getString("description_eq");
                int id_user = rs.getInt("id_user");
                int id_muni = rs.getInt("id_muni");

                // Récupération de l'utilisateur associé à l'équipement
                EndUser user = serviceUser.getOneByID(id_user);
                Muni muni = serviceMuni.getOneByID(id_muni);


                // Création de l'équipement et ajout à la liste
                Equipement equipement = new Equipement(id_equipement, reference_eq, nom_eq, categorie_eq, date_ajouteq, quantite_eq, image_eq, description_eq, user,muni);
                equipements.add(equipement);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des équipements : " + e.getMessage());
        }

        return equipements;
    }

    @Override
    public Equipement getOneByID(int id) {
        Equipement equipement = null;
        String req = "SELECT * FROM equipement WHERE id_equipement=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_equipement = rs.getInt("id_equipement");
                String reference_eq = rs.getString("reference_eq");
                String nom_eq = rs.getString("nom_eq");
                String categorie_eq = rs.getString("categorie_eq");
                Date date_ajouteq = rs.getDate("date_ajouteq");
                int quantite_eq = rs.getInt("quantite_eq");
                String image_eq = rs.getString("image_eq");
                String description_eq = rs.getString("description_eq");
                int id_user = rs.getInt("id_user");
                int id_muni = rs.getInt("id_muni");


                // Récupération de l'utilisateur associé à l'équipement
                EndUser user = serviceUser.getOneByID(id_user);
                Muni muni = serviceMuni.getOneByID(id_muni);

                // Création de l'équipement
                equipement = new Equipement(id_equipement, reference_eq, nom_eq, categorie_eq, date_ajouteq, quantite_eq, image_eq, description_eq, user,muni);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'équipement : " + e.getMessage());
        }
        return equipement;
    }

}


