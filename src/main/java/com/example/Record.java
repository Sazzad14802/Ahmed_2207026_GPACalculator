package com.example;

public class Record {
        private String roll;
        private double gpa;

        public Record(String roll, double gpa) {
            this.roll = roll;
            this.gpa = gpa;
        }

        public String getRoll() {
            return roll;
        }

        public void setRoll(String roll) {
            this.roll = roll;
        }

        public double getGpa() {
            return gpa;
        }

        public void setGpa(double gpa) {
            this.gpa = gpa;
        }
    }
