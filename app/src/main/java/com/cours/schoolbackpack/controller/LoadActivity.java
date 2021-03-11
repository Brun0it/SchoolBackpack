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