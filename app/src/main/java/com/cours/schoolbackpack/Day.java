package com.cours.schoolbackpack;

import java.util.ArrayList;
import java.util.List;

public class Day {
    List<Class> classes = new ArrayList<>();

    public Day() {
    }

    public Day(List<Class> classes) {
        this.classes = classes;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public void add(Class aClass) {
        classes.add(aClass);
    }
}
