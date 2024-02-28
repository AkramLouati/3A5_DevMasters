package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class EquipementItemGuiFront {
    @FXML
    private Button avisFButton;

    @FXML
    private Text categorieitemTFF;

    @FXML
    private Text dateajoutitemTFF;

    @FXML
    private Text descriptionitemTAF;

    @FXML
    private ImageView imageViewaffiche;

    @FXML
    private Text nomitemTFF;

    @FXML
    private Text quantiteitemTFF;

    @FXML
    private Text referenceitemTFF;

    @FXML
    private Button useButton;

    @FXML
    void avisFEquipementAction(ActionEvent event) {
        try {
            System.out.println("Resource URL: " + getClass().getResource("/AfficherAvisGuiFront.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherAvisGuiFront.fxml"));
            avisFButton.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    @FXML
    void utiliserEquipementAction(ActionEvent event) {

    }
    private Equipement equipement;
    ServiceEquipement serviceEquipement = new ServiceEquipement();
    public void setData(Equipement equipement) {
        this.equipement = equipement;
        nomitemTFF.setText(equipement.getNom_eq());
        referenceitemTFF.setText(equipement.getReference_eq());
        quantiteitemTFF.setText(String.valueOf(equipement.getQuantite_eq()));
        categorieitemTFF.setText(equipement.getCategorie_eq());
        descriptionitemTAF.setText(equipement.getDescription_eq());
        dateajoutitemTFF.setText(String.valueOf(equipement.getDate_ajouteq()));
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
