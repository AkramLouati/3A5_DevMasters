package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Vote;
import edu.esprit.services.ServiceUser;
import edu.esprit.services.ServiceVote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AjouterVote {

    private VoteList voteListController;

    @FXML
    private TextField TDdesc;

    @FXML
    private TextField TFdateS;

    private final ServiceVote serviceVote = new ServiceVote();

    public void setVoteListController(VoteList voteListController) {
        this.voteListController = voteListController;
    }

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

                // Refresh the VoteList
                refreshVoteList();
            } catch (SQLException e) {
                // En cas d'erreur lors de l'ajout du vote
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout du vote : " + e.getMessage());
            }
        } else {
            // Affichage d'une notification d'erreur si les champs ne sont pas valides
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs correctement !");
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

        return isValid;
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

    private void refreshVoteList() {
        if (voteListController != null) {
            voteListController.loadVotes();
        }
    }
}
