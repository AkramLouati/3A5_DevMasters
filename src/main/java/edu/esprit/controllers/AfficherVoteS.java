package edu.esprit.controllers;

import edu.esprit.entities.Vote;
import edu.esprit.services.ServiceVote;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AfficherVoteS implements Initializable {

    @FXML
    private GridPane gridA;

    @FXML
    private ScrollPane scrollA;

    private ServiceVote serviceVote = new ServiceVote();
    Set<Vote> voteList = serviceVote.getAll();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        try {
            for (Vote vote : voteList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/VoteItem.fxml"));
                GridPane gridPane = fxmlLoader.load();

                VoteItem itemController = fxmlLoader.getController();
                itemController.setData(vote);

                gridA.add(gridPane, column++, row);
                GridPane.setMargin(gridPane, new Insets(10));

                if (column == 3) {
                    column = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

