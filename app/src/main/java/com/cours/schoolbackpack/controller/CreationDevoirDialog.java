package com.cours.schoolbackpack.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.cours.schoolbackpack.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreationDevoirDialog {

    public static void showDialog(final Activity activity, Boolean evaluation){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_creation_devoir);

        ConstraintLayout background = dialog.findViewById(R.id.background);
        Button add = dialog.findViewById(R.id.add);
        Button close = dialog.findViewById(R.id.close);
        TextView title = dialog.findViewById(R.id.title);
        EditText textPlain = dialog.findViewById(R.id.textPlain);

        if ((activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) background.setBackground(activity.getDrawable(R.drawable.background_dark));
        if (evaluation) {
            title.setText("Nouvelle évaluation");
            textPlain.setBackground(activity.getDrawable(R.drawable.background_pink_text_plain));
        }
        else {
            title.setText("Nouvel exercice");
            textPlain.setBackground(activity.getDrawable(R.drawable.background_blue_text_plain));
            textPlain.setHintTextColor(activity.getResources().getColor(R.color.little_white));
            textPlain.setTextColor(activity.getResources().getColor(R.color.white));
        }

        close.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}