package com.theog.schoolbackpack.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.theog.schoolbackpack.R;
import com.theog.schoolbackpack.model.DataBaseManager;
import com.theog.schoolbackpack.model.Devoir;
import com.theog.schoolbackpack.ui.devoirs.DevoirsFragment;

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
        ImageView entete;
        ConstraintLayout background;

        public DevoirAdapterViewHolder(View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.note);
            background = itemView.findViewById(R.id.background);
            matiere = itemView.findViewById(R.id.matiere);
            button = itemView.findViewById(R.id.button);
            entete = itemView.findViewById(R.id.entete);
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

        if(devoir.isEvaluation()) {
            holder.button.setVisibility(View.GONE);
            holder.entete.setColorFilter(ContextCompat.getColor(context, R.color.pink), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if(devoir.getFait()) {
            holder.button.setImageResource(R.drawable.ic_baseline_close_24);
            holder.button.setBackgroundTintList(activity.getResources().getColorStateList(R.color.grey));
            holder.entete.setColorFilter(ContextCompat.getColor(context, R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN);
        } else holder.entete.setColorFilter(ContextCompat.getColor(context, R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devoir.setFait(!devoir.getFait());
                DataBaseManager db = new DataBaseManager(activity);//context ??
                db.updateDevoir(devoir);
                db.close();
                ((DevoirsFragment) fragment).displayList(((DevoirsFragment) fragment).getCalendar());
            }
        });

        if (darkMode) holder.background.setBackground(activity.getResources().getDrawable(R.drawable.background_dark_devoirs));
        else holder.background.setBackground(activity.getResources().getDrawable(R.drawable.background_light_devoirs));

        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyDevoirDialog.showDialog(fragment, devoir);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devoirs.size();
    }
}