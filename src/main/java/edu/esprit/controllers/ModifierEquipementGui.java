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
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
    private ServiceEquipement se;
    private Equipement equipement ;
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

        // Vérifier si tous les champs sont valides
            if (equipement != null && se != null) {

                // Créer une boîte de dialogue de confirmation
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier cette equipement ?");
                confirmationAlert.setTitle("Confirmation de modification");

                // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
                Optional<ButtonType> result = confirmationAlert.showAndWait();

                // Vérifier si l'utilisateur a cliqué sur le bouton OK
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Mettre à jour les données de la réclamation avec les valeurs des champs de texte
                    equipement.setReference_eq(referencemodifTF.getText());
                    equipement.setNom_eq(nommodifTF.getText());
                    equipement.setQuantite_eq(quantitemodifCB.getValue());
                    equipement.setDescription_eq(descriptionmodifTF.getText());
                    LocalDate dateSelectionnee = dateajoutmodif.getValue();
                    if (dateSelectionnee != null) {
                        // Convertir LocalDate en java.sql.Date
                        Date dateSQL = Date.valueOf(dateSelectionnee);
                        equipement.setDate_ajouteq(dateSQL);
                    } else {
                        // Gérer le cas où aucune date n'a été sélectionnée
                        showAlert(Alert.AlertType.WARNING, "Date non sélectionnée", "Veuillez sélectionner une date.");
                        return; // Sortir de la méthode car la date est requise
                    }
                    equipement.setImage_eq(imagePath); // imagePath peut être nul si aucune image n'est sélectionnée
                    try {
                        // Appeler la méthode de modification du service de réclamation
                        se.modifier(equipement);

                        // Afficher un message de succès
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setContentText("equipement modifiée avec succès !");
                        successAlert.setTitle("Modification réussie");
                        successAlert.show();

                        // Rediriger l'utilisateur vers la vue précédente (par exemple, la liste des réclamations)
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEquipementGUi.fxml"));
                            modifiquipementbtn.getScene().setRoot(root);
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
            }
    }

    //}
    public void setData(Equipement equipement) {
        this.equipement = equipement;
        // Assurez-vous que l'équipement n'est pas null
        if (equipement != null) {
            // Initialisez les champs de l'interface utilisateur avec les valeurs de l'équipement
            referencemodifTF.setText(equipement.getReference_eq());
            nommodifTF.setText(equipement.getNom_eq());
            descriptionmodifTF.setText(equipement.getDescription_eq());
            quantitemodifCB.setValue(equipement.getQuantite_eq());
            // Assurez-vous de sélectionner la bonne catégorie (Fixe ou Mobile)
            if (equipement.getCategorie_eq().equals("Fixe")) {
                categoriefixemodif.setSelected(true);
            } else {
                categoriemobilemodif.setSelected(true);
            }
            // Récupérer la date d'ajout de l'équipement
            java.util.Date dateAjout = equipement.getDate_ajouteq();

// Vérifier si la date d'ajout n'est pas null
            if (dateAjout != null) {
                // Convertir la date en java.sql.Date
                java.sql.Date sqlDateAjout = new java.sql.Date(dateAjout.getTime());
                // Convertir la date SQL en LocalDate pour le DatePicker
                LocalDate localDateAjout = sqlDateAjout.toLocalDate();
                dateajoutmodif.setValue(localDateAjout);
            } else {
                // Si la date est null, définissez la date actuelle comme valeur par défaut
                dateajoutmodif.setValue(LocalDate.now());
            }

            String imageUrl = equipement.getImage_eq();
            // Chargez l'image associée à l'équipement dans l'ImageView
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    // Créer une instance de File à partir du chemin d'accès à l'image
                    File file = new File(imageUrl);
                    // Récupérer le nom du fichier
                    String fileName = file.getName();
                    // Mettre à jour le Label avec le nom du fichier
                    imageequipementmodfi.setText(fileName);

                    // Convertir le chemin de fichier en URL
                    String fileUrl = file.toURI().toURL().toString();
                    // Créer une instance d'Image à partir de l'URL de fichier
                    Image image = new Image(fileUrl);
                    // Définir l'image dans l'ImageView
                    imagevieweqmodif.setImage(image);
                } catch (MalformedURLException e) {
                    // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                    e.printStackTrace();
                }
            } else {
                // Si l'URL de l'image est vide, vous pouvez définir une image par défaut
                // Par exemple, si vous avez une image "imageblanche.png" dans votre dossier src/main/resources
                // Vous pouvez utiliser getClass().getResource() pour obtenir son URL
                URL defaultImageUrl = getClass().getResource("/img/Baladia.png");
                if (defaultImageUrl != null) { // Vérifier que defaultImageUrl n'est pas nul
                    Image defaultImage = new Image(defaultImageUrl.toString());
                    imagevieweqmodif.setImage(defaultImage);
                } else {
                    System.err.println("L'image par défaut n'a pas pu être chargée.");
                }
            }
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
        System.out.println(equipement);
    }

    @FXML
    void telechargerImageEquipemnt(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );
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

            // Mettre à jour le chemin d'accès à l'image dans la réclamation
            if (equipement != null) {
                equipement.setImage_eq(imagePath);
            }
        }
    }


    public void setServiceEquipement(ServiceEquipement se) {
        this.se = se;

    }
}