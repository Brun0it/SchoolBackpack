package com.theog.schoolbackpack.controller;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.theog.schoolbackpack.R;
import com.theog.schoolbackpack.model.DataBaseManager;
import com.theog.schoolbackpack.model.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

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

        addEvaluation.setOnClickListener(v -> {
            DataBaseManager db = new DataBaseManager(fragment.requireContext());
            List<Subject> mats = db.getSubjects();
            db.close();
            if (mats.size() > 0) new CreationDevoirDialog().showDialog(fragment, false); else Toast.makeText(fragment.requireContext(), "Vous devez dabord créer au moins une matière pour pouvoir ajouter une évaluation.", Toast.LENGTH_LONG);
            new CreationDevoirDialog().showDialog(fragment, true);
            dialog.dismiss();
        });

        addExercice.setOnClickListener(v -> {
            DataBaseManager db = new DataBaseManager(fragment.requireContext());
            List<Subject> mats = db.getSubjects();
            db.close();
            if (mats.size() > 0) new CreationDevoirDialog().showDialog(fragment, false); else Toast.makeText(fragment.requireContext(), "Vous devez dabord créer au moins une matière pour pouvoir ajouter un exercice.", Toast.LENGTH_LONG);
            dialog.dismiss();
        });

        close.setOnClickListener(v -> dialog.dismiss());

        background  .setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}