package com.cours.schoolbackpack.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cours.schoolbackpack.R;
import com.cours.schoolbackpack.model.Class;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassAdapterViewHolder>{

    List<Class> classes;
    Activity activity;
    Context context;
    Fragment fragment;
    Boolean darkMode;

    public static class ClassAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView teacher;
        TextView time;
        TextView duration;
        LinearLayout smallTasks;
        LinearLayout tasks;
        ConstraintLayout background;
        Space space;
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
        }
    }

    public ClassAdapter(List<Class> classes, Activity activity, Context context, Fragment fragment, Boolean darkMode) {
        this.classes = classes;
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
        holder.name.setText(aClass.getName());
        holder.teacher.setText(aClass.getClassroom() + " | " + aClass.getTeacher());
        holder.time.setText(aClass.getTime() + " - " + aClass.getFinalTime());

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
            holder.smallTasks.setVisibility(View.VISIBLE);
            holder.tasks.setVisibility(View.GONE);
        } else {
            holder.smallTasks.setVisibility(View.GONE);
            holder.tasks.setVisibility(View.VISIBLE);
            holder.space.getLayoutParams().height = ((aClass.getDuration()-60)/4)*10;
            holder.space.requestLayout();
        }*/
        if (darkMode) holder.background.setBackground(activity.getResources().getDrawable(R.drawable.background_dark));
        else holder.background.setBackground(activity.getResources().getDrawable(R.drawable.background_light));
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }
}