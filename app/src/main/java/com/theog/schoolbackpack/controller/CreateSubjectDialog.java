package com.theog.schoolbackpack.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.theog.schoolbackpack.R;
import com.theog.schoolbackpack.model.DataBaseManager;
import com.theog.schoolbackpack.model.Subject;
import com.theog.schoolbackpack.ui.profil.ProfilFragment;

public class CreateSubjectDialog {

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public static void showDialog(ProfilFragment fragment){
        final Activity activity = fragment.requireActivity();
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_create_subject);

        EditText matiere = dialog.findViewById(R.id.matiere);
        EditText prof = dialog.findViewById(R.id.prof);

        ConstraintLayout background = dialog.findViewById(R.id.background);
        Button add = dialog.findViewById(R.id.add), close = dialog.findViewById(R.id.close);

        if ((activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) background.setBackground(activity.getDrawable(R.drawable.background_dark));

        add.setOnClickListener(v -> {
            Subject mat = new Subject(matiere.getText().toString(), prof.getText().toString());
            DataBaseManager db1 = new DataBaseManager(activity);
            db1.addSubject(mat);
            db1.close();
            fragment.displayMat();
            dialog.dismiss();
        });

        close.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}