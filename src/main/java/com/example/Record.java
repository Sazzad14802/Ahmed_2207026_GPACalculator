package com.example;

public class Record {
    private int id;
    private String roll;
    private double gpa;

    public Record(int id, String roll, double gpa) {
        this.id = id;
        this.roll = roll;
        this.gpa = gpa;
    }

    public int getId() { return id; }
    public String getRoll() { return roll; }
    public double getGpa() { return gpa; }

    public void setRoll(String roll) { this.roll = roll; }
    public void setGpa(double gpa) { this.gpa = gpa; }
}
