package edu.esprit.controllers;

import com.google.zxing.WriterException;
import edu.esprit.controllers.user.Login;
import edu.esprit.entities.EndUser;
import edu.esprit.services.ServiceUser;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import static edu.esprit.api.Qrcode.displayQRCode;
import static edu.esprit.api.Qrcode.generateQRCode;

public class MainGuiController {

    @FXML
    private Button BTNGestionAct;
    @FXML
    private BorderPane MainAnchorPaneBaladity;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private VBox MainLeftSidebar;

    private static final String USER_PREF_KEY = "current_user";

    ServiceUser serviceUser = new ServiceUser();

    int userId = Integer.parseInt(getCurrentUser());

    EndUser endUser = serviceUser.getOneByID(userId);

    private boolean isSidebarVisible = true;

    @FXML
    void BTNToggleSidebar(ActionEvent event) {
        TranslateTransition sideBarTransition = new TranslateTransition(Duration.millis(200), MainLeftSidebar);

        double sidebarWidth = 308;
        double hiddenWidth = 0; // Width when sidebar is hidden

        if (isSidebarVisible) {
            // Hide sidebar
            sideBarTransition.setToX(-sidebarWidth);
            isSidebarVisible = false;
            // Set the width of the MainLeftSidebar when sidebar is hidden
            MainLeftSidebar.setPrefWidth(hiddenWidth);
            // Set the width of the SecondBorderPane when sidebar is hidden
            SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() + sidebarWidth);
        } else {
            // Show sidebar
            sideBarTransition.setToX(0);
            isSidebarVisible = true;
            // Set the width of the MainLeftSidebar back to its original value
            MainLeftSidebar.setPrefWidth(MainLeftSidebar.getWidth() + sidebarWidth);
            // Set the width of the SecondBorderPane back to its original value
            SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() - sidebarWidth);
        }

        sideBarTransition.play();
    }


    public void BTNGestionEvennement(ActionEvent actionEvent) {

    }

    public void BTNGestionUser(ActionEvent actionEvent) {
    }

    public void BTNGestionRec(ActionEvent actionEvent) {
    }

    public void BTNGestionAct(ActionEvent actionEvent) {
        try {
            if(endUser.getType().equals("Citoyen") || endUser.getType().equals("Employé")|| endUser.getType().equals("Directeur")){
                    System.out.println("Resource URL: " + getClass().getResource("/ActualiteGui/AfficherActualiteCitoyenGui.fxml"));
                Parent root = FXMLLoader.load(getClass().getResource("/ActualiteGui/AfficherActualiteCitoyenGui.fxml"));
                BTNGestionAct.getScene().setRoot(root);
            } else if (endUser.getType().equals("Responsable employé")) {
                System.out.println("Resource URL: " + getClass().getResource("/ActualiteGui/AfficherActualiteGui.fxml"));
                Parent root = FXMLLoader.load(getClass().getResource("/ActualiteGui/AfficherActualiteGui.fxml"));
                BTNGestionAct.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    public void BTNGestionEquipement(ActionEvent actionEvent) {
    }

    public void BTNGestionTache(ActionEvent actionEvent) {

    }
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void BTNGestionReclamation(ActionEvent actionEvent) {
        try {
            if(endUser.getType().equals("Citoyen") || endUser.getType().equals("Employé")|| endUser.getType().equals("Responsable employé")){
                System.out.println("Resource URL: " + getClass().getResource("/reclamationGui/ReclamationGui.fxml"));
                Parent root = FXMLLoader.load(getClass().getResource("/reclamationGui/ReclamationGui.fxml"));
                BTNGestionAct.getScene().setRoot(root);
            } else if (endUser.getType().equals("Directeur")) {
                System.out.println("Resource URL: " + getClass().getResource("/reclamationGui/DirecteurReclamationGui.fxml"));
                Parent root = FXMLLoader.load(getClass().getResource("/reclamationGui/DirecteurReclamationGui.fxml"));
                BTNGestionAct.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    private String getCurrentUser() {
        Preferences preferences = Preferences.userNodeForPackage(Login.class);
        return preferences.get(USER_PREF_KEY, "DefaultUser");
    }
    @FXML
    void chatbotAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ChatbotGui.fxml"));
            MainAnchorPaneBaladity.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    public void BTNGestionEquipements(ActionEvent actionEvent) {
        try {
            if( endUser.getType().equals("Responsable employé")|| endUser.getType().equals("Directeur")){
                System.out.println("Resource URL: " + getClass().getResource("/equipementGui/AfficherEquipementGui.fxml"));
                Parent root = FXMLLoader.load(getClass().getResource("/equipementGui/AfficherEquipementGui.fxml"));
                BTNGestionAct.getScene().setRoot(root);
            } else if (endUser.getType().equals("Employé")) {
                System.out.println("Resource URL: " + getClass().getResource("/equipementGui/AfficherEquipementGuiFront.fxml"));
                Parent root = FXMLLoader.load(getClass().getResource("/equipementGui/AfficherEquipementGuiFront.fxml"));
                BTNGestionAct.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
}

    public void BTNGestionEvent(ActionEvent actionEvent) {
        try {
            if( endUser.getType().equals("Responsable employé")){
                System.out.println("Resource URL: " + getClass().getResource("/evenementGui/EventDashboard.fxml"));
                Parent root = FXMLLoader.load(getClass().getResource("/evenementGui/EventDashboard.fxml"));
                BTNGestionAct.getScene().setRoot(root);
            } else if (endUser.getType().equals("Citoyen")||endUser.getType().equals("Directeur")||endUser.getType().equals("Employé")) {
                System.out.println("Resource URL: " + getClass().getResource("/evenementGui/EventShowFront.fxml"));
                Parent root = FXMLLoader.load(getClass().getResource("/evenementGui/EventShowFront.fxml"));
                BTNGestionAct.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    public void scan(ActionEvent actionEvent) {
         // Remplacez par le chemin de votre image
        String data = "https://me-qr.com/link-list/dppVOGFb/show";  // Your data to be encoded in the QR code

        try {
            displayQRCode(generateQRCode(data));
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        String imagePath = "qrcode.png";
        try {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());

                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.setTitle("Image");

                ImageView imageView = new ImageView(image);

                AnchorPane root = new AnchorPane();
                root.getChildren().add(imageView);

                Scene scene = new Scene(root);
                popupStage.setScene(scene);

                popupStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Le fichier image spécifié n'existe pas !");
                alert.setTitle("Erreur");
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors du chargement de l'image !");
            alert.setTitle("Erreur");
            alert.show();
        }
    }
}
