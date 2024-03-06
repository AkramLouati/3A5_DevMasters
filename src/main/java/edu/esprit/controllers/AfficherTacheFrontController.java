package edu.esprit.controllers;

import edu.esprit.entities.Tache;
import edu.esprit.services.EtatTache;
import edu.esprit.services.ServiceTache;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AfficherTacheFrontController implements Initializable {

    private final ServiceTache ST = new ServiceTache();
    public TextField todoCount;
    public TextField doingCount;
    public TextField doneCount;
    @FXML
    private TextField searchTacheLabel;
    @FXML
    private GridPane TO_DO;
    @FXML
    private GridPane DOING;
    @FXML
    private GridPane DONE;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enableGridDropping(TO_DO);
        enableGridDropping(DOING);
        enableGridDropping(DONE);
        loadTasks();
        // Add listener to searchTextField
        searchTacheLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Trigger searchTacheLabel method whenever text changes
                searchTacheLabel(new ActionEvent());
            }
        });
    }

    private void refreshAllGrids() {
        TO_DO.getChildren().clear();
        DOING.getChildren().clear();
        DONE.getChildren().clear();
        loadTasks();
    }

    private void loadTasks() {
        Set<Tache> tacheList = ST.getAll();
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
                        TO_DO.addRow(TO_DO.getRowCount(), anchorPane);
                        todoCounter++;
                        break;
                    case DOING:
                        DOING.addRow(DOING.getRowCount(), anchorPane);
                        doingCounter++;
                        break;
                    case DONE:
                        DONE.addRow(DONE.getRowCount(), anchorPane);
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

    private void enableGridDropping(GridPane gridPane) {
        gridPane.setOnDragOver(event -> {
            if (event.getGestureSource() != gridPane && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        gridPane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                int taskId = Integer.parseInt(db.getString());
                Tache task = ST.getOneByID(taskId);
                if (task != null) {
                    // Assuming the grid's ID corresponds to the new state of the task
                    EtatTache newState = EtatTache.valueOf(gridPane.getId());
                    task.setEtat_T(newState);
                    ST.modifier(task); // Update the task state in the database
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
            if (success) {
                refreshAllGrids(); // Refresh all grids after successful drop
            }
        });
    }

    //    @FXML
//    void searchTacheLabel(ActionEvent event) {
//        String query = searchTacheLabel.getText().trim().toLowerCase(); // Get search query
//        Set<Tache> tacheList = ST.getAll();
//        int todoCounter = 0;
//        int doingCounter = 0;
//        int doneCounter = 0;
//
//        try {
//            TO_DO.getChildren().clear(); // Clear existing rows
//            DOING.getChildren().clear();
//            DONE.getChildren().clear();
//
//            for (Tache tache : tacheList) {
//                // Check if task label contains the search query
//                if (tache.getTitre_T().toLowerCase().contains(query)) {
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TacheFront.fxml"));
//                    AnchorPane anchorPane = fxmlLoader.load();
//                    TacheFrontController itemController = fxmlLoader.getController();
//                    itemController.setData(tache);
//
//                    switch (tache.getEtat_T()) {
//                        case TO_DO:
//                            TO_DO.addRow(TO_DO.getRowCount(), anchorPane);
//                            todoCounter++;
//                            break;
//                        case DOING:
//                            DOING.addRow(DOING.getRowCount(), anchorPane);
//                            doingCounter++;
//                            break;
//                        case DONE:
//                            DONE.addRow(DONE.getRowCount(), anchorPane);
//                            doneCounter++;
//                            break;
//                    }
//                }
//            }
//            // Update counts
//            todoCount.setText("TO DO | " + todoCounter);
//            doingCount.setText("DOING | " + doingCounter);
//            doneCount.setText("DONE | " + doneCounter);
//        } catch (IOException e) {
//            e.printStackTrace();
//            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load tasks.");
//        }
//    }
    @FXML
    void searchTacheLabel(ActionEvent event) {
        String query = searchTacheLabel.getText().trim().toLowerCase(); // Get search query
        Set<Tache> tacheList = ST.getAll();
        int[] counters = new int[EtatTache.values().length];

        try {
            TO_DO.getChildren().clear(); // Clear existing rows
            DOING.getChildren().clear();
            DONE.getChildren().clear();

            tacheList.stream()
                    .filter(tache -> tache.getTitre_T().toLowerCase().contains(query))
                    .forEach(tache -> {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TacheFront.fxml"));
                            AnchorPane anchorPane = fxmlLoader.load();
                            TacheFrontController itemController = fxmlLoader.getController();
                            itemController.setData(tache);

                            GridPane targetGrid;
                            switch (tache.getEtat_T()) {
                                case TO_DO:
                                    targetGrid = TO_DO;
                                    break;
                                case DOING:
                                    targetGrid = DOING;
                                    break;
                                case DONE:
                                    targetGrid = DONE;
                                    break;
                                default:
                                    targetGrid = null;
                            }

                            if (targetGrid != null) {
                                targetGrid.addRow(targetGrid.getRowCount(), anchorPane);
                                counters[tache.getEtat_T().ordinal()]++;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load tasks.");
                        }
                    });

            // Update counts
            for (EtatTache etatTache : EtatTache.values()) {
                int index = etatTache.ordinal();
                switch (etatTache) {
                    case TO_DO:
                        todoCount.setText("TO DO | " + counters[index]);
                        break;
                    case DOING:
                        doingCount.setText("DOING | " + counters[index]);
                        break;
                    case DONE:
                        doneCount.setText("DONE | " + counters[index]);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load tasks.");
        }
    }

}
