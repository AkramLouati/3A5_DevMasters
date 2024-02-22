package edu.esprit.controllers;

import edu.esprit.entities.Actualite;
import edu.esprit.services.ServiceActualite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AfficherActualite implements Initializable {
    @FXML
    private GridPane gridA;

    @FXML
    private ScrollPane scrollA;

    private ServiceActualite sr = new ServiceActualite();
    Set<Actualite> actualiteSet = sr.getAll();
    List<Actualite> actualiteList = new ArrayList<>(actualiteSet);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < actualiteList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ActualiteItem.fxml"));
                HBox hbox = fxmlLoader.load(); // Change this line

                ActualiteController itemController = fxmlLoader.getController();
                itemController.setData(actualiteList.get(i));

                if (column == 1) {
                    column = 0;
                    row++;
                }

                gridA.add(hbox, column++, row); //(child,column,row)
                //set grid width
                gridA.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridA.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridA.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridA.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridA.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridA.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(hbox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void toAjouterActualite(ActionEvent actionEvent) {

    }
}
