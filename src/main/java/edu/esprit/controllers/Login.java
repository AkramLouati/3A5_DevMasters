package edu.esprit.controllers;

import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Login {


    public Label clickableLabel;
    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pfMotDePasse;

    ServiceUser serviceUser = new ServiceUser();

    @FXML
    void handleLabelClick(MouseEvent event) {
        System.out.println("Label clicked!");
        // Add your label click logic here
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Register.fxml")));
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

    @FXML
    void loginButtonAction(ActionEvent event) {
        String email = tfEmail.getText();
        String password = pfMotDePasse.getText();
        // Perform your authentication logic here
        if (isValidCredentials(email, password)) {
            // Successful login, you can navigate to another screen or perform other actions
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/UserAccount.fxml")));
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
//            showAlert("Login Successful");
        } else {
            // Invalid credentials, show an alert
            showAlert();
        }
    }

    private boolean isValidCredentials(String email, String password) {
        // Replace this with your authentication logic (e.g., checking against a database)
        String email1 = serviceUser.authenticateUser(email,password).getEmail();
        String password1 = serviceUser.authenticateUser(email,password).getPassword();
        return email1.equals(email) && password1.equals(password);
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Status");
        alert.setHeaderText(null);
        alert.setContentText("Invalid username or password");
        alert.showAndWait();
    }
}
