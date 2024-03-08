package edu.esprit.controllers.tache;

import edu.esprit.entities.CommentaireTache;
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
import java.util.Objects;
import java.util.Set;

public class DetailTacheFrontController {

    @FXML
    private Text TFEtatDetail, TFdateDebutDetail, TFdateFinDetail, TFdescriptionDetail, TFTitreDetail, TFCategorieDetail;

    @FXML
    private ImageView PieceJointedetail;

    @FXML
    private TextField txt_date_C, txt_text_C;
    private Tache tache;
    private ServiceCommentaireTache serviceCommentaireTache = new ServiceCommentaireTache();

    public void setData(Tache tache) {
        this.tache = tache;
        if (tache != null) {
            txt_date_C.setEditable(false);
            txt_text_C.setEditable(false);
            Set<CommentaireTache> commentairesTache = serviceCommentaireTache.getCommentairesForTask(tache);
            if (!commentairesTache.isEmpty()) {
                CommentaireTache commentaireTache = commentairesTache.iterator().next();
                txt_text_C.setText(commentaireTache.getText_C());
                txt_date_C.setText(commentaireTache.getDate_C().toString());
            } else {
                txt_text_C.setText("");
                txt_date_C.setText("Pas de commentaire...");
            }
            TFCategorieDetail.setText(tache.getCategorie().getNom_Cat());
            TFTitreDetail.setText(tache.getTitre_T());
            TFdateDebutDetail.setText(String.valueOf(tache.getDate_DT()));
            TFdateFinDetail.setText(String.valueOf(tache.getDate_FT()));
            TFdescriptionDetail.setText(tache.getDesc_T());
            TFEtatDetail.setText(tache.getEtat_T().toString());
            String pieceJointeUrl = tache.getPieceJointe_T();
            if (pieceJointeUrl != null && !pieceJointeUrl.isEmpty()) {
                Image image = new Image(pieceJointeUrl);
                PieceJointedetail.setImage(image);
            } else {
            }
        }
    }

    @FXML
    void Exit(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/tacheGui/AfficherTacheFront.fxml")));
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
        if (tache != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tacheGui/AjouterCommentaireTache.fxml"));
                Parent root = loader.load();
                AjouterCommentaireTacheController controller = loader.getController();

                // Pass the service and the current tache to the controller
                controller.setServiceCommentaireTache(serviceCommentaireTache);
                controller.setTache(tache);

                // Create a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors du chargement de l'interface utilisateur.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Sélectionnez une tâche pour ajouter un commentaire.");
            alert.showAndWait();
        }
    }


    @FXML
    void BTNModifCMNT(ActionEvent event) {
        Set<CommentaireTache> commentairesTache = serviceCommentaireTache.getCommentairesForTask(tache);
        if (!commentairesTache.isEmpty()) {
            CommentaireTache commentaireTache = commentairesTache.iterator().next(); // Get the first comment for modification
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tacheGui/ModifierCommentaireTache.fxml"));
                Parent root = loader.load();
                ModifierCommentaireTacheController controller = loader.getController();
                // Pass the service, current comment, and task ID to the controller
                controller.setServiceCommentaireTache(serviceCommentaireTache);
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
        Set<CommentaireTache> commentairesTache = serviceCommentaireTache.getCommentairesForTask(tache);
        if (!commentairesTache.isEmpty()) {
            CommentaireTache commentaireTache = commentairesTache.iterator().next(); // Get the first comment to delete
            serviceCommentaireTache.supprimer(commentaireTache.getId_Cmnt());
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