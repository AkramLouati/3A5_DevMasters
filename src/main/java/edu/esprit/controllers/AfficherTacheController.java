package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceCategorieT;
import edu.esprit.services.ServiceTache;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AfficherTacheController implements Initializable {

    public BorderPane firstborderpane;
    EndUser user = new EndUser(12);
    private ServiceCategorieT serviceCategorieT;
    @FXML
    private TextField searchbar;
    @FXML
    private AnchorPane MainAnchorPaneBaladity;
    @FXML
    private BorderPane SecondBorderPane;
    @FXML
    private VBox MainLeftSidebar;
    private boolean isSidebarVisible = true;
    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    private Tache taches = new Tache();
    private Stage stage; // Define a stage variable
    private ServiceTache sr = new ServiceTache();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchbar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Trigger searchTacheLabel method whenever text changes
                searchTacheLabel(new ActionEvent());
            }
        });
        loadTasks();
    }

    private void loadTasks() {
        Set<Tache> tacheList = sr.getAll();
        try {
            for (Tache tache : tacheList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Tache.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                TacheController itemController = fxmlLoader.getController();
                itemController.setData(tache);


                grid.addRow(grid.getRowCount(), anchorPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            TranslateTransition borderPaneTransition = new TranslateTransition(Duration.millis(250), SecondBorderPane);
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

    public void ajouterButton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTache.fxml"));
            Parent root = loader.load();
            AjouterTacheController controller = loader.getController();

            // Get the stage of the current scene
            Stage currentStage = (Stage) grid.getScene().getWindow();

            // Set the stage to the controller
            controller.setStage(currentStage);

            // Create a new scene with the root and set it on the current stage
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load AjouterTache view");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void PDFTacheLabel(ActionEvent actionEvent) {
        // Get all tasks from the service
        Set<Tache> allTasks = sr.getAll();

        try {
            // Create a new PDF document
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a content stream for writing to the PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set font and position for writing
            // Using a different font that supports the required characters
            // For example, PDType1Font.HELVETICA
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            // Set text color
            contentStream.setNonStrokingColor(Color.BLACK);

            // Starting position for writing
            float yPosition = page.getMediaBox().getHeight() - 50; // Start from the top of the page
            float margin = 50; // Left margin

            // Write tasks to the PDF
            for (Tache task : allTasks) {
                // Write the task details
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Title: " + task.getTitre_T() + ", Etat Tache: " + task.getEtat_T());
                contentStream.endText();

                // Move to the next line for the next task
                yPosition -= 20; // Adjust line spacing as needed
            }

            contentStream.close();

            // Prompt the user to choose where to save the PDF file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showSaveDialog(stage);

            if (selectedFile != null) {
                // Save the document
                document.save(selectedFile);
                document.close();

                showAlert(Alert.AlertType.INFORMATION, "Success", "Tasks exported to PDF successfully!");
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "No file selected. PDF export canceled.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to export tasks to PDF");
        }
    }

    @FXML
    void CategorieLabel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCategorie.fxml"));
            Parent root = loader.load();
            AfficherCategorieController controller = loader.getController();

            // You might need to pass any necessary data or dependencies to the controller here

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void MeilleurEmpTLabel(ActionEvent actionEvent) {
    }

    @FXML
    void searchTacheLabel(ActionEvent actionEvent) {
        String searchText = searchbar.getText().toLowerCase().trim();
        Set<Tache> allTasks = sr.getAll();
        Set<Tache> filteredTasks = new HashSet<>();

        for (Tache task : allTasks) {
            if (task.getTitre_T().toLowerCase().contains(searchText)) { // Assuming getType() returns the type of the task
                filteredTasks.add(task);
            }
        }

        displayTasks(filteredTasks);
    }

    private void displayTasks(Set<Tache> tasks) {
        grid.getChildren().clear(); // Clear existing tasks
        try {
            for (Tache tache : tasks) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Tache.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                TacheController itemController = fxmlLoader.getController();
                itemController.setData(tache);
                grid.addRow(grid.getRowCount(), anchorPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
