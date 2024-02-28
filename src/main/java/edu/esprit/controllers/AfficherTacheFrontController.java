package edu.esprit.controllers;

import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceTache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AfficherTacheFrontController implements Initializable {

    public TextField todoCount;
    public TextField doingCount;
    public TextField doneCount;
    @FXML
    private GridPane gridTodo;

    @FXML
    private GridPane gridDoing;

    @FXML
    private GridPane gridDone;

    private ServiceTache sr = new ServiceTache();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTasks();
    }

    private void loadTasks() {
        Set<Tache> tacheList = sr.getAll();
        int todoCounter = 0;
        int doingCounter = 0;
        int doneCounter = 0;

        try {
            for (Tache tache : tacheList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TacheFront.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                TacheFrontController itemController = fxmlLoader.getController();
                itemController.setData(tache);

                switch (tache.getEtat_T()) {
                    case TO_DO:
                        gridTodo.addRow(gridTodo.getRowCount(), anchorPane);
                        todoCounter++;
                        break;
                    case DOING:
                        gridDoing.addRow(gridDoing.getRowCount(), anchorPane);
                        doingCounter++;
                        break;
                    case DONE:
                        gridDone.addRow(gridDone.getRowCount(), anchorPane);
                        doneCounter++;
                        break;
                }
            }
            todoCount.setText("TO DO | " + todoCounter);
            doingCount.setText("DOING | " + doingCounter);
            doneCount.setText("DONE | " + doneCounter);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load tasks.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void ajouterButton(ActionEvent event) {
        // Implement your logic here
    }

    @FXML
    void searchTacheLabel(ActionEvent event) {
        // Implement your logic here
    }
}
