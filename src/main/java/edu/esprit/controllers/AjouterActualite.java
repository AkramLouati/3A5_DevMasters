package edu.esprit.controllers;
import com.sun.javafx.charts.Legend;
import edu.esprit.entities.Actualite;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceActualite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;
import java.io.IOException;
import java.sql.SQLException;
public class AjouterActualite {
    @FXML
    private TextField TFtitre;
    @FXML
    private TextField TFdescription;

    private final ServiceActualite sp = new ServiceActualite();
    java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

    @FXML
    void ajouterActualiteAction(ActionEvent event) {

        Muni muni = new Muni(1);
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
        sp.ajouter(new Actualite(TFtitre.getText(),TFdescription.getText(),sqlDate,"dvd",muni));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("GG");
        alert.show();

    }
    @FXML
    void modifierActualiteAction(ActionEvent event) {
        try {
            // Assuming you have a separate FXML file for the modification interface named "ModifierActualite.fxml"
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterActualite.fxml"));
            TFtitre.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
    public void navigatetoAfficherActualiteAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherActualite.fxml"));
            TFtitre.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }}



}
