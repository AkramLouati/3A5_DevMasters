package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceEquipement;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import java.sql.Date;
import java.util.Optional;

public class ModifierEquipementGui {
    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private VBox MainLeftSidebar;
    private boolean isSidebarVisible = true;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private RadioButton categoriefixemodif;

    @FXML
    private RadioButton categoriemobilemodif;

    @FXML
    private DatePicker dateajoutmodif;

    @FXML
    private TextArea descriptionmodifTF;

    @FXML
    private BorderPane firstborderpane;

    @FXML
    private Label imageequipementmodfi;

    @FXML
    private ImageView imagevieweqmodif;

    @FXML
    private Button modifiquipementbtn;

    @FXML
    private Button navigateequipementbtn;

    @FXML
    private TextField nommodifTF;

    @FXML
    private ComboBox<Integer> quantitemodifCB;

    @FXML
    private TextField referencemodifTF;

    @FXML
    private Button telechargerimagemodif;
    Muni muni = new Muni(1);
    EndUser user = new EndUser(1,muni);
    private ServiceEquipement se;
    private Equipement equipement;
    private String imagePath;
    private Label label;
    @FXML
    void BTNGestionAct(ActionEvent event) {

    }

    @FXML
    void BTNGestionEquipement(ActionEvent event) {

    }

    @FXML
    void BTNGestionEvennement(ActionEvent event) {

    }

    @FXML
    void BTNGestionRec(ActionEvent event) {

    }

    @FXML
    void BTNGestionTache(ActionEvent event) {

    }

    @FXML
    void BTNGestionUser(ActionEvent event) {

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
    void modifierEquipementAction(ActionEvent event) {
        if (equipement != null && se!= null) {
            // Créer une boîte de dialogue de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier cet equipement ?");
            confirmationAlert.setTitle("Confirmation de modification");

            // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            // Vérifier si l'utilisateur a cliqué sur le bouton OK
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Mettre à jour les données de l'equipement  avec les valeurs des champs de texte
                String categorie = categoriefixemodif.isSelected() ? "Fixe" : "Mobile";
                Date dateAjoutModif = Date.valueOf(dateajoutmodif.getValue());
                equipement.setReference_eq(referencemodifTF.getText());
                equipement.setNom_eq(nommodifTF.getText());
                equipement.setCategorie_eq(categorie);
                equipement.setQuantite_eq(quantitemodifCB.getValue());
                equipement.setDate_ajouteq(dateAjoutModif);
                equipement.setDescription_eq(descriptionmodifTF.getText());
                equipement.setImage_eq(imagePath); // imagePath peut être nul si aucune image n'est sélectionnée
                try {
                    // Appeler la méthode de modification du service equipement
                    se.modifier(equipement);

                    // Afficher un message de succès
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setContentText("equipement modifiée avec succès !");
                    successAlert.setTitle("Modification réussie");
                    successAlert.show();

                } catch (Exception e) {
                    // Afficher un message d'erreur en cas d'échec de la modification
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setContentText("Erreur lors de la modification de l'equipement': " + e.getMessage());
                    errorAlert.setTitle("Erreur de modification");
                    errorAlert.show();
                }
            }
        } else {
            // Afficher un message d'erreur si l'equipement est null ou si le service equipement est null
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de modifier l'equipement car aucune equipement n'est pas sélectionnée ou le service equipement est null.");
            errorAlert.setTitle("Erreur de modification");
            errorAlert.show();
        }

    }

    @FXML
    void navigatetoAfficherEquipementAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEquipementGui.fxml"));
            referencemodifTF.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    @FXML
    void selectQuantite(ActionEvent event) {
        Integer selectedQuantity = (Integer) quantitemodifCB.getSelectionModel().getSelectedItem();

    }
    public void initialize() {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        for (int i = 0; i <= 20; i++) {
            list.add(i);
        }
        quantitemodifCB.setItems(list);
    }

    @FXML
    void telechargerImageEquipemnt(ActionEvent event) {
        telechargerimagemodif.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png"));
            Stage stage = (Stage) telechargerimagemodif.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                // Affiche le nom du fichier sélectionné
                imageequipementmodfi.setText(selectedFile.getName());

                // Récupère le chemin absolu du fichier
                String absolutePath = selectedFile.getAbsolutePath();
                // Stocke le chemin absolu dans la variable de classe
                imagePath = absolutePath;

                // Crée une URL à partir du chemin absolu du fichier
                String fileUrl = new File(absolutePath).toURI().toString();

                // Crée une image à partir de l'URL du fichier
                Image image = new Image(fileUrl);

                // Affiche l'image dans l'ImageView
                imagevieweqmodif.setImage(image);
            }
        });


    }
}
