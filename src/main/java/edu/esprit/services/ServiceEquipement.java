package edu.esprit.services;

import edu.esprit.entities.Equipement;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceEquipement implements IService<Equipement> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Equipement equipement) {
        String req = "INSERT INTO equipement (ref_eq, nom_eq, typeMateriel_eq, quantite_eq, etat_eq, description_eq, id_user) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, equipement.getRef_eq());
            ps.setString(2, equipement.getNom_eq());
            ps.setString(3, equipement.getTypeMateriel_eq());
            ps.setInt(4, equipement.getQuantite_eq());
            ps.setString(5, equipement.getEtat_eq());
            ps.setString(6, equipement.getDescription_eq());
            ps.setInt(7, equipement.getId_user());
            ps.executeUpdate();
            System.out.println("Equipement ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'équipement : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Equipement equipement) {
        String req = "UPDATE equipement SET ref_eq=?, nom_eq=?, typeMateriel_eq=?, quantite_eq=?, etat_eq=?, description_eq=?, id_user=? WHERE id_eq=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, equipement.getRef_eq());
            ps.setString(2, equipement.getNom_eq());
            ps.setString(3, equipement.getTypeMateriel_eq());
            ps.setInt(4, equipement.getQuantite_eq());
            ps.setString(5, equipement.getEtat_eq());
            ps.setString(6, equipement.getDescription_eq());
            ps.setInt(7, equipement.getId_user());
            ps.setInt(8, equipement.getId_eq());
            ps.executeUpdate();
            System.out.println("Equipement modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'équipement : " + e.getMessage());
        }
    }


    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM equipement WHERE id_eq=?";
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
                int id_eq = rs.getInt("id_eq");
                String ref_eq = rs.getString("ref_eq");
                String nom_eq = rs.getString("nom_eq");
                String typeMateriel_eq = rs.getString("typeMateriel_eq");
                int quantite_eq = rs.getInt("quantite_eq");
                String etat_eq = rs.getString("etat_eq");
                String description_eq = rs.getString("description_eq");
                int id_user = rs.getInt("id_user");

                Equipement equipement = new Equipement(id_eq, ref_eq, nom_eq, typeMateriel_eq, quantite_eq, etat_eq, description_eq, id_user);
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
        String req = "SELECT * FROM equipement WHERE id_eq=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_eq = rs.getInt("id_eq");
                String ref_eq = rs.getString("ref_eq");
                String nom_eq = rs.getString("nom_eq");
                String typeMateriel_eq = rs.getString("typeMateriel_eq");
                int quantite_eq = rs.getInt("quantite_eq");
                String etat_eq = rs.getString("etat_eq");
                String description_eq = rs.getString("description_eq");
                int id_user = rs.getInt("id_user");

                equipement = new Equipement(id_eq, ref_eq, nom_eq, typeMateriel_eq, quantite_eq, etat_eq, description_eq, id_user);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'équipement : " + e.getMessage());
        }
        return equipement;
    }

}
