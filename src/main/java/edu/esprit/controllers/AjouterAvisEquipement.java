package edu.esprit.controllers;

import edu.esprit.entities.Avis;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServiceEquipement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.Date;

public class AjouterAvisEquipement {
    @FXML
    private TextField commentaireeq;

    @FXML
    private DatePicker dateavis;

    @FXML
    private TextField noteeq;
    private final ServiceAvis serviceAvis = new ServiceAvis();
    private final ServiceEquipement se = new ServiceEquipement();
    Muni muni = new Muni(1);
    EndUser user = new EndUser(1,muni);
    Equipement e = new Equipement(1);
    java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

    @FXML
    void ajouterAvisAction(ActionEvent event) {
        try {
            // Récupérer les valeurs saisies par l'utilisateur
            int note_avis = Integer.parseInt(noteeq.getText());
            String commentaire_avis = commentaireeq.getText();
            // Créer un nouvel objet Avis avec ces valeurs
            Avis nouvelAvis = new Avis(user,muni, e, note_avis, commentaire_avis, sqlDate);

            // Appeler la méthode de service appropriée pour ajouter cet avis
            serviceAvis.ajouter(nouvelAvis);

            // Afficher un message d'alerte en cas de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Avis ajouté avec succès !");
            alert.show();
        } catch (NumberFormatException e) {
            // Afficher un message d'alerte si la note saisie n'est pas un nombre valide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("La note doit être un nombre valide !");
            alert.showAndWait();
        }
    }

    @FXML
    void navigateAfficherAvisAction(ActionEvent event) {

    }
}


