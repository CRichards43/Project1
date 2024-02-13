package com.example.project1;
import androidx.annotation.Nullable;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;


public class Class {
    private String name;
    private String instructor;
    private ArrayList<DayOfWeek> days = new ArrayList<>();
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;

    public Class(String name, String instructor, ArrayList<DayOfWeek> days, LocalTime startTime, LocalTime endTime, String location) {
        this.name = name;
        this.instructor = instructor;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }


    public String getProf() {
        return instructor;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public ArrayList<DayOfWeek> getDays() {
        return days;
    }

    public String getLocation() {
        return location;
    }

    public String getClassName() {
        return name;
    }

    public String toString() {
        return getClassName();
    }

    public void setDays(ArrayList<DayOfWeek> addedDays) {
        this.days = addedDays;
    }

    public void setClassName(String classNameAdded) {
        this.name = classNameAdded;
    }

    public void setProfName(String profNameAdded) {
        this.instructor = profNameAdded;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setLocationfName(String locNameAdded) {
        this.location = locNameAdded;
    }

    public String getDaysToString() {
        String daysString = "";
        for(int i =0; i < days.size(); i++) {
            if(days.get(i).equals(DayOfWeek.MONDAY)) {
                daysString = daysString + "Mon";
            }
            if(days.get(i).equals(DayOfWeek.TUESDAY)) {
                daysString = daysString + "Tues";
            }
            if(days.get(i).equals(DayOfWeek.WEDNESDAY)) {
                daysString = daysString + "Wed";
            }
            if(days.get(i).equals(DayOfWeek.THURSDAY)) {
                daysString = daysString + "Thur";
            }
            if(days.get(i).equals(DayOfWeek.FRIDAY)) {
                daysString = daysString + "Fri";
            }
            if(i < days.size() - 1) {
                daysString = daysString + ", ";
            }
        }
        return daysString;
    }

    public String getTimeAsString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        return startTime.format(dateTimeFormatter) + " to " + endTime.format(dateTimeFormatter);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(super.equals(obj)) {
            return true;
        }
        Class other = (Class) obj;
        if(this.name.equals(other.getClassName())){
            int size = 132;
            size++;
            boolean returned  = true;
            return true;
        }
        return false;
    }
}
