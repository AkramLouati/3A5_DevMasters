package edu.esprit.controllers;

import edu.esprit.entities.Avis;
import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServiceEquipement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;

public class AvisItemGuiFront {
    @FXML
    private Text commentaireequipement;

    @FXML
    private Text dateequipement;

    @FXML
    private Button modifierAvis;

    @FXML
    private Text noteequipement;

    @FXML
    private Button supprimerAvis;

    @FXML
    void modifierAvisAction(ActionEvent event) {

    }

    @FXML
    void supprimerAvisAction(ActionEvent event) {

    }
    private Avis avis;
    ServiceAvis serviceAvis= new ServiceAvis();
    public void setData(Avis avis) {
        this.avis = avis;
        commentaireequipement.setText(avis.getCommentaire_avis());
        noteequipement.setText(String.valueOf(avis.getNote_avis()));
        dateequipement.setText(String.valueOf(avis.getDate_avis()));
    }
}
