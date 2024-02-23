package edu.esprit.controllers;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.services.ServiceCommentaireTache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ModifierCommentaireTacheController {

    @FXML
    private TextArea commentField;

    private int taskId; // Variable to store the task ID

    private CommentaireTache commentaireTache; // Variable to store the current comment

    private ServiceCommentaireTache serviceCommentaireTache;

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setData(CommentaireTache commentaireTache) {
        this.commentaireTache = commentaireTache;
        commentField.setText(commentaireTache.getText_C());
    }

    @FXML
    void modifierCommentaire(ActionEvent event) {
        String newCommentText = commentField.getText();

        // Update the comment text
        commentaireTache.setText_C(newCommentText);

        // Modify the comment using the service
        serviceCommentaireTache.modifier(commentaireTache);

        // You might want to close the window or show a confirmation message here
    }
}
