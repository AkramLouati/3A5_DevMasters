package edu.esprit.controllers;

import edu.esprit.entities.Avis;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

public class AjouterAvisGuiFront {
    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private Button ajouterButton;

    @FXML
    private TextArea commentaireeq;

    @FXML
    private DatePicker dateaviseq;

    @FXML
    private BorderPane firstborderpane;

    @FXML
    private ComboBox<Integer> noteeq;

    @FXML
    private Button retourButton;
    @FXML
    private Label commentaireErrorLabel;

    @FXML
    void BTNGestionAct(ActionEvent event) {

    }

    @FXML
    void BTNGestionEquipement(ActionEvent event) {

    }

    @FXML
    void BTNGestionEvennement(ActionEvent event) {

    }

    @FXML
    void BTNGestionRec(ActionEvent event) {

    }

    @FXML
    void BTNGestionTache(ActionEvent event) {

    }
    @FXML
    void selectQuantite(ActionEvent event) {
        Integer selectedQuantity = (Integer) noteeq.getSelectionModel().getSelectedItem();

    }
    @FXML
    public void initialize() {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        for (int i = 0; i <= 20; i++) {
            list.add(i);
        }
        noteeq.setItems(list);
    }
    private final ServiceAvis serviceAvis = new ServiceAvis();
    Muni muni = new Muni(1);
    EndUser user = new EndUser(1,muni);
    Equipement e = new Equipement(29);
    java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
    @FXML
    void ajouterAvisAction(ActionEvent event) {
        String commentaire_avis = commentaireeq.getText().trim();
        String[] mots = commentaire_avis.split("\\s+");

        if (mots.length > 50) {
            commentaireErrorLabel.setText("Le commentaire ne doit pas dépasser 50 mots.");
            commentaireErrorLabel.setVisible(true);
            return; // Arrête la méthode si le commentaire dépasse 50 mots
        } else {
            commentaireErrorLabel.setVisible(false);
        }
        try {
            // Récupérer les valeurs saisies par l'utilisateur
            int note_avis = Integer.parseInt(noteeq.getValue().toString());

            Date date_avis = Date.valueOf(dateaviseq.getValue()); // Convertissez la valeur du DatePicker en objet Date
            // Créer un nouvel objet Avis avec ces valeurs
            Avis nouvelAvis = new Avis(e,user, muni, note_avis, commentaireeq.getText(), date_avis);

            // Appeler la méthode de service appropriée pour ajouter cet avis
            serviceAvis.ajouter(nouvelAvis);

            // Afficher un message d'alerte en cas de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Avis ajouté avec succès !");
            alert.show();
        } catch (NumberFormatException e) {
            // Afficher un message d'alerte si la note saisie n'est pas un nombre valide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("La note doit être un nombre valide !");
            alert.showAndWait();
        }

    }

    @FXML
    void retourAvisAction(ActionEvent event) {
        try {
            System.out.println("Resource URL: " + getClass().getResource("/AfficherAvisGuiFront.fxml"));
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherAvisGuiFront.fxml")));
            retourButton.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }


}
