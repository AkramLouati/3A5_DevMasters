package edu.esprit.services;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.entities.Tache;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class serviceTache implements IService<Tache> {
    Connection cnx = DataSource.getInstance().getCnx();

    public boolean addComment(CommentaireTache commentaire) {
        serviceCommentaireTache cs = new serviceCommentaireTache();
        String req = "INSERT INTO commentairetache (id_user, id_T, date_C, texte_C) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, commentaire.getId_user());
            ps.setInt(2, commentaire.getId_T());
            ps.setDate(3, new java.sql.Date(commentaire.getDate_C().getTime()));
            ps.setString(4, commentaire.getText_C());
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void ajouter(Tache tache) {
        String req = "INSERT INTO tache (categorie_T, titre_T, pieceJointe_T, date_DT, date_FT, desc_T, etat_T, id_user) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tache.getCategorie_T());
            ps.setString(2, tache.getTitre_T());
            ps.setString(3, tache.getPieceJointe_T());
            ps.setDate(4, new java.sql.Date(tache.getDate_DT().getTime()));
            ps.setDate(5, new java.sql.Date(tache.getDate_FT().getTime()));
            ps.setString(6, tache.getDesc_T());
            ps.setString(7, tache.getEtat_T().toString());
            ps.setInt(8, tache.getId_user());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating task failed, no rows affected.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tache.setId_T(generatedKeys.getInt(1));
                    System.out.println("Tâche ajoutée avec succès.");
                } else {
                    throw new SQLException("Creating task failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Tache tache) {
        String req = "UPDATE tache SET categorie_T=?, titre_T=?, pieceJointe_T=?, date_DT=?, date_FT=?, desc_T=?, etat_T=?, id_user=? WHERE id_T=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, tache.getCategorie_T());
            ps.setString(2, tache.getTitre_T());
            ps.setString(3, tache.getPieceJointe_T());
            ps.setDate(4, new java.sql.Date(tache.getDate_DT().getTime()));
            ps.setDate(5, new java.sql.Date(tache.getDate_FT().getTime()));
            ps.setString(6, tache.getDesc_T());
            ps.setString(7, tache.getEtat_T().toString());
            ps.setInt(8, tache.getId_user());
            ps.setInt(9, tache.getId_T());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM tache WHERE id_T=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Tache> getAll() {
        Set<Tache> taches = new HashSet<>();

        String req = "SELECT * FROM tache";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt("id_T");
                String categorie = rs.getString("categorie_T");
                String titre = rs.getString("titre_T");
                String pieceJointe = rs.getString("pieceJointe_T");
                Date dateDT = rs.getDate("date_DT");
                Date dateFT = rs.getDate("date_FT");
                String desc = rs.getString("desc_T");
                EtatTache etat = EtatTache.valueOf(rs.getString("etat_T"));
                int id_user = rs.getInt("id_user");
                Tache tache = new Tache(id, categorie, titre, pieceJointe, dateDT, dateFT, desc, etat, id_user);
                taches.add(tache);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taches;
    }

    @Override
    public Tache getOneByID(int id) {
        String req = "SELECT * FROM tache WHERE id_T = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String categorie = rs.getString("categorie_T");
                String titre = rs.getString("titre_T");
                String pieceJointe = rs.getString("pieceJointe_T");
                Date dateDT = rs.getDate("date_DT");
                Date dateFT = rs.getDate("date_FT");
                String desc = rs.getString("desc_T");
                EtatTache etat = EtatTache.valueOf(rs.getString("etat_T"));
                int id_user = rs.getInt("id_user");
                return new Tache(id, categorie, titre, pieceJointe, dateDT, dateFT, desc, etat, id_user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no task found with the provided ID
    }

    public boolean isValidT(Tache tache) throws IllegalArgumentException {
        // Vérifier si la catégorie, le titre et l'utilisateur sont non nuls et non vides
        if (tache.getCategorie_T() == null || tache.getCategorie_T().isEmpty()) {
            throw new IllegalArgumentException("Categorie Obligatoire");
        }
        if (tache.getTitre_T() == null || tache.getTitre_T().isEmpty()) {
            throw new IllegalArgumentException("Titre Obligatoire");
        }
        if (tache.getDate_DT() == null) {
            throw new IllegalArgumentException("Date Debut Obligatoire");
        }
        if (tache.getDate_FT() == null) {
            throw new IllegalArgumentException("Date fin Obligatoire");
        }
        if (tache.getDate_FT().before(tache.getDate_DT())) {
            throw new IllegalArgumentException("Date fin est avant Date Debut");
        }
        EtatTache etatT = tache.getEtat_T();
        if (etatT != EtatTache.TO_DO && etatT != EtatTache.DOING && etatT != EtatTache.DONE) {
            throw new IllegalArgumentException("Etat de la tâche doit etre (TO_DO | DOING | DONE)");
        }
        if (tache.getId_user() <= 0) {
            throw new IllegalArgumentException("ID User Obligatoire");
        }
        return true;
    }
}
