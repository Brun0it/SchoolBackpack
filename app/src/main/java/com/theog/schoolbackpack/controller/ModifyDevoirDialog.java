package com.theog.schoolbackpack.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theog.schoolbackpack.R;
import com.theog.schoolbackpack.model.DataBaseManager;
import com.theog.schoolbackpack.model.Devoir;
import com.theog.schoolbackpack.model.Subject;
import com.theog.schoolbackpack.ui.devoirs.DevoirsFragment;
import com.theog.schoolbackpack.ui.edt.EdtFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ModifyDevoirDialog {

    public static void showDialog(Fragment fragment, Devoir devoir){
        Activity activity = fragment.requireActivity();
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_modify_devoir);

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
        if (fragment.getClass() == DevoirsFragment.class) selectedDate = ((DevoirsFragment) fragment).getCalendar();
        else selectedDate = ((EdtFragment) fragment).getCalendar();

        ConstraintLayout background = dialog.findViewById(R.id.background);
        Button modify = dialog.findViewById(R.id.modify);
        Button delete = dialog.findViewById(R.id.delete);
        Button close = dialog.findViewById(R.id.close);
        TextView title = dialog.findViewById(R.id.title);
        TextView dateText = dialog.findViewById(R.id.dateText);
        EditText textPlain = dialog.findViewById(R.id.textPlain);
        ImageView selectDate = dialog.findViewById(R.id.selectDate);
        ImageView nextSubject = dialog.findViewById(R.id.nextSubject);

        dateText.setText(selectedDate.get(Calendar.DAY_OF_MONTH) + "/" + (selectedDate.get(Calendar.MONTH)+1) + "/" + selectedDate.get(Calendar.YEAR));

        if ((activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) background.setBackground(activity.getDrawable(R.drawable.background_dark));

        if (!devoir.isEvaluation()) {
            title.setText("Modifier l'exercice");
            textPlain.setBackground(activity.getDrawable(R.drawable.background_blue_text_plain));
            textPlain.setHintTextColor(activity.getResources().getColor(R.color.little_white));
            textPlain.setTextColor(activity.getResources().getColor(R.color.white));
            selectDate.setColorFilter(activity.getResources().getColor(R.color.blue), android.graphics.PorterDuff.Mode.MULTIPLY);
            nextSubject.setColorFilter(activity.getResources().getColor(R.color.blue), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        Calendar finalSelectedDate = selectedDate;
        selectDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(activity, (view, year, month, dayOfMonth) -> {
                finalSelectedDate.set(Calendar.YEAR, year);
                finalSelectedDate.set(Calendar.MONTH, month);
                finalSelectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateText.setText(finalSelectedDate.get(Calendar.DAY_OF_MONTH) + "/" + (finalSelectedDate.get(Calendar.MONTH)+1) + "/" + finalSelectedDate.get(Calendar.YEAR));
            }, finalSelectedDate.get(Calendar.YEAR), finalSelectedDate.get(Calendar.MONTH), finalSelectedDate.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        textPlain.setText(devoir.getNotes());
        finalSelectedDate.set(devoir.getDate().get(Calendar.YEAR), devoir.getDate().get(Calendar.MONTH), devoir.getDate().get(Calendar.DAY_OF_MONTH));
        sp1.setSelection((devoir.getSubject().getId())-1);

        modify.setOnClickListener(v -> {
            devoir.setNotes(textPlain.getText().toString());
            devoir.setDate(finalSelectedDate);
            devoir.setSubject(subjects.get((int) sp1.getSelectedItemId()));
            DataBaseManager db1 = new DataBaseManager(activity);
            if(devoir.isEvaluation()) db1.updateEvaluation(devoir);
            else db1.updateDevoir(devoir);
            db1.close();
            ((DevoirsFragment) fragment).updateDate();
            dialog.dismiss();
        });

        delete.setOnClickListener(v -> {
            new AlertDialog.Builder(activity)
                    .setMessage("Êtes vous sûr de vouloir supprimer ce devoir ?")
                    .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            DataBaseManager db1 = new DataBaseManager(activity);
                            if (devoir.isEvaluation()) db1.deleteEvaluation(devoir.getId());
                            else db1.deleteDevoir(devoir.getId());
                            db1.close();
                            ((DevoirsFragment) fragment).updateDate();
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