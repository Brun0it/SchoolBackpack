package com.cours.schoolbackpack.model;

import java.util.Calendar;
import java.util.Date;

public class Devoir {

    private Subject subject;
    private Calendar date;
    private String notes;
    private Boolean evaluation;
    private Boolean fait = false;

    public Devoir(Subject subject, Calendar date, String notes, Boolean evaluation) {
        this.subject = subject;
        this.date = date;
        this.notes = notes;
        this.evaluation = evaluation;
    }

    public Devoir(Subject subject, Calendar date, String notes, int fait, Boolean evaluation) {
        this.subject = subject;
        this.date = date;
        this.notes = notes;
        this.fait = (fait == 1);
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

    public void setFait(Boolean fait) {
        this.fait = fait;
    }

    public Boolean getFait() {
        return fait;
    }

    public int getFaitSQL() {
        if (fait) return 1;
        return 0;
    }
}
