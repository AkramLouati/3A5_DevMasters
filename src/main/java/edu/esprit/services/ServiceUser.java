package edu.esprit.services;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class ServiceUser implements IService<EndUser>{
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(EndUser endUser) {

    }

    @Override
    public void modifier(EndUser endUser) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Set<EndUser> getAll() {
        return null;
    }

    @Override
    public EndUser getOneByID(int id) {
        String req = "SELECT * FROM `enduser` WHERE `id_user` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Supposons que `id_muni` est le nom de la colonne contenant l'ID de Muni dans la table end_user
                int id_muni = rs.getInt("id_muni");
                // Vous devez récupérer les détails de Muni en utilisant son ID ici
                Muni muni = getServiceMuni().getOneByID(id_muni);
                return new EndUser(id, muni);
            } else {
                System.out.println("EndUser with ID " + id + " not found.");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ServiceMuni getServiceMuni() {
        return new ServiceMuni(); // Vous pouvez utiliser une injection de dépendance ou un singleton selon votre architecture
    }
}
