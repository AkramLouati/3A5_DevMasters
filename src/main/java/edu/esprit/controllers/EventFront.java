package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventFront implements Initializable {

    private EventShowFront eventShowFront;

    @FXML
    private ImageView img;

    @FXML
    private Text nomEventTT;

    private Evenement evenement;

    private ServiceEvenement serviceEvenement;

    public void setEventShowFront(EventShowFront eventShowFront) {
        this.eventShowFront = eventShowFront;
    }

    public void setData(Evenement evenement) {
        this.evenement = evenement;
        nomEventTT.setText(evenement.getNomEvent());
        String imagePath = evenement.getImageEvent();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                img.setImage(image);
            }
        }
    }

    // Méthode pour actualiser la vue des événements
    private void actualiserVueEvenements() {
        // Redirection vers la vue précédente (par exemple, la liste des événements)
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/EventShowFront.fxml"));
            nomEventTT.getScene().setRoot(root);
        } catch (IOException e) {
            // Gérer l'exception si la redirection échoue
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Une erreur s'est produite lors de la redirection.");
            errorAlert.setTitle("Erreur de redirection");
            errorAlert.show();
        }
    }

    @FXML
    void joinEventOnclick(ActionEvent event) {
        // Récupérer l'événement choisi
        Evenement evenementChoisi = this.evenement;

        if (evenementChoisi == null) {
            showAlert("Erreur", "Aucun événement choisi !");
            return;
        }

        // Sauvegarder l'utilisateur actuel dans la session
        EndUser currentUser = Session.getCurrentUser();
        if (currentUser == null) {
            showAlert("Erreur", "Aucun utilisateur actuel trouvé !");
            return;
        }

        // Vérifier si la capacité maximale est supérieure à 0
        if (evenementChoisi.getCapaciteMax() <= 0) {
            showAlert("Erreur", "L'événement est complet !");
            return;
        }

        // Décrémenter la capacité maximale de l'événement
        evenementChoisi.setCapaciteMax(evenementChoisi.getCapaciteMax() - 1);

        // Mettre à jour l'événement dans la base de données
        ServiceEvenement serviceEvenement = new ServiceEvenement();
        serviceEvenement.modifier(evenementChoisi);

        // Afficher un message de succès
        showAlert("Succès", "Inscription à l'événement réussie !");

        // Charger l'interface joinEvent.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/joinEvent.fxml"));
            Parent root = loader.load();

            // Passer l'événement choisi au contrôleur de l'interface joinEvent
            JoinEvent joinEventController = loader.getController();
            joinEventController.setData(evenementChoisi);

            // Afficher l'interface joinEvent
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger l'interface joinEvent.");
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Méthode pour sauvegarder l'utilisateur actuel (vous devez implémenter cette méthode selon votre logique)
    private void saveCurrentUser(EndUser currentUser) {
        // Implémentez votre logique de sauvegarde ici
    }

    @FXML
    void partagerOnClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
