package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.prefs.Preferences;

public class Login {

    private static final String USER_PREF_KEY = "current_user";
    public Hyperlink forgetPwd;


    @FXML
    private Button loginButtonAction;

    public Hyperlink clickableLabel;

    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pfMotDePasse;

    ServiceUser serviceUser = new ServiceUser();

    @FXML
    void handleLabelClick(ActionEvent event) {
        System.out.println("Label clicked!");
        // Add your label click logic here
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/RegisterUser.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("S'inscrire");
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
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Veuillez remplir tous les champs!");
        } else if (isValidEmail(email)) {
            if (isValidCredentials(email, password)) {
                // Successful login, you can navigate to another screen or perform other actions

                EndUser currentUser = serviceUser.authenticateUser(email, password);

                if (currentUser.getType().equals("Admin")) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserGui.fxml"));
                        Parent root = loader.load();

                        // Continue with your navigation code
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        if (stage != null) {
                            Scene scene = new Scene(root, 1310, 845);
                            stage.setTitle("Dashboard");
                            stage.setScene(scene);
                            stage.show();
                        } else {
                            System.out.println("Error: Stage is null");
                        }

                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Sorry");
                        alert.setTitle("Error");
                        alert.show();
                    }
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserAccount.fxml"));
                        Parent root = loader.load();

                        // Get the controller instance
                        UserAccount userAccountController = loader.getController();
                        // Pass the current user to the UserAccount controller
                        userAccountController.setCurrentUser(currentUser);

                        // Continue with your navigation code
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("User Account");
                        stage.show();
                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Sorry");
                        alert.setTitle("Error");
                        alert.show();
                    }
                }
//            showAlert("Login Successful");
            } else {
                // Invalid credentials, show an alert
                showAlert("Password invalid ");
            }
        }else{
            showAlert("Email est invalid!");
        }
    }

    private boolean isValidCredentials(String email, String password) {
        // Replace this with your authentication logic (e.g., checking against a database)
        EndUser user = serviceUser.authenticateUser(email,password);
        if (user != null){
            String email1 = user.getEmail();
            String password1 = user.getPassword();
            return email1.equals(email) && password1.equals(password);
        }else {
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        return serviceUser.getOneByEmail(email) != null;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setCurrentUser(int userId) {
        Preferences preferences = Preferences.userNodeForPackage(Login.class);
        preferences.put(USER_PREF_KEY, String.valueOf(userId));
        System.out.println("Current User saved: " + userId);
    }

    private String getCurrentUser() {
        Preferences preferences = Preferences.userNodeForPackage(Login.class);
        return preferences.get(USER_PREF_KEY, "DefaultUser");
    }

}
