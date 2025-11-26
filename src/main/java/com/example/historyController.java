package com.example;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

public class historyController {
    @FXML
    private void switchToHomeScreen() throws IOException {
        App.setRoot("homeScreen");
    }

    @FXML
    private TableView<Record> tableHistory;
    @FXML
    private TableColumn<Record, String> colRoll;
    @FXML
    private TableColumn<Record, Double> colGpa;

    @FXML
    public void initialize() {
        colRoll.setCellValueFactory(new PropertyValueFactory<>("roll"));
        colGpa.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        loadData();

        tableHistory.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("DELETE")) {
                Record selected = tableHistory.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    ReportDAO.deleteReport(selected.getId());
                    tableHistory.getItems().remove(selected);
                }
            }
        });
        tableHistory.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // DOUBLE CLICK
                Record selected = tableHistory.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    editRecord(selected);
                }
            }
        });
    }

    private void editRecord(Record rec) {
        TextInputDialog gpaDialog = new TextInputDialog(String.valueOf(rec.getGpa()));
        gpaDialog.setTitle("Edit GPA");
        gpaDialog.setHeaderText("Enter new GPA of roll " + rec.getRoll() + " :");
        String newGpaStr = gpaDialog.showAndWait().orElse(null);
        if (newGpaStr == null)
            return;

        double newGpa = Double.parseDouble(newGpaStr);
        ReportDAO.updateReport(rec.getId(), rec.getRoll(), newGpa);
        rec.setGpa(newGpa);
        tableHistory.refresh();
    }

    private void loadData() {
        Task<ObservableList<Record>> task = new Task<>() {
            @Override
            protected ObservableList<Record> call() {
                return ReportDAO.fetchReports();
            }
        };

        task.setOnSucceeded(e -> {
            tableHistory.setItems(task.getValue());
        });

        task.setOnFailed(e -> {
            new Alert(Alert.AlertType.ERROR, "Failed to load data!").showAndWait();
        });

        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(task);
        exec.shutdown();
    }
}
