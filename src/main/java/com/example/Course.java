package com.example;

public class Course {
    private String title;
    private String code;
    private String teacher1;
    private String teacher2;
    private double credit;
    private String grade;

    public Course(String title, String code, String teacher1, String teacher2, double credit, String grade) {
        this.title = title;
        this.code = code;
        this.teacher1 = teacher1;
        this.teacher2 = teacher2;
        this.credit = credit;
        this.grade = grade;
    }
    public String getTitle() { return title; }
    public String getCode() { return code; }
    public String getTeacher1() { return teacher1; }
    public String getTeacher2() { return teacher2; }
    public double getCredit() { return credit; }
    public String getGrade() { return grade; }
}
