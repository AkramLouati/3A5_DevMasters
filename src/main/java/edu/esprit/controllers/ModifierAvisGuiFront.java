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
import java.util.Optional;

public class ModifierAvisGuiFront {
    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private TextArea commentaireeqmodif;

    @FXML
    private DatePicker dateaviseqmodif;

    @FXML
    private BorderPane firstborderpane;

    @FXML
    private Button modifierButton;

    @FXML
    private ComboBox<Integer> noteeqmodif;

    @FXML
    private Button retourButton;
    Muni muni = new Muni(1);
    EndUser user = new EndUser(1,muni);
    private ServiceEquipement se;
    private Equipement equipement;
    private ServiceAvis sa;
    private Avis avis;

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
    void selectNote(ActionEvent event) {
        Integer selectedQuantity = (Integer) noteeqmodif.getSelectionModel().getSelectedItem();
    }
    public void initialize() {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        for (int i = 0; i <= 20; i++) {
            list.add(i);
        }
        noteeqmodif.setItems(list);
    }
    @FXML
    void modifierAvisAction(ActionEvent event) {
        if (avis != null && sa!= null) {
            // Créer une boîte de dialogue de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier cet avis ?");
            confirmationAlert.setTitle("Confirmation de modification");

            // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            // Vérifier si l'utilisateur a cliqué sur le bouton OK
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Mettre à jour les données de l'avis  avec les valeurs des champs de texte
                Date dateAvisModif = Date.valueOf(dateaviseqmodif.getValue());
                avis.setNote_avis(noteeqmodif.getValue());
                avis.setCommentaire_avis(commentaireeqmodif.getText());
                avis.setDate_avis(dateAvisModif);
                try {
                    // Appeler la méthode de modification du service avis
                    sa.modifier(avis);

                    // Afficher un message de succès
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setContentText("avis modifiée avec succès !");
                    successAlert.setTitle("Modification réussie");
                    successAlert.show();

                } catch (Exception e) {
                    // Afficher un message d'erreur en cas d'échec de la modification
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setContentText("Erreur lors de la modification de l'avis': " + e.getMessage());
                    errorAlert.setTitle("Erreur de modification");
                    errorAlert.show();
                }
            }
        } else {
            // Afficher un message d'erreur si l'avis est null ou si le service avis est null
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de modifier l'avis car aucune avis n'est pas sélectionnée ou le service avis est null.");
            errorAlert.setTitle("Erreur de modification");
            errorAlert.show();
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

