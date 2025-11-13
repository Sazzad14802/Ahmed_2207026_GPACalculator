package com.example;

import com.example.Course;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class calcScreenController {
    @FXML
    private TextField txtTitle, txtCode, txtTeacher1, txtTeacher2, txtCredit;
    @FXML
    private ComboBox<String> comboGrade;
    private List<Course> courses = new ArrayList<>();


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
    private void calcGPA() {
        double totalPoints = 0;
        double totalCredits = 0;

        for (Course c : courses) {
            double points = gradeToPoint.getOrDefault(c.getGrade(), 0.0);
            totalPoints += points * c.getCredit();
            totalCredits += c.getCredit();
        }

        double gpa = totalPoints / totalCredits;
        System.out.println("GPA: " + gpa);

    }
}