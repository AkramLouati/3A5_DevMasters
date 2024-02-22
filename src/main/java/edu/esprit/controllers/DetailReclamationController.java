package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailReclamationController {
    @FXML
    private Text TFadresseReclamationDetail;

    @FXML
    private Text TFdateReclamationDetail;

    @FXML
    private Text TFdescriptionReclamationDetail;

    @FXML
    private ImageView Imagedetail;

    @FXML
    private Text TFsujetReclamationDetail;

    @FXML
    private Text TFtypeReclamationDetail;
    private Reclamation reclamation;
    public void setData(Reclamation reclamation) {
        this.reclamation = reclamation;
        TFsujetReclamationDetail.setText(reclamation.getSujet_reclamation());
        TFtypeReclamationDetail.setText(reclamation.getType_reclamation());
        TFdateReclamationDetail.setText(String.valueOf(reclamation.getDate_reclamation()));
        TFdescriptionReclamationDetail.setText(reclamation.getDescription_reclamation());
        TFadresseReclamationDetail.setText(reclamation.getAdresse_reclamation());
        String imageUrl = reclamation.getImage_reclamation();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                // Créer une instance de File à partir du chemin d'accès à l'image
                File file = new File(imageUrl);
                // Convertir le chemin de fichier en URL
                String fileUrl = file.toURI().toURL().toString();
                // Créer une instance d'Image à partir de l'URL de fichier
                Image image = new Image(fileUrl);
                // Définir l'image dans l'ImageView
                Imagedetail.setImage(image);
            } catch (MalformedURLException e) {
                // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                e.printStackTrace();
            }
        } else {
            // Si l'URL de l'image est vide ou null, afficher une image par défaut
            // Par exemple, si vous avez une image "imageblanche.png" dans votre dossier src/main/resources
            // Vous pouvez utiliser getClass().getResource() pour obtenir son URL
            URL defaultImageUrl = getClass().getResource("/edu/esprit/img/imageblanche.png");
            if (defaultImageUrl != null) {
                Image defaultImage = new Image(defaultImageUrl.toString());
                Imagedetail.setImage(defaultImage);
            } else {
                System.err.println("L'image par défaut n'a pas été trouvée !");
            }
        }
    }

    @FXML
    void buttonReturnDetailReclamation(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherReclamation.fxml"));
            TFsujetReclamationDetail.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

}
