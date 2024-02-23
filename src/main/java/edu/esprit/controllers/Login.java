package edu.esprit.controllers;

import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.*;

public class Login {


    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pfMotDePasse;

    ServiceUser serviceUser = new ServiceUser();

    @FXML
    void loginButtonAction(ActionEvent event) {
        String email = tfEmail.getText();
        String password = pfMotDePasse.getText();
        // Perform your authentication logic here
        if (isValidCredentials(email, password)) {
            // Successful login, you can navigate to another screen or perform other actions
            showAlert("Login Successful");
        } else {
            // Invalid credentials, show an alert
            showAlert("Invalid username or password");
        }
    }

    private boolean isValidCredentials(String email, String password) {
        // Replace this with your authentication logic (e.g., checking against a database)
        String email1 = serviceUser.authenticateUser(email,password).getEmail();
        String password1 = serviceUser.authenticateUser(email,password).getPassword();
        return email1.equals(email) && password1.equals(password);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
