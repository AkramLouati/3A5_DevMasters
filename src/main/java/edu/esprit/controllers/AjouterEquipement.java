package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import edu.esprit.services.ServicePersonne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
public class AjouterEquipement{
    @FXML
    private TextField refeq;

    @FXML
    private TextField nomeq;

    @FXML
    private TextField desc;

    @FXML
    private ComboBox<String> etat;

    @FXML
    private RadioButton materielfix;

    @FXML
    private RadioButton materielmobile;

    @FXML
    private ComboBox<Integer> qunatite;
    private final ServiceEquipement se = new ServiceEquipement();
    @FXML
    void ajouterEquipementAction(ActionEvent event) {
        // Récupération des valeurs des champs
        String reference = refeq.getText();
        String nom = nomeq.getText();
        String description = desc.getText();
        String etatEquipement = etat.getValue();
        int quantite = qunatite.getValue();
        String typeMateriel;

        // Vérification de l'état sélectionné
        if (materielfix.isSelected()) {
            typeMateriel = "Fixe";
        } else if (materielmobile.isSelected()) {
            typeMateriel = "Mobile";
        } else {
            typeMateriel = "";
        }

        // Vérification des champs obligatoires
        if (reference.isEmpty() || nom.isEmpty() || description.isEmpty() || etatEquipement == null || typeMateriel.isEmpty()) {
            showAlert("Veuillez remplir tous les champs !");
        } else {
            // Ajoutez votre logique pour ajouter l'équipement à la base de données ici
            // Par exemple :
            Equipement nouvelEquipement = new Equipement(reference, nom, typeMateriel, quantite, etatEquipement, description, idUser);
            ServiceEquipement.ajouter(nouvelEquipement);
            showAlert("Equipement ajouté avec succès !");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
