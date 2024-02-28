package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Vote;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceVote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class VoteList implements Initializable {

    @FXML
    private VBox VoteLayout;
    private Scene currentScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Vote> votes = new ArrayList<>(getVotes());
        for (Vote vote : votes) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = getClass().getResource("/VoteItem.fxml");
            fxmlLoader.setLocation(location);

            try {
                GridPane gridPane = fxmlLoader.load();
                VoteItem voteItemController = fxmlLoader.getController();
                voteItemController.setData(vote);
                VoteLayout.getChildren().add(gridPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<Vote> getVotes() {
        List<Vote> voteList = new ArrayList<>();
        ServiceVote serviceVote = new ServiceVote();
        Set<Vote> votes = serviceVote.getAll();

        for (Vote vote : votes) {
            item(vote, voteList);
        }

        return voteList;
    }

    void item(Vote vote, List<Vote> voteList) {
        vote.setDesc_E(vote.getDesc_E());
        vote.setDate_SV(vote.getDate_SV());
        voteList.add(vote);
    }

    @FXML
    void AjouterPropClick(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface d'ajout d'événement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterVote.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            AjouterVote controller = loader.getController();

            // Obtenir la scène actuelle à partir de l'un des éléments de l'interface actuelle
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Rend la fenêtre modale
            stage.setTitle("Ajouter proposition");
            stage.setScene(new Scene(root));

            // Afficher la fenêtre de ajout
            stage.showAndWait();
        } catch (IOException e) {
            // Gérer les exceptions liées au chargement de l'interface
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de navigation");
            alert.setContentText("Une erreur s'est produite lors de la navigation vers l'interface d'ajout de proposition.");
            alert.show();
        }
    }


}

