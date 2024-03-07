package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceEquipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class AfficherEquipementGuiFront implements Initializable {
    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private BorderPane firstborderpane;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TextField searchField;
    Muni muni = new Muni(2);
    EndUser user = new EndUser(5, muni);
    private ServiceEquipement se = new ServiceEquipement();
    Set<Equipement> equipementSet = se.getAll();
    List<Equipement> equipementList = new ArrayList<>(equipementSet);
    @FXML
    void BTNGestionAct(ActionEvent event) {

    }

    @FXML
    void BTNGestionEquipement(ActionEvent event) {

    }

    @FXML
    void BTNGestionEvennement(ActionEvent event) {

    }

    @FXML
    void BTNGestionRec(ActionEvent event) {

    }

    @FXML
    void BTNGestionTache(ActionEvent event) {

    }
    @FXML
    void searchEquipments(ActionEvent event) {
        String searchTerm = searchField.getText().trim();
        String selectedCategory = categoryComboBox.getValue();

        // Filtrer les équipements par terme de recherche et/ou catégorie sélectionnée
        List<Equipement> filteredList = equipementList.stream()
                .filter(equipement ->
                        equipement.getNom_eq().toLowerCase().contains(searchTerm.toLowerCase()) &&
                                (selectedCategory == null || equipement.getCategorie_eq().equalsIgnoreCase(selectedCategory)))
                .collect(Collectors.toList());

        // Rafraîchir la grille avec les équipements filtrés
        refreshGrid(filteredList);
    }

    @FXML
    void sortByCategory(ActionEvent event) {
        String selectedCategory = categoryComboBox.getValue();

        if ("Sélectionner une catégorie".equals(selectedCategory)) {
            // Si "Sélectionner une catégorie" est sélectionné, actualiser la grille avec tous les équipements
            refreshGrid(equipementList);
        } else {
            // Trier les équipements par catégorie sélectionnée
            List<Equipement> sortedList = equipementList.stream()
                    .filter(equipement -> equipement.getCategorie_eq().equalsIgnoreCase(selectedCategory))
                    .collect(Collectors.toList());
            // Rafraîchir la grille avec les équipements triés
            refreshGrid(sortedList);
        }
    }
    private void refreshGrid(List<Equipement> equipments) {
        grid.getChildren().clear(); // Effacer la grille actuelle
        int column = 0;
        int row = 1;
        try {
            for (Equipement equipement : equipments) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EquipementItemGuiFront.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                EquipementItemGuiFront itemController = fxmlLoader.getController();
                itemController.setData(equipement);
                if (column == 1) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisez la liste d'équipements
        ObservableList<String> categories = FXCollections.observableArrayList("Fixe", "Mobile","Sélectionner une catégorie");
        categoryComboBox.setItems(categories);
        refreshEquipments();
    }
    private void refreshEquipments() {
        // Obtenez tous les équipements initialement
        Set<Equipement> equipementSet = se.getAll();
        equipementList = new ArrayList<>(equipementSet);
        // Rafraîchissez la grille avec les équipements initiaux
        refreshGrid(equipementList);
    }
}
