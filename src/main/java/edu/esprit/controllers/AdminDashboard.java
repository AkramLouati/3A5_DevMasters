package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceMuni;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;


public class AdminDashboard implements Initializable {

    @FXML
    private VBox usersLayout;

    @FXML
    private HBox muniButtonLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Users list
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

        //Muni list
        List<Muni> munis = new ArrayList<>(munis());
        for (Muni muni : munis) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = getClass().getResource("/MuniItem.fxml");
            fxmlLoader.setLocation(location);
//            fxmlLoader.setLocation(getClass().getResource("UserItem.fxml"));



            try {
                HBox hBox = fxmlLoader.load();
                MuniItem cic = fxmlLoader.getController();
                cic.setData(muni);
                muniButtonLayout.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<EndUser> users(){
        List<EndUser> userList = new ArrayList<>();
//        EndUser user = new EndUser();

        ServiceUser serviceUser = new ServiceUser();
//        EndUser users = serviceUser.getOneByID(35);
        Set<EndUser> list = serviceUser.getAll();

        // Utilisation de la boucle for-each
        for (EndUser user : list) {
            // Faire quelque chose avec chaque utilisateur (EndUser)
            itemUser(user,userList);
        }

        return userList;
    }

    private List<Muni> munis(){
        List<Muni> muniList = new ArrayList<>();

        ServiceMuni serviceMuni = new ServiceMuni();
//        Muni muni = serviceMuni.getOneByID(15);
        Set<Muni> list = serviceMuni.getAll();

        // Utilisation de la boucle for-each
        for (Muni muni1 : list) {
            // Faire quelque chose avec chaque utilisateur (EndUser)
            itemMuni(muni1,muniList);
        }

        return muniList;
    }

    void itemUser(EndUser user,List<EndUser> userList){
        user.setNom(user.getNom());
        user.setImage(user.getImage());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setEmail(user.getEmail());
        user.setType(user.getType());
        userList.add(user);
    }

    void itemMuni(Muni muni,List<Muni> muniList){
        muni.setNom_muni(muni.getNom_muni());
        muniList.add(muni);
    }

    @FXML
    void addMuni(ActionEvent event) {
        openForm();
    }

    public void openForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutMuniFrom.fxml"));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Popup Form");

            Scene popupScene = new Scene(root, 600, 500);
            popupStage.setScene(popupScene);
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
