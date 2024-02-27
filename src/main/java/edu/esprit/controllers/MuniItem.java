package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MuniItem implements Initializable {

    public HBox muniButtonLayout;
    @FXML
    private String textButton;

    @FXML
    private Button muniButton;

    public void setData(Muni muni){


        muniButton.setText(muni.getNom_muni());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
