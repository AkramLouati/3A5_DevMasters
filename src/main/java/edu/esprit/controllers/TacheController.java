package edu.esprit.controllers;

import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceTache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TacheController {
    @FXML
    private Text TFdate_reclamationaff;

    @FXML
    private Text TFsujet_reclamationaff;

    @FXML
    private Text TFstatus_reclamationaff;

    private Tache taches;
    ServiceTache serviceTache = new ServiceTache();

    public void setData(Tache taches){
        this.taches = taches;
        TFsujet_reclamationaff.setText(taches.getTitre_T());
        TFdate_reclamationaff.setText(taches.getDate_DT().toString());
        TFstatus_reclamationaff.setText(taches.getEtat_T().toString());
    }
    @FXML
    void viewDetailReclamationAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailTaches.fxml"));
            Parent root = loader.load();
            DetailTachesController controller = loader.getController();
            controller.setData(taches);
            TFsujet_reclamationaff.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
    @FXML
    void deleteReclamationAction(ActionEvent event) {
        if (taches != null) {
            ServiceTache serviceReclamation = new ServiceTache();
            serviceReclamation.supprimer(taches.getId_T());

            // Afficher une alerte pour informer l'utilisateur que la réclamation a été supprimée avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La réclamation a été supprimée avec succès.");
            alert.setTitle("Réclamation supprimée");
            alert.show();

            // Rediriger l'utilisateur vers la vue précédente (par exemple, la liste des réclamations)
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherTaches.fxml")));
                TFsujet_reclamationaff.getScene().setRoot(root);
            } catch (IOException e) {
                // Gérer l'exception si la redirection échoue
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Une erreur s'est produite lors de la redirection.");
                errorAlert.setTitle("Erreur de redirection");
                errorAlert.show();
            }
        } else {
            // Afficher un message d'erreur si la réclamation est null
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de supprimer la réclamation car aucune réclamation n'est sélectionnée.");
            errorAlert.setTitle("Erreur de suppression");
            errorAlert.show();
        }
    }
    @FXML
    void modifierReclamationAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTache.fxml"));
            Parent root = loader.load();
            AjouterTacheController controller = loader.getController();

            // Set the stage if it's not null

            Stage stage = new Stage();
            if (stage != null) {
                controller.setStage(stage);
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Erreur lors de l'ajout de la tâche : " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
