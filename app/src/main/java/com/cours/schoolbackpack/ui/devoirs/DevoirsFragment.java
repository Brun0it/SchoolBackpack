package com.cours.schoolbackpack.ui.devoirs;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cours.schoolbackpack.R;
import com.cours.schoolbackpack.controller.CategoryDevoirAdapter;
import com.cours.schoolbackpack.controller.ClassAdapter;
import com.cours.schoolbackpack.controller.DevoirAdapter;
import com.cours.schoolbackpack.controller.NewDevoirDialog;
import com.cours.schoolbackpack.model.Class;
import com.cours.schoolbackpack.model.DataBaseManager;
import com.cours.schoolbackpack.model.Day;
import com.cours.schoolbackpack.model.Devoir;
import com.cours.schoolbackpack.model.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DevoirsFragment extends Fragment {

    private DevoirsViewModel devoirsViewModel;
    private RecyclerView recyclerView;
    private ConstraintLayout header, mondayLayout, tuesdayLayout, wednesdayLayout, thursdayLayout, fridayLayout;
    private TextView mondayName, tuesdayName, wednesdayName, thursdayName, fridayName, mondayNumber, tuesdayNumber, wednesdayNumber, thursdayNumber, fridayNumber, weekTextView;
    private ImageButton previousWeek, nextWeek;
    private final Day monday = new Day();
    private final Day tuesday= new Day();
    private final Day wednesday= new Day();
    private final Day thursday= new Day();
    private final Day friday= new Day();
    private List<Devoir> devoirs = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance();
    private int weekNmb;
    private ImageView currentDate;
    private FloatingActionButton newDevoir;
    private ImageView redMon, redTue, redWed, redThu, redFri, blueMon, blueTue, blueWed, blueThu, blueFri;

    public Calendar getCalendar() {
        return calendar;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        devoirsViewModel =
                new ViewModelProvider(this).get(DevoirsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_devoirs, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        header = root.findViewById(R.id.header);

        mondayLayout = root.findViewById(R.id.mondayLayout);
        tuesdayLayout = root.findViewById(R.id.tuesdayLayout);
        wednesdayLayout = root.findViewById(R.id.wednesdayLayout);
        thursdayLayout = root.findViewById(R.id.thursdayLayout);
        fridayLayout = root.findViewById(R.id.fridayLayout);

        mondayName = root.findViewById(R.id.mondayName);
        tuesdayName = root.findViewById(R.id.tuesdayName);
        wednesdayName = root.findViewById(R.id.wednesdayName);
        thursdayName = root.findViewById(R.id.thursdayName);
        fridayName = root.findViewById(R.id.fridayName);

        mondayNumber = root.findViewById(R.id.mondayNumber);
        tuesdayNumber = root.findViewById(R.id.tuesdayNumber);
        wednesdayNumber = root.findViewById(R.id.wednesdayNumber);
        thursdayNumber = root.findViewById(R.id.thursdayNumber);
        fridayNumber = root.findViewById(R.id.fridayNumber);

        redMon = root.findViewById(R.id.redMon);
        redTue = root.findViewById(R.id.redTue);
        redWed = root.findViewById(R.id.redWed);
        redThu = root.findViewById(R.id.redThu);
        redFri = root.findViewById(R.id.redFri);
        blueMon = root.findViewById(R.id.blueMon);
        blueTue = root.findViewById(R.id.blueTue);
        blueWed = root.findViewById(R.id.blueWed);
        blueThu = root.findViewById(R.id.blueThu);
        blueFri = root.findViewById(R.id.blueFri);

        mondayLayout.setOnClickListener(v -> {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            updateDate();
        });
        tuesdayLayout.setOnClickListener(v -> {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            updateDate();
        });
        wednesdayLayout.setOnClickListener(v -> {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            updateDate();
        });
        thursdayLayout.setOnClickListener(v -> {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            updateDate();
        });
        fridayLayout.setOnClickListener(v -> {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            updateDate();
        });

        previousWeek = root.findViewById(R.id.previousWeek);
        nextWeek = root.findViewById(R.id.nextWeek);
        weekTextView = root.findViewById(R.id.weekTextView);
        weekTextView.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) ->
                    updateDate(year, month, dayOfMonth), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        weekTextView.setOnLongClickListener(v -> {
            calendar = Calendar.getInstance();
            updateDate();
            return true;
        });

        currentDate = root.findViewById(R.id.currentDate);
        currentDate.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            updateDate();
            currentDate.setVisibility(View.GONE);
        });

        calendar = Calendar.getInstance();
        previousWeek.setOnClickListener(v -> {
            calendar.add(Calendar.HOUR_OF_DAY, -(24*7));
            updateDate();
        });
        nextWeek.setOnClickListener(v -> {
            calendar.add(Calendar.HOUR_OF_DAY, 24*7);
            updateDate();
        });

        newDevoir = root.findViewById(R.id.newDevoir);
        newDevoir.setOnClickListener(v -> new NewDevoirDialog().showDialog(getActivity()));

        if (isDarkMode()) {
            previousWeek.setColorFilter(Color.argb(255, 255, 255, 255));
            nextWeek.setColorFilter(Color.argb(255, 255, 255, 255));
        }

        generateDays();
        updateDate();
        currentDate.setVisibility(View.GONE);
        return root;
    }

    public void generateDays() {
        Calendar time1 = Calendar.getInstance();
        Calendar time2 = Calendar.getInstance();
        Calendar time3 = Calendar.getInstance();
        Calendar time4 = Calendar.getInstance();
        Calendar time5 = Calendar.getInstance();
        Calendar time6 = Calendar.getInstance();
        time1.set(Calendar.HOUR_OF_DAY, 9);
        time1.set(Calendar.MINUTE, 0);
        time2.set(Calendar.HOUR_OF_DAY, 9);
        time2.set(Calendar.MINUTE, 30);
        time2.add(Calendar.DAY_OF_YEAR, 1);
        time3.set(Calendar.HOUR_OF_DAY, 13);
        time3.set(Calendar.MINUTE, 30);
        time4.set(Calendar.HOUR_OF_DAY, 16);
        time4.set(Calendar.MINUTE, 30);
        time5.set(Calendar.HOUR_OF_DAY, 8);
        time5.set(Calendar.MINUTE, 0);
        time6.set(Calendar.HOUR_OF_DAY, 14);
        time6.set(Calendar.MINUTE, 0);

        Subject maths = new Subject("Maths", "M. Walkowiak");
        Subject anglais = new Subject("Anglais", "M. Roulin");
        Subject francais = new Subject("Français", "Mme Vieillard");
        Subject allemand = new Subject("Allemand", "Mme Piau");
        Subject sport = new Subject("Sport", "M. Hamon");
        Subject histoireGeo = new Subject("Histoire Géo", "M. Venant");

        Calendar time0 = Calendar.getInstance();
        time0.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        monday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), maths, "Amphi 2", time1, 30));
        monday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), anglais, "TD4", time2, 150));
        monday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), francais, "TD4", time3, 180));

        time0.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        tuesday.add(new Class(time0.get(Calendar.DAY_OF_WEEK),allemand, "TD4", time3, 180));
        tuesday.add(new Class(time0.get(Calendar.DAY_OF_WEEK),sport, "TD4", time4, 90));

        time0.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        wednesday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), histoireGeo, "TD4", time1, 180));
        wednesday.add(new Class(time0.get(Calendar.DAY_OF_WEEK),francais, "TD4", time3, 180));

        time0.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        thursday.add(new Class(time0.get(Calendar.DAY_OF_WEEK),maths, "TP3", time3, 180));

        time0.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        friday.add(new Class(time0.get(Calendar.DAY_OF_WEEK),allemand, "TD4", time5, 90));
        friday.add(new Class(time0.get(Calendar.DAY_OF_WEEK),maths, "TD4", time2, 180));
        friday.add(new Class(time0.get(Calendar.DAY_OF_WEEK),anglais, "TD4", time6, 90));

        Devoir eval1 = new Devoir(allemand, time2, "eval1", true);
        Devoir exo1 = new Devoir(maths, Calendar.getInstance(), "exo1", false);
        Devoir exo2 = new Devoir(maths, Calendar.getInstance(), "exo2", false);
        Devoir exo3 = new Devoir(francais, time2, "exo3", false);
        Devoir exo4 = new Devoir(sport, time2, "exo4", false);

        exo4.setFait(true);
        devoirs.add(eval1);
        devoirs.add(exo1);
        devoirs.add(exo2);
        devoirs.add(exo3);
        devoirs.add(exo4);
    }

    public void displayList(List<Devoir> devoirs) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<Devoir> evaluations = new ArrayList<>();
        List<Devoir> exNFait = new ArrayList<>();
        List<Devoir> exFait = new ArrayList<>();
        for(int i=0; i<devoirs.size(); i++) {
            if (devoirs.get(i).isEvaluation()) evaluations.add(devoirs.get(i));
            else if (!devoirs.get(i).getFait()) exNFait.add(devoirs.get(i));
            else exFait.add(devoirs.get(i));
        }

        List<List<Devoir>> shortedDevoirs = new ArrayList<>();
        shortedDevoirs.add(evaluations);
        shortedDevoirs.add(exNFait);
        shortedDevoirs.add(exFait);

        CategoryDevoirAdapter categoryDevoirAdapter = new CategoryDevoirAdapter(shortedDevoirs, getActivity(), getContext(), this, isDarkMode());
        recyclerView.setAdapter(categoryDevoirAdapter);
    }

    public void displayList(@NotNull Calendar date) {
        displayList(getDevoirs(date));
    }

    public List<Devoir> getDevoirs(Calendar calendar){
        Log.e("lodkz", "hefsdnsfe");
        DataBaseManager db = new DataBaseManager(requireActivity());
        List<Devoir> devoirs = db.getDevoirs();
        db.close();
        for(int i=0; i<this.devoirs.size(); i++){
            if(this.devoirs.get(i).getDate().get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && this.devoirs.get(i).getDate().get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)) devoirs.add(this.devoirs.get(i));
        }
        return devoirs;
    }

    public void updateDate(int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        weekNmb = calendar.get(Calendar.WEEK_OF_YEAR);
        updateDate();
    }

    @SuppressLint("SetTextI18n")
    public void updateDate() {

        if (calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
                && calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR))
            currentDate.setVisibility(View.GONE);
        else currentDate.setVisibility(View.VISIBLE);

        int saveDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Calendar localCalendar = calendar;
        weekNmb = localCalendar.get(Calendar.WEEK_OF_YEAR);
        int dayOfWeek;
        weekTextView.setText("Semaine " + weekNmb);

        localCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        dayOfWeek = localCalendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfWeek >= 10) mondayNumber.setText(dayOfWeek + "");
        else mondayNumber.setText("0" + dayOfWeek);
        badgeGesture(localCalendar, redMon, blueMon);

        localCalendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        dayOfWeek = localCalendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfWeek >= 10) tuesdayNumber.setText(dayOfWeek + "");
        else tuesdayNumber.setText("0" + dayOfWeek);
        badgeGesture(localCalendar, redTue, blueTue);

        localCalendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        dayOfWeek = localCalendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfWeek >= 10) wednesdayNumber.setText(dayOfWeek + "");
        else wednesdayNumber.setText("0" + dayOfWeek);
        badgeGesture(localCalendar, redWed, blueWed);

        localCalendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        dayOfWeek = localCalendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfWeek >= 10) thursdayNumber.setText(dayOfWeek + "");
        else thursdayNumber.setText("0" + dayOfWeek);
        badgeGesture(localCalendar, redThu, blueThu);

        localCalendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        dayOfWeek = localCalendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfWeek >= 10) fridayNumber.setText(dayOfWeek + "");
        else fridayNumber.setText("0" + dayOfWeek);
        badgeGesture(localCalendar, redFri, blueFri);

        localCalendar.set(Calendar.DAY_OF_WEEK, saveDayOfWeek);

        selectDay();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void selectDay() {
        unselectAllDays();
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 2:
                if (isDarkMode()) mondayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else mondayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                mondayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                mondayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(calendar);
                break;
            case 3:
                if (isDarkMode()) tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                tuesdayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                tuesdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(calendar);
                break;
            case 4:
                if (isDarkMode()) wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                wednesdayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                wednesdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(calendar);
                break;
            case 5:
                if (isDarkMode()) thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                thursdayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                thursdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(calendar);
                break;
            case 6:
                if (isDarkMode()) fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                fridayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                fridayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(calendar);
                break;
            default:
                calendar.add(Calendar.DAY_OF_YEAR, 2);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                updateDate();
                break;
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void unselectAllDays() {
        if (isDarkMode()) {
            header.setBackground(requireActivity().getDrawable(R.drawable.header_dark));
            mondayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
        }
        else {
            header.setBackground(requireActivity().getDrawable(R.drawable.header_light));
            mondayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
            tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
            wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
            thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
            fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
        }

        mondayName.setTextColor(requireActivity().getResources().getColor(R.color.grey));
        tuesdayName.setTextColor(requireActivity().getResources().getColor(R.color.grey));
        wednesdayName.setTextColor(requireActivity().getResources().getColor(R.color.grey));
        thursdayName.setTextColor(requireActivity().getResources().getColor(R.color.grey));
        fridayName.setTextColor(requireActivity().getResources().getColor(R.color.grey));

        if (isDarkMode()) {
            mondayNumber.setTextColor(requireActivity().getResources().getColor(R.color.white));
            tuesdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.white));
            wednesdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.white));
            thursdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.white));
            fridayNumber.setTextColor(requireActivity().getResources().getColor(R.color.white));
        } else {
            mondayNumber.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
            tuesdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
            wednesdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
            thursdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
            fridayNumber.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
        }

    }

    public boolean isDarkMode() {
        return (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }

    public void badgeGesture(Calendar calendar, ImageView red, ImageView blue){
        for(int i=0; i<getDevoirs(calendar).size() && (red.getVisibility() == View.GONE || blue.getVisibility() == View.GONE); i++){
            if (getDevoirs(calendar).get(i).isEvaluation()) red.setVisibility(View.VISIBLE);
            else blue.setVisibility(View.VISIBLE);
        }
    }
}