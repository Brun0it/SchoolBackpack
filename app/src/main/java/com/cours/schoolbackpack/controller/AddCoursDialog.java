package com.cours.schoolbackpack.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.cours.schoolbackpack.R;
import com.cours.schoolbackpack.model.Devoir;
import com.cours.schoolbackpack.model.Subject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddCoursDialog {

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public static void showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_add_cours);

        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("Maths", "M. Walkowiak"));
        subjects.add(new Subject("Anglais", "M. Roulin"));
        subjects.add(new Subject("Français", "Mme Vieillard"));
        subjects.add(new Subject("Allemand", "Mme Piau"));
        subjects.add(new Subject("Sport", "M. Hamon"));
        subjects.add(new Subject("Histoire Géo", "M. Venant"));

        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < subjects.size(); i++) {
            list.add(subjects.get(i).getName());
        }

        final Spinner sp1 = dialog.findViewById(R.id.matieres);
        TextView heureDebut = dialog.findViewById(R.id.heureDebut);
        TextView heureFin = dialog.findViewById(R.id.heureFin);

        Calendar calendar = Calendar.getInstance();
        String hour;
        String min;

        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (hourOfDay < 10) hour = "0" + hourOfDay;
        else hour = "" + hourOfDay;
        int minute = calendar.get(Calendar.MINUTE);
        if (minute < 10) min = "0" + minute;
        else min = "" + minute;
        heureDebut.setText(hour + "h" + min);

        calendar.add(Calendar.HOUR_OF_DAY, 1);
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (hourOfDay < 10) hour = "0" + hourOfDay;
        else hour = "" + hourOfDay;
        minute = calendar.get(Calendar.MINUTE);
        if (minute < 10) min = "0" + minute;
        else min = "" + minute;
        heureFin.setText(hour + "h" + min);

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);

        heureDebut.setOnClickListener(v -> {
            int mHour = Integer.parseInt(heureDebut.getText().toString().charAt(0) + "" + heureDebut.getText().toString().charAt(1));
            int mMin = Integer.parseInt(heureDebut.getText().toString().charAt(3) + "" + heureDebut.getText().toString().charAt(4));
            TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour;
                    if (hourOfDay < 10) hour = "0" + hourOfDay;
                    else hour = "" + hourOfDay;
                    String min;
                    if (minute < 10) min = "0" + minute;
                    else min = "" + minute;

                    heureDebut.setText(hour + "h" + min);
                    if (hourOfDay < Integer.parseInt(heureFin.getText().toString().charAt(0) + "" + heureFin.getText().toString().charAt(1)))
                        heureFin.setTextColor(activity.getResources().getColor(R.color.blue));
                    else if (Integer.parseInt(heureFin.getText().toString().charAt(0) + "" + heureFin.getText().toString().charAt(1)) == hourOfDay
                            && minute < Integer.parseInt(heureFin.getText().toString().charAt(3) + "" + heureFin.getText().toString().charAt(4)) )
                        heureFin.setTextColor(activity.getResources().getColor(R.color.blue));
                    else heureFin.setTextColor(activity.getResources().getColor(R.color.pink));
                }
            }, mHour, mMin, true);
            timePickerDialog.show();
        });

        heureFin.setOnClickListener(v -> {
            int mHour = Integer.parseInt(heureFin.getText().toString().charAt(0) + "" + heureFin.getText().toString().charAt(1));
            int mMin = Integer.parseInt(heureFin.getText().toString().charAt(3) + "" + heureFin.getText().toString().charAt(4));
            TimePickerDialog timePickerDialog = new TimePickerDialog(activity, (view, hourOfDay1, minute1) -> {
                String hour1;
                if (hourOfDay1 < 10) hour1 = "0" + hourOfDay1;
                else hour1 = "" + hourOfDay1;
                String min1;
                if (minute1 < 10) min1 = "0" + minute1;
                else min1 = "" + minute1;

                heureFin.setText(hour1 + "h" + min1);
                if (Integer.parseInt(heureDebut.getText().toString().charAt(0) + "" + heureDebut.getText().toString().charAt(1)) < hourOfDay1)
                    heureFin.setTextColor(activity.getResources().getColor(R.color.blue));
                else if (Integer.parseInt(heureDebut.getText().toString().charAt(0) + "" + heureDebut.getText().toString().charAt(1)) == hourOfDay1
                        && Integer.parseInt(heureDebut.getText().toString().charAt(3) + "" + heureDebut.getText().toString().charAt(4)) < minute1)
                    heureFin.setTextColor(activity.getResources().getColor(R.color.blue));
                else heureFin.setTextColor(activity.getResources().getColor(R.color.pink));
            }, mHour, mMin, true);
            timePickerDialog.show();
        });

        ConstraintLayout background = dialog.findViewById(R.id.background);
        Button close = dialog.findViewById(R.id.close);

        if ((activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) background.setBackground(activity.getDrawable(R.drawable.background_dark));

        close.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}