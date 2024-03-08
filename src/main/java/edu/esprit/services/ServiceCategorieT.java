package edu.esprit.services;

import edu.esprit.entities.CategorieT;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceCategorieT implements IService<CategorieT> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(CategorieT categorie) {
        String req = "INSERT INTO categorietache (nom_Cat) VALUES (?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, categorie.getNom_Cat());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating category failed");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categorie.setId_Cat(generatedKeys.getInt(1));
                    System.out.println("Category added successfully.");
                } else {
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(CategorieT categorie) {
        String req = "UPDATE categorietache SET nom_Cat=? WHERE id_Cat=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, categorie.getNom_Cat());
            ps.setInt(2, categorie.getId_Cat());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM categorietache WHERE id_Cat=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<CategorieT> getAll() {
        Set<CategorieT> categories = new HashSet<>();

        String req = "SELECT * FROM categorietache";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt("id_Cat");
                String nom_Cat = rs.getString("nom_Cat");
                CategorieT categorie = new CategorieT(id, nom_Cat);
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public CategorieT getOneByID(int id) {
        String req = "SELECT * FROM categorietache WHERE id_Cat = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nom_Cat = rs.getString("nom_Cat");
                return new CategorieT(id, nom_Cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAllCategoryNames() throws SQLException {
        List<String> categoryNames = new ArrayList<>();
        String query = "SELECT nom_Cat FROM categorietache"; // Adjust the query according to your database schema

        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("nom_Cat");
                categoryNames.add(name);
            }
        }
        return categoryNames;
    }

    public List<Integer> getAllCategoryIds() throws SQLException {
        List<Integer> categoryIds = new ArrayList<>();
        String query = "SELECT id_Cat FROM categorietache"; // Adjust the query according to your database schema

        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id_Cat");
                categoryIds.add(id);
            }
        }
        return categoryIds;
    }

    public CategorieT getCategoryByName(String categoryName) {
        String query = "SELECT * FROM categorietache WHERE nom_Cat = ?";
        try {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, categoryName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id_Cat");
                return new CategorieT(id, categoryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
