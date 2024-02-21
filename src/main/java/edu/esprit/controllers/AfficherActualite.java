package edu.esprit.controllers;

import edu.esprit.entities.Actualite;

import edu.esprit.services.ServiceActualite;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class AfficherActualite {

    @FXML
    private ListView<Actualite> listView;

    public Label categorie;
    private ServiceActualite serviceActualite;
    private ObservableList<Actualite> actualiteObservableList;

    public AfficherActualite() {
        this.serviceActualite = new ServiceActualite();
        actualiteObservableList = FXCollections.observableArrayList();
    }

    public void initialize() {
        serviceActualite = new ServiceActualite();
        updateDataFromDatabase();

        // Schedule periodic update
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updateDataFromDatabase, 0, 1, TimeUnit.SECONDS);
    }

    public void afficherToutesTaches() {
        actualiteObservableList.clear();
        Set<Actualite> actualites = serviceActualite.getAll();
        actualiteObservableList.addAll(actualites);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateDataFromDatabase() {
        Set<Actualite> actualites = serviceActualite.getAll();
        ObservableList<Actualite> act = FXCollections.observableArrayList(actualites);

        // Update UI on JavaFX Application Thread
        Platform.runLater(() -> {
            listView.setItems(act);
        });
    }
    public void supprimerTacheAction(javafx.event.ActionEvent event) {
        Actualite selectedActualite = listView.getSelectionModel().getSelectedItem();
        if (selectedActualite != null) {
            serviceActualite.supprimer(selectedActualite.getId_a());
            afficherToutesTaches(); // Refresh the list view
            showAlert(Alert.AlertType.INFORMATION, "Success", "Actualite supprimée avec succès.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Veuillez sélectionner une actualite à supprimer.");
        }
    }
    public void PdfTacheAction(ActionEvent actionEvent) {
    }

    public void ExcelTacheAction(ActionEvent actionEvent) {
    }

    public void EmployeeDuMoiAction(ActionEvent actionEvent) {
    }
}
