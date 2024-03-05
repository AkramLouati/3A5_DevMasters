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

    }

    @FXML
    void partagerOnClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
