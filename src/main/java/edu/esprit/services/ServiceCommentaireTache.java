package edu.esprit.services;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceCommentaireTache implements IService<CommentaireTache> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(CommentaireTache ct) {
        String req = "INSERT INTO commentairetache (id_user, id_T, date_C, texte_C) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, ct.getUser().getId());
            ps.setInt(2, ct.getId_T());
            ps.setDate(3, new java.sql.Date(ct.getDate_C().getTime()));
            ps.setString(4, ct.getText_C());
            ps.executeUpdate();
            System.out.println("Commentaire ajouter");
        } catch (SQLException e) {
            System.out.println("Erreur ajout commentaire: " + e.getMessage());
        }
    }
    /*
        @Override
        public void modifier(CommentaireTache ct) {
            String req = "UPDATE commentairetache SET id_user=?, id_T=?, date_C=?, texte_C=? WHERE id_C=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, ct.getUser().getId());
                ps.setInt(2, ct.getId_T());
                ps.setDate(3, new java.sql.Date(ct.getDate_C().getTime()));
                ps.setString(4, ct.getText_C());
                ps.setInt(5, ct.getId_C());
                int rowsAffected = ps.executeUpdate();
                ps.executeUpdate();
            System.out.println("Commentaire ajouter");
            } catch (SQLException e) {
            System.out.println("Erreur ajout commentaire: " + e.getMessage());
        }
    }
    */
    @Override
    public void modifier(CommentaireTache ct) {
        String req = "UPDATE commentairetache SET texte_C=? WHERE id_C=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, ct.getText_C());
            ps.setInt(2, ct.getId_C());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM commentairetache WHERE id_C=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<CommentaireTache> getAll() {
        Set<CommentaireTache> ct = new HashSet<>();
        String req = "SELECT * FROM commentairetache";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_C = rs.getInt("id_C");
                int id_user = rs.getInt("id_user");
                int id_T = rs.getInt("id_T");
                Date date_C = rs.getDate("date_C");
                String texte_C = rs.getString("texte_C");
                CommentaireTache commentaireTache = new CommentaireTache(id_C, id_user, id_T, date_C, texte_C);
                ct.add(commentaireTache);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving comments: " + e.getMessage());
        }
        return ct;
    }

    @Override
    public CommentaireTache getOneByID(int id) {
        String req = "SELECT * FROM commentairetache WHERE id_C=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_user = rs.getInt("id_user");
                int id_T = rs.getInt("id_T");
                Date date_C = rs.getDate("date_C");
                String texte_C = rs.getString("texte_C");
                return new CommentaireTache(id, id_user, id_T, date_C, texte_C);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving comment: " + e.getMessage());
        }
        return null;
    }
    public boolean isValidC(CommentaireTache ct) throws IllegalArgumentException {

        if (ct.getText_C() == null || ct.getText_C().isEmpty()) {
            throw new IllegalArgumentException("Commentaire Obligatoire");
        }
        return true;
    }

}
