package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceTache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AfficherTachesController implements Initializable {
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    EndUser user = new EndUser(12);
    private ServiceTache sr=new ServiceTache();
    Set<Tache> tacheSet = sr.getAll();
    List<Tache> tacheList= new ArrayList<>(tacheSet);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        try {
            for (Tache tache : tacheList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Tache.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                TacheController itemController = fxmlLoader.getController();
                itemController.setData(tache);

                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AjoutTacheLabel(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTache.fxml"));
            Parent root = loader.load();
            AjouterTacheController controller = loader.getController();

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

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void PDFTacheLabel(ActionEvent actionEvent) {
    }

    public void ExcelTacheLabel(ActionEvent actionEvent) {
    }

    public void MeilleurEmpTLabel(ActionEvent actionEvent) {
    }

    public void searchTacheLabel(ActionEvent actionEvent) {
    }
}
