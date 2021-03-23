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
import com.cours.schoolbackpack.model.Subject;
import com.cours.schoolbackpack.ui.profil.ProfilFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MatiereAdapter extends RecyclerView.Adapter<MatiereAdapter.MatiereAdapterViewHolder>{

    List<Subject> matieres;
    Activity activity;
    Context context;
    Fragment fragment;
    Boolean darkMode;

    public static class MatiereAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView prof;
        ConstraintLayout background;
        ImageButton button;

        public MatiereAdapterViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.matiere);
            prof = itemView.findViewById(R.id.prof);
            background = itemView.findViewById(R.id.background);
            button  = itemView.findViewById(R.id.button);
        }
    }

    public MatiereAdapter(List<Subject> matieres, Fragment fragment, Boolean darkMode) {
        this.matieres = matieres;
        this.activity = fragment.requireActivity();
        this.context = fragment.requireContext();
        this.fragment = fragment;
        this.darkMode = darkMode;
    }

    @Override
    public MatiereAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_matiere, parent, false);
        MatiereAdapterViewHolder matiereAdapterViewHolder = new MatiereAdapterViewHolder(view);
        return matiereAdapterViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(MatiereAdapterViewHolder holder, int position) {
        Subject matiere = matieres.get(position);
        holder.name.setText(matiere.getName());
        holder.prof.setText(matiere.getTeacher());
    }

    @Override
    public int getItemCount() {
        return matieres.size();
    }
}