package edu.esprit.services;

import edu.esprit.entities.Publicite;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;


public class ServicePublicite implements IService<Publicite> {
        Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void ajouter(Publicite publicite) {

        String req = "INSERT INTO `publicite`(`titre_pub`, `description_pub`, `contact_pub`, `localisation_pub`, `image_pub`,`id_user`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, publicite.getTitre_pub());
            ps.setString(2, publicite.getDescription_pub());
            ps.setInt(3, publicite.getContact_pub());
            ps.setString(4, publicite.getLocalisation_pub());
            ps.setString(5, publicite.getImage_pub());
            ps.setInt(6, publicite.getId_user());
            ps.executeUpdate();
            System.out.println("publicite added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Publicite publicite) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Set<Publicite> getAll() {
        return null;
    }

    @Override
    public Publicite getOneByID(int id) {
        return null;
    }
}
