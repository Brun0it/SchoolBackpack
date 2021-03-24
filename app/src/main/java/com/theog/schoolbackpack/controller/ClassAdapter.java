package com.theog.schoolbackpack.controller;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.theog.schoolbackpack.R;
import com.theog.schoolbackpack.model.Class;
import com.theog.schoolbackpack.model.Devoir;
import com.theog.schoolbackpack.ui.profil.ProfilFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassAdapterViewHolder>{

    List<Class> classes;
    List<Devoir> devoirs;
    Calendar date;
    Activity activity;
    Context context;
    Fragment fragment;
    Boolean darkMode;

    public static class ClassAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView teacher;
        TextView time;
        TextView duration;
        LinearLayout evaluationLayout;
        LinearLayout exerciceLayout;
        TextView evaluationText;
        TextView exerciceText;
        LinearLayout smallTasks;
        LinearLayout tasks;
        ConstraintLayout background;
        Space space;
        ImageView durationLogo;

        public ClassAdapterViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            teacher = itemView.findViewById(R.id.teacher);
            time = itemView.findViewById(R.id.time);
            smallTasks = itemView.findViewById(R.id.smallTasks);
            tasks = itemView.findViewById(R.id.tasks);
            space = itemView.findViewById(R.id.space);
            background = itemView.findViewById(R.id.background);
            duration = itemView.findViewById(R.id.duration);
            evaluationLayout = itemView.findViewById(R.id.evaluationLayout);
            exerciceLayout = itemView.findViewById(R.id.exerciceLayout);
            evaluationText = itemView.findViewById(R.id.evaluationText);
            exerciceText = itemView.findViewById(R.id.exerciceText);
            durationLogo  = itemView.findViewById(R.id.durationLogo);
        }
    }

    public ClassAdapter(List<Class> classes, List<Devoir> devoirs, Calendar date, Activity activity, Context context, Fragment fragment, Boolean darkMode) {
        this.classes = classes;
        this.devoirs = devoirs;
        this.date = date;
        this.activity = activity;
        this.context = context;
        this.fragment = fragment;
        this.darkMode = darkMode;
    }

    @Override
    public ClassAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_class, parent, false);
        ClassAdapterViewHolder classAdapterViewHolder = new ClassAdapterViewHolder(view);
        return classAdapterViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ClassAdapterViewHolder holder, int position) {
        Class aClass = classes.get(position);
        holder.name.setText(aClass.getSubject().getName());
        if (aClass.getClassroom().equals("")) holder.teacher.setText(aClass.getSubject().getTeacher());
        else holder.teacher.setText(aClass.getClassroom() + " | " + aClass.getSubject().getTeacher());
        holder.time.setText(aClass.getTimeStringFormat() + " - " + aClass.getFinalTime());

        if (position == 0) holder.space.setVisibility(View.VISIBLE);
        else holder.space.setVisibility(View.GONE);

        int h = ((int) Math.floor(aClass.getDuration()/60));
        String hour;
        if (h < 1) hour = "";
        else hour = h + "h";

        int m = ((int) (aClass.getDuration() - 60*Math.floor(aClass.getDuration()/60)));
        String min;
        if (m < 1) min = "";
        else {
            if (m < 10) min = "+" + m;
            else min = m + "";
            if (h < 1) min = min + "m";
        }

        holder.duration.setText(hour + min);
        /*if (aClass.getDuration() < 60) {
            //holder.smallTasks.setVisibility(View.VISIBLE);
            //holder.tasks.setVisibility(View.GONE);
        } else {
            //holder.smallTasks.setVisibility(View.GONE);
            //holder.tasks.setVisibility(View.VISIBLE);
            holder.space.getLayoutParams().height = ((aClass.getDuration()-60)/4)*10;
            holder.space.requestLayout();
        }*/
        if (darkMode) holder.background.setBackground(activity.getResources().getDrawable(R.drawable.background_dark));
        else holder.background.setBackground(activity.getResources().getDrawable(R.drawable.background_light));

        if (devoirs != null) {
            List<Devoir> devoirs2 = new ArrayList<>();
            for (int i = 0; i < devoirs.size(); i++) {
                if (devoirs.get(i).getSubject().getId() == aClass.getSubject().getId() && devoirs.get(i).getDate().get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)
                        && devoirs.get(i).getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR))
                    devoirs2.add(devoirs.get(i));
            }

            int nbEvals = 0, nbExos = 0;
            for (int i = 0; i < devoirs2.size(); i++) {
                if (devoirs2.get(i).isEvaluation()) ++nbEvals;
                else ++nbExos;
            }


            if (nbEvals == 0) holder.evaluationLayout.setVisibility(View.GONE);
            else {
                if (nbEvals > 1) holder.evaluationText.setText(nbEvals + " évaluations");
                else holder.evaluationText.setText(nbEvals + " évaluation");
                holder.evaluationLayout.setVisibility(View.VISIBLE);
            }

            if (nbExos == 0) holder.exerciceLayout.setVisibility(View.GONE);
            else {
                if (nbExos > 1) holder.exerciceText.setText(nbExos + " exercices");
                else holder.exerciceText.setText(nbExos + " exercice");
                holder.exerciceLayout.setVisibility(View.VISIBLE);
            }

        }

        if (fragment.getClass() == ProfilFragment.class) {
            holder.evaluationLayout.setVisibility(View.GONE);
            holder.exerciceLayout.setVisibility(View.GONE);
        }

        switch (activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                holder.durationLogo.setColorFilter(Color.argb(255, 255, 255, 255));
                break;
            default:
                break;
        }

        if (fragment.getClass() == ProfilFragment.class) {
            holder.background.setOnClickListener(v -> {
                new ModifyClassDialog().showDialog((ProfilFragment) fragment, aClass);
            });
        }
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }
}