package edu.esprit.controllers;

import edu.esprit.entities.Actualite;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import edu.esprit.entities.Publicite;
import edu.esprit.services.ServiceActualite;
import edu.esprit.services.ServicePublicite;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AjouterPubliciteController implements Initializable {

    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private VBox MainLeftSidebar;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private TextField TFcontactpub;

    @FXML
    private TextField TFdescriptionpub;

    @FXML
    private TextField TFlocalisationpub;

    @FXML
    private TextField TFtitrepub;

    @FXML
    private Button ajouterPubliciteAction;

    @FXML
    private BorderPane firstborderpane;

    @FXML
    private ImageView imgView_actualite;

    @FXML
    private ImageView imgView_pub;

    @FXML
    private Label labelPub;

    @FXML
    private ComboBox<String> offrePubCombo;

    @FXML
    private Button tolistActualite;

    @FXML
    private Button uploadbuttonP;

    private boolean isSidebarVisible = true;
    private final ServicePublicite sp = new ServicePublicite();
    private String imagePath;

    java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser la taille du SecondBorderPane avec la même largeur que la barre latérale
        double sidebarWidth = MainLeftSidebar.getWidth();
        SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() + sidebarWidth);

        // Add a listener to the text property of TFtitrepub
        TFtitrepub.textProperty().addListener((observable, oldValue, newValue) -> {
            // Validate the length of the text
            if (newValue.length() > 6) {
                // If length is greater than 6, set the background color to a light color
                TFtitrepub.setStyle("-fx-background-color: #ffffff;"); // Light green
            } else {
                // If length is 6 or less, set the background color to a light red color
                TFtitrepub.setStyle("-fx-background-color: #e83939;"); // Light pink
            }
        });

        // Add a listener to the text property of TFdescriptionpub
        TFdescriptionpub.textProperty().addListener((observable, oldValue, newValue) -> {
            // Validate the length of the text
            if (newValue.length() >= 15) {
                // If length is 15 or more, set the background color to a light color
                TFdescriptionpub.setStyle("-fx-background-color: #ffffff;"); // Light green
            } else {
                // If length is less than 15, set the background color to a light red color
                TFdescriptionpub.setStyle("-fx-background-color: #e83939;"); // Light pink
            }
        });

        // Add a listener to the text property of TFlocalisationpub
        TFlocalisationpub.textProperty().addListener((observable, oldValue, newValue) -> {
            // Validate the length of the text
            if (newValue.length() >= 3) {
                // If length is 2 or more, set the background color to a light color
                TFlocalisationpub.setStyle("-fx-background-color: rgba(0,169,71,0.98);"); // Light green
            } else {
                // If length is less than 2, set the background color to a light red color
                TFlocalisationpub.setStyle("-fx-background-color: #e83939;"); // Light pink
            }
        });
    }

    private boolean validateContact(String contactValue) {
        try {
            int contact = Integer.parseInt(contactValue);
            if (contact <= 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Veuillez saisir un numéro de contact valide.");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Veuillez saisir un numéro de contact valide.");
            return false;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void uploadimgP(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );
        Stage stage = (Stage) uploadbuttonP.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            // Affiche le nom du fichier sélectionné
            labelPub.setText(selectedFile.getName());

            // Récupère le chemin absolu du fichier
            String absolutePath = selectedFile.getAbsolutePath();
            // Stocke le chemin absolu dans la variable de classe
            imagePath = absolutePath;

            // Crée une URL à partir du chemin absolu du fichier
            String fileUrl = new File(absolutePath).toURI().toString();

            // Crée une image à partir de l'URL du fichier
            Image image = new Image(fileUrl);

            // Affiche l'image dans l'ImageView
            imgView_pub.setImage(image);
        }
    }

    @FXML
    public void tolistActualite(ActionEvent actionEvent) {
        try {
            System.out.println("Resource URL: " + getClass().getResource("/AfficherActualiteGui.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherActualiteGui.fxml"));
            TFtitrepub.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    @FXML
    public void BTNToggleSidebar(ActionEvent event) {
        TranslateTransition sideBarTransition = new TranslateTransition(Duration.millis(400), MainLeftSidebar);

        double sidebarWidth = MainLeftSidebar.getWidth();

        if (isSidebarVisible) {
            // Hide sidebar
            sideBarTransition.setByX(-sidebarWidth);
            isSidebarVisible = false;
            // Adjust the width of SecondBorderPane
            SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() + sidebarWidth);
            // Translate SecondBorderPane to the left to take the extra space
            TranslateTransition borderPaneTransition = new TranslateTransition(Duration.millis(400), SecondBorderPane);
            borderPaneTransition.setByX(-sidebarWidth);
            borderPaneTransition.play();
        } else {
            // Show sidebar
            sideBarTransition.setByX(sidebarWidth);
            isSidebarVisible = true;
            // Adjust the width of SecondBorderPane
            SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() - sidebarWidth);
            // Reset the translation of SecondBorderPane to 0
            TranslateTransition borderPaneTransition = new TranslateTransition(Duration.millis(250), SecondBorderPane);
            borderPaneTransition.setByX(sidebarWidth);
            borderPaneTransition.play();
        }

        sideBarTransition.play();
    }

    public void BTNGestionEvennement(ActionEvent actionEvent) {
    }

    public void BTNGestionUser(ActionEvent actionEvent) {
    }

    public void BTNGestionRec(ActionEvent actionEvent) {
    }

    public void BTNGestionAct(ActionEvent actionEvent) {
    }

    public void BTNGestionEquipement(ActionEvent actionEvent) {
    }

    public void BTNGestionTache(ActionEvent actionEvent) {
    }

    @FXML
    public void ajouterPubliciteAction(ActionEvent actionEvent) {
        Muni muni = new Muni(1);
        EndUser user = new EndUser(12, muni);
        Actualite actualite = new Actualite(87, user);

        // Check if an image is selected
        if (imagePath == null || !new File(imagePath).isFile()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Veuillez télécharger une image.");
            return;
        }

        // Validate contact
        if (!validateContact(TFcontactpub.getText())) {
            return;
        }

        // Validate offer
        if (offrePubCombo.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Veuillez sélectionner une offre.");
            return;
        }

        // Validate all fields are filled
        if (TFtitrepub.getText().isEmpty() || TFdescriptionpub.getText().isEmpty() ||
                TFlocalisationpub.getText().isEmpty() || TFcontactpub.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Veuillez remplir tous les champs.");
            return;
        }

        // Create and add the Publicite object
        sp.ajouter(new Publicite(
                TFtitrepub.getText(),
                TFdescriptionpub.getText(),
                Integer.parseInt(TFcontactpub.getText()),
                TFlocalisationpub.getText(),
                imagePath,
                offrePubCombo.getValue(),
                user,
                actualite
        ));

        // Display success message
        showAlert(Alert.AlertType.INFORMATION, "Publicité a été ajoutée", "Publicité ajoutée, merci pour votre confiance");
    }
}
