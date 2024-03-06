package edu.esprit.controllers;
import edu.esprit.utils.OneSignalNotificationSender;
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
    private Button rendreButton;

    @FXML
    void utiliserEquipementAction(ActionEvent event) {
        // Vérifier si la quantité d'équipement est supérieure à zéro
        if (equipement.getQuantite_eq() > 0) {
            // Réduire la quantité d'équipement disponible
            int nouvelleQuantite = equipement.getQuantite_eq() - 1;
            // Mettre à jour la quantité dans la base de données
            equipement.setQuantite_eq(nouvelleQuantite);
            serviceEquipement.modifier(equipement); // Mettre à jour la quantité dans la base de données
            // Mettre à jour l'affichage de la quantité
            quantiteitemTFF.setText(String.valueOf(nouvelleQuantite));
            // Vérifier si la quantité est devenue zéro
            if (nouvelleQuantite == 0) {
                // Envoyer une notification à l'administrateur
                OneSignalNotificationSender.envoyerNotificationAdmin("Stock en rupture pour l'équipement : " + equipement.getNom_eq());
                // Afficher une notification à l'administrateur
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Stock en rupture pour l'équipement : " + equipement.getNom_eq());
                alert.setTitle("Stock épuisé");
                alert.show();
            }
        } else {
            // Afficher une notification si la quantité est déjà nulle
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Stock déjà épuisé pour l'équipement : " + equipement.getNom_eq());
            alert.setTitle("Stock épuisé");
            alert.show();
        }
    }
    @FXML
    void rendreEquipementAction(ActionEvent event) {
        // Incrémenter la quantité d'équipement disponible
        int nouvelleQuantite = equipement.getQuantite_eq() + 1;
        // Mettre à jour la quantité dans la base de données
        equipement.setQuantite_eq(nouvelleQuantite);
        serviceEquipement.modifier(equipement); // Mettre à jour la quantité dans la base de données
        // Mettre à jour l'affichage de la quantité
        quantiteitemTFF.setText(String.valueOf(nouvelleQuantite));
        // Afficher une notification à l'administrateur
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("L'équipement " + equipement.getNom_eq() + " a été rendu avec succès.");
        alert.setTitle("Équipement rendu");
        alert.show();
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
    @FXML
    void avisFEquipementAction(ActionEvent event) {
        if (equipement != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAvisGuiFront.fxml"));
                Parent root = loader.load();
                AfficherAvisGuiFront controller = loader.getController();
                controller.setServiceEquipement(serviceEquipement);
                controller.setData(equipement); // Set the data before loading the controller
                nomitemTFF.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Sorry");
                alert.setTitle("Error");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("L'objet Equipement passé en argument est null.");
            alert.setTitle("Error");
            alert.show();
        }
    }


}

