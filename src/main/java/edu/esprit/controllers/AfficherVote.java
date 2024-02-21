package edu.esprit.controllers;

import edu.esprit.entities.Vote;
import edu.esprit.services.ServiceVote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AfficherVote implements Initializable {
        @FXML
        private ListView<Vote> voteListView;

        private final ServiceVote serviceVote = new ServiceVote();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            afficherVotes();
        }

        private void afficherVotes() {
            Set<Vote> votes = serviceVote.getAll();
            ObservableList<Vote> observableVotes = FXCollections.observableArrayList(votes);
            voteListView.setItems(observableVotes);
        }

        @FXML
        private void supprimerVoteAction() {
            // Obtenez l'élément sélectionné dans la ListView
            Vote selectedVote = voteListView.getSelectionModel().getSelectedItem();
            if (selectedVote != null) {
                // Supprimez l'élément sélectionné à partir de la base de données
                serviceVote.supprimer(selectedVote.getId_V());
                // Mettez à jour la liste des votes affichée
                afficherVotes();
            }
        }
    }


