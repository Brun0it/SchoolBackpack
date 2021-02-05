package com.cours.schoolbackpack.model;

import java.util.Calendar;

public class Devoir {

    private Subject subject;
    private Calendar date;
    private String notes;
    private Boolean evaluation;

    public Devoir(Subject subject, Calendar date, String notes, Boolean evaluation) {
        this.subject = subject;
        this.date = date;
        this.notes = notes;
        this.evaluation = evaluation;
    }

    public Subject getSubject() {
        return subject;
    }

    public Calendar getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }

    public Boolean isEvaluation() {
        return evaluation;
    }
}
