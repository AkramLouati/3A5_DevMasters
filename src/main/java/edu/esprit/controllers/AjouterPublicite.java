package edu.esprit.controllers;
import com.sun.javafx.charts.Legend;
import edu.esprit.entities.Actualite;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import edu.esprit.entities.Publicite;
import edu.esprit.services.ServiceActualite;
import edu.esprit.services.ServicePublicite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.io.IOException;
import java.sql.SQLException;
public class AjouterPublicite {
    @FXML
    private TextField TFtitrepub;
    @FXML
    private TextField TFdescriptionpub;
    @FXML
    private TextField TFlocalisationpub;
    @FXML
    private TextField TFcontactpub;
    @FXML
    private ImageView imgView_pub;

    @FXML
    private ComboBox<String> offrePubCombo;

    @FXML
    private Label labelPub;
    @FXML
    private Button uploadbuttonP;
    private final ServicePublicite sp = new ServicePublicite();
    private String imagePath;

    java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
    @FXML
    void ajouterActualiteAction(ActionEvent event) {
        Muni muni = new Muni(1);
        EndUser user = new EndUser(1, muni);
        Actualite actualite = new Actualite(21, muni);

        try {
            // Parse the contact information to an integer
            int contact = Integer.parseInt(TFcontactpub.getText());

            // Create and add the Publicite object
            sp.ajouter(new Publicite(TFtitrepub.getText(), TFdescriptionpub.getText(), contact, TFlocalisationpub.getText(), imagePath, offrePubCombo.getValue(), user, actualite));

            // Display success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Publicité a été ajoutée");
            alert.setContentText("GG");
            alert.show();
        } catch (NumberFormatException e) {
            // Handle the case where TFcontactpub.getText() is not a valid integer
            e.printStackTrace(); // Log or handle the exception as needed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir un numéro de contact valide.");
            alert.show();
        }
    }



    @FXML
    public void uploadimgP(ActionEvent actionEvent) {
        uploadbuttonP.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png"));
            Stage stage = (Stage) uploadbuttonP.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                // Affiche le nom du fichier sélectionné
                labelPub.setText(selectedFile.getName());

                // Récupère le chemin absolu du fichier
                String absolutePath = selectedFile.getAbsolutePath();
                // Stocke le chemin absolu dans la variable de classe
                imagePath = absolutePath;

                // Crée une URL à partir du chemin absolu du fichier
                String fileUrl = new File(absolutePath).toURI().toString();

                // Crée une image à partir de l'URL du fichier
                Image image = new Image(fileUrl);

                // Affiche l'image dans l'ImageView
                imgView_pub.setImage(image);
            }
        });
    }

    public void tolistActualite(ActionEvent actionEvent) {
        try {
            System.out.println("Resource URL: " + getClass().getResource("/AfficherActualite.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherActualite.fxml"));
            TFtitrepub.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }
}
