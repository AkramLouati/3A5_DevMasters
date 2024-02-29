package edu.esprit.controllers;

import edu.esprit.entities.Vote;
import edu.esprit.services.ServiceVote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ModifierVote {

    @FXML
    private TextField TDdescM;

    @FXML
    private TextField TFdateSM;

    private ServiceVote serviceVote;
    private Vote vote;

    public void setData(Vote vote) {
        this.vote = vote;
        // Afficher les données du vote dans les champs de texte
        TDdescM.setText(vote.getDesc_E());
        TFdateSM.setText(vote.getDate_SV());
    }

    @FXML
    void ModifierVoteOnClick() {
        if (validateFields()) {
            // Récupérer les nouvelles valeurs des champs de texte
            String description = TDdescM.getText();
            String dateSoumission = TFdateSM.getText();

            // Mettre à jour les données du vote
            vote.setDesc_E(description);
            vote.setDate_SV(dateSoumission);

            // Appeler le service pour mettre à jour le vote dans la base de données
            if (serviceVote == null) {
                serviceVote = new ServiceVote();
            }
            serviceVote.modifier(vote);

            // Afficher une alerte pour confirmer la modification
            showAlert(Alert.AlertType.INFORMATION, "Success", "Le vote a été modifié avec succès.");
        } else {
            // Afficher une notification d'erreur si les champs ne sont pas valides
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs correctement !");
        }
    }

    private boolean validateFields() {
        boolean isValid = true;

        // Validation de la description
        if (TDdescM.getText().isEmpty()) {
            setInvalidFieldStyle(TDdescM);
            isValid = false;
        } else {
            setValidFieldStyle(TDdescM);
        }

        // Validation de la date de soumission
        if (!isValidDate(TFdateSM.getText())) {
            setInvalidFieldStyle(TFdateSM);
            isValid = false;
        } else {
            setValidFieldStyle(TFdateSM);
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
