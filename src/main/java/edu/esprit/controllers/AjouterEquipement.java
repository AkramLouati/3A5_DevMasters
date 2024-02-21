package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceEquipement;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AjouterEquipement {

    @FXML
    private Button ajouteq;

    @FXML
    private TextField desc;

    @FXML
    private TextField etateq;

    @FXML
    private TextField nomeq;

    @FXML
    private TextField quantiteq;

    @FXML
    private TextField refeq;

    @FXML
    private Button retourajout;

    @FXML
    private TextField typeeq;

    @FXML
    private Button uploadimg;
    private final ServiceEquipement serviceEquipement = new ServiceEquipement();
    private final ServiceUser serviceUser = new ServiceUser();

    @FXML
    void ajouterEquipementAction(ActionEvent event) {
        try {
            EndUser currentUser = serviceUser.getOneByID(1);
            String refEq = refeq.getText();
            String nomEq = nomeq.getText();
            String typeMaterielEq = typeeq.getText();
            int quantiteEq = Integer.parseInt(quantiteq.getText());
            String etatEq = etateq.getText();
            String descriptionEq = desc.getText();

            Equipement equipement = new Equipement(refEq, nomEq, typeMaterielEq, quantiteEq, etatEq, descriptionEq,currentUser);
            serviceEquipement.ajouter(equipement);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Equipement ajouté avec succès !");
            alert.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Veuillez saisir une quantité valide !");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Erreur lors de l'ajout de l'équipement : " + e.getMessage());
            alert.show();
        }
    }

    }

   /* @FXML
    void ajouterImageAction(ActionEvent event) {

    }
    @FXML
    void retourAjoutEqAction(ActionEvent event) {

    }*/

