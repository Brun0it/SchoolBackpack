package com.cours.schoolbackpack.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cours.schoolbackpack.R;
import com.cours.schoolbackpack.model.Devoir;

import java.util.List;

public class CategoryDevoirAdapter extends RecyclerView.Adapter<CategoryDevoirAdapter.DevoirAdapterViewHolder>{

    List<List<Devoir>> devoirs;
    Activity activity;
    Context context;
    Fragment fragment;
    Boolean darkMode;

    public static class DevoirAdapterViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout background;
        TextView categoryName;
        RecyclerView recyclerView;

        public DevoirAdapterViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            background = itemView.findViewById(R.id.background);
        }
    }

    public CategoryDevoirAdapter(List<List<Devoir>> devoirs, Fragment fragment, Boolean darkMode) {
        this.devoirs = devoirs;
        this.activity = fragment.requireActivity();
        this.context = fragment.requireContext();
        this.fragment = fragment;
        this.darkMode = darkMode;
    }

    @Override
    public DevoirAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_devoirs,  parent, false);
        DevoirAdapterViewHolder devoirAdapterViewHolder = new DevoirAdapterViewHolder(view);
        return devoirAdapterViewHolder;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(DevoirAdapterViewHolder holder, int position) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        holder.recyclerView.setLayoutManager(layoutManager);

        DevoirAdapter devoirAdapter = new DevoirAdapter(devoirs.get(position), activity, context, fragment, darkMode);
        holder.recyclerView.setAdapter(devoirAdapter);

        if (position == 0) {
            if (devoirs.get(position).size() > 1) holder.categoryName.setText("Évaluations à réviser");
            else if (devoirs.get(position).size() == 1) holder.categoryName.setText("Évaluation à réviser");
            else {
                holder.categoryName.setVisibility(View.GONE);
                holder.recyclerView.setVisibility(View.GONE);
            }
        }
        else if (position == 1) {
            if (devoirs.get(position).size() > 1) holder.categoryName.setText("Exercices à faire");
            else if (devoirs.get(position).size() == 1) holder.categoryName.setText("Exercice à faire");
            else {
                holder.categoryName.setVisibility(View.GONE);
                holder.recyclerView.setVisibility(View.GONE);
            }
        }
        else {
            if (devoirs.get(position).size() > 1) holder.categoryName.setText("Exercices terminés");
            else if (devoirs.get(position).size() == 1) holder.categoryName.setText("Exercice terminé");
            else {
                holder.categoryName.setVisibility(View.GONE);
                holder.recyclerView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return devoirs.size();
    }
}