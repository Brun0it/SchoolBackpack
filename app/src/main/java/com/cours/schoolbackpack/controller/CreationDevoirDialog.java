package com.cours.schoolbackpack.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.cours.schoolbackpack.R;
import com.cours.schoolbackpack.model.DataBaseManager;
import com.cours.schoolbackpack.model.Devoir;
import com.cours.schoolbackpack.model.Subject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreationDevoirDialog {

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public static void showDialog(final Activity activity, Boolean evaluation){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_creation_devoir);

        DataBaseManager db = new DataBaseManager(activity);
        List<Subject> subjects = db.getSubjects();
        db.close();

        //DEBUT PHASE DE TEST

        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < subjects.size(); i++) {
            list.add(subjects.get(i).getName());
        }

        final Spinner sp1 = dialog.findViewById(R.id.matieres);

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);

        //FIN

        Calendar selectedDate = Calendar.getInstance();

        ConstraintLayout background = dialog.findViewById(R.id.background);
        Button add = dialog.findViewById(R.id.add);
        Button close = dialog.findViewById(R.id.close);
        TextView title = dialog.findViewById(R.id.title);
        TextView dateText = dialog.findViewById(R.id.dateText);
        EditText textPlain = dialog.findViewById(R.id.textPlain);
        ImageView selectDate = dialog.findViewById(R.id.selectDate);
        ImageView nextSubject = dialog.findViewById(R.id.nextSubject);

        dateText.setText(selectedDate.get(Calendar.DAY_OF_MONTH) + "/" + (selectedDate.get(Calendar.MONTH)+1) + "/" + selectedDate.get(Calendar.YEAR));

        if ((activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) background.setBackground(activity.getDrawable(R.drawable.background_dark));

        if (!evaluation) {
            title.setText("Nouvel exercice");
            textPlain.setBackground(activity.getDrawable(R.drawable.background_blue_text_plain));
            textPlain.setHintTextColor(activity.getResources().getColor(R.color.little_white));
            textPlain.setTextColor(activity.getResources().getColor(R.color.white));
            selectDate.setColorFilter(activity.getResources().getColor(R.color.blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            nextSubject.setColorFilter(activity.getResources().getColor(R.color.blue), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        selectDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(activity, (view, year, month, dayOfMonth) -> {
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateText.setText(selectedDate.get(Calendar.DAY_OF_MONTH) + "/" + (selectedDate.get(Calendar.MONTH)+1) + "/" + selectedDate.get(Calendar.YEAR));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        add.setOnClickListener(v -> {
            DataBaseManager db1 = new DataBaseManager(activity);
            Devoir devoir = new Devoir(subjects.get((int) sp1.getSelectedItemId()), selectedDate,textPlain.getText().toString(), evaluation);
            db1.addDevoir(devoir);
            db1.close();
            dialog.dismiss();
        });

        close.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}