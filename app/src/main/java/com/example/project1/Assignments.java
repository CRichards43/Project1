package com.example.project1;


import java.time.LocalDate;
import java.time.LocalTime;



public class Assignments implements Comparable<Assignments>{
    private String title;
    private LocalDate dueDate;
    private String time;
    private Class dueClass;
    private boolean isToDo;
    public Assignments(Class dueClass, String title, LocalDate date,String time, boolean isToDo) {
        this.dueClass = dueClass;
        this.title = title;
        this.dueDate = date;
        this.time = time;
        this.isToDo = isToDo;
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
    public String getTime() {
        return time;
    }

    public LocalDate getDate() {
        return dueDate;
    }

    public Class getDueClass() {
        return dueClass;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(LocalDate date) {
        this.dueDate = date;
    }

    public void setClass(Class dueClass) {
        this.dueClass = dueClass;
    }

}
