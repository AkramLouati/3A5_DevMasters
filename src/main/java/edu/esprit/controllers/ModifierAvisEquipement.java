package edu.esprit.controllers;

import edu.esprit.entities.Avis;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceAvis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;

public class ModifierAvisEquipement {
    @FXML
    private TextField commentaireeqmodif;

    @FXML
    private DatePicker dateavismodif;

    @FXML
    private TextField noteeqmodif;
    private ServiceAvis serviceAvis;
    private Avis avis;
    Muni muni = new Muni(1);
    EndUser user = new EndUser(1,muni);
    Equipement equipement=new Equipement(2);

    @FXML
    void modifierAvisAction(ActionEvent event) {
        LocalDate localDate = LocalDate.now();
        if (avis != null && serviceAvis != null) {
            // Mettre à jour les données de la réclamation avec les valeurs des champs de texte
            avis.setNote_avis(Integer.parseInt(noteeqmodif.getText()));
            avis.setCommentaire_avis(commentaireeqmodif.getText());
            avis.setDate_avis(Date.valueOf(localDate));

            try {
                // Appeler la méthode de modification du service de réclamation
                serviceAvis.modifier(avis);

                // Afficher un message de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setContentText("Réclamation modifiée avec succès !");
                successAlert.setTitle("Modification réussie");
                successAlert.show();
            } catch (Exception e) {
                // Afficher un message d'erreur en cas d'échec de la modification
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Erreur lors de la modification de la réclamation : " + e.getMessage());
                errorAlert.setTitle("Erreur de modification");
                errorAlert.show();
            }
        } else {
            // Afficher un message d'erreur si la réclamation est null ou si le service de réclamation est null
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de modifier la réclamation car aucune réclamation n'est sélectionnée ou le service de réclamation est null.");
            errorAlert.setTitle("Erreur de modification");
            errorAlert.show();
        }
    }

    }

    //@FXML
    //void navigateAfficherAvisAction(ActionEvent event) {

    //}

