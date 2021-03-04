package com.cours.schoolbackpack.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

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
                + "idJours INTEGER REFERENCES JOURS(idJours),"
                + "salleCours VARCHAR(50) NOT NULL,"
                + "debutCours DATE NOT NULL,"
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
                + "travailEvaluation VARCHAR(500) NOT NULL,"
                + "idCours INTEGER REFERENCES SEMAINE(idSemaine));";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
