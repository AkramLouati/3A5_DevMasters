package edu.esprit.tests;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.utils.DataSource;

import javax.xml.crypto.Data;
import java.util.Set;

import java.util.Set;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.utils.DataSource;

public class Main {
    public static void main(String[] args) {
        // Create a DataSource instance to ensure database connection
        DataSource ds = new DataSource();

        // Instantiate the ServiceEvenement class
        ServiceEvenement serviceEvenement = new ServiceEvenement();

        // Test CRUD operations
        // 1. Add an event
        Evenement eventToAdd = new Evenement(13, "Test Event", "2024-02-15 10:00:00", "2024-02-15 12:00:00", 100, "Test Category");
        serviceEvenement.ajouter(eventToAdd);

        // 2. Retrieve all events
        System.out.println("All events:");
        Set<Evenement> allEvents = serviceEvenement.getAll();
        for (Evenement event : allEvents) {
            System.out.println(event);
        }

        // 3. Update an event
        // Assuming there's an event with ID 1, update its name
        Evenement eventToUpdate = serviceEvenement.getOneByID(1);
        if (eventToUpdate != null) {
            eventToUpdate.setNomEvent("Updated Test Event");
            serviceEvenement.modifier(eventToUpdate);
            System.out.println("Event updated successfully!");
        } else {
            System.out.println("Event not found!");
        }

        // 4. Delete an event
        // Assuming there's an event with ID 1 to delete
        serviceEvenement.supprimer(1);
        System.out.println("Event deleted successfully!");
    }
}






