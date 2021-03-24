package com.theog.schoolbackpack.model;

public class Subject {

    private int id;
    private String name;
    private String teacher;

    public Subject(int id, String name, String teacher) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
    }

    public Subject(String name, String teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
