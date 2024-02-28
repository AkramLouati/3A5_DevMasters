package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReclamationEditController implements Initializable {

    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private VBox MainLeftSidebar;

    private boolean isSidebarVisible = true;

    @FXML
    private TextField TFmodifieradresse_reclamation;

    @FXML
    private TextField TFmodifiersujet_reclamation;

    @FXML
    private TextArea TmodifierAdescription_reclamation;

    @FXML
    private ImageView modifierimgView_reclamation;

    @FXML
    private Label label;

    @FXML
    private ComboBox<String> modifiertypeReclamationComboBox;

    @FXML
    private Button uploadimgmodifier;

    @FXML
    private Button uploadbutton_modifier;
    private ServiceReclamation serviceReclamation;
    private Reclamation reclamation;
    private String imagePath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser la taille du SecondBorderPane avec la même largeur que la barre latérale
        double sidebarWidth = MainLeftSidebar.getWidth();
        SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() + sidebarWidth);
    }
    @FXML
    void BTNToggleSidebar(ActionEvent event) {
        TranslateTransition sideBarTransition = new TranslateTransition(Duration.millis(400), MainLeftSidebar);

        double sidebarWidth = MainLeftSidebar.getWidth();

        if (isSidebarVisible) {
            // Hide sidebar
            sideBarTransition.setByX(-sidebarWidth);
            isSidebarVisible = false;
            // Adjust the width of SecondBorderPane
            SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() + sidebarWidth);
            // Translate SecondBorderPane to the left to take the extra space
            TranslateTransition borderPaneTransition = new TranslateTransition(Duration.millis(400), SecondBorderPane);
            borderPaneTransition.setByX(-sidebarWidth);
            borderPaneTransition.play();
        } else {
            // Show sidebar
            sideBarTransition.setByX(sidebarWidth);
            isSidebarVisible = true;
            // Adjust the width of SecondBorderPane
            SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() - sidebarWidth);
            // Reset the translation of SecondBorderPane to 0
            TranslateTransition borderPaneTransition = new TranslateTransition(Duration.millis(250), SecondBorderPane);
            borderPaneTransition.setByX(sidebarWidth);
            borderPaneTransition.play();
        }

        sideBarTransition.play();
    }


    public void BTNGestionEvennement(ActionEvent actionEvent) {

    }

    public void BTNGestionUser(ActionEvent actionEvent) {
    }

    public void BTNGestionRec(ActionEvent actionEvent) {

    }

    public void BTNGestionAct(ActionEvent actionEvent) {

    }

    public void BTNGestionEquipement(ActionEvent actionEvent) {
    }

    public void BTNGestionTache(ActionEvent actionEvent) {

    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setMainAnchorPaneContent(AnchorPane ajouterAP) {
        MainAnchorPaneBaladity.getChildren().setAll(ajouterAP);
    }
    @FXML
    void modifierReclamationAction(ActionEvent event) {
        if (reclamation != null && serviceReclamation != null) {
            // Créer une boîte de dialogue de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier cette réclamation ?");
            confirmationAlert.setTitle("Confirmation de modification");

            // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            // Vérifier si l'utilisateur a cliqué sur le bouton OK
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Mettre à jour les données de la réclamation avec les valeurs des champs de texte
                reclamation.setSujet_reclamation(TFmodifiersujet_reclamation.getText());
                reclamation.setType_reclamation(modifiertypeReclamationComboBox.getValue());
                reclamation.setDescription_reclamation(TmodifierAdescription_reclamation.getText());
                reclamation.setAdresse_reclamation(TFmodifieradresse_reclamation.getText());
                reclamation.setImage_reclamation(imagePath); // imagePath peut être nul si aucune image n'est sélectionnée
                try {
                    // Appeler la méthode de modification du service de réclamation
                    serviceReclamation.modifier(reclamation);

                    // Afficher un message de succès
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setContentText("Réclamation modifiée avec succès !");
                    successAlert.setTitle("Modification réussie");
                    successAlert.show();

                    // Rediriger l'utilisateur vers la vue précédente (par exemple, la liste des réclamations)
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/AfficherReclamationGui.fxml"));
                        TFmodifiersujet_reclamation.getScene().setRoot(root);
                    } catch (IOException e) {
                        // Gérer l'exception si la redirection échoue
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setContentText("Une erreur s'est produite lors de la redirection.");
                        errorAlert.setTitle("Erreur de redirection");
                        errorAlert.show();
                    }
                } catch (Exception e) {
                    // Afficher un message d'erreur en cas d'échec de la modification
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setContentText("Erreur lors de la modification de la réclamation : " + e.getMessage());
                    errorAlert.setTitle("Erreur de modification");
                    errorAlert.show();
                }
            }
        } else {
            // Afficher un message d'erreur si la réclamation est null ou si le service de réclamation est null
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de modifier la réclamation car aucune réclamation n'est sélectionnée ou le service de réclamation est null.");
            errorAlert.setTitle("Erreur de modification");
            errorAlert.show();
        }
    }



    @FXML
    void cancelModifierReclamationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherReclamationGui.fxml"));
            TFmodifiersujet_reclamation.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    @FXML
    void uploadbutton_modifier(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );
        Stage stage = (Stage) uploadimgmodifier.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            // Affiche le nom du fichier sélectionné
            label.setText(selectedFile.getName());

            // Récupère le chemin absolu du fichier
            String absolutePath = selectedFile.getAbsolutePath();
            // Stocke le chemin absolu dans la variable de classe
            imagePath = absolutePath;

            // Crée une URL à partir du chemin absolu du fichier
            String fileUrl = new File(absolutePath).toURI().toString();

            // Crée une image à partir de l'URL du fichier
            Image image = new Image(fileUrl);

            // Affiche l'image dans l'ImageView
            modifierimgView_reclamation.setImage(image);

            // Mettre à jour le chemin d'accès à l'image dans la réclamation
            if (reclamation != null) {
                reclamation.setImage_reclamation(imagePath);
            }
        }
    }

    // Méthode pour initialiser les champs avec les données de la réclamation
    public void setData(Reclamation reclamation) {
        this.reclamation = reclamation;
        if (reclamation != null) {
            TFmodifiersujet_reclamation.setText(reclamation.getSujet_reclamation());
            modifiertypeReclamationComboBox.setValue(reclamation.getType_reclamation());
            TmodifierAdescription_reclamation.setText(reclamation.getDescription_reclamation());
            TFmodifieradresse_reclamation.setText(reclamation.getAdresse_reclamation());
            String imageUrl = reclamation.getImage_reclamation();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    // Créer une instance de File à partir du chemin d'accès à l'image
                    File file = new File(imageUrl);
                    // Récupérer le nom du fichier
                    String fileName = file.getName();
                    // Mettre à jour le Label avec le nom du fichier
                    label.setText(fileName);

                    // Convertir le chemin de fichier en URL
                    String fileUrl = file.toURI().toURL().toString();
                    // Créer une instance d'Image à partir de l'URL de fichier
                    Image image = new Image(fileUrl);
                    // Définir l'image dans l'ImageView
                    modifierimgView_reclamation.setImage(image);
                } catch (MalformedURLException e) {
                    // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                    e.printStackTrace();
                }
            } else {
                // Si l'URL de l'image est vide, vous pouvez définir une image par défaut
                // Par exemple, si vous avez une image "imageblanche.png" dans votre dossier src/main/resources
                // Vous pouvez utiliser getClass().getResource() pour obtenir son URL
                URL defaultImageUrl = getClass().getResource("/assets/imageblanche.png");
                if (defaultImageUrl != null) { // Vérifier que defaultImageUrl n'est pas nul
                    Image defaultImage = new Image(defaultImageUrl.toString());
                    modifierimgView_reclamation.setImage(defaultImage);
                } else {
                    System.err.println("L'image par défaut n'a pas pu être chargée.");
                }
            }
        }
    }


    public void setServiceReclamation(ServiceReclamation serviceReclamation) {
        this.serviceReclamation = serviceReclamation;
    }

}