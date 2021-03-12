package com.cours.schoolbackpack.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataBaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SchoolBP.db";

    private static final int DATABASE_VERSION = 1;

    public DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE MATIERE ("
                + "idMatiere INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nomMatiere VARCHAR(50) NOT NULL,"
                + "profMatiere VARCHAR(50) NOT NULL);";
        db.execSQL(query);

        query = "CREATE TABLE SEMAINE ("
                + "idSemaine INTEGER PRIMARY KEY AUTOINCREMENT);";
                db.execSQL(query);

        query = "CREATE TABLE JOUR ("
                + "idJours INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "idSemaine INTEGER REFERENCES SEMAINE(idSemaine));";
        db.execSQL(query);

        query = "CREATE TABLE COURS ("
                + "idCours INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "idJour INTEGER REFERENCES JOURS(idJours),"
                + "salleCours VARCHAR(50) NOT NULL,"
                + "debutCours INTEGER NOT NULL,"
                + "dureeCours INTEGER NOT NULL,"
                + "idMatiere INTEGER REFERENCES MATIERE(idMatiere));";
        db.execSQL(query);

        query = "CREATE TABLE DEVOIR ("
                + "idDevoir INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "dateDevoir DATE NOT NULL,"
                + "travailDevoir VARCHAR(500) NOT NULL,"
                + "idCours INTEGER REFERENCES COURS(idCours));";
        db.execSQL(query);

        query = "CREATE TABLE EVALUATION ("
                + "idEvaluation INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "idCours INTEGER REFERENCES COURS(idCours),"
                + "dateEvaluation DATE NOT NULL,"
                + "travailEvaluation VARCHAR(500) NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addClass(Class cours) {
        if (cours != null) {
            String query = "INSERT INTO COURS (idJour, salleCours, debutCours, dureeCours, idMatiere) VALUES (" + cours.getIdJour() + ", '" + cours.getClassroom().replace("'","''") + "',"
                    + cours.getTimeSQL() + ", " + cours.getDuration() + ", "
                    + cours.getSubject().getId() +");";
            getWritableDatabase().execSQL(query);
        }
    }

    public Class getClass(int id) {
        String query = "SELECT * FROM COURS WHERE idCours = "+ id +";";
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        Calendar calendar = Calendar.getInstance();
        int hours = (int) (cursor.getInt(3) - cursor.getInt(3) % 60) / 60;
        int minute = cursor.getInt(3) % 60;
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minute);
        Class aClass = new Class(cursor.getInt(0), cursor.getInt(1), getSubject(cursor.getInt(5)), cursor.getString(2), calendar, cursor.getInt(4));
        cursor.close();
        return aClass;
    }

    public List<Class> getClasses(int idJour) {
        List<Class> classes = new ArrayList<>();
        String query = "SELECT * FROM COURS WHERE idJour = " + idJour + ";";
        Cursor cursor = getWritableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            classes.add(getClass(id));
            cursor.moveToNext();
        }
        return classes;
    }

    public List<List<Class>> getShortedClasses() {
        List<List<Class>> classes = new ArrayList<>();
        classes.add(getClasses(Calendar.MONDAY));
        classes.add(getClasses(Calendar.TUESDAY));
        classes.add(getClasses(Calendar.WEDNESDAY));
        classes.add(getClasses(Calendar.THURSDAY));
        classes.add(getClasses(Calendar.FRIDAY));

        for (int i = 0; i < classes.size(); i++) {
            List<Class> selectedClasses = classes.get(i);
            for (int j = 0; j < selectedClasses.size()-1; j++) {
                if (selectedClasses.get(j).getTime().after(selectedClasses.get(j+1).getTime())) {
                    Class tmpClass = selectedClasses.get(j);
                    selectedClasses.set(j, selectedClasses.get(j+1));
                    selectedClasses.set(j+1, tmpClass);
                }
            }
            Log.e("DBManager", i + "");
        }
        return classes;
    }

    public List<Class> getShortedClasses(int idJour) {
        List<Class> classes = getClasses(idJour);
        for (int j = 0; j < classes.size()-1; j++) {
            if (classes.get(j).getTime().after(classes.get(j+1).getTime())) {
                Class tmpClass = classes.get(j);
                classes.set(j, classes.get(j+1));
                classes.set(j+1, tmpClass);
            }
        }
        return classes;
    }

    public void updateClass(Class aClass) {
        String query = "UPDATE COURS SET salleCours = '"+ aClass.getClassroom().replace("'","''") +"' WHERE idCours = "+ aClass.getId() +";";
        getWritableDatabase().execSQL(query);
        query = "UPDATE COURS SET debutCours = "+ aClass.getTimeSQL() +" WHERE idCours = "+ aClass.getId() +";";
        getWritableDatabase().execSQL(query);
        query = "UPDATE COURS SET dureeCours = "+ aClass.getDuration() +" WHERE idCours = "+ aClass.getId() +";";
        getWritableDatabase().execSQL(query);
        query = "UPDATE COURS SET idMatiere = "+ aClass.getSubject().getId() +" WHERE idCours = "+ aClass.getId() +";";
        getWritableDatabase().execSQL(query);
    }

    public void deleteClass(int id) {
        String query = "DELETE FROM COURS WHERE idCours = "+ id +";";
        getWritableDatabase().execSQL(query);
    }

    public void deleteAllClass() {
        String query = "DELETE FROM COURS";
        getWritableDatabase().execSQL(query);
    }

    public void addSubject(Subject subject) {
        if (subject != null) {
            String query = "INSERT INTO MATIERE (nomMatiere, profMatiere) VALUES ('" + subject.getName().replace("'","''") + "','"
                    + subject.getTeacher().replace("'","''") +"');";
            getWritableDatabase().execSQL(query);
        }
    }

    public Subject getSubject(int id) {
        String query = "SELECT * FROM MATIERE WHERE idMatiere = "+ id +";";
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        Subject subject = new Subject(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        cursor.close();
        return subject;
    }

    public List<Subject> getSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String query = "SELECT * FROM MATIERE";
        Cursor cursor = getWritableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            subjects.add(getSubject(id));
            cursor.moveToNext();
        }
        return subjects;
    }
}
