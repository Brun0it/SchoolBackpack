package com.cours.schoolbackpack.model;

public class Subject {

    private String name;
    private String teacher;

    public Subject(String name, String teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }
}
