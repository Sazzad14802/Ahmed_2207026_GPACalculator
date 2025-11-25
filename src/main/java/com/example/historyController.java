package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    }

    private void loadData() {
        tableHistory.setItems(ReportDAO.fetchReports());
    }

}
