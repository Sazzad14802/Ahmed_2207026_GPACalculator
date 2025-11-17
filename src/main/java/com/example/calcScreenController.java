package com.example;

import java.io.IOException;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class calcScreenController {
    @FXML
    private TextField txtTitle, txtCode, txtTeacher1, txtTeacher2, txtCredit;
    @FXML
    private ComboBox<String> comboGrade;
    private ObservableList<Course> courses = FXCollections.observableArrayList();
    @FXML
    private TableView<Course> tableCourses;
    @FXML
    private TableColumn<Course, String> colTitle;
    @FXML
    private TableColumn<Course, Double> colCredit;
    @FXML
    private TableColumn<Course, String> colGrade;

    @FXML
    public void initialize() {

        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        tableCourses.setItems(courses);

        comboGrade.getItems().addAll("A+", "A", "A-", "B+", "B", "C", "F");
        comboGrade.setValue("A+"); // default

        txtCredit.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*(\\.\\d*)?")) { // regex: digits with optional decimal
                return change; // accept change
            } else {
                return null; // reject change
            }
        }));

        tableCourses.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("DELETE")) {
                Course selected = tableCourses.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    tableCourses.getItems().remove(selected);
                    new Alert(Alert.AlertType.INFORMATION, "Course removed!").showAndWait();
                }
            }
        });
    }

    @FXML
    private void switchToHomeScreen() throws IOException {
        App.setRoot("homeScreen");
    }

    @FXML
    private void addCourse() {
        Course c = new Course(
                txtTitle.getText(),
                txtCode.getText(),
                txtTeacher1.getText(),
                txtTeacher2.getText(),
                Double.parseDouble(txtCredit.getText()),
                comboGrade.getValue());
        courses.add(c);

        new Alert(Alert.AlertType.INFORMATION, "Course added!").showAndWait();

        txtTitle.clear();
        txtCode.clear();
        txtTeacher1.clear();
        txtTeacher2.clear();
        txtCredit.clear();
        comboGrade.setValue("A+");
    }

    private static final Map<String, Double> gradeToPoint = Map.of(
            "A+", 4.0,
            "A", 3.75,
            "A-", 3.5,
            "B+", 3.25,
            "B", 3.0,
            "B-", 2.75,
            "C+", 2.5,
            "C", 2.25,
            "D", 2.0,
            "F", 0.0);

    @FXML
    private void calcGPA() throws IOException {
        double totalPoints = 0;
        double totalCredits = 0;

        for (Course c : courses) {
            double points = gradeToPoint.getOrDefault(c.getGrade(), 0.0);
            totalPoints += points * c.getCredit();
            totalCredits += c.getCredit();
        }

        double gpa = totalPoints / totalCredits;
        System.out.println("GPA: " + gpa);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gpaReport.fxml"));
        Parent root = loader.load();

        reportController controller = loader.getController();
        controller.setData(courses, gpa);

        Stage stage2 = new Stage();
        stage2.setTitle("GPA Report");
        stage2.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        stage2.setScene(new Scene(root));
        stage2.show();

    }
}