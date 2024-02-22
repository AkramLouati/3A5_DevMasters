package edu.esprit.controllers;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.services.ServiceCommentaireTache;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AfficherCommentaireController {

    @FXML
    private Label Label_date_C;

    @FXML
    private Label Label_text_C;

    @FXML
    private TextField txt_date_C;

    @FXML
    private TextField txt_text_C;

    private ServiceCommentaireTache serviceCommentaireTache;

    public void initialize() {
        serviceCommentaireTache = new ServiceCommentaireTache();
        txt_date_C.setEditable(false);
        txt_text_C.setEditable(false);
    }

    // Method to load comment data for a specified task ID
    public void afficherCommentaire(int taskId) {
        CommentaireTache commentaireTache = serviceCommentaireTache.getCommentaireForTask(taskId);
        if (commentaireTache != null) {
            txt_text_C.setText(commentaireTache.getText_C());
            txt_date_C.setText(commentaireTache.getDate_C().toString());
        } else {
            txt_text_C.setText("Pas de commentaire.");
            txt_date_C.setText("");
        }
    }
}
