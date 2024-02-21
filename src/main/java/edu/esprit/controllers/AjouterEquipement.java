package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterEquipement {

    @FXML
    private TextField refeq;

    @FXML
    private TextField nomeq;

    @FXML
    private ComboBox<String> etat;

    @FXML
    private RadioButton materielfix;

    @FXML
    private RadioButton materielmobile;

    @FXML
    private ComboBox<Integer> qunatite;

    private final ServiceEquipement serviceEquipement = new ServiceEquipement();
    private final ServiceUser serviceUser = new ServiceUser();


    @FXML
    void ajouterEquipementAction(ActionEvent event) {
        try {
            // Récupérer les valeurs des champs de l'interface graphique
            String reference = refeq.getText();
            String nom = nomeq.getText();
            String etatEquipement = etat.getValue();
            String typeMateriel = (materielfix.isSelected()) ? "Fixe" : "Mobile";
            int quantite = qunatite.getValue();

            // Créer un nouvel équipement
            Equipement equipement = new Equipement(reference, nom, typeMateriel, quantite, etatEquipement);

            // Appeler le service pour ajouter l'équipement
            serviceEquipement.ajouter(equipement);

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("L'équipement a été ajouté avec succès!");
            alert.show();
        } catch (SQLException e) {
            // En cas d'erreur SQL, afficher une alerte d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setContentText("Une erreur SQL est survenue : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void retourAjoutEqAction(ActionEvent event) {
        // Redirection vers une autre vue (retour)
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEquipement.fxml"));
            refeq.getScene().setRoot(root);
        } catch (IOException e) {
            // En cas d'erreur lors de la redirection, afficher une alerte d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de redirection");
            alert.setContentText("Une erreur est survenue lors de la redirection : " + e.getMessage());
            alert.show();
        }
    }
}

