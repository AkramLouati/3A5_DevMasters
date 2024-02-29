package edu.esprit.controllers;

import edu.esprit.entities.Actualite;
import edu.esprit.entities.Publicite;
import edu.esprit.services.ServicePublicite;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifierPubliciteController implements Initializable {

    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private VBox MainLeftSidebar;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private TextField TFcontactpubModif;

    @FXML
    private TextField TFdescriptionpubModif;

    @FXML
    private TextField TFlocalisationpubModif;

    @FXML
    private TextField TFtitrepubModif;

    @FXML
    private BorderPane firstborderpane;

    @FXML
    private ImageView imgView_pubModif;

    @FXML
    private ImageView imgView_pub;

    @FXML
    private Label labelPubModif;

    @FXML
    private Button modifierPubliciteAction;
    private boolean isSidebarVisible = true;
    @FXML
    private Label numeroexiste;

    @FXML
    private ComboBox<String> offrePubComboModif;

    @FXML
    private Button retourToAfficherPub;

    @FXML
    private Button uploadbuttonPModif;

    private ServicePublicite servicePublicite;
    private Publicite publicite;
    private String imagePath;


    @FXML
    void modifierPubliciteAction(ActionEvent event) {
        if (publicite != null && servicePublicite != null) {
        publicite.setTitre_pub(TFtitrepubModif.getText());
        publicite.setDescription_pub(TFdescriptionpubModif.getText());
            TFcontactpubModif.textProperty().addListener((observable, oldValue, newValue) -> {
                // Allow only numeric input
                if (!newValue.matches("\\d*")) {
                    // If non-numeric characters are entered, remove them
                    TFcontactpubModif.setText(newValue.replaceAll("[^\\d]", ""));
                }

                // Limit the length to 8 characters
                if (newValue.length() > 8) {
                    TFcontactpubModif.setText(newValue.substring(0, 8));
                }

                // Additional validation for exactly 8 numeric characters
                if (newValue.length() == 8 && !newValue.matches("\\d{8}")) {
                    // If exactly 8 characters are entered, but they are not all numeric, show an aler
                    // Clear the field
                    TFcontactpubModif.clear();
                }
            });
        publicite.setLocalisation_pub(TFlocalisationpubModif.getText());
        publicite.setOffre_pub(offrePubComboModif.getValue());
        publicite.setImage_pub(imagePath);

        try {
            servicePublicite.modifier(publicite);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setContentText("pub modifiée avec succès !");
            successAlert.setTitle("Modification réussie");
            successAlert.show();
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Erreur lors de la modification de la publicité : " + e.getMessage());
            errorAlert.setTitle("Erreur de modification");
            errorAlert.show();
        }}
else
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setContentText("Impossible de modifier l'actualité car aucune publicité n'est sélectionnée ");
        errorAlert.setTitle("Erreur de modification");
        errorAlert.show();
    }
}
    @FXML
    void uploadimgP(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );
        Stage stage = (Stage) uploadbuttonPModif.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            labelPubModif.setText(selectedFile.getName());
            String absolutePath = selectedFile.getAbsolutePath();
            imagePath = absolutePath;
            String fileUrl = new File(absolutePath).toURI().toString();
            Image image = new Image(fileUrl);
            imgView_pubModif.setImage(image);

            if (publicite != null) {
                publicite.setImage_pub(imagePath);
            }
        }
    }

    public void setData(Publicite publicite) {
        this.publicite = publicite;
        if (publicite != null) {
            TFtitrepubModif.setText(publicite.getTitre_pub());
            TFdescriptionpubModif.setText(publicite.getDescription_pub());

            // Assuming TFcontactpubModif is your TextField for contact
            TFcontactpubModif.setText(String.valueOf(publicite.getContact_pub()));

            String imageUrl = publicite.getImage_pub();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    File file = new File(imageUrl);
                    String fileUrl = file.toURI().toURL().toString();
                    Image image = new Image(fileUrl);
                    imgView_pubModif.setImage(image);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                URL defaultImageUrl = getClass().getResource("/edu/esprit/img/imageblanche.png");
                Image defaultImage = new Image(defaultImageUrl.toString());
                imgView_pubModif.setImage(defaultImage);
            }

            // Assuming offrePubComboModif is your ComboBox for offers
            offrePubComboModif.setValue(publicite.getOffre_pub());
        }
        TFlocalisationpubModif.setText(publicite.getLocalisation_pub());
    }


    public void setServicePublicite(ServicePublicite servicePublicite) {
        this.servicePublicite = servicePublicite;
    }

    @FXML
    void retourToAfficherPub(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPubliciteCitoyenGui.fxml"));
            Parent root = loader.load();

            // Update the scene to display the list of advertisements
            TFtitrepubModif.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Erreur lors du retour à la liste des publicités.");
        }
    }

    private void showErrorAlert(String errorMessage) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setContentText(errorMessage);
        errorAlert.setTitle("Erreur");
        errorAlert.show();
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser la taille du SecondBorderPane avec la même largeur que la barre latérale
        double sidebarWidth = MainLeftSidebar.getWidth();
        SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() + sidebarWidth);
    }
}
