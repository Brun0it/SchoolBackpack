package com.cours.schoolbackpack;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
        LinearLayout smallTasks;
        LinearLayout tasks;
        Space space;
        public ClassAdapterViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            teacher = itemView.findViewById(R.id.teacher);
            time = itemView.findViewById(R.id.time);
            smallTasks = itemView.findViewById(R.id.smallTasks);
            tasks = itemView.findViewById(R.id.tasks);
            space = itemView.findViewById(R.id.space);
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
        if (aClass.getDuration() < 60) {
            holder.smallTasks.setVisibility(View.VISIBLE);
            holder.tasks.setVisibility(View.GONE);
        } else {
            holder.smallTasks.setVisibility(View.GONE);
            holder.tasks.setVisibility(View.VISIBLE);
            holder.space.getLayoutParams().height = ((aClass.getDuration()-60)/4)*10;
            holder.space.requestLayout();
        }
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }
}