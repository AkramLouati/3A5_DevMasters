package edu.esprit.controllers;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceCommentaireTache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class DetailTacheController {
    @FXML
    private Text TFEtatDetail, TFdateDebutDetail, TFdateFinDetail, TFdescriptionDetail, TFTitreDetail, TFCategorieDetail;

    @FXML
    private ImageView PieceJointedetail;

    @FXML
    private TextField txt_date_C, txt_text_C;

    private Tache taches;
    private EndUser userId;
    private int taskId; // Variable to store the task ID

    private ServiceCommentaireTache serviceCommentaireTache;

    public void setUserAndTaskIds(EndUser userId, Tache task) {
        this.userId = userId;
        this.taskId = task.getId_T();
    }


    public void setServiceCommentaireTache(ServiceCommentaireTache serviceCommentaireTache) {
        this.serviceCommentaireTache = serviceCommentaireTache;
    }

    public void setData(Tache taches) {
        this.taches = taches;
        if (taches != null) {
            serviceCommentaireTache = new ServiceCommentaireTache();
            txt_date_C.setEditable(false);
            txt_text_C.setEditable(false);
            CommentaireTache commentaireTache = serviceCommentaireTache.getCommentaireForTask(taches.getId_T());
            if (commentaireTache != null) {
                txt_text_C.setText(commentaireTache.getText_C());
                txt_date_C.setText(commentaireTache.getDate_C().toString());
            } else {
                txt_text_C.setText("");
                txt_date_C.setText("Pas de commentaire...");
            }
            TFCategorieDetail.setText(taches.getCategorie().getNom_Cat());
            TFTitreDetail.setText(taches.getTitre_T());
            TFdateDebutDetail.setText(String.valueOf(taches.getDate_DT()));
            TFdateFinDetail.setText(String.valueOf(taches.getDate_FT()));
            TFdescriptionDetail.setText(taches.getDesc_T());
            TFEtatDetail.setText(taches.getEtat_T().toString());
            Image image = new Image(taches.getPieceJointe_T());
            PieceJointedetail.setImage(image);
        }
    }

    @FXML
    void buttonReturnListeTaches(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherTache.fxml"));
            TFTitreDetail.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    @FXML
    void BTNAjoutCMNT(ActionEvent event) {
        CommentaireTache commentaireTache = serviceCommentaireTache.getCommentaireForTask(taches.getId_T());
        if (commentaireTache == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCommentaireTache.fxml"));
                Parent root = loader.load();
                AjouterCommentaireTacheController controller = loader.getController();

                // Pass the service and user/task IDs to the controller
                controller.setServiceCommentaireTache(serviceCommentaireTache);
                controller.setUserAndTaskIds(taches, taches.getUser());

                // Create a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("commentaire Existe deja!");
            alert.showAndWait();
        }
    }


    @FXML
    void BTNModifCMNT(ActionEvent event) {
        CommentaireTache commentaireTache = serviceCommentaireTache.getCommentaireForTask(taches.getId_T());
        if (commentaireTache != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCommentaireTache.fxml"));
                Parent root = loader.load();
                ModifierCommentaireTacheController controller = loader.getController();

                // Pass the service, current comment, and task ID to the controller
                controller.setServiceCommentaireTache(serviceCommentaireTache);
                controller.setUserAndTaskIds(taches, taches.getUser());
                controller.setData(commentaireTache);
                // Create a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucun commentaire à modifier.");
            alert.showAndWait();
        }
    }

    @FXML
    void BTNSuppCMNT(ActionEvent event) {
        CommentaireTache commentaireTache = serviceCommentaireTache.getCommentaireForTask(taches.getId_T());
        if (commentaireTache != null) {
            serviceCommentaireTache.supprimer(commentaireTache.getId_C());
            txt_text_C.setText("");
            txt_date_C.setText("");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le commentaire a été supprimé avec succès.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucun commentaire à supprimer.");
            alert.showAndWait();
        }
    }

}


