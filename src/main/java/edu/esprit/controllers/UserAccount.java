package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class UserAccount {

    @FXML
    private Label label;

    @FXML
    private ImageView modifierimgView_reclamation;

    @FXML
    private ImageView imageF;

    @FXML
    private PasswordField pfMotDePasse;

    @FXML
    private TextField tfAddresse;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfNumTel;

    @FXML
    private Button uploadbutton_modifier;

    File selectedFile;

    ServiceUser serviceUser = new ServiceUser();

    private EndUser currentUser;

    public void setCurrentUser(EndUser user) {
        this.currentUser = user;
        // Now, you can use 'currentUser' to access the current user in this controller
    }

    @FXML
    void modifierUser(ActionEvent event) {
        String nom = tfNom.getText();
        String email = tfEmail.getText();
        String password = pfMotDePasse.getText();
        String numTel = tfNumTel.getText();
        String addresse = tfAddresse.getText();
        String type = serviceUser.getOneByID(currentUser.getId()).getType();
        Muni muni = serviceUser.getOneByID(currentUser.getId()).getMuni();
        serviceUser.modifier(new EndUser(currentUser.getId(), email,nom,password,type,numTel,muni,addresse,selectedFile.getAbsolutePath()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("User Updated");
        alert.show();

    }

    @FXML
    void navigate(ActionEvent event) {

    }

    @FXML
    void pickImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Handle the selected image file (e.g., display it, process it, etc.)
            System.out.println("Selected image: " + selectedFile.getAbsolutePath());
            Image image = new Image(selectedFile.toURI().toString());
            imageF.setImage(image);
        }
    }

}
