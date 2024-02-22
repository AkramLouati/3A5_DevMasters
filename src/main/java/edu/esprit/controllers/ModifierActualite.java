package edu.esprit.controllers;

import edu.esprit.entities.Actualite;
import edu.esprit.services.ServiceActualite;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class ModifierActualite {

    @FXML
    private TextField TFdescriptionModifier;

    @FXML
    private TextField TFtitreModifier;

    @FXML
    private ImageView imgView_actualiteModifier;

    @FXML
    private Label labelAModifier;

    @FXML
    private Button modifierActualiteAction;

    @FXML
    private Button uploadbuttonAModifier;
    private ServiceActualite serviceActualite;
    private Actualite actualite;
    private String imagePath;

    @FXML
    void modifierActualiteAction(ActionEvent event) {

        if (actualite != null && serviceActualite != null) {
            // Mettre à jour les données de la réclamation avec les valeurs des champs de texte
            actualite.setTitre_a(TFtitreModifier.getText());
            actualite.setDescription_a(TFdescriptionModifier.getText());
            actualite.setDate_a(java.sql.Date.valueOf(java.time.LocalDate.now()));

            //actualite.setImage_a(TFmodifieradresse_reclamation.getText());

            try {
                // Appeler la méthode de modification du service de réclamation
                serviceActualite.modifier(actualite);

                // Afficher un message de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setContentText("Actualité modifiée avec succès !");
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
            errorAlert.setContentText("Impossible de modifier l'actualité car aucune actualité n'est sélectionnée ");
            errorAlert.setTitle("Erreur de modification");
            errorAlert.show();
        }
    }

    @FXML
    void navigatetoAfficherActualiteAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherActualite.fxml"));
            TFtitreModifier.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    @FXML
    void uploadimgAModifer(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );
        Stage stage = (Stage) uploadbuttonAModifier.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            // Affiche le nom du fichier sélectionné
            labelAModifier.setText(selectedFile.getName());

            // Récupère le chemin absolu du fichier
            String absolutePath = selectedFile.getAbsolutePath();
            // Stocke le chemin absolu dans la variable de classe
            imagePath = absolutePath;

            // Crée une URL à partir du chemin absolu du fichier
            String fileUrl = new File(absolutePath).toURI().toString();

            // Crée une image à partir de l'URL du fichier
            Image image = new Image(fileUrl);

            // Affiche l'image dans l'ImageView
            imgView_actualiteModifier.setImage(image);

            // Mettre à jour le chemin d'accès à l'image dans la réclamation
            if (actualite != null) {
                actualite.setImage_a(imagePath);
            }
        }
    }
    public void setData(Actualite actualite) {
        this.actualite = actualite;
        if (actualite != null) {
            TFtitreModifier.setText(actualite.getTitre_a());
                TFdescriptionModifier.setText(actualite.getDescription_a());

            String imageUrl = actualite.getImage_a();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    // Créer une instance de File à partir du chemin d'accès à l'image
                    File file = new File(imageUrl);
                    // Convertir le chemin de fichier en URL
                    String fileUrl = file.toURI().toURL().toString();
                    // Créer une instance d'Image à partir de l'URL de fichier
                    Image image = new Image(fileUrl);
                    // Définir l'image dans l'ImageView
                    imgView_actualiteModifier.setImage(image);
                } catch (MalformedURLException e) {
                    // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                    e.printStackTrace();
                }
            } else {
                // Si l'URL de l'image est vide, vous pouvez définir une image par défaut
                // Par exemple, si vous avez une image "imageblanche.png" dans votre dossier src/main/resources
                // Vous pouvez utiliser getClass().getResource() pour obtenir son URL
                URL defaultImageUrl = getClass().getResource("/edu/esprit/img/imageblanche.png");
                Image defaultImage = new Image(defaultImageUrl.toString());
                imgView_actualiteModifier.setImage(defaultImage);
            }
        }
    }

    public void setServiceActualite(ServiceActualite serviceActualite) {
        this.serviceActualite = serviceActualite;
    }

}
