package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class AjouterEvent {
    @FXML
    private TextField TFnomEvent;

    @FXML
    private TextField TFdateDebut;

    @FXML
    private TextField TFdateFin;

    @FXML
    private TextField TFcapacite;

    @FXML
    private TextField TFcategorie;

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private final ServiceUser serviceUser = new ServiceUser(); // Assuming this service exists to retrieve users

    @FXML
    public void AjouterEventAction(javafx.event.ActionEvent actionEvent) {

        try {
            // Retrieve the current user (you may need to implement this logic based on your application)
            // For now, let's assume user ID is 1
            EndUser currentUser = serviceUser.getOneByID(1);

            // Create the event object
            Evenement evenement = new Evenement(
                    currentUser, // Pass the current user
                    TFnomEvent.getText(),
                    TFdateDebut.getText(),
                    TFdateFin.getText(),
                    Integer.parseInt(TFcapacite.getText()), // Parse capacity to integer
                    TFcategorie.getText()
            );

            // Add the event
            serviceEvenement.ajouter(evenement);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Evenement ajouté avec succès !");
            alert.show();
        } catch (SQLException | NumberFormatException e) {
            // Show error message if something goes wrong
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de l'ajout de l'événement : " + e.getMessage());
            alert.showAndWait();
        }
    }

}


