package com.example;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReportDAO {

    private static final String DB_URL = "jdbc:sqlite:gpa_reports.db";

    public ReportDAO() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement st = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS reports (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "roll TEXT NOT NULL," +
                    "gpa REAL NOT NULL)";
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertReport(String rollNumber, double gpa) {
        String sql = "INSERT INTO reports (roll, gpa) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, rollNumber);
            pst.setDouble(2, gpa);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Record> fetchReports() {
        ObservableList<Record> records = FXCollections.observableArrayList();
        String sql = "SELECT * FROM reports";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String roll = rs.getString("roll");
                double gpa = rs.getDouble("gpa");
                records.add(new Record(id, roll, gpa));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void updateReport(int id, String newRollNumber, double newGpa) {
        String sql = "UPDATE reports SET roll = ?, gpa = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, newRollNumber);
            pst.setDouble(2, newGpa);
            pst.setInt(3, id);
            int rows = pst.executeUpdate();
            System.out.println(rows + " row(s) updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteReport(int id) {
        String sql = "DELETE FROM reports WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            int rows = pst.executeUpdate();
            System.out.println(rows + " row(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
