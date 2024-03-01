package edu.esprit.controllers;

import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceTache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import java.io.IOException;

public class TacheFrontController {
    ServiceTache serviceTache = new ServiceTache();

    @FXML
    private Text TF_Titre_Tache;
    private Tache taches;

    public void setData(Tache taches) {
        this.taches = taches;
        TF_Titre_Tache.setText(taches.getTitre_T());
    }

    @FXML
    void viewDetailTache(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailTacheFront.fxml"));
            Parent root = loader.load();
            DetailTacheFrontController controller = loader.getController();
            controller.setData(taches);
            TF_Titre_Tache.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
