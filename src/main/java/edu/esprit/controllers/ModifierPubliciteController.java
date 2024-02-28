package edu.esprit.controllers;

import edu.esprit.entities.Actualite;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import edu.esprit.entities.Publicite;
import edu.esprit.services.ServiceActualite;
import edu.esprit.services.ServicePublicite;
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

public class ModifierPubliciteController {

    @FXML
    private TextField TFcontactpubModif;

    @FXML
    private TextField TFdescriptionpubModif;

    @FXML
    private TextField TFlocalisationpubModif;

    @FXML
    private TextField TFtitrepubModif;

    @FXML
    private ImageView imgView_pubModif;

    @FXML
    private Label labelPubModif;

    @FXML
    private Button modifierPubliciteAction;

    @FXML
    private ComboBox<String> offrePubComboModif;

    @FXML
    private Button uploadbuttonPModif;
    private ServicePublicite servicePublicite;
    private Publicite publicite;
    private String imagePath;
    Muni muni = new Muni(1);
    EndUser user = new EndUser(12,muni);
    Actualite actualite = new Actualite(68,user);

    @FXML
    void modifierPubliciteAction(ActionEvent event) {
        if (publicite != null && servicePublicite != null) {
            // Mettre à jour les données de la réclamation avec les valeurs des champs de texte
                publicite.setTitre_pub(TFtitrepubModif.getText());
                publicite.setDescription_pub(TFdescriptionpubModif.getText());
                publicite.setTitre_pub(TFtitrepubModif.getText());
                publicite.setDescription_pub(TFdescriptionpubModif.getText());

// Assuming offrePubComboModif is a ComboBox<String>
            publicite.setOffre_pub(offrePubComboModif.getValue());

// Assuming TFcontactpubModif is a TextField
            try {
                // Attempt to parse the text from the TextField as an integer
                int contact = Integer.parseInt(TFcontactpubModif.getText());
                publicite.setContact_pub(contact);
            } catch (NumberFormatException e) {
                // Handle the case where the text is not a valid integer
                // You might want to show an error message to the user
                e.printStackTrace(); // Print for debugging, you can replace this with appropriate error handling
            }

            publicite.setLocalisation_pub(TFlocalisationpubModif.getText());

            try {
                // Appeler la méthode de modification du service de réclamation
                servicePublicite.modifier(publicite);

                // Afficher un message de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setContentText("Publicité modifiée avec succès !");
                successAlert.setTitle("Modification réussie");
                successAlert.show();
            } catch (Exception e) {

                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Erreur lors de la modification de la publicité : " + e.getMessage());
                errorAlert.setTitle("Erreur de modification");
                errorAlert.show();
            }
        } else {
            // Afficher un message d'erreur si la réclamation est null ou si le service de réclamation est null
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de modifier la publicité car aucune publicité n'est sélectionnée ");
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
            // Affiche le nom du fichier sélectionné
            labelPubModif.setText(selectedFile.getName());

            // Récupère le chemin absolu du fichier
            String absolutePath = selectedFile.getAbsolutePath();
            // Stocke le chemin absolu dans la variable de classe
            imagePath = absolutePath;

            // Crée une URL à partir du chemin absolu du fichier
            String fileUrl = new File(absolutePath).toURI().toString();

            // Crée une image à partir de l'URL du fichier
            Image image = new Image(fileUrl);

            // Affiche l'image dans l'ImageView
            imgView_pubModif.setImage(image);

            // Mettre à jour le chemin d'accès à l'image dans la réclamation
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

            // Assuming publicite.getContact_pub() returns an int
            TFcontactpubModif.setText(String.valueOf(publicite.getContact_pub()));

            TFlocalisationpubModif.setText(publicite.getLocalisation_pub());

            // Assuming offrePubComboModif is a ComboBox<String>
            offrePubComboModif.setValue(publicite.getOffre_pub());

            String imageUrl = publicite.getImage_pub();
        }

        String imageUrl = publicite.getImage_pub();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                // Créer une instance de File à partir du chemin d'accès à l'image
                File file = new File(imageUrl);
                // Convertir le chemin de fichier en URL
                String fileUrl = file.toURI().toURL().toString();
                // Créer une instance d'Image à partir de l'URL de fichier
                Image image = new Image(fileUrl);
                // Définir l'image dans l'ImageView
                imgView_pubModif.setImage(image);
            } catch (MalformedURLException e) {
                // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                e.printStackTrace();
            }
        }
    }


    public void setServicePublicite(ServicePublicite servicePublicite) {
        this.servicePublicite = servicePublicite;
    }

    public void retourToAfficherPub(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPublicite.fxml"));
            Parent root = loader.load();

            // Si nécessaire, vous pouvez effectuer d'autres opérations ou envoyer des données au contrôleur ici

            // Mettre à jour la scène pour afficher la liste des publicités
            TFtitrepubModif.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }

}

