package edu.esprit.controllers.reclamation;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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

public class AdminReclamationController implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private Label receiverNameLabel211111;

    @FXML
    private Label receiverNameLabel2111111;

    @FXML
    private Label receiverNameLabel2111112;

    @FXML
    private Label receiverNameLabel21111131;

    @FXML
    private ScrollPane scroll;
    Muni muni = new Muni(15,"La Soukra","sokra@gmail.com","sokra123","fergha");
    EndUser user = new EndUser(38,"admin@gmail.com","admin","admin","admin","99999999",muni,"admin","C:\\Users\\MSI\\Desktop\\pidev\\3A5_DevMasters\\src\\main\\resources\\assets\\man1.png");

    private ServiceReclamation sr=new ServiceReclamation();
    Set<Reclamation> reclamationSet = sr.getAll();
    List<Reclamation> reclamationList = new ArrayList<>(reclamationSet);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < reclamationList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/reclamationGui/AdminReclamationItemComponentGui.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                AdminReclamationItemComponentController itemController = fxmlLoader.getController();
                itemController.setData(reclamationList.get(i));

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
    void NavigateTomessagerie(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/reclamationGui/AdminMessagerieAfficher.fxml"));
            receiverNameLabel211111.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }


}
