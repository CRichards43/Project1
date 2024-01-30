package com.example.project1;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;


public class Class {
    private String name;
    private String instructor;
    private ArrayList<DayOfWeek> days = new ArrayList<>();
    private String startTime;
    private String endTime;

    public Class(String name, String instructor, ArrayList<DayOfWeek> days, String startTime, String endTime) {
        this.name = name;
        this.instructor = instructor;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
