package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class EquipementItemController {
    @FXML
    private Text TFnomeuipement;

    @FXML
    private Text TFquantite_equipement;

    @FXML
    private Text TFreference_equipement;

    @FXML
    private ImageView imgView_reclamation;
    private Equipement equipement;
    ServiceEquipement serviceEquipement = new ServiceEquipement();

    public void setData(Equipement equipement){
        this.equipement = equipement;
        TFnomeuipement.setText(equipement.getNom_eq());
        TFreference_equipement.setText(equipement.getRef_eq());
        TFquantite_equipement.setText(String.valueOf(equipement.getQuantite_eq()));
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
                imgView_reclamation.setImage(image);
            } catch (MalformedURLException e) {
                // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                e.printStackTrace();
            }
        }

    }
}
