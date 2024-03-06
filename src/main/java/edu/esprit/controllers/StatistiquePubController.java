package edu.esprit.controllers;

import edu.esprit.services.ServicePublicite;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class StatistiquePubController implements Initializable {

    @FXML
    private AnchorPane MainAnchorPaneBaladity;

    @FXML
    private VBox MainLeftSidebar;

    @FXML
    private BorderPane SecondBorderPane;

    @FXML
    private AnchorPane StatistiquePub;

    @FXML
    private BorderPane firstborderpane;

    private boolean isSidebarVisible = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the size of SecondBorderPane with the same width as the sidebar
        double sidebarWidth = MainLeftSidebar.getWidth();
        SecondBorderPane.setPrefWidth(SecondBorderPane.getWidth() + sidebarWidth);

        // Create SwingNode for pie chart
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(createSwingContent());
        StatistiquePub.getChildren().add(swingNode);
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

    @FXML
    void BTNGestionEvennement(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Info", "Gestion des Événements");
    }

    @FXML
    void BTNGestionUser(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Info", "Gestion des Utilisateurs");
    }

    @FXML
    void BTNGestionRec(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Info", "Gestion des Réclamations");
    }

    @FXML
    void BTNGestionAct(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Info", "Gestion des Actualités");
    }

    @FXML
    void BTNGestionEquipement(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Info", "Gestion des Équipements");
    }

    @FXML
    void BTNGestionTache(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Info", "Gestion des Tâches");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private JFreeChart createPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        ServicePublicite servicePublicite=new ServicePublicite();


             int countOffer3Months = servicePublicite.getCountPubByOffer("3 mois :50dt");
             int countOffer6Months = servicePublicite.getCountPubByOffer("6 mois :90dt");
             int countOffer9Months = servicePublicite.getCountPubByOffer("9 mois :130dt");
               int total= countOffer3Months + countOffer6Months + countOffer9Months;


        double pourcentageTraitees = (double) countOffer3Months / total * 100;
        double pourcentageEnCours = (double) countOffer6Months / total * 100;
        double pourcentageNonTraitees = (double) countOffer9Months / total * 100;

        dataset.setValue("offre1 (" + String.format("%.2f", pourcentageTraitees) + "%)", countOffer3Months);
        dataset.setValue("offre2(" + String.format("%.2f", pourcentageEnCours) + "%)", countOffer6Months);
        dataset.setValue("offre3(" + String.format("%.2f", pourcentageNonTraitees) + "%)", countOffer9Months);

        return ChartFactory.createPieChart(
                "Statistiques des publicites par statut",
                dataset,
                true,
                true,
                false
        );
    }

    private JComponent createSwingContent() {
        // Wrap the JFreeChart ChartPanel inside a SwingNode
        ChartPanel chartPanel = new ChartPanel(createPieChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 300));

        // Ensure Swing-related code runs on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Set the preferred size for the ChartPanel
            chartPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        });

        return chartPanel;
    }








}
