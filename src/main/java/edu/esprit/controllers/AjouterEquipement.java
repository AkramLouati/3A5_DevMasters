package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceEquipement;
import edu.esprit.services.ServiceUser;
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
import java.sql.SQLException;


public class AjouterEquipement {
    @FXML
    private Button ajouteq;

    @FXML
    private TextField desc;

    @FXML
    private TextField etateq;

    @FXML
    private ImageView img;

    @FXML
    private TextField nomeq;

    @FXML
    private TextField quantiteq;

    @FXML
    private TextField refeq;

    @FXML
    private TextField typeeq;

    @FXML
    private Button uploadimg;
    @FXML
    private Label imgurl;
    private final ServiceEquipement se = new ServiceEquipement();
    Muni muni = new Muni(1);
    EndUser user = new EndUser(1,muni);
    private String imagePath;
    private Label label;

    @FXML
    void ajouterEquipementAction(ActionEvent event) {
        // Utilisez imagePath pour enregistrer le chemin absolu de l'image dans la base de données
        se.ajouter(new Equipement(refeq.getText(), nomeq.getText(), imagePath, typeeq.getText(), Integer.parseInt(quantiteq.getText()), etateq.getText(),desc.getText(),user,muni));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Equipement a été ajoutée");
        alert.setContentText("GG");
        alert.show();

    }

    @FXML
    void navigatetoAfficherEquipementAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEquipement.fxml"));
            refeq.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    @FXML
    void uploadImageAction(ActionEvent event) {
        uploadimg.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png"));
            Stage stage = (Stage) uploadimg.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                // Affiche le nom du fichier sélectionné
                imgurl.setText(selectedFile.getName());

                // Récupère le chemin absolu du fichier
                String absolutePath = selectedFile.getAbsolutePath();
                // Stocke le chemin absolu dans la variable de classe
                imagePath = absolutePath;

                // Crée une URL à partir du chemin absolu du fichier
                String fileUrl = new File(absolutePath).toURI().toString();

                // Crée une image à partir de l'URL du fichier
                Image image = new Image(fileUrl);

                // Affiche l'image dans l'ImageView
                img.setImage(image);
            }
        });
    }

}

