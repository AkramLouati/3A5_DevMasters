package edu.esprit.controllers;

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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailTacheController {
    @FXML
    private Text TFEtatDetail;

    @FXML
    private Text TFdateDebutDetail;

    @FXML
    private Text TFdateFinDetail;

    @FXML
    private Text TFdescriptionDetail;

    @FXML
    private ImageView PieceJointedetail;

    @FXML
    private Text TFTitreDetail;

    @FXML
    private Text TFCategorieDetail;

    private int taskId; // Variable to store the task ID

    @FXML
    private TextField txt_date_C;

    @FXML
    private TextField txt_text_C;
    private Tache taches;

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public void setServiceCommentaireTache(ServiceCommentaireTache serviceCommentaireTache) {
        this.serviceCommentaireTache = serviceCommentaireTache;
    }
    private ServiceCommentaireTache serviceCommentaireTache;
    public void setData(Tache taches){
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
                txt_text_C.setText("Pas de commentaire...");
                txt_date_C.setText("Pas de commentaire...");
            }
            TFCategorieDetail.setText(taches.getCategorie().getNom_Cat());
            TFTitreDetail.setText(taches.getTitre_T());
            TFdateDebutDetail.setText(String.valueOf(taches.getDate_DT()));
            TFdateFinDetail.setText(String.valueOf(taches.getDate_FT()));
            TFdescriptionDetail.setText(taches.getDesc_T());
            TFEtatDetail.setText(taches.getEtat_T().toString());

            String imageUrl = taches.getPieceJointe_T();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    // Créer une instance de File à partir du chemin d'accès à l'image
                    File file = new File(imageUrl);
                    // Convertir le chemin de fichier en URL
                    String fileUrl = file.toURI().toURL().toString();
                    // Créer une instance d'Image à partir de l'URL de fichier
                    Image image = new Image(fileUrl);
                    // Définir l'image dans l'ImageView
                    PieceJointedetail.setImage(image);
                } catch (MalformedURLException e) {
                    // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                    e.printStackTrace();
                }
            } else {
                // Si l'URL de l'image est vide, vous pouvez définir une image par défaut
                // Par exemple, si vous avez une image "imageblanche.png" dans votre dossier src/main/resources
                // Vous pouvez utiliser getClass().getResource() pour obtenir son URL
                URL defaultImageUrl = getClass().getResource("/edu/esprit/img/imageblanche.png");
                assert defaultImageUrl != null;
                Image defaultImage = new Image(defaultImageUrl.toString());
                PieceJointedetail.setImage(defaultImage);
            }
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCommentaireTache.fxml"));
            Parent root = loader.load();
            AjouterCommentaireTacheController controller = loader.getController();

            // Pass the task ID to the controller
            controller.setTaskId(taches.getId_T());

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error
        }
    }

    @FXML
    void BTNModifCMNT(ActionEvent event) {
        // Check if a comment exists
        CommentaireTache commentaireTache = serviceCommentaireTache.getCommentaireForTask(taches.getId_T());
        if (commentaireTache != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCommentaireTache.fxml"));
                Parent root = loader.load();
                ModifierCommentaireTacheController controller = loader.getController();

                // Pass the current comment to the controller
                controller.setData(commentaireTache);

                // Pass the task ID to the controller
                controller.setTaskId(taches.getId_T());

                // Create a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle error
            }
        } else {
            // Show an alert indicating that no comment exists
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucun commentaire à modifier.");
            alert.showAndWait();
        }
    }

    @FXML
    void BTNSuppCMNT(ActionEvent event) {
        // Check if a comment exists
        CommentaireTache commentaireTache = serviceCommentaireTache.getCommentaireForTask(taches.getId_T());
        if (commentaireTache != null) {
            // Delete the comment from the database
            serviceCommentaireTache.supprimer(commentaireTache.getId_C());
            // Reset comment fields
            txt_text_C.setText("");
            txt_date_C.setText("");
            // Show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le commentaire a été supprimé avec succès.");
            alert.showAndWait();
        } else {
            // Show an error message indicating no comment exists
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucun commentaire à supprimer.");
            alert.showAndWait();
        }
    }

}
