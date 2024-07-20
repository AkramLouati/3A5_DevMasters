package edu.esprit.controllers.user;

import edu.esprit.services.ServiceMuni;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AfficherMuni implements Initializable {
    private final ServiceMuni serviceMuni = new ServiceMuni();
    @FXML
    private Label LLpersonnes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LLpersonnes.setText(serviceMuni.getAll().toString());
    }
}
