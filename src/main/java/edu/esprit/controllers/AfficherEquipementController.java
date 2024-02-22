package edu.esprit.controllers;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AfficherEquipementController implements Initializable {
    @FXML
    private GridPane grid_equipement;

    @FXML
    private ScrollPane scroll_equipement;
    private ServiceEquipement se=new ServiceEquipement();
    Set<Equipement> EquipementSet = se.getAll();
    List<Equipement> EquipementList = new ArrayList<>(EquipementSet);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < EquipementList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Equipementitem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                EquipementItemController itemController = fxmlLoader.getController();
                itemController.setData(EquipementList.get(i));

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid_equipement.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid_equipement.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid_equipement.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid_equipement.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid_equipement.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid_equipement.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid_equipement.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
