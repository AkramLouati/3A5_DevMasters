package edu.esprit.controllers;


import edu.esprit.entities.Actualite;
import edu.esprit.entities.Publicite;
import edu.esprit.services.ServiceActualite;
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

public class ActualiteController {

    @FXML
    private Text DateActualiteAff;
    @FXML
    private ImageView ImageActualite;

    @FXML
    private Text DescriptionActualiteAff;

    @FXML
    private Text TitreActualiteAff;

    private Actualite actualite;
    ServiceActualite serviceActualite = new ServiceActualite();

    public void setData(Actualite actualite) {
        this.actualite = actualite;
        TitreActualiteAff.setText(actualite.getTitre_a());
        DescriptionActualiteAff.setText(String.valueOf(actualite.getDescription_a()));
        DateActualiteAff.setText(String.valueOf(actualite.getDate_a()));
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
                ImageActualite.setImage(image);
            } catch (MalformedURLException e) {
                // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                e.printStackTrace();
            }
        }
    }


    public void deleteActualiteAction(ActionEvent actionEvent) {
        if (actualite != null) {
            ServiceActualite serviceActualite1 = new ServiceActualite();
            serviceActualite1.supprimer(actualite.getId_a());

            // Afficher une alerte pour informer l'utilisateur que la réclamation a été supprimée avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(" actualité a été supprimée avec succès.");
            alert.setTitle("actualité supprimée");
            alert.show();

            // Rediriger l'utilisateur vers la vue précédente (par exemple, la liste des réclamations)
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherActualiteGui.fxml"));
                TitreActualiteAff.getScene().setRoot(root);
            } catch (IOException e) {
                // Gérer l'exception si la redirection échoue
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Une erreur s'est produite lors de la redirection.");
                errorAlert.setTitle("Erreur de redirection");
                errorAlert.show();
            }
        } else {
            // Afficher un message d'erreur si la réclamation est null
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de supprimer l'actualité car aucune réclamation n'est sélectionnée.");
            errorAlert.setTitle("Erreur de suppression");
            errorAlert.show();
        }
    }

    public void modifierActualiteAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierActualite.fxml"));
            Parent root = loader.load();
            ModifierActualite controller = loader.getController();
            controller.setServiceActualite(serviceActualite);
            controller.setData(actualite);
            TitreActualiteAff.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    public void viewDetailActualiteAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPublicite.fxml"));
            Parent root = loader.load();

            // Passer l'id de l'actualité à AfficherPubliciteController
            AfficherPubliciteController controller = loader.getController();
            controller.setActualiteId(actualite.getId_a());

            TitreActualiteAff.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }


}


