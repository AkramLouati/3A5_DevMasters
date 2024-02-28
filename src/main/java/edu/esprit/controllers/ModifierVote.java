package edu.esprit.controllers;

import edu.esprit.entities.Vote;
import edu.esprit.services.ServiceVote;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Le vote a été modifié avec succès.");
        alert.setTitle("Vote modifié");
        alert.show();
    }

    @FXML
    void navigateOnClick() {
        try {
            // Charger le fichier FXML de l'interface AfficherVoteS.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherVoteS.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton "Navigate"
            Stage stage = (Stage) TDdescM.getScene().getWindow();

            // Mettre la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions liées au chargement de l'interface AfficherVoteS.fxml
        }
    }
}