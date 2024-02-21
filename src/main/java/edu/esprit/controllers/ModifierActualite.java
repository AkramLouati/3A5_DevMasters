package edu.esprit.controllers;

import edu.esprit.entities.Actualite;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceActualite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Date;

public class ModifierActualite {

    private final ServiceActualite serviceActualite = new ServiceActualite();

    @FXML
    private TextField TFdescription;

    @FXML
    private TextField TFtitre;

    @FXML
    void modifierActualiteAction(ActionEvent event) {
        // Get the values from the text fields
        String titre = TFtitre.getText();
        String description = TFdescription.getText();

        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

        // Retrieve the existing Actualite object
        Actualite existingActualite = serviceActualite.getOneByID(actualiteId);

        // Update the fields
        existingActualite.setTitre_a(titre);
        existingActualite.setDescription_a(description);
        existingActualite.setDate_a(sqlDate);
        // Call the service method to update the actualite
        serviceActualite.modifier(existingActualite);

        // Close the current stage (scene)
        Stage stage = (Stage) TFtitre.getScene().getWindow();
        stage.close();
    }

    // You can implement the method to navigate to the display page
    @FXML
    void navigatetoAfficherActualiteAction(ActionEvent event) {
        // Implement your navigation logic here
    }

    private int actualiteId;

    public void setActualiteId(int id) {
        actualiteId = id;

        // Retrieve the existing Actualite object
        Actualite existingActualite = serviceActualite.getOneByID(actualiteId);

        // Populate the fields with the existing actualite data
        TFtitre.setText(existingActualite.getTitre_a());
        TFdescription.setText(existingActualite.getDescription_a());

    }
}
