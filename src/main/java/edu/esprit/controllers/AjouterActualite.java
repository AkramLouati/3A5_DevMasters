package edu.esprit.controllers;
import com.sun.javafx.charts.Legend;
import edu.esprit.entities.Actualite;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceActualite;
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
public class AjouterActualite {
    @FXML
    private TextField TFtitre;
    @FXML
    private TextField TFdescription;
    @FXML
    private ImageView imgView_actualite;

    @FXML
    private Label labelA;

    @FXML
    private Button uploadbuttonA;

    private final ServiceActualite sp = new ServiceActualite();
    java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
    Muni muni = new Muni(1);
    private String imagePath;

    @FXML
    void ajouterActualiteAction(ActionEvent event) {

        sp.ajouter(new Actualite(TFtitre.getText(),TFdescription.getText(),sqlDate,imagePath,muni));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("GG");
        alert.show();

    }
    public void navigatetoAfficherActualiteAction(ActionEvent actionEvent) {
        try {
            System.out.println("Resource URL: " + getClass().getResource("/AfficherActualite.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherActualite.fxml"));
            TFtitre.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }


    }

    public void uploadimgA(ActionEvent actionEvent) {
        uploadbuttonA.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png"));
            Stage stage = (Stage) uploadbuttonA.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                // Affiche le nom du fichier sélectionné
                labelA.setText(selectedFile.getName());

                // Récupère le chemin absolu du fichier
                String absolutePath = selectedFile.getAbsolutePath();
                // Stocke le chemin absolu dans la variable de classe
                imagePath = absolutePath;

                // Crée une URL à partir du chemin absolu du fichier
                String fileUrl = new File(absolutePath).toURI().toString();

                // Crée une image à partir de l'URL du fichier
                Image image = new Image(fileUrl);

                // Affiche l'image dans l'ImageView
                imgView_actualite.setImage(image);
            }
        });
    }

}
