package edu.esprit.controllers;

import edu.esprit.entities.Vote;
import edu.esprit.services.ServiceVote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AfficherVoteItem {

        @FXML
        private Text DescriptionAff;

        @FXML
        private Text DateSoumAFF;

        private Vote vote;

        private ServiceVote serviceVote;

        public void setData(Vote vote) {
            this.vote = vote;
            DescriptionAff.setText(vote.getDesc_E());
            DateSoumAFF.setText(vote.getDate_SV());
        }

        @FXML
        void supprimerItemVoteOnClick(ActionEvent event) {
            if (vote != null) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation de suppression");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce vote ?");

                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (serviceVote == null) {
                        serviceVote = new ServiceVote();
                    }
                    serviceVote.supprimer(vote.getId_V());

                    // Actualiser la vue des votes
                    actualiserVueVotes();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Le vote a été supprimé avec succès.");
                    alert.setTitle("Vote supprimé");
                    alert.show();
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Impossible de supprimer le vote car aucun vote n'est sélectionné.");
                errorAlert.setTitle("Erreur de suppression");
                errorAlert.show();
            }
        }

        @FXML
        void modifierVoteOnClick(ActionEvent event) {
            try {
                // Charger la vue de modification de vote
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierVote.fxml"));
                Parent root = loader.load();

                // Passer le vote à modifier au contrôleur de modification
                ModifierVote controller = loader.getController();
                controller.setData(vote);

                // Afficher la vue de modification de vote
                DescriptionAff.getScene().setRoot(root);
            } catch (IOException e) {
                // Gérer les exceptions liées au chargement de la vue de modification
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erreur lors de la modification du vote.");
                alert.setTitle("Erreur de modification");
                alert.show();
            }
        }

        @FXML
        void ajouterVoteOnclickk(ActionEvent event) {
            try {
                // Charger le fichier FXML de l'interface d'ajout de vote
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterVote.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène
                Scene scene = new Scene(root);

                // Obtenir la scène actuelle à partir de l'un des éléments de l'interface actuelle
                Stage stage = (Stage) DescriptionAff.getScene().getWindow();

                // Mettre la nouvelle scène sur le stage
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                // Gérer les exceptions liées au chargement de l'interface
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de navigation");
                alert.setContentText("Une erreur s'est produite lors de la navigation vers l'interface d'ajout de vote.");
                alert.show();
            }
        }

        // Méthode pour actualiser la vue des votes
        private void actualiserVueVotes() {
            // Redirection vers la vue précédente (par exemple, la liste des votes)
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherVoteS.fxml"));
                DescriptionAff.getScene().setRoot(root);
            } catch (IOException e) {
                // Gérer l'exception si la redirection échoue
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Une erreur s'est produite lors de la redirection.");
                errorAlert.setTitle("Erreur de redirection");
                errorAlert.show();
            }
        }
    }

