package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EventItem implements Initializable {

    @FXML
    private ImageView img;

    @FXML
    private Text nomEventT;


    private Evenement evenement;

    private ServiceEvenement serviceEvenement;

    public void setData(Evenement evenement) {
        this.evenement = evenement;
        nomEventT.setText(evenement.getNomEvent());
        // Si vous avez une image pour afficher, vous pouvez la charger ici
        // img.setImage(new Image(evenement.getImagePath()));
        String imagePath = evenement.getImageEvent();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                img.setImage(image);
            }
        }
    }

    @FXML
    void ModifierEvenementClick(ActionEvent event) {
        try {
            // Charger la vue de modification d'événement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
            Parent root = loader.load();

            // Passer l'événement à modifier au contrôleur de modification
            ModifierEvent controller = loader.getController();
            controller.setData(evenement);

            // Créer une nouvelle fenêtre (stage) pour afficher la vue de modification
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Rend la fenêtre modale
            stage.setTitle("Modifier événement");
            stage.setScene(new Scene(root));

            // Afficher la fenêtre de modification
            stage.showAndWait();
        } catch (IOException e) {
            // Gérer les exceptions liées au chargement de la vue de modification
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la modification de l'événement.");
            alert.setTitle("Erreur de modification");
            alert.show();
        }
    }


    @FXML
    void SupprimerEvenementClick(ActionEvent event) {
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
    void DetailsEvenementClick(ActionEvent event) {
        try {
            // Charger la vue des détails de l'événement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsEvent.fxml"));
            Parent root = loader.load();

            // Passer l'événement dont les détails sont affichés au contrôleur des détails
            DetailsEvent controller = loader.getController();
            controller.setData(evenement);

            // Afficher la vue des détails de l'événement
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            // Gérer les exceptions liées au chargement de la vue des détails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors du chargement des détails de l'événement.");
            alert.setTitle("Erreur de chargement des détails");
            alert.show();
        }
    }
    // Méthode pour actualiser la vue des événements
    private void actualiserVueEvenements() {
        // Redirection vers la vue précédente (par exemple, la liste des événements)
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/EventDashboard.fxml"));
            nomEventT.getScene().setRoot(root);
        } catch (IOException e) {
            // Gérer l'exception si la redirection échoue
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Une erreur s'est produite lors de la redirection.");
            errorAlert.setTitle("Erreur de redirection");
            errorAlert.show();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
