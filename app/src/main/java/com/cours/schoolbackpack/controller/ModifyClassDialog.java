package com.cours.schoolbackpack.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
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

public class ModifyClassDialog {

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public static void showDialog(ProfilFragment fragment, Class aClass){
        final Activity activity = fragment.requireActivity();
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_modify_cours);

        DataBaseManager db = new DataBaseManager(activity);
        List<Subject> subjects = db.getSubjects();
        db.close();

        final List<String> list = new ArrayList<>();
        for (int i = 0; i < subjects.size(); i++) {
            list.add(subjects.get(i).getName());
        }

        EditText classroom = dialog.findViewById(R.id.classroom);
        classroom.setText(aClass.getClassroom());

        final Spinner sp1 = dialog.findViewById(R.id.matieres);
        TextView startHour = dialog.findViewById(R.id.startHour);
        startHour.setText(aClass.getFullTime());
        TextView endHour = dialog.findViewById(R.id.endHour);
        endHour.setText(aClass.getFullFinalTime());


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);
        int id = -1;
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getId() == aClass.getSubject().getId()) {
                id = i;
                break;
            }
        }
        sp1.setSelection(id);

        startHour.setOnClickListener(v -> {
            int mHour = Integer.parseInt(startHour.getText().toString().charAt(0) + "" + startHour.getText().toString().charAt(1));
            int mMin = Integer.parseInt(startHour.getText().toString().charAt(3) + "" + startHour.getText().toString().charAt(4));
            TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour;
                    if (hourOfDay < 10) hour = "0" + hourOfDay;
                    else hour = "" + hourOfDay;
                    String min;
                    if (minute < 10) min = "0" + minute;
                    else min = "" + minute;

                    startHour.setText(hour + "h" + min);
                    if (hourOfDay < Integer.parseInt(endHour.getText().toString().charAt(0) + "" + endHour.getText().toString().charAt(1)))
                        endHour.setTextColor(activity.getResources().getColor(R.color.blue));
                    else if (Integer.parseInt(endHour.getText().toString().charAt(0) + "" + endHour.getText().toString().charAt(1)) == hourOfDay
                            && minute < Integer.parseInt(endHour.getText().toString().charAt(3) + "" + endHour.getText().toString().charAt(4)) )
                        endHour.setTextColor(activity.getResources().getColor(R.color.blue));
                    else endHour.setTextColor(activity.getResources().getColor(R.color.pink));
                }
            }, mHour, mMin, true);
            timePickerDialog.show();
        });

        endHour.setOnClickListener(v -> {
            int mHour = Integer.parseInt(endHour.getText().toString().charAt(0) + "" + endHour.getText().toString().charAt(1));
            int mMin = Integer.parseInt(endHour.getText().toString().charAt(3) + "" + endHour.getText().toString().charAt(4));
            TimePickerDialog timePickerDialog = new TimePickerDialog(activity, (view, hourOfDay1, minute1) -> {
                String hour1;
                if (hourOfDay1 < 10) hour1 = "0" + hourOfDay1;
                else hour1 = "" + hourOfDay1;
                String min1;
                if (minute1 < 10) min1 = "0" + minute1;
                else min1 = "" + minute1;

                endHour.setText(hour1 + "h" + min1);
                if (Integer.parseInt(startHour.getText().toString().charAt(0) + "" + startHour.getText().toString().charAt(1)) < hourOfDay1)
                    endHour.setTextColor(activity.getResources().getColor(R.color.blue));
                else if (Integer.parseInt(startHour.getText().toString().charAt(0) + "" + startHour.getText().toString().charAt(1)) == hourOfDay1
                        && Integer.parseInt(startHour.getText().toString().charAt(3) + "" + startHour.getText().toString().charAt(4)) < minute1)
                    endHour.setTextColor(activity.getResources().getColor(R.color.blue));
                else endHour.setTextColor(activity.getResources().getColor(R.color.pink));
            }, mHour, mMin, true);
            timePickerDialog.show();
        });

        ConstraintLayout background = dialog.findViewById(R.id.background);
        Button modify = dialog.findViewById(R.id.modify), delete = dialog.findViewById(R.id.delete),
                close = dialog.findViewById(R.id.close);

        if ((activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) background.setBackground(activity.getDrawable(R.drawable.background_dark));

        modify.setOnClickListener(v -> {
            Calendar startCalendar = Calendar.getInstance(), endCalendar = Calendar.getInstance(), localCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startHour.getText().toString().charAt(0) + "" + startHour.getText().toString().charAt(1)));
            startCalendar.set(Calendar.MINUTE, Integer.parseInt(startHour.getText().toString().charAt(3) + "" + startHour.getText().toString().charAt(4)));
            endCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endHour.getText().toString().charAt(0) + "" + endHour.getText().toString().charAt(1)));
            endCalendar.set(Calendar.MINUTE, Integer.parseInt(endHour.getText().toString().charAt(3) + "" + endHour.getText().toString().charAt(4)));
            localCalendar.set(Calendar.HOUR_OF_DAY, endCalendar.get(Calendar.HOUR_OF_DAY) - startCalendar.get(Calendar.HOUR_OF_DAY));
            localCalendar.set(Calendar.MINUTE, endCalendar.get(Calendar.MINUTE) - startCalendar.get(Calendar.MINUTE));
            if (startCalendar.before(endCalendar)) {
                int duration = localCalendar.get(Calendar.HOUR)*60 + localCalendar.get(Calendar.MINUTE);
                aClass.setClassroom(classroom.getText().toString());
                aClass.setTime(startCalendar);
                aClass.setDuration(duration);
                aClass.setSubject(subjects.get((int) sp1.getSelectedItemId()));
                Log.e("ModifyClassDialog", aClass.getDuration() + "");

                DataBaseManager db1 = new DataBaseManager(activity);
                db1.updateClass(aClass);
                db1.close();
                fragment.displayList(aClass.getIdJour());
                dialog.dismiss();
            } else {
                Toast.makeText(activity, "Heure de fin incorrecte", Toast.LENGTH_LONG).show();
            }
        });

        delete.setOnClickListener(v -> {
            DataBaseManager db1 = new DataBaseManager(activity);
            int dayId = aClass.getIdJour();
            db1.deleteClass(aClass.getId());
            db1.close();
            db1 = new DataBaseManager(activity);
            fragment.displayList(db.getClasses(dayId));
            db1.close();
            dialog.dismiss();
        });

        close.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}