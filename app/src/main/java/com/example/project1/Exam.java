package com.example.project1;

import java.time.LocalDate;
import java.time.LocalTime;

public class Exam extends Assignments{
    private String location;
    public Exam(Class dueClass, String title, LocalDate date, LocalTime time, String location) {
        super(dueClass, title, date, time, false);
        this.location = location;
    }

    public void setLocation(String locNameAdded) {
        this.location = locNameAdded;
    }

    public String getLocation() {
        return location;
    }
}
