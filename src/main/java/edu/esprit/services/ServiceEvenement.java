package edu.esprit.services;

import edu.esprit.entities.Evenement;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceEvenement implements IService<Evenement> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Evenement evenement) {
        // Vérification des champs requis
        if (!validateEvenement(evenement)) {
            System.out.println("Tous les champs doivent être remplis !");
            return;
        }

        String req = "INSERT INTO evenement (id_user, nom_E, date_DHE, date_DHF, capacite_E, categorie_E) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, evenement.getId_user());
            ps.setString(2, evenement.getNomEvent());
            ps.setString(3, evenement.getDateEtHeureDeb());
            ps.setString(4, evenement.getDateEtHeureFin());
            ps.setInt(5, evenement.getCapaciteMax());
            ps.setString(6, evenement.getCategorie());
            ps.executeUpdate();
            System.out.println("Evenement ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Evenement evenement) {
        if (!validateEvenement(evenement)) {
            System.out.println("Tous les champs doivent être remplis !");
            return;
        }

        String req = "UPDATE evenement SET id_user=?, nom_E=?, date_DHE=?, date_DHF=?, capacite_E=?, categorie_E=? WHERE id_E=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, evenement.getId_user());
            ps.setString(2, evenement.getNomEvent());
            ps.setString(3, evenement.getDateEtHeureDeb());
            ps.setString(4, evenement.getDateEtHeureFin());
            ps.setInt(5, evenement.getCapaciteMax());
            ps.setString(6, evenement.getCategorie());
            ps.setInt(7, evenement.getId_E());
            ps.executeUpdate();
            System.out.println("Evenement modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean validateEvenement(Evenement evenement) {
        return evenement.getId_user() > 0 &&
                !evenement.getNomEvent().isEmpty() &&
                !evenement.getDateEtHeureDeb().isEmpty() &&
                !evenement.getDateEtHeureFin().isEmpty() &&
                evenement.getCapaciteMax() > 0 &&
                !evenement.getCategorie().isEmpty();
    }


    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM evenement WHERE id_E=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Evenement deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Set<Evenement> getAll() {
        Set<Evenement> evenements = new HashSet<>();
        String req = "SELECT * FROM evenement";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt("id_E");
                int id_user = rs.getInt("id_user");
                String nom_E = rs.getString("nom_E");
                String date_DHE = rs.getString("date_DHE");
                String date_DHF = rs.getString("date_DHF");
                int capacite_E = rs.getInt("capacite_E");
                String categorie_E = rs.getString("categorie_E");
                Evenement evenement = new Evenement(id, nom_E, id_user, date_DHE, date_DHF, capacite_E, categorie_E);
                evenements.add(evenement);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return evenements;
    }

    @Override
    public Evenement getOneByID(int id) {
        String req = "SELECT * FROM evenement WHERE id_E=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_user = rs.getInt("id_user");
                String nom_E = rs.getString("nom_E");
                String date_DHE = rs.getString("date_DHE");
                String date_DHF = rs.getString("date_DHF");
                int capacite_E = rs.getInt("capacite_E");
                String categorie_E = rs.getString("categorie_E");
                return new Evenement(id, nom_E, id_user, date_DHE, date_DHF, capacite_E, categorie_E);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

