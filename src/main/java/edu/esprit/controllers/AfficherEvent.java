package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

import edu.esprit.services.ServiceEvenement;
import edu.esprit.entities.Evenement;

public class AfficherEvent implements Initializable {

    @FXML
    private ListView<Evenement> eventListView;

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventListView.getItems().addAll(serviceEvenement.getAll());
    }

    @FXML
    void supprimerEventAction() {
        Evenement selectedEvent = eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert(AlertType.WARNING, "Sélectionnez un événement", "Veuillez sélectionner un événement à supprimer.");
        } else {
            serviceEvenement.supprimer(selectedEvent.getId_E());
            eventListView.getItems().remove(selectedEvent);
            showAlert(AlertType.INFORMATION, "Suppression réussie", "L'événement a été supprimé avec succès.");
        }
    }

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
