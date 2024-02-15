package edu.esprit.tests;

import edu.esprit.entities.Avis;
import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServiceEquipement;
import edu.esprit.utils.DataSource;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        DataSource ds = new DataSource();
        Connection cnx = ds.getCnx();
/*
        // Create a ServiceEquipement object
         ServiceEquipement serviceEquipement = new ServiceEquipement();

        // Create a sample Equipement object
        Equipement equipement = new Equipement("ref1", "Nom Equipement 1", "Type Matériel 1", 10, "Bon état", "Description Equipement 1", 1);
        // Sample data, adjust as needed

       // Call the ajouter method to add the Equipement object to the database
       serviceEquipement.ajouter(equipement);

       // Close the connection
         try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
       // Create a ServiceEquipement object
       ServiceEquipement serviceEquipement = new ServiceEquipement();

        // Create a sample Equipement object to modify
        Equipement equipementToModify = new Equipement();
        equipementToModify.setId_eq(2); // Assuming the ID of the Equipement to modify is 1
        equipementToModify.setRef_eq("new_ref");
        equipementToModify.setNom_eq("New Name");
        equipementToModify.setTypeMateriel_eq("New Type");
        equipementToModify.setQuantite_eq(20);
        equipementToModify.setEtat_eq("Good condition");
        equipementToModify.setDescription_eq("Updated description");
        equipementToModify.setId_user(1); // Assuming the ID of the user is 2

        // Call the modifier method to modify the Equipement object in the database
        serviceEquipement.modifier(equipementToModify);

        // Close the connection
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }

        // Call the getAll method to retrieve all Equipements from the database
        Set<Equipement> equipements = serviceEquipement.getAll();

        // Display the retrieved Equipements
        for (Equipement equipement : equipements) {
            System.out.println(equipement);
        }

        // Close the connection
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
        // Créer un objet ServiceEquipement
        ServiceEquipement serviceEquipement = new ServiceEquipement();

        // Appeler la méthode getOneByID pour récupérer un Equipement par son ID (supposons que l'ID de l'Equipement à récupérer est 1)
        Equipement equipement = serviceEquipement.getOneByID(3);

        // Afficher l'Equipement récupéré
        if (equipement != null) {
            System.out.println("Equipement récupéré : " + equipement);
        } else {
            System.out.println("Aucun équipement trouvé avec cet ID.");
        }

        // Fermer la connexion
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
        // Créer un objet ServiceEquipement
        ServiceEquipement serviceEquipement = new ServiceEquipement();

        // Supprimer un équipement par son ID (supposons que l'ID de l'équipement à supprimer est 1)
        int idEquipementASupprimer = 2;
        serviceEquipement.supprimer(idEquipementASupprimer);

        // Afficher un message pour indiquer que l'équipement a été supprimé

        // Fermer la connexion
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }

        // Créer un objet ServiceAvis
        ServiceAvis serviceAvis = new ServiceAvis(cnx);

        // Créer un objet Equipement pour l'Avis
        Equipement equipement = new Equipement();
        equipement.setId_eq(2); // ID de l'équipement auquel l'avis est associé

        // Créer un objet Avis
        Avis avis = new Avis();
        avis.setEquipement(equipement);
        avis.setNote_avis(6); // Note de l'avis
        avis.setCommentaire_avis("Bon equipment!"); // Commentaire de l'avis
        avis.setDate_avis(new Date()); // Date de l'avis

        // Appeler la méthode ajouter pour ajouter l'avis dans la base de données
        serviceAvis.ajouter(avis);

        // Fermer la connexion
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }

        // Créer un objet ServiceAvis
        ServiceAvis serviceAvis = new ServiceAvis(cnx);

        // Créer un objet Avis à modifier (supposons que l'avis avec l'ID 1 existe dans la base de données)
        Avis avis = new Avis();
        avis.setId_avis(1); // ID de l'avis à modifier
        avis.setNote_avis(5); // Nouvelle note de l'avis
        avis.setCommentaire_avis("Très bon équipement."); // Nouveau commentaire de l'avis
        avis.setDate_avis(new Date()); // Nouvelle date de l'avis

        // Appeler la méthode modifier pour modifier l'avis dans la base de données
        serviceAvis.modifier(avis);

        // Fermer la connexion
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }

        // Créer un objet ServiceAvis
        ServiceAvis serviceAvis = new ServiceAvis(cnx);

        // Appeler la méthode getAll pour récupérer tous les avis de la base de données
        Set<Avis> avisList = serviceAvis.getAll();

        // Afficher la liste des avis
        for (Avis avis : avisList) {
            System.out.println(avis);
        }

        // Fermer la connexion
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }

        // Créer un objet ServiceAvis
        ServiceAvis serviceAvis = new ServiceAvis(cnx);

        // Appeler la méthode getOneByID pour récupérer un avis par son ID (supposons que l'ID de l'Equipement à récupérer est 1)
        Avis avis = serviceAvis.getOneByID(1);

        // Afficher avis récupéré
        if (avis != null) {
            System.out.println("Avis récupéré : " + avis);
        } else {
            System.out.println("Aucun avis trouvé avec cet ID.");
        }

        // Fermer la connexion
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
        */
// Créer un objet ServiceAvis
        ServiceAvis serviceAvis = new ServiceAvis(cnx);

        // Suppression de l'avis avec un ID spécifique (remplacez 1 par l'ID de l'avis que vous souhaitez supprimer)
        int idAvisASupprimer = 1;
        serviceAvis.supprimer(idAvisASupprimer);
    }
    }






