package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherEvent implements Initializable {

    @FXML
    private ListView<Evenement> eventListView;

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventListView.getItems().addAll(serviceEvenement.getAll());
        eventListView.setCellFactory(eventListView -> new EvenementListCell());
    }

    @FXML
    void supprimerEventAction() {
        Evenement selectedEvent = eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert(Alert.AlertType.WARNING, "Sélectionnez un événement", "Veuillez sélectionner un événement à supprimer.");
        } else {
            serviceEvenement.supprimer(selectedEvent.getId_E());
            eventListView.getItems().remove(selectedEvent);
            showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", "L'événement a été supprimé avec succès.");
        }
    }

    @FXML
    void modifierEventAction() {
        Evenement selectedEvent = eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert(Alert.AlertType.WARNING, "Sélectionnez un événement", "Veuillez sélectionner un événement à modifier.");
        } else {
            // Demander à l'utilisateur de saisir les nouvelles informations
            TextInputDialog dialog = new TextInputDialog(selectedEvent.getNomEvent());
            dialog.setTitle("Modifier l'événement");
            dialog.setHeaderText("Modifier l'événement: " + selectedEvent.getNomEvent());
            dialog.setContentText("Nouveau nom de l'événement:");
            // Afficher les informations actuelles de l'événement
            dialog.setContentText("Nom actuel de l'événement: " + selectedEvent.getNomEvent() + "\n" +
                    "Date de début: " + selectedEvent.getDateEtHeureDeb() + "\n" +
                    "Date de fin: " + selectedEvent.getDateEtHeureFin() + "\n" +
                    "Capacité maximale: " + selectedEvent.getCapaciteMax() + "\n" +
                    "Catégorie: " + selectedEvent.getCategorie());

            // Afficher la boîte de dialogue et récupérer les nouvelles informations
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(nouveauNom -> {
                // Mettre à jour les informations de l'événement avec les nouvelles valeurs
                selectedEvent.setNomEvent(nouveauNom);
                // Vous pouvez ajouter ici le code pour mettre à jour les autres informations de l'événement
                serviceEvenement.modifier(selectedEvent);
                eventListView.refresh();
                showAlert(Alert.AlertType.INFORMATION, "Modification réussie", "Les informations de l'événement ont été modifiées avec succès.");
            });
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Classe interne pour personnaliser l'affichage des événements dans la ListView
    private class EvenementListCell extends ListCell<Evenement> {
        @Override
        protected void updateItem(Evenement evenement, boolean empty) {
            super.updateItem(evenement, empty);
            if (empty || evenement == null) {
                setText(null);
            } else {
                // Afficher toutes les informations de l'événement
                setText("Nom: " + evenement.getNomEvent() + "\n" +
                        "Date de début: " + evenement.getDateEtHeureDeb() + "\n" +
                        "Date de fin: " + evenement.getDateEtHeureFin() + "\n" +
                        "Capacité maximale: " + evenement.getCapaciteMax() + "\n" +
                        "Catégorie: " + evenement.getCategorie());
            }
        }
    }
}


