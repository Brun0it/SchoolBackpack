package com.cours.schoolbackpack.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.cours.schoolbackpack.R;
import com.cours.schoolbackpack.model.Class;
import com.cours.schoolbackpack.model.DataBaseManager;
import com.cours.schoolbackpack.model.Subject;
import com.cours.schoolbackpack.ui.profil.ProfilFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ModifySubjectDialog {

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public static void showDialog(ProfilFragment fragment, Subject mat){
        final Activity activity = fragment.requireActivity();
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_modify_subject);

        EditText matiere = dialog.findViewById(R.id.matiere);
        matiere.setText(mat.getName());
        EditText prof = dialog.findViewById(R.id.prof);
        prof.setText(mat.getTeacher());

        ConstraintLayout background = dialog.findViewById(R.id.background);
        Button modify = dialog.findViewById(R.id.modify), delete = dialog.findViewById(R.id.delete),
                close = dialog.findViewById(R.id.close);

        if ((activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) background.setBackground(activity.getDrawable(R.drawable.background_dark));

        modify.setOnClickListener(v -> {
            mat.setName(matiere.getText().toString());
            mat.setTeacher(prof.getText().toString());
            DataBaseManager db1 = new DataBaseManager(activity);
            db1.updateSubject(mat);
            db1.close();
            fragment.displayMat();
            dialog.dismiss();
        });

        delete.setOnClickListener(v -> {
            new AlertDialog.Builder(activity)
                    .setMessage("Êtes vous sûr de vouloir supprimer cette matière ?\nCela supprimera également tous les devoirs/évaluations ainsi que tous les cours liés à cette matière")
                    .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            DataBaseManager db1 = new DataBaseManager(activity);
                            db1.deleteSubject(mat.getId());
                            db1.close();
                            fragment.displayMat();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Annuler",null)
                    .show();
        });

        close.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}