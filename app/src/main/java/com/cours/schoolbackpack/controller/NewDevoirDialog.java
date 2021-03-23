package com.cours.schoolbackpack.controller;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cours.schoolbackpack.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewDevoirDialog {

    public static void showDialog(Fragment fragment){
        Activity activity = fragment.requireActivity();
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_new_devoir);

        FloatingActionButton close = dialog.findViewById(R.id.close);
        Button addEvaluation = dialog.findViewById(R.id.addEvaluation);
        Button addExercice = dialog.findViewById(R.id.addExercice);
        TextView background = dialog.findViewById(R.id.backgroundText);
        Spinner matieres = dialog.findViewById(R.id.matieres);

        addEvaluation.setOnClickListener(v -> {
            new CreationDevoirDialog().showDialog(fragment, true);
            dialog.dismiss();
        });

        addExercice.setOnClickListener(v -> {
            new CreationDevoirDialog().showDialog(fragment, false);
            dialog.dismiss();
        });

        close.setOnClickListener(v -> dialog.dismiss());

        background  .setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}