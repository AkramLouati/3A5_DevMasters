package edu.esprit.controllers;

import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceMuni;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AjoutMuni {
    @FXML
    private TextField TFnom;

    @FXML
    private TextField TFemail;

    @FXML
    private PasswordField TFpassword;

    private final ServiceMuni serviceMuni = new ServiceMuni();

    @FXML
    void ajouterMuniAction(ActionEvent event) {
        serviceMuni.ajouter(new Muni(TFnom.getText(),TFemail.getText(),TFpassword.getText(),"Fergha"));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("GG");
        alert.show();

    }

    public void navigatetoAfficherMuniAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherMuni.fxml"));
            TFnom.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }
}
