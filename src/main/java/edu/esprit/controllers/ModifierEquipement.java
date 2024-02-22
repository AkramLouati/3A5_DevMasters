package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ModifierEquipement {
    @FXML
    private TextField descmod;

    @FXML
    private TextField etateqmod;

    @FXML
    private ImageView modifiereqimg;

    @FXML
    private TextField nomeqmod;

    @FXML
    private TextField quantiteeqmod;

    @FXML
    private TextField refeqmod;

    @FXML
    private TextField typeeqmod;

    @FXML
    private Button uploadimgmod;
    private Label label;
    private String imagePath;
    @FXML
    void modifierEquipementAction(ActionEvent event) {
        Equipement e = new Equipement();
        ServiceEquipement se =  new ServiceEquipement();
        if (e != null && se != null) {
            // Mettre à jour les données de l'equipement avec les valeurs des champs de texte
            e.setRef_eq(refeqmod.getText());
            e.setNom_eq(nomeqmod.getText());
            e.setTypeMateriel_eq(typeeqmod.getText());
            e.setEtat_eq(etateqmod.getText());
            e.setQuantite_eq(Integer.parseInt(quantiteeqmod.getText()));
            e.setDescription_eq(descmod.getText());


            try {
                // Appeler la méthode de modification du service de equipement
                se.modifier(e);

                // Afficher un message de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setContentText("Equipement modifiée avec succès !");
                successAlert.setTitle("Modification réussie");
                successAlert.show();
            } catch (Exception exception) {
                // Afficher un message d'erreur en cas d'échec de la modification
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Erreur lors de la modification de l'equipement' : " + exception.getMessage());
                errorAlert.setTitle("Erreur de modification");
                errorAlert.show();
            }
        } else {
            // Afficher un message d'erreur si l'equipement est null ou si le service d'equipement est null
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de modifier l'equipement car aucune equipement est sélectionnée ou le service d'équipement est null.");
            errorAlert.setTitle("Erreur de modification");
            errorAlert.show();
        }

    }

    @FXML
    void navigatetoAfficherEquipementAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEquipement.fxml"));
            refeqmod.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    @FXML
    void uploadImageModifierAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );
        Stage stage = (Stage) uploadimgmod.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            // Affiche le nom du fichier sélectionné
            label.setText(selectedFile.getName());

            // Récupère le chemin absolu du fichier
            String absolutePath = selectedFile.getAbsolutePath();
            // Stocke le chemin absolu dans la variable de classe
            imagePath = absolutePath;

            // Crée une URL à partir du chemin absolu du fichier
            String fileUrl = new File(absolutePath).toURI().toString();

            // Crée une image à partir de l'URL du fichier
            Image image = new Image(fileUrl);

            // Affiche l'image dans l'ImageView
            // uploadimgmod.setImage(image);

            // Mettre à jour le chemin d'accès à l'image dans la réclamation
            // if (equipement != null) {
            //     equipement.setImage_eq(imagePath);
            }
        }

    }

