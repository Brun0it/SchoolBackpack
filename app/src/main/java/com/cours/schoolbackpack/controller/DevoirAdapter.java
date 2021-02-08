package com.cours.schoolbackpack.controller;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cours.schoolbackpack.R;
import com.cours.schoolbackpack.model.Class;
import com.cours.schoolbackpack.model.Devoir;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DevoirAdapter extends RecyclerView.Adapter<DevoirAdapter.DevoirAdapterViewHolder>{

    List<Devoir> devoirs;
    Activity activity;
    Context context;
    Fragment fragment;
    Boolean darkMode;

    public static class DevoirAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView note;
        TextView matiere;
        ImageButton button;
        ConstraintLayout background;

        public DevoirAdapterViewHolder(View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.note);
            background = itemView.findViewById(R.id.background);
            matiere = itemView.findViewById(R.id.matiere);
            button = itemView.findViewById(R.id.button);
        }
    }

    public DevoirAdapter(List<Devoir> devoirs, Activity activity, Context context, Fragment fragment, Boolean darkMode) {
        this.devoirs = devoirs;
        this.activity = activity;
        this.context = context;
        this.fragment = fragment;
        this.darkMode = darkMode;
    }

    @Override
    public DevoirAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_devoir, parent, false);
        DevoirAdapterViewHolder devoirAdapterViewHolder = new DevoirAdapterViewHolder(view);
        return devoirAdapterViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(DevoirAdapterViewHolder holder, int position) {
        Devoir devoir = devoirs.get(position);
        holder.matiere.setText(devoir.getSubject().getName());
        holder.note.setText(devoir.getNotes());

        if(devoir.isEvaluation()) holder.button.setVisibility(View.GONE);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devoir.setFait(!devoir.getFait());
            }
        });

        if (darkMode) holder.background.setBackground(activity.getResources().getDrawable(R.drawable.background_dark));
        else holder.background.setBackground(activity.getResources().getDrawable(R.drawable.background_light));

    }

    @Override
    public int getItemCount() {
        return devoirs.size();
    }
}