package edu.esprit.services;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class serviceCommentaireTache implements IService<CommentaireTache> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public boolean ajouter(CommentaireTache commentaireTache) {
        if (!isValidC(commentaireTache)) {
            return false;
        }
        String req = "INSERT INTO commentairetache (id_user, id_T, date_C, texte_C) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, commentaireTache.getId_user());
            ps.setInt(2, commentaireTache.getId_T());
            ps.setDate(3, new java.sql.Date(commentaireTache.getDate_C().getTime()));
            ps.setString(4, commentaireTache.getText_C());
            ps.executeUpdate();
            System.out.println("Comment added successfully!");
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding comment: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addComment(CommentaireTache commentaireTache) {
        return false;
    }

    @Override
    public void modifier(CommentaireTache commentaireTache) {
        if (!isValidC(commentaireTache)) {
            return;
        }
        String req = "UPDATE commentairetache SET id_user=?, id_T=?, date_C=?, texte_C=? WHERE id_C=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, commentaireTache.getId_user());
            ps.setInt(2, commentaireTache.getId_T());
            ps.setDate(3, new java.sql.Date(commentaireTache.getDate_C().getTime()));
            ps.setString(4, commentaireTache.getText_C());
            ps.setInt(5, commentaireTache.getId_C());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Comment updated successfully!");
            } else {
                System.out.println("No comment found for updating.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating comment: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM commentairetache WHERE id_C=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Comment deleted successfully!");
            } else {
                System.out.println("No comment found for deletion.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting comment: " + e.getMessage());
        }
    }

    @Override
    public Set<CommentaireTache> getAll() {
        Set<CommentaireTache> commentaires = new HashSet<>();
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
                commentaires.add(commentaireTache);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving comments: " + e.getMessage());
        }
        return commentaires;
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
        public boolean isValidC(CommentaireTache commentaireTache) {
        // Vérifier si l'utilisateur, le texte du commentaire et l'ID de la tâche sont valides
        if (commentaireTache.getId_user() <= 0) {
            System.out.println("Error: User ID is required and must be greater than 0.");
            return false;
        }
        if (commentaireTache.getId_T() <= 0) {
            System.out.println("Error: Task ID is required and must be greater than 0.");
            return false;
        }
        if (commentaireTache.getText_C() == null || commentaireTache.getText_C().isEmpty()) {
            System.out.println("Error: Comment text is required.");
            return false;
        }
        return true;
    }

}
