package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
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
import java.net.MalformedURLException;
import java.net.URL;

public class ModifierReclamationController{
    @FXML
    private TextField TFmodifieradresse_reclamation;

    @FXML
    private TextField TFmodifiersujet_reclamation;

    @FXML
    private TextArea TmodifierAdescription_reclamation;

    @FXML
    private ImageView modifierimgView_reclamation;

    @FXML
    private Label label;

    @FXML
    private ComboBox<String> modifiertypeReclamationComboBox;

    @FXML
    private Button uploadbutton_modifier;
    private ServiceReclamation serviceReclamation;
    private Reclamation reclamation;
    private String imagePath;



    @FXML
    void modifierReclamationAction(ActionEvent event) {
        if (reclamation != null && serviceReclamation != null) {
            // Mettre à jour les données de la réclamation avec les valeurs des champs de texte
            reclamation.setSujet_reclamation(TFmodifiersujet_reclamation.getText());
            reclamation.setType_reclamation(modifiertypeReclamationComboBox.getValue());
            reclamation.setDescription_reclamation(TmodifierAdescription_reclamation.getText());
            reclamation.setAdresse_reclamation(TFmodifieradresse_reclamation.getText());
            reclamation.setImage_reclamation(imagePath); // imagePath peut être nul si aucune image n'est sélectionnée

            try {
                // Appeler la méthode de modification du service de réclamation
                serviceReclamation.modifier(reclamation);

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


    @FXML
    void navigatetoAfficherReclamationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherReclamation.fxml"));
            TFmodifiersujet_reclamation.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    @FXML
    void uploadimgmodifier(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );
        Stage stage = (Stage) uploadbutton_modifier.getScene().getWindow();
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
            modifierimgView_reclamation.setImage(image);

            // Mettre à jour le chemin d'accès à l'image dans la réclamation
            if (reclamation != null) {
                reclamation.setImage_reclamation(imagePath);
            }
        }
    }

    // Méthode pour initialiser les champs avec les données de la réclamation
    public void setData(Reclamation reclamation) {
        this.reclamation = reclamation;
        if (reclamation != null) {
            TFmodifiersujet_reclamation.setText(reclamation.getSujet_reclamation());
            modifiertypeReclamationComboBox.setValue(reclamation.getType_reclamation());
            TmodifierAdescription_reclamation.setText(reclamation.getDescription_reclamation());
            TFmodifieradresse_reclamation.setText(reclamation.getAdresse_reclamation());
            String imageUrl = reclamation.getImage_reclamation();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    // Créer une instance de File à partir du chemin d'accès à l'image
                    File file = new File(imageUrl);
                    // Convertir le chemin de fichier en URL
                    String fileUrl = file.toURI().toURL().toString();
                    // Créer une instance d'Image à partir de l'URL de fichier
                    Image image = new Image(fileUrl);
                    // Définir l'image dans l'ImageView
                    modifierimgView_reclamation.setImage(image);
                } catch (MalformedURLException e) {
                    // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                    e.printStackTrace();
                }
            } else {
                // Si l'URL de l'image est vide, vous pouvez définir une image par défaut
                // Par exemple, si vous avez une image "imageblanche.png" dans votre dossier src/main/resources
                // Vous pouvez utiliser getClass().getResource() pour obtenir son URL
                URL defaultImageUrl = getClass().getResource("/assets/imageblanche.png");
                if (defaultImageUrl != null) { // Vérifier que defaultImageUrl n'est pas nul
                    Image defaultImage = new Image(defaultImageUrl.toString());
                    modifierimgView_reclamation.setImage(defaultImage);
                } else {
                    System.err.println("L'image par défaut n'a pas pu être chargée.");
                }
            }
        }
    }

    public void setServiceReclamation(ServiceReclamation serviceReclamation) {
        this.serviceReclamation = serviceReclamation;
    }

}
