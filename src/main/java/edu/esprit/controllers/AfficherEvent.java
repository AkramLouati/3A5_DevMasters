package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class AfficherEvent implements Initializable {

    @FXML
    private ListView<Evenement> eventListView;

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventListView.getItems().addAll(serviceEvenement.getAll());
        eventListView.setCellFactory(param -> new EvenementListCell());
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

    // Classe de cellule personnalisée pour afficher les événements sans l'ID
    private static class EvenementListCell extends ListCell<Evenement> {
        @Override
        protected void updateItem(Evenement item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
            } else {
                // Afficher toutes les informations de l'événement
                setText("Nom: " + item.getNomEvent() + "\n" +
                        "Date et heure de début: " + item.getDateEtHeureDeb() + "\n" +
                        "Date et heure de fin: " + item.getDateEtHeureFin() + "\n" +
                        "Capacité maximale: " + item.getCapaciteMax() + "\n" +
                        "Catégorie: " + item.getCategorie());
            }
        }
    }
}
