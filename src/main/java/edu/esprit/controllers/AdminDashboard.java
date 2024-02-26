package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AdminDashboard implements Initializable {

    @FXML
    private VBox usersLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<EndUser> users = new ArrayList<>(users());
        for (EndUser user : users) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = getClass().getResource("/UserItem.fxml");
            fxmlLoader.setLocation(location);
//            fxmlLoader.setLocation(getClass().getResource("UserItem.fxml"));


            try {
                HBox hBox = fxmlLoader.load();
                UserItem cic = fxmlLoader.getController();
                cic.setData(user);
                usersLayout.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<EndUser> users(){
        List<EndUser> userList = new ArrayList<>();
//        EndUser user = new EndUser();

        ServiceUser serviceUser = new ServiceUser();
        EndUser users = serviceUser.getOneByID(35);
        Set<EndUser> list = serviceUser.getAll();

        // Utilisation de la boucle for-each
        for (EndUser user : list) {
            // Faire quelque chose avec chaque utilisateur (EndUser)
            item(user,userList);
        }

        return userList;
    }

    void item(EndUser user,List<EndUser> userList){
        user.setNom(user.getNom());
        user.setImage(user.getImage());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setEmail(user.getEmail());
        user.setType(user.getType());
        userList.add(user);
    }

}
