package com.cours.schoolbackpack.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.cours.schoolbackpack.R;
import com.cours.schoolbackpack.controller.MainActivity;
import com.cours.schoolbackpack.model.DataBaseManager;
import com.cours.schoolbackpack.model.Subject;

public class LoadActivity extends AppCompatActivity {

    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
        DataBaseManager db = new DataBaseManager(this);

        //Remise à zéro de la BDD
        /*db.getWritableDatabase().execSQL("DROP TABLE MATIERE");
        db.getWritableDatabase().execSQL("DROP TABLE SEMAINE");
        db.getWritableDatabase().execSQL("DROP TABLE JOUR");
        db.getWritableDatabase().execSQL("DROP TABLE COURS");
        db.getWritableDatabase().execSQL("DROP TABLE DEVOIR");
        db.getWritableDatabase().execSQL("DROP TABLE EVALUATION");
        db.close();

        db = new DataBaseManager(this);
        String query = "CREATE TABLE MATIERE ("
                + "idMatiere INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nomMatiere VARCHAR(50) NOT NULL,"
                + "profMatiere VARCHAR(50) NOT NULL);";
        db.getWritableDatabase().execSQL(query);

        query = "CREATE TABLE SEMAINE ("
                + "idSemaine INTEGER PRIMARY KEY AUTOINCREMENT);";
        db.getWritableDatabase().execSQL(query);

        query = "CREATE TABLE JOUR ("
                + "idJours INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "idSemaine INTEGER REFERENCES SEMAINE(idSemaine));";
        db.getWritableDatabase().execSQL(query);

        query = "CREATE TABLE COURS ("
                + "idCours INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "idJour INTEGER REFERENCES JOURS(idJours),"
                + "salleCours VARCHAR(50) NOT NULL,"
                + "debutCours INTEGER NOT NULL,"
                + "dureeCours INTEGER NOT NULL,"
                + "idMatiere INTEGER REFERENCES MATIERE(idMatiere));";
        db.getWritableDatabase().execSQL(query);

        query = "CREATE TABLE DEVOIR ("
                + "idDevoir INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "jourDevoir INTEGER NOT NULL,"
                + "moisDevoir INTEGER NOT NULL,"
                + "anneeDevoir INTEGER NOT NULL,"
                + "travailDevoir VARCHAR(500) NOT NULL,"
                + "statutDevoir INTEGER NOT NULL,"
                + "idMatiere INTEGER REFERENCES MATIERE(idMatiere));";
        db.getWritableDatabase().execSQL(query);

        query = "CREATE TABLE EVALUATION ("
                + "idEvaluation INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "idCours INTEGER REFERENCES COURS(idCours),"
                + "dateEvaluation DATE NOT NULL,"
                + "travailEvaluation VARCHAR(500) NOT NULL);";
        db.getWritableDatabase().execSQL(query);
        db.close();

        db = new DataBaseManager(this);*/
        if (db.getSubjects().size() < 1) {
            db.addSubject(new Subject("Maths", "M. Walkowiak"));
            db.addSubject(new Subject("Anglais", "M. Roulin"));
            db.addSubject(new Subject("Français", "Mme Vieillard"));
            db.addSubject(new Subject("Allemand", "Mme Piau"));
            db.addSubject(new Subject("Sport", "M. Hamon"));
            db.addSubject(new Subject("Histoire Géo", "M. Venant"));
        }
        db.close();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    @Override
    public void onBackPressed() {

    }

}