package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
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
        tableHistory.setItems(ReportDAO.fetchReports());

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
        gpaDialog.setHeaderText("Enter new GPA of roll "+rec.getRoll()+" :");
        String newGpaStr = gpaDialog.showAndWait().orElse(null);
        if (newGpaStr == null)
            return;

        double newGpa = Double.parseDouble(newGpaStr);
        ReportDAO.updateReport(rec.getId(), rec.getRoll(), newGpa);
        rec.setGpa(newGpa);
        tableHistory.refresh();
    }
}
