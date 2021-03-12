package com.cours.schoolbackpack.model;

import android.util.Log;

import java.sql.Time;
import java.util.Calendar;

public class Class {
    private int id;
    private int idJour;
    private Subject subject;
    private String classroom;
    private Calendar time;
    private int duration;

    public int getIdJour() {
        return idJour;
    }

    public Class(int idJour, Subject subject, String classroom, Calendar time, int duration) {
        this.idJour = idJour;
        this.subject = subject;
        this.classroom = classroom;
        this.time = time;
        this.duration = duration;
    }

    public Class(int id, int idJour, Subject subject, String classroom, Calendar time, int duration) {
        this(idJour, subject, classroom, time, duration);
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getClassroom() {
        return classroom;
    }

    public Calendar getTime() {
        return time;
    }

    public String getTimeStringFormat() {
        String hour = time.get(Calendar.HOUR_OF_DAY) + "";
        String min = time.get(Calendar.MINUTE) + "";
        if (time.getTime().getMinutes() == 0) min = "";
        if (min.length() == 1) min = 0 + "" + min;
        return hour + "h" + min;
    }

    public String getFullTime() {
        String hour = time.get(Calendar.HOUR_OF_DAY) + "";
        String min = time.get(Calendar.MINUTE) + "";
        if (hour.length() == 1) hour = "0" + hour;
        if (min.length() == 1) min = "0" + min;
        return hour + "h" + min;
    }

    public String getFinalTime() {
        Calendar finalTime = Calendar.getInstance();
        finalTime.set(Calendar.HOUR_OF_DAY, time.getTime().getHours());
        finalTime.set(Calendar.MINUTE, time.getTime().getMinutes());
        finalTime.add(Calendar.MINUTE, duration);
        String hour = finalTime.getTime().getHours() + "";
        String min = finalTime.getTime().getMinutes() + "";
        if (finalTime.getTime().getMinutes() == 0) min = "";
        if (min.length() == 1) min = 0 + "" + min;
        return hour + "h" + min;
    }

    public String getFullFinalTime() {
        Calendar finalTime = Calendar.getInstance();
        finalTime.set(Calendar.HOUR_OF_DAY, time.getTime().getHours());
        finalTime.set(Calendar.MINUTE, time.getTime().getMinutes());
        finalTime.add(Calendar.MINUTE, duration);
        String hour = finalTime.get(Calendar.HOUR_OF_DAY) + "";
        String min = finalTime.get(Calendar.MINUTE) + "";
        if (hour.length() == 1) hour = "0" + hour;
        if (min.length() == 1) min = "0" + min;
        return hour + "h" + min;
    }

    public int getDuration() {
        return duration;
    }

    public int getTimeSQL() {
        return this.time.get(Calendar.HOUR_OF_DAY) * 60 + this.time.get(Calendar.MINUTE);
    }

    public int getId() {
        return id;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Class{" +
                "idJours=" + idJour +
                ", subject=" + subject.getName() + " with " + subject.getTeacher() +
                ", classroom='" + classroom + '\'' +
                ", time=" + time.get(Calendar.HOUR_OF_DAY) + "h" + time.get(Calendar.MINUTE) +
                ", duration=" + duration + "min" +
                '}';
    }
}
