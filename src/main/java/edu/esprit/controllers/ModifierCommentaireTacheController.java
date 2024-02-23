package edu.esprit.controllers;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceCommentaireTache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ModifierCommentaireTacheController {

    private int taskId; // Variable to store the task ID
    private Tache tache; // Variable to store the task
    private EndUser user; // Variable to store the user
    private CommentaireTache commentaireTache; // Variable to store the current comment

    private ServiceCommentaireTache serviceCommentaireTache;

    public void setData(CommentaireTache commentaireTache) {
        this.commentaireTache = commentaireTache;
        commentField.setText(commentaireTache.getText_C());
    }

    public void setServiceCommentaireTache(ServiceCommentaireTache serviceCommentaireTache) {
        this.serviceCommentaireTache = serviceCommentaireTache;
    }

    public void setUserAndTaskIds(Tache tache, EndUser user) {
        this.tache = tache;
        this.user = user;
    }

    @FXML
    private TextArea commentField;

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @FXML
    void modifierCommentaire(ActionEvent event) {
        if (serviceCommentaireTache == null) {
            System.err.println("Service CommentaireTache not set.");
            return;
        }

        String newCommentText = commentField.getText();

        // Update the comment text
        commentaireTache.setText_C(newCommentText);
        // Set the task and user IDs
        // Modify the comment using the service
        serviceCommentaireTache.modifier(commentaireTache);

        // Show confirmation message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification réussie");
        alert.setHeaderText(null);
        alert.setContentText("Le commentaire a été modifié avec succès.");
        alert.showAndWait();

        // Close the window
        Stage stage = (Stage) commentField.getScene().getWindow();
        stage.close();
}}
