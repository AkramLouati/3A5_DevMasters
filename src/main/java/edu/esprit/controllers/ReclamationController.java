package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import java.io.IOException;

public class ReclamationController {
    @FXML
    private Text TFdate_reclamationaff;

    @FXML
    private Text TFsujet_reclamationaff;

    @FXML
    private Text TFstatus_reclamationaff;

    private Reclamation reclamation;
    ServiceReclamation serviceReclamation = new ServiceReclamation();

    public void setData(Reclamation reclamation){
        this.reclamation = reclamation;
        TFsujet_reclamationaff.setText(reclamation.getSujet_reclamation());
        TFdate_reclamationaff.setText(String.valueOf(reclamation.getDate_reclamation()));
        TFstatus_reclamationaff.setText(reclamation.getStatus_reclamation());
    }
    @FXML
    void viewDetailReclamationAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailReclamation.fxml"));
            Parent root = loader.load();
            DetailReclamationController controller = loader.getController();
            controller.setData(reclamation);
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
        if (reclamation != null) {
            ServiceReclamation serviceReclamation = new ServiceReclamation();
            serviceReclamation.supprimer(reclamation.getId_reclamation());

            // Afficher une alerte pour informer l'utilisateur que la réclamation a été supprimée avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La réclamation a été supprimée avec succès.");
            alert.setTitle("Réclamation supprimée");
            alert.show();

            // Rediriger l'utilisateur vers la vue précédente (par exemple, la liste des réclamations)
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherReclamation.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReclamation.fxml"));
            Parent root = loader.load();
            ModifierReclamationController controller = loader.getController();
            controller.setServiceReclamation(serviceReclamation);
            controller.setData(reclamation);
            TFsujet_reclamationaff.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }


}
