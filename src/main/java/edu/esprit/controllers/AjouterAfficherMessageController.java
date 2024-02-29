package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Messagerie;
import edu.esprit.entities.Muni;
import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceMessagerie;
import edu.esprit.services.ServiceReclamation;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class AjouterAfficherMessageController implements Initializable {


    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private VBox MainLeftSidebar;

    @FXML
    private Label Nameuser;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private TextField TFmessage;

    @FXML
    private ScrollPane chatVbox;

    @FXML
    private BorderPane firstborderpane;

    @FXML
    private GridPane grid;
    @FXML
    private ImageView userphoto;
    ServiceReclamation serviceReclamation = new ServiceReclamation();
    ServiceMessagerie serviceMessagerie= new ServiceMessagerie();

    java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
    private boolean isSidebarVisible = true;
    Muni muni = new Muni(15,"La Soukra","sokra@gmail.com","sokra123","fergha");
    EndUser userDirecteur = new EndUser(36,"yassine@gmail.com","yassine","yassine123","directeur","97404777",muni,"soukra","C:\\Users\\MSI\\Desktop\\pidev\\3A5_DevMasters\\src\\main\\resources\\assets\\profile.png");

    public void setData(Reclamation reclamation) {
        this.reclamation = reclamation;
        Nameuser.setText(reclamation.getUser().getNom());
        String imageUrl = reclamation.getUser().getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                // Créer une instance de File à partir du chemin d'accès à l'image
                File file = new File(imageUrl);
                // Convertir le chemin de fichier en URL
                String fileUrl = file.toURI().toURL().toString();
                // Créer une instance d'Image à partir de l'URL de fichier
                Image image = new Image(fileUrl);
                // Définir l'image dans l'ImageView
                userphoto.setImage(image);
            } catch (MalformedURLException e) {
                // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                e.printStackTrace();
            }
        } else {
            // Si l'URL de l'image est vide ou null, afficher une image par défaut
            // Par exemple, si vous avez une image "imageblanche.png" dans votre dossier src/main/resources
            // Vous pouvez utiliser getClass().getResource() pour obtenir son URL
            URL defaultImageUrl = getClass().getResource("/assets/profile.png");
            if (defaultImageUrl != null) {
                Image defaultImage = new Image(defaultImageUrl.toString());
                userphoto.setImage(defaultImage);
            } else {
                System.err.println("L'image par défaut n'a pas été trouvée !");
            }
        }
        ServiceMessagerie serviceMessagerie = new ServiceMessagerie();
        Set<Messagerie> messages = serviceMessagerie.getAllMessagesByReciverAndSender(reclamation.getUser().getId(),userDirecteur.getId());
        List<Messagerie> messagerieList = new ArrayList<>(messages);
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < messagerieList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/MessageItemComponentGui.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                MessageItemComponentController itemController = fxmlLoader.getController();
                itemController.setData(messagerieList.get(i));

                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



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

    private Reclamation reclamation;




    @FXML
    void sendMessage(ActionEvent event) {
        String messageContent = TFmessage.getText();
        // Vérifier si le champ de texte est vide
        if (isTextFieldEmpty(TFmessage)) {
            // Afficher une alerte pour informer l'utilisateur que le champ de texte est vide
            showAlert(Alert.AlertType.WARNING, "Champ vide", "Veuillez saisir un message.");
            // Mettre la bordure du champ de texte en rouge pour indiquer qu'il est vide
            TFmessage.setStyle("-fx-border-color: red;");
            return; // Sortir de la méthode sans envoyer le message
        }

        // Envoyer le message
        serviceMessagerie.ajouter(new Messagerie(sqlDate, messageContent, reclamation.getUser(), userDirecteur, "text"));
        // Réinitialiser le style du champ de texte à sa valeur par défaut
        TFmessage.setStyle(null);
        // Effacer le contenu du champ de texte après l'envoi du message avec succès
        TFmessage.clear();
        setData(reclamation);
    }

    public void setServiceReclamation(ServiceReclamation serviceReclamation) {
        this.serviceReclamation = serviceReclamation;
    }
    @FXML
    void buttonReturnAfficherReclamation(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherReclamationGui.fxml"));
            MainAnchorPaneBaladity.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
    private boolean isTextFieldEmpty(TextField textField) {
        return textField.getText().trim().isEmpty();
    }




}
