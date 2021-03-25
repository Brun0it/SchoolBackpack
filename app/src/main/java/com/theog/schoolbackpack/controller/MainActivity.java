package com.theog.schoolbackpack.controller;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.theog.schoolbackpack.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    protected boolean backToDo = false;
    private ConstraintLayout mainPage, edtPage, matPage;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_devoir, R.id.navigation_edt, R.id.navigation_profil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        navView.setOnNavigationItemReselectedListener(this);
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {

    }

    public void init(ConstraintLayout mainPage, ConstraintLayout edtPage, ConstraintLayout matPage) {
        this.mainPage = mainPage;
        this.edtPage = edtPage;
        this.matPage = matPage;
        backToDo = false;
    }

    @Override
    public void onBackPressed() {
        if (backToDo) {
            backToDo = false;
            edtPage.setVisibility(View.GONE);
            matPage.setVisibility(View.GONE);
            mainPage.setVisibility(View.VISIBLE);
        } else {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                super.onBackPressed();
                return;
            } else {
                backToast = Toast.makeText(getBaseContext(), "Appuyez une deuxième fois pour quitter", Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime= System.currentTimeMillis();
        }
    }

    public void setBack(boolean b) {
        backToDo = b;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        backToDo=false;
        return false;
    }
}