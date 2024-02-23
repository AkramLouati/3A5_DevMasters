package edu.esprit.controllers;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceCommentaireTache;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.Date;

public class AjouterCommentaireTacheController {

    private ServiceCommentaireTache serviceCommentaireTache;

    private Tache tache;
    private EndUser user;

    public void setUserAndTaskIds(Tache tache, EndUser user) {
        this.tache = tache;
        this.user = user;
    }


    public void setServiceCommentaireTache(ServiceCommentaireTache serviceCommentaireTache) {
        this.serviceCommentaireTache = serviceCommentaireTache;
    }

    @FXML
    private TextArea commentField;

    @FXML
    void ajouterCommentaire(javafx.event.ActionEvent event) {
        String commentaireText = commentField.getText();

        // Creating the comment object
        CommentaireTache commentaireTache = new CommentaireTache();
        commentaireTache.setText_C(commentaireText);
        commentaireTache.setId_T(tache.getId_T()); // Set the task ID
        commentaireTache.setUser(user); // Set the task ID

        commentaireTache.setDate_C(new Date()); // Set the current date

        // Call the service to add the comment
        serviceCommentaireTache.ajouter(commentaireTache);
    }
}
