package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class AfficherEventItem {
    @FXML
    private Text NomEventAff;

    @FXML
    private Text DateDebAFF;

    @FXML
    private Text DateFinAFF;

    @FXML
    private Text CapMaxAFF;

    @FXML
    private Text categorieAFF;

    private Evenement evenement;

    private ServiceEvenement serviceEvenement;

    public void setData(Evenement evenement) {
        this.evenement = evenement;
        NomEventAff.setText(evenement.getNomEvent());
        DateDebAFF.setText(evenement.getDateEtHeureDeb());
        DateFinAFF.setText(evenement.getDateEtHeureFin());
        CapMaxAFF.setText(String.valueOf(evenement.getCapaciteMax()));
        categorieAFF.setText(evenement.getCategorie());
    }

    @FXML
    void supprimerItemEventOnClick() {
        if (evenement != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (serviceEvenement == null) {
                    serviceEvenement = new ServiceEvenement();
                }
                serviceEvenement.supprimer(evenement.getId_E());

                // Actualiser la vue des événements
                actualiserVueEvenements();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("L'événement a été supprimé avec succès.");
                alert.setTitle("Événement supprimé");
                alert.show();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de supprimer l'événement car aucun événement n'est sélectionné.");
            errorAlert.setTitle("Erreur de suppression");
            errorAlert.show();
        }
    }
    @FXML
    void modifierEventOnClick(ActionEvent event) {
        try {
            // Charger la vue de modification d'événement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
            Parent root = loader.load();

            // Passer l'événement à modifier au contrôleur de modification
            ModifierEvent controller = loader.getController();
            controller.setData(evenement);

            // Afficher la vue de modification d'événement
            NomEventAff.getScene().setRoot(root);
        } catch (IOException e) {
            // Gérer les exceptions liées au chargement de la vue de modification
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la modification de l'événement.");
            alert.setTitle("Erreur de modification");
            alert.show();
        }
    }
    @FXML
    void ajouterEventOnclickk(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface d'ajout d'événement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'un des éléments de l'interface actuelle
            Stage stage = (Stage) NomEventAff.getScene().getWindow();

            // Mettre la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Gérer les exceptions liées au chargement de l'interface
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de navigation");
            alert.setContentText("Une erreur s'est produite lors de la navigation vers l'interface d'ajout d'événement.");
            alert.show();
        }
    }

    // Méthode pour actualiser la vue des événements
    private void actualiserVueEvenements() {
        // Redirection vers la vue précédente (par exemple, la liste des événements)
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEventS.fxml"));
            NomEventAff.getScene().setRoot(root);
        } catch (IOException e) {
            // Gérer l'exception si la redirection échoue
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Une erreur s'est produite lors de la redirection.");
            errorAlert.setTitle("Erreur de redirection");
            errorAlert.show();
        }
    }


}
