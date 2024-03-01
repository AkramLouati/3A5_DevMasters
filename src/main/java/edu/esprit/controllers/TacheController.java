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

import java.util.Objects;

public class TacheController {
    ServiceTache serviceTache = new ServiceTache();
    @FXML
    private Text TFdate_reclamationaff;
    @FXML
    private Text TFsujet_reclamationaff;
    @FXML
    private Text TFstatus_reclamationaff;
    private Tache taches;

    public void setData(Tache taches) {
        this.taches = taches;
        TFsujet_reclamationaff.setText(taches.getTitre_T());
        TFdate_reclamationaff.setText(taches.getDate_DT().toString());
        TFstatus_reclamationaff.setText(taches.getEtat_T().toString());
    }

    @FXML
    void viewDetailTache(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailTache.fxml"));
            Parent root = loader.load();
            DetailTacheController controller = loader.getController();
            controller.setData(taches);
            TFsujet_reclamationaff.getScene().setRoot(root);
        } catch (Exception e) {
            // Print or log the exception message
            e.printStackTrace();
        }
    }

    @FXML
    void deleteTache(ActionEvent event) {
        if (taches != null) {
            ServiceTache serviceReclamation = new ServiceTache();
            serviceReclamation.supprimer(taches.getId_T());

            // Afficher une alerte pour informer l'utilisateur que la réclamation a été supprimée avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Tache supprimée avec succès.");
            alert.setTitle("Tache supprimée");
            alert.show();

            // Rediriger l'utilisateur vers la vue précédente (par exemple, la liste des réclamations)
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherTache.fxml")));
                TFsujet_reclamationaff.getScene().setRoot(root);
            } catch (Exception e) {
                // Print or log the exception message
                e.printStackTrace();
            }
        } else {
            // Afficher un message d'erreur si la réclamation est null
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de supprimer la tache car aucune tache n'est sélectionnée.");
            errorAlert.setTitle("Erreur de suppression");
            errorAlert.show();
        }
    }

    @FXML
    void modifierTache(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierTache.fxml"));
            Parent root = loader.load();
            ModifierTacheController controller = loader.getController();
            controller.initModifier(taches); // Call initModifier and pass the necessary data
            Stage currentStage = (Stage) TFsujet_reclamationaff.getScene().getWindow();
            controller.setStage(currentStage);
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (Exception e) {
            // Print or log the exception message
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
