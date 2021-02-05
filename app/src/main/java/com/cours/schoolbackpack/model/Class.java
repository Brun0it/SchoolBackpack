package com.cours.schoolbackpack.model;

import java.sql.Time;
import java.util.Calendar;

public class Class {
    private Subject subject;
    private String classroom;
    private Calendar time;
    private int duration;

    public Class(Subject subject, String classroom, Calendar time, int duration) {
        this.subject = subject;
        this.classroom = classroom;
        this.time = time;
        this.duration = duration;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getClassroom() {
        return classroom;
    }


    public String getTime() {
        String hour = time.getTime().getHours() + "";
        String min = time.getTime().getMinutes() + "";
        if (time.getTime().getMinutes() == 0) min = "";
        if (min.length() == 1) min = 0 + "" + min;
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

    public int getDuration() {
        return duration;
    }
}
