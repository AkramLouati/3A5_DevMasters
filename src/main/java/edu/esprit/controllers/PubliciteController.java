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
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PubliciteController {

    @FXML
    private Text TitrePubliciteAff;
    @FXML
    private Text DescriptionPubliciteAff;
    @FXML
    private Text contactPubliciteAff;
    @FXML
    private Text localisationPubliciteAff;
    @FXML
    private ImageView ImagePublicite;
    @FXML
    private Text offrePubliciteAff;

    private Publicite publicite;
    ServicePublicite servicePublicite = new ServicePublicite();

    Muni muni = new Muni(1);
    EndUser user = new EndUser(12,muni);
    Actualite actualite = new Actualite(102,user);


    public void setData(Publicite publicite) {
        this.publicite = publicite;
        TitrePubliciteAff.setText(publicite.getTitre_pub());
        DescriptionPubliciteAff.setText(publicite.getDescription_pub());
        contactPubliciteAff.setText(String.valueOf(publicite.getContact_pub()));
        localisationPubliciteAff.setText(publicite.getLocalisation_pub());
        offrePubliciteAff.setText(String.valueOf(publicite.getOffre_pub()));

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
                ImagePublicite.setImage(image);
            } catch (MalformedURLException e) {
                // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                e.printStackTrace();
            }
        }
    }


    public void deletePubliciteAction(ActionEvent actionEvent) {
        if (publicite != null) {
            ServicePublicite servicePublicite1 = new ServicePublicite();
            servicePublicite1.supprimer(publicite.getId_pub());

            // Display an alert to inform the user that the publicite has been successfully deleted
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La publicité a été supprimée avec succès.");
            alert.setTitle("Publicité supprimée");
            alert.show();

            // Redirect the user to the previous view (e.g., the list of publicites)
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherPubliciteGui.fxml"));
                TitrePubliciteAff.getScene().setRoot(root);
            } catch (IOException e) {
                // Handle the exception if the redirection fails
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Une erreur s'est produite lors de la redirection.");
                errorAlert.setTitle("Erreur de redirection");
                errorAlert.show();
            }
        } else {
            // Display an error message if the publicite is null
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de supprimer la publicité car aucune publicité n'est sélectionnée.");
            errorAlert.setTitle("Erreur de suppression");
            errorAlert.show();
        }
    }
    @FXML
    void modifierPubliciteAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPubliciteGui.fxml"));
            Parent root = loader.load();
            ModifierPubliciteController controller = loader.getController();
            controller.setServicePublicite(servicePublicite);
            controller.setData(publicite);
            TitrePubliciteAff.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }


    public void ValiderPubliciteAction(ActionEvent actionEvent) {
    }

    public void deletePubliciteActionC(ActionEvent actionEvent) {
        if (publicite != null) {
            ServicePublicite servicePublicite1 = new ServicePublicite();
            servicePublicite1.supprimer(publicite.getId_pub());

            // Display an alert to inform the user that the publicite has been successfully deleted
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La publicité a été supprimée avec succès.");
            alert.setTitle("Publicité supprimée");
            alert.show();

            // Redirect the user to the previous view (e.g., the list of publicites)
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherPubliciteCitoyenGui.fxml"));
                TitrePubliciteAff.getScene().setRoot(root);
            } catch (IOException e) {
                // Handle the exception if the redirection fails
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Une erreur s'est produite lors de la redirection.");
                errorAlert.setTitle("Erreur de redirection");
                errorAlert.show();
            }
        } else {
            // Display an error message if the publicite is null
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de supprimer la publicité car aucune publicité n'est sélectionnée.");
            errorAlert.setTitle("Erreur de suppression");
            errorAlert.show();
        }
    }
}
