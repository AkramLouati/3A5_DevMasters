package edu.esprit.controllers;

import edu.esprit.entities.Publicite;
import edu.esprit.services.ServicePublicite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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

public class AfficherPubliciteController implements Initializable {
    @FXML
    private GridPane gridPub;

    @FXML
    private ScrollPane scrollPub;

    private ServicePublicite servicePublicite = new ServicePublicite();
    Set<Publicite> publiciteSet = servicePublicite.getAll();
    List<Publicite> publiciteList = new ArrayList<>(publiciteSet);
    private int actualiteId; // New variable to store the id_a


    public void setActualiteId(int actualiteId) {
        this.actualiteId = actualiteId;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < publiciteList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/PubliciteItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                PubliciteController itemController = fxmlLoader.getController();
                itemController.setData(publiciteList.get(i));

                if (column == 1) {
                    column = 0;
                    row++;
                }

                gridPub.add(anchorPane, column++, row); //(child,column,row)
                // set grid width
                gridPub.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPub.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPub.setMaxWidth(Region.USE_PREF_SIZE);

                // set grid height
                gridPub.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPub.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPub.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
