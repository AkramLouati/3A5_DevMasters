package edu.esprit.controllers;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.services.ServiceCommentaireTache;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.awt.event.ActionEvent;

public class AjouterCommentaireTacheController {

    @FXML
    private TextArea commentField;

    private int taskId; // Variable to store the task ID

    private ServiceCommentaireTache serviceCommentaireTache = new ServiceCommentaireTache(); // Initialize the service

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @FXML
    void ajouterCommentaire(javafx.event.ActionEvent  event) {
        String commentaireText = commentField.getText();

        // Creating the comment object
        CommentaireTache commentaireTache = new CommentaireTache();
        commentaireTache.setId_T(taskId); // Set the task ID
        commentaireTache.setText_C(commentaireText);

        // Adding the comment using the service
        serviceCommentaireTache.ajouter(commentaireTache);

        // You might want to close the window or show a confirmation message here
    }
}
