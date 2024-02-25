package edu.esprit.tests;

import edu.esprit.entities.*;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.utils.DataSource;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Set;

import java.util.Set;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.utils.DataSource;

import java.util.Set;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceVote;
import edu.esprit.utils.DataSource;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Create a DataSource instance to ensure database connection
        DataSource ds = new DataSource();

        // Instantiate the ServiceEvenement class with DataSource instance
        ServiceEvenement serviceEvenement = new ServiceEvenement();

        // Test CRUD operations for Evenement
        // 1. Add an event
        EndUser endUser = new EndUser(13); // Assuming you have an EndUser with ID 13
        Evenement eventToAdd = new Evenement(endUser, "Test Event", "2024-02-15 10:00:00", "2024-02-15 12:00:00", 100, "Test Category", "image_path_here");
        serviceEvenement.ajouter(eventToAdd);

        // 2. Retrieve all events
        System.out.println("All events:");
        Set<Evenement> allEvents = serviceEvenement.getAll();
        for (Evenement event : allEvents) {
            System.out.println(event);
        }

        // 3. Update an event
        // Assuming there's an event with ID 10, update its name
        Evenement eventToUpdate = serviceEvenement.getOneByID(34); // Assuming ID 34 exists
        if (eventToUpdate != null) {
            eventToUpdate.setNomEvent("sport");
            serviceEvenement.modifier(eventToUpdate);
            System.out.println("Event updated successfully!");
        } else {
            System.out.println("Event not found!");
        }

        // 4. Delete an event
        // Assuming there's an event with ID 5 to delete
        serviceEvenement.supprimer(5); // Assuming ID 5 exists
        System.out.println("Event deleted successfully!");
    }
}







