package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Vote;
import edu.esprit.services.ServiceUser;
import edu.esprit.services.ServiceVote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AjouterVote {
    private EventDashboard eventDashboardController;

    @FXML
    private TextField TDdesc;

    @FXML
    private TextField TFdateS;

    private final ServiceVote serviceVote = new ServiceVote();

    @FXML
    void AjouterVoteOnClick(ActionEvent event) {
        if (validateFields()) {
            try {
                // Récupération de l'utilisateur actuel (à remplacer par votre mécanisme d'authentification)
                ServiceUser serviceUser = new ServiceUser();
                EndUser user = serviceUser.getOneByID(12); // Exemple : suppose que l'utilisateur actuel a l'ID 1

                // Création du vote
                Vote vote = new Vote(
                        user,
                        TDdesc.getText(),
                        TFdateS.getText()
                );

                // Ajout du vote via le service
                serviceVote.ajouter(vote);

                // Affichage d'une notification de succès
                showAlert(Alert.AlertType.INFORMATION, "Success", "Vote ajouté avec succès !");
            } catch (SQLException e) {
                // En cas d'erreur lors de l'ajout du vote
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout du vote : " + e.getMessage());
            }
        } else {
            // Affichage d'une notification d'erreur si les champs ne sont pas valides
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs correctement !");
        }
    }
    public void setEventDashboardController(EventDashboard eventDashboardController) {
        this.eventDashboardController = eventDashboardController;
    }

    private void refreshEventDashboard() {
        if (eventDashboardController != null) {
            eventDashboardController.loadEvents();
        }
    }

    @FXML
    void returnOnclick(ActionEvent event) {
        try {
            // Fermer la fenêtre actuelle
            Stage stage = (Stage) TDdesc.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer les exceptions liées à la fermeture de la fenêtre
        }
    }


    private boolean validateFields() {
        boolean isValid = true;

        // Validation de la description
        if (TDdesc.getText().isEmpty()) {
            setInvalidFieldStyle(TDdesc);
            isValid = false;
        } else {
            setValidFieldStyle(TDdesc);
        }

        // Validation de la date de soumission
        if (!isValidDate(TFdateS.getText())) {
            setInvalidFieldStyle(TFdateS);
            isValid = false;
        } else {
            setValidFieldStyle(TFdateS);
        }

        return isValid;
    }

    private boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void setInvalidFieldStyle(TextField textField) {
        textField.setStyle("-fx-border-color: red;");
    }

    private void setValidFieldStyle(TextField textField) {
        textField.setStyle("-fx-border-color: lime;");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}
