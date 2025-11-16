package com.example;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class reportController {
    @FXML
    private Label gpaLabel;
    @FXML
    private TableView<Course> tableReport;
    @FXML
    private TableColumn<Course, String> colCourseRep;
    @FXML
    private TableColumn<Course, Double> colCreditRep;
    @FXML
    private TableColumn<Course, String> colGradeRep;

    public void setData(List<Course> courses, double gpa) {
        gpaLabel.setText("GPA: " + String.format("%.2f", gpa));
        colCourseRep.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCreditRep.setCellValueFactory(new PropertyValueFactory<>("credit"));
        colGradeRep.setCellValueFactory(new PropertyValueFactory<>("grade"));
        tableReport.getItems().addAll(courses);
    }
}
