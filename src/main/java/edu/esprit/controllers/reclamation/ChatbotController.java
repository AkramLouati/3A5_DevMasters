package edu.esprit.controllers.reclamation;

import edu.esprit.services.chatbot;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatbotController implements Initializable {

    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private VBox MainLeftSidebar;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private TextField TFmessage;

    @FXML
    private ScrollPane chatVbox;

    @FXML
    private BorderPane firstborderpane;

    @FXML
    private Label message;

    private boolean isSidebarVisible = true;
    private chatbot chatbot;


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser la taille du SecondBorderPane avec la même largeur que la barre latérale
        double sidebarWidth = MainLeftSidebar.getWidth();
        SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() + sidebarWidth);
        chatbot = new chatbot(); // Instanciation de la classe Chatbot


        // Ajouter cette ligne pour initialiser le contenu de la ScrollPane
        chatVbox.setContent(new VBox());

        // Commencer la conversation avec le premier texte
        String firstMessage = chatbot.firstText();
        addMessageToChat("Baladity", firstMessage);
    }

    @FXML
    void sendMessage(ActionEvent event) {
        String messageContent = TFmessage.getText();
        // Ajoutez le message de l'utilisateur au chat
        addMessageToChat("Moi", messageContent);
        // Obtenir la réponse du chatbot
        String réponseChatbot = chatbot.repondre(messageContent);
        // Ajouter la réponse du chatbot au chat
        addMessageToChat("Chatbot", réponseChatbot);
        // Effacez le champ de texte après l'envoi du message
        TFmessage.clear();
    }

    private void addMessageToChat(String sender, String message) {
        // Créer un label pour afficher le message
        Label messageLabel = new Label(sender + ": " + message);
        // Ajouter les styles CSS
        messageLabel.getStyleClass().addAll("message", "label-styled");
        // Permettre le retour à la ligne
        messageLabel.setWrapText(true);

        // Ajouter une nouvelle ligne pour le message du chatbot
        if (sender.equals("Chatbot")) {
            // Charger l'image du chatbot
            ImageView chatbotImage = new ImageView(new Image(getClass().getResourceAsStream("/assets/chatbot.png")));
            // Redimensionner l'image
            chatbotImage.setFitWidth(50); // Définir la largeur souhaitée
            chatbotImage.setFitHeight(50); // Définir la hauteur souhaitée
            // Définir l'image dans le label
            messageLabel.setGraphic(chatbotImage);
        }

        // Obtenez les enfants actuels de la zone de chat
        ObservableList<Node> chatContent = ((VBox) chatVbox.getContent()).getChildren();
        // Ajoutez le label au contenu actuel de la zone de chat
        chatContent.add(messageLabel);
    }
}





