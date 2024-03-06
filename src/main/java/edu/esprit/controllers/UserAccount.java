package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Municipality;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;

public class UserAccount {

    @FXML
    private Label label;

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

    private static final String USER_PREF_KEY = "current_user";

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$");

    File selectedFile = null;

    ServiceUser serviceUser = new ServiceUser();

    private int currentUserId;

    @FXML
    void modifierUser(ActionEvent event) {
        currentUserId = Integer.parseInt(getCurrentUser());
        String nom = tfNom.getText();
        String email = tfEmail.getText();
        String password = pfMotDePasse.getText();
        String numTel = tfNumTel.getText();
        String addresse = tfAddresse.getText();
        String type = serviceUser.getOneByID(currentUserId).getType();
        Municipality muni = serviceUser.getOneByID(currentUserId).getMuni();

        if(nom.isEmpty() || email.isEmpty() || password.isEmpty() || numTel.isEmpty() || addresse.isEmpty() || selectedFile == null){
            showAlert("Veuillez remplir tous les champs!");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            showAlert("Veuillez entrer un email valid!");
        } else {
            String hashedPassword = hashPassword(password);
            serviceUser.modifier(new EndUser(currentUserId, email,nom,hashedPassword,type,numTel,muni,addresse,selectedFile.getAbsolutePath(),false));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("User Updated");
            alert.show();
        }


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

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert byte array to hexadecimal representation
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Handle the exception appropriately
            return null;
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String getCurrentUser() {
        Preferences preferences = Preferences.userNodeForPackage(Login.class);
        return preferences.get(USER_PREF_KEY, "DefaultUser");
    }
}
