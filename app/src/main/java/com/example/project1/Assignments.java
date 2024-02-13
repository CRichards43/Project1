package com.example.project1;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;


public class Assignments implements Comparable<Assignments>{
    private String title;
    private LocalDate dueDate;
    private LocalTime time;
    private Class dueClass;
    private boolean isToDo;
    private boolean isComplete;
    public Assignments(Class dueClass, String title, LocalDate date, LocalTime time, boolean isToDo) {
        this.dueClass = dueClass;
        this.title = title;
        this.dueDate = date;
        this.time = time;
        this.isToDo = isToDo;
        isComplete = false;
    }

    @Override
    public int compareTo(Assignments o) {
        if(dueDate.compareTo(o.getDate()) != 0) {
            return dueDate.compareTo(o.getDate());
        }
        return time.compareTo(o.getTime());
    }

    public boolean isToDo() {
        return isToDo;
    }
    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return dueDate;
    }

    public Class getDueClass() {
        return dueClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDate(LocalDate date) {
        this.dueDate = date;
    }

    public void setClass(Class dueClass) {
        this.dueClass = dueClass;
    }

    public void setDueTime(LocalTime dueTime) {
        this.time = dueTime;
    }

    public String getTimeAsString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        return time.format(dateTimeFormatter);
    }
    public String getDueDateAsString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return dueDate.format(format);
    }

    public boolean isExam() {
        return this instanceof Exam;
    }

    public void complete() {
        isComplete = true;
    }

    public boolean isComplete() { return isComplete;
    }

}
