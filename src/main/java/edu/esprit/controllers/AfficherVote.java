package edu.esprit.controllers;

import edu.esprit.entities.Vote;
import edu.esprit.services.ServiceVote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
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
        voteListView.setCellFactory(param -> new VoteListCell());
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

    // Classe de cellule personnalisée pour afficher les votes sans l'ID
    private static class VoteListCell extends ListCell<Vote> {
        @Override
        protected void updateItem(Vote item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
            } else {
                // Afficher toutes les informations du vote
                setText("Description: " + item.getDesc_E() + "\n" +
                        "Date: " + item.getDate_SV() + "\n" +
                        "Utilisateur: " + item.getUser().getId());
            }
        }
    }
}



