package edu.esprit.controllers;

import edu.esprit.entities.Avis;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServiceEquipement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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

public class AfficherAvisGuiFront implements Initializable {
    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private Button ajouterAvisbutton;

    @FXML
    private BorderPane firstborderpane;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    @FXML
    private Button retourEquipement;


    Muni muni = new Muni(2);
    EndUser user = new EndUser(5, muni);
    //Equipement e = new Equipement(1);
    private ServiceAvis sa = new ServiceAvis();
    Set<Avis> avisSet = sa.getAll();
    List<Avis> avisList = new ArrayList<>(avisSet);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < avisList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/AvisItemGuiFront.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                AvisItemGuiFront itemController = fxmlLoader.getController();
                itemController.setData(avisList.get(i));

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
    void ajouterAvisAction(ActionEvent event) {

    }
    @FXML
    void navigateAvisEquipement(ActionEvent event) {
        try {
            System.out.println("Resource URL: " + getClass().getResource("/AfficherEquipementGuiFront.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEquipementGuiFront.fxml"));
            retourEquipement.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }
}