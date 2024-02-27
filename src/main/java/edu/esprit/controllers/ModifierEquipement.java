package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

public class ModifierEquipement {
    @FXML
    private RadioButton categoriefixemodif;

    @FXML
    private RadioButton categoriemobilemodif;

    @FXML
    private DatePicker dateajoutmodif;

    @FXML
    private TextArea descriptionmodifTF;

    @FXML
    private Label imageequipementmodfi;

    @FXML
    private ImageView imagevieweqmodif;

    @FXML
    private Button modifiquipementbtn;

    @FXML
    private Button navigateequipementbtn;

    @FXML
    private TextField nommodifTF;

    @FXML
    private ComboBox<Integer> quantitemodifCB;

    @FXML
    private TextField referencemodifTF;

    @FXML
    private Button telechargerimagemodif;
    private final ServiceEquipement se = new ServiceEquipement();
    Muni muni = new Muni(1);
    EndUser user = new EndUser(1,muni);
    Equipement equipement = new Equipement(2);
    private String imagePath;
    private Label label;

    @FXML
    void modifierEquipementAction(ActionEvent event) {
        // Récupérer les valeurs des champs
        String reference = referencemodifTF.getText();
        String nom = nommodifTF.getText();
        String categorie = categoriefixemodif.isSelected() ? "Fixe" : "Mobile";
        String description = descriptionmodifTF.getText();
        int quantite = quantitemodifCB.getValue();
        String imagePath = this.imagePath; // Récupérer le chemin de l'image
        Date dateAjoutModif = Date.valueOf(dateajoutmodif.getValue());

        // Créer un nouvel équipement avec les valeurs des champs
        equipement = new Equipement(2,reference, nom, categorie, dateAjoutModif , quantite, imagePath, description, user, muni);

        // Appeler la méthode de modification du service d'équipement
        se.modifier(equipement);

        // Afficher un message de succès
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setContentText("Equipement modifié avec succès !");
        successAlert.setTitle("Modification réussie");
        successAlert.show();

    }

    @FXML
    void navigatetoAfficherEquipementAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEquipement.fxml"));
            referencemodifTF.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    @FXML
    void selectQuantiteModif(ActionEvent event) {
        Integer selectedQuantity = (Integer) quantitemodifCB.getSelectionModel().getSelectedItem();

    }
    public void initialize() {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        for (int i = 0; i <= 20; i++) {
            list.add(i);
        }
        quantitemodifCB.setItems(list);
    }

    @FXML
    void telechargerImageEquipemnt(ActionEvent event) {
        telechargerimagemodif.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png"));
            Stage stage = (Stage) telechargerimagemodif.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                // Affiche le nom du fichier sélectionné
                imageequipementmodfi.setText(selectedFile.getName());

                // Récupère le chemin absolu du fichier
                String absolutePath = selectedFile.getAbsolutePath();
                // Stocke le chemin absolu dans la variable de classe
                imagePath = absolutePath;

                // Crée une URL à partir du chemin absolu du fichier
                String fileUrl = new File(absolutePath).toURI().toString();

                // Crée une image à partir de l'URL du fichier
                Image image = new Image(fileUrl);

                // Affiche l'image dans l'ImageView
                imagevieweqmodif.setImage(image);
            }
        });

    }
}

