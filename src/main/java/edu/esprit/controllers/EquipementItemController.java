package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class EquipementItemController {
    @FXML
    private Button avisButton;

    @FXML
    private Text categorieitemTF;

    @FXML
    private Text dateajoutitemTF;

    @FXML
    private Button deleteButton;

    @FXML
    private Text descriptionitemTA;

    @FXML
    private Button editButton;

    @FXML
    private ImageView imageViewaffiche;

    @FXML
    private Text nomitemTF;

    @FXML
    private Text quantiteitemTF;

    @FXML
    private Text referenceitemTF;

    @FXML
    void modifierEquipementAction(ActionEvent event) {
        try {
            System.out.println("Resource URL: " + getClass().getResource("/ModifierEquipementGui.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/ModifierEquipementGui.fxml"));
            editButton.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    @FXML
    void supprimerEquipementAction(ActionEvent event) {
        if (equipement != null) {
            ServiceEquipement serviceEquipement = new ServiceEquipement();
            serviceEquipement.supprimer(equipement.getId_equipement());

            // Afficher une alerte pour informer l'utilisateur que l'equipement a été supprimée avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("L'équipement a été supprimée avec succès.");
            alert.setTitle("Equipement supprimée");
            alert.show();

            // Rediriger l'utilisateur vers la vue précédente (par exemple, la liste des equipements)
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherEquipementGui.fxml"));
                editButton.getScene().setRoot(root);
            } catch (IOException e) {
                // Gérer l'exception si la redirection échoue
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Une erreur s'est produite lors de la redirection.");
                errorAlert.setTitle("Erreur de redirection");
                errorAlert.show();
            }
        } else {
            // Afficher un message d'erreur si l'equipement est null
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de supprimer l'equipement' car aucune équipement n'est sélectionnée.");
            errorAlert.setTitle("Erreur de suppression");
            errorAlert.show();
        }

    }
    @FXML
    void avisEquipementAction(ActionEvent event) {

    }
    private Equipement equipement;
    ServiceEquipement serviceEquipement = new ServiceEquipement();

    public void setData(Equipement equipement) {
        this.equipement = equipement;
        nomitemTF.setText(equipement.getNom_eq());
        referenceitemTF.setText(equipement.getReference_eq());
        quantiteitemTF.setText(String.valueOf(equipement.getQuantite_eq()));
        categorieitemTF.setText(equipement.getCategorie_eq());
        descriptionitemTA.setText(equipement.getDescription_eq());
        dateajoutitemTF.setText(String.valueOf(equipement.getDate_ajouteq()));
        String imageUrl = equipement.getImage_eq();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                // Créer une instance de File à partir du chemin d'accès à l'image
                File file = new File(imageUrl);
                // Convertir le chemin de fichier en URL
                String fileUrl = file.toURI().toURL().toString();
                // Créer une instance d'Image à partir de l'URL de fichier
                Image image = new Image(fileUrl);
                // Définir l'image dans l'ImageView
                imageViewaffiche.setImage(image);
            } catch (MalformedURLException e) {
                // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                e.printStackTrace();
            }
        }
    }
}
