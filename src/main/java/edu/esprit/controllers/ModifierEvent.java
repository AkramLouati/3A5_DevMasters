package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifierEvent {
    @FXML
    private TextField TFnomM;

    @FXML
    private TextField TFdateDebM;

    @FXML
    private TextField TFdateFinM;

    @FXML
    private TextField TFcapaciteM;

    @FXML
    private TextField TFcategorieM;

    private ServiceEvenement serviceEvenement;
    private Evenement evenement;

    public void setData(Evenement evenement) {
        this.evenement = evenement;
        // Afficher les données de l'événement dans les champs de texte
        TFnomM.setText(evenement.getNomEvent());
        TFdateDebM.setText(evenement.getDateEtHeureDeb());
        TFdateFinM.setText(evenement.getDateEtHeureFin());
        TFcapaciteM.setText(String.valueOf(evenement.getCapaciteMax()));
        TFcategorieM.setText(evenement.getCategorie());
    }

    @FXML
    void ModifierEventClick() {
        // Récupérer les nouvelles valeurs des champs de texte
        String nom = TFnomM.getText();
        String dateDeb = TFdateDebM.getText();
        String dateFin = TFdateFinM.getText();
        int capaciteMax = Integer.parseInt(TFcapaciteM.getText());
        String categorie = TFcategorieM.getText();

        // Mettre à jour les données de l'événement
        evenement.setNomEvent(nom);
        evenement.setDateEtHeureDeb(dateDeb);
        evenement.setDateEtHeureFin(dateFin);
        evenement.setCapaciteMax(capaciteMax);
        evenement.setCategorie(categorie);

        // Appeler le service pour mettre à jour l'événement dans la base de données
        if (serviceEvenement == null) {
            serviceEvenement = new ServiceEvenement();
        }
        serviceEvenement.modifier(evenement);

        // Afficher une alerte pour confirmer la modification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("L'événement a été modifié avec succès.");
        alert.setTitle("Événement modifié");
        alert.show();
    }
    @FXML
    void navigateOnClick() {
        try {
            // Charger le fichier FXML de l'interface AfficherEventS.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEventS.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton "Navigate"
            Stage stage = (Stage) TFnomM.getScene().getWindow();

            // Mettre la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions liées au chargement de l'interface AfficherEventS.fxml
        }
    }
}
