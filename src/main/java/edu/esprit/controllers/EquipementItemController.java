package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class EquipementItemController {
    @FXML
    private Text categorieeq;

    @FXML
    private Text dateajouteq;

    @FXML
    private Button deleteButton;

    @FXML
    private Text descriptioneq;

    @FXML
    private Button editButton;

    @FXML
    private ImageView imageViewaffiche;

    @FXML
    private Text nomeq;

    @FXML
    private Text quantiteeq;

    @FXML
    private Text referenceeq;

    @FXML
    void modifierEquipementAction(ActionEvent event) {

    }

    @FXML
    void supprimerEquipementAction(ActionEvent event) {

    }
    private Equipement equipement;
    ServiceEquipement serviceEquipement = new ServiceEquipement();

    public void setData(Equipement equipement) {
        this.equipement = equipement;
        nomeq.setText(equipement.getNom_eq());
        referenceeq.setText(equipement.getReference_eq());
        quantiteeq.setText(String.valueOf(equipement.getQuantite_eq()));
        categorieeq.setText(equipement.getCategorie_eq());
        descriptioneq.setText(equipement.getDescription_eq());
        dateajouteq.setText(String.valueOf(equipement.getDate_ajouteq()));
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
