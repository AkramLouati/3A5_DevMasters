package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceMuni;
import edu.esprit.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfEmail;

    @FXML
    private ComboBox<String> profilTypeComboBox;

    @FXML
    private ComboBox<String> muniSelectionComboBox;

    @FXML
    private PasswordField pfMotDePasse;

    @FXML
    private TextField tfNumTel;

    @FXML
    private TextField tfLocation;

    @FXML
    private Button loginButton;

    Muni muni;
    ServiceUser serviceUser = new ServiceUser();

    ServiceMuni serviceMuni = new ServiceMuni();

    @FXML
    void registerAction(ActionEvent event) {
        String nom = tfNom.getText();
        String email = tfEmail.getText();
        String motDePasse = pfMotDePasse.getText();
        String numTel = tfNumTel.getText();
        String location = tfLocation.getText();

        // Votre logique de traitement d'inscription ici
        // Vous pouvez appeler votre service pour enregistrer l'utilisateur, par exemple
        if (nom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }else {
            serviceUser.ajouter(new EndUser(nom,email,motDePasse,profilTypeComboBox.getValue(),numTel,muni,location,""));
            showAlert("Inscription réussie pour : " + nom);
        }
    }

    @FXML
    void profileType(ActionEvent event) {
        // Access the selected item in profilTypeComboBox
        String selectedProfileType = profilTypeComboBox.getValue();
        System.out.println("Selected Profile Type: " + selectedProfileType);
    }

    @FXML
    void muniSelection(ActionEvent event) {

        // Access the selected item in muniSelectionComboBox
        String selectedMuni = muniSelectionComboBox.getValue();
        System.out.println("Selected Muni: " + selectedMuni);
        muni = serviceMuni.getOneByName(selectedMuni.toString());
        System.out.println(muni);

    }

    @FXML
    void loginButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    private void showAlert(String message) {
        // Affiche une boîte de dialogue d'information avec le message donné
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> typesProfile = FXCollections.observableArrayList();
        ObservableList<String> muniSelection = FXCollections.observableArrayList();

        typesProfile.addAll("Citoyen", "Employé", "Responsable employé");
        muniSelection.addAll("Ariana Ville", "La Soukra", "Mnihla","Raoued","Ettadhamen","Sidi Thabet");

        // Définir les éléments du ComboBox en utilisant la liste observable
        profilTypeComboBox.setItems(typesProfile);
        muniSelectionComboBox.setItems(muniSelection);
    }
}
