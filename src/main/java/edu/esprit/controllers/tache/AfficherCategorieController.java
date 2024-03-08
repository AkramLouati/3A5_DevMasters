package edu.esprit.controllers.tache;

import edu.esprit.entities.CategorieT;
import edu.esprit.services.ServiceCategorieT;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class AfficherCategorieController {
    private final ServiceCategorieT serviceCategorieT;
    @FXML
    private ListView<CategorieT> categoryListView;

    public AfficherCategorieController() {
        serviceCategorieT = new ServiceCategorieT();
    }

    @FXML
    private void initialize() {
        try {
            categoryListView.getItems().addAll(serviceCategorieT.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addCategory() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter Category");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrer Nom Categorie:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            CategorieT newCategory = new CategorieT();
            newCategory.setNom_Cat(name);
            serviceCategorieT.ajouter(newCategory);
            refreshListView();
        });
    }

    @FXML
    private void updateCategory() {
        CategorieT selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            TextInputDialog dialog = new TextInputDialog(selectedCategory.getNom_Cat());
            dialog.setTitle("Mise A Jour Categorie");
            dialog.setHeaderText(null);
            dialog.setContentText("Nom Categoriue:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                selectedCategory.setNom_Cat(name);
                serviceCategorieT.modifier(selectedCategory);
                refreshListView();
            });
        } else {
            showAlert("Aucune Selection!", "Selectionner Categorie.");
        }
    }

    @FXML
    private void deleteCategory() {
        CategorieT selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            serviceCategorieT.supprimer(selectedCategory.getId_Cat());
            categoryListView.getItems().remove(selectedCategory);
        } else {
            showAlert("Aucune Selection!", "Selectionner Categorie.");
        }
    }

    private void refreshListView() {
        try {
            categoryListView.getItems().clear();
            categoryListView.getItems().addAll(serviceCategorieT.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
