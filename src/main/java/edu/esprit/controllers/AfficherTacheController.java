package edu.esprit.controllers;

import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceTache;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class AfficherTacheController {

    @FXML
    private ListView<Tache> listView;

    public Label categorie;
    private ServiceTache serviceTache;
    private ObservableList<Tache> tacheObservableList;

    public AfficherTacheController() {
        this.serviceTache = new ServiceTache();
        tacheObservableList = FXCollections.observableArrayList();
    }

    public void initialize() {
        serviceTache = new ServiceTache();
        updateDataFromDatabase();

        // Schedule periodic update
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updateDataFromDatabase, 0, 1, TimeUnit.SECONDS);
    }

    public void afficherToutesTaches() {
        tacheObservableList.clear();
        Set<Tache> taches = serviceTache.getAll();
        tacheObservableList.addAll(taches);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void supprimerTacheAction(javafx.event.ActionEvent event) {
        Tache selectedTache = listView.getSelectionModel().getSelectedItem();
        if (selectedTache != null) {
            serviceTache.supprimer(selectedTache.getId_T());
            afficherToutesTaches(); // Refresh the list view
            showAlert(Alert.AlertType.INFORMATION, "Success", "Tâche supprimée avec succès.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Veuillez sélectionner une tâche à supprimer.");
        }
    }

    public void ajouterTacheAction(javafx.event.ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterModifierTache.fxml"));
                Parent root = loader.load();
                AjouterModifierTacheController controller = loader.getController();

                // Set the stage if it's not null

                Stage stage = new Stage();
                if (stage != null) {
                    controller.setStage(stage);
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Erreur lors de l'ajout de la tâche : " + e.getMessage());
            }
        }

    public void modifierTacheAction(javafx.event.ActionEvent event) {
        Tache selectedTache = listView.getSelectionModel().getSelectedItem();
        if (selectedTache != null) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterModifierTache.fxml"));
                Parent root = loader.load();
                AjouterModifierTacheController controller = loader.getController();
                controller.initModifier(selectedTache);

                // Set the stage if it's not null

                Stage stage = new Stage();
                if (stage != null) {
                    controller.setStage(stage);
                    stage.setScene(new Scene(root));
                    stage.show();
                }

            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Erreur lors de la modification de la tâche : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Veuillez sélectionner une tâche à modifier.");
        }
    }

    private void updateDataFromDatabase() {
        Set<Tache> taches = serviceTache.getAll();
        ObservableList<Tache> observableTaches = FXCollections.observableArrayList(taches);

        // Update UI on JavaFX Application Thread
        Platform.runLater(() -> {
            listView.setItems(observableTaches);
        });
    }

    public void PdfTacheAction(ActionEvent actionEvent) {
    }

    public void ExcelTacheAction(ActionEvent actionEvent) {
    }

    public void EmployeeDuMoiAction(ActionEvent actionEvent) {
    }
}
