package com.cours.schoolbackpack.ui;

import com.cours.schoolbackpack.Day;

import java.util.ArrayList;
import java.util.List;

public class Week {

    List<Day> days = new ArrayList<>();

    public Week() {
    }

    public Week(List<Day> days) {
        this.days = days;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public void add(Day days) {
        this.days.add(days);
    }
}
