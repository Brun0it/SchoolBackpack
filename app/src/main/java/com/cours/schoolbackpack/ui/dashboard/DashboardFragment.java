package com.cours.schoolbackpack.ui.dashboard;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cours.schoolbackpack.model.Class;
import com.cours.schoolbackpack.controller.ClassAdapter;
import com.cours.schoolbackpack.model.Day;
import com.cours.schoolbackpack.R;
import com.cours.schoolbackpack.ui.Week;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ConstraintLayout header, mondayLayout, tuesdayLayout, wednesdayLayout, thursdayLayout, fridayLayout;
    private TextView mondayName, tuesdayName, wednesdayName, thursdayName, fridayName, mondayNumber, tuesdayNumber, wednesdayNumber, thursdayNumber, fridayNumber, weekTextView;
    private ImageButton previousWeek, nextWeek;
    private Day monday = new Day(), tuesday= new Day(), wednesday= new Day(), thursday= new Day(), friday= new Day();
    private Week week = new Week();
    private Calendar calendar;
    private int weekNmb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

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

        mondayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAllDays();
                selectDay(Calendar.MONDAY);
            }
        });
        tuesdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAllDays();
                selectDay(Calendar.TUESDAY);
            }
        });
        wednesdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAllDays();
                selectDay(Calendar.WEDNESDAY);
            }
        });
        thursdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAllDays();
                selectDay(Calendar.THURSDAY);
            }
        });
        fridayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAllDays();
                selectDay(Calendar.FRIDAY);
            }
        });

        previousWeek = root.findViewById(R.id.previousWeek);
        nextWeek = root.findViewById(R.id.nextWeek);
        weekTextView = root.findViewById(R.id.weekTextView);


        calendar = Calendar.getInstance();
        previousWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.HOUR_OF_DAY, -(24*7));
                updateDate();
            }
        });
        nextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.HOUR_OF_DAY, 24*7);
                updateDate();
            }
        });

        updateDate();
        unselectAllDays();
        generateDays();
        selectDay(calendar.get(Calendar.DAY_OF_WEEK)-1);
        Log.e("DashboardFrag",  calendar.get(Calendar.DAY_OF_WEEK) + "");
        return root;
    }

    public void updateDate() {
        Calendar localCalendar = calendar;
        weekNmb = localCalendar.get(Calendar.WEEK_OF_YEAR);
        int dayOfWeek;
        weekTextView.setText("Semaine " + weekNmb);

        localCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        dayOfWeek = localCalendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfWeek >= 10) mondayNumber.setText(dayOfWeek + "");
        else mondayNumber.setText("0" + dayOfWeek);

        localCalendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        dayOfWeek = localCalendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfWeek >= 10) tuesdayNumber.setText(dayOfWeek + "");
        else tuesdayNumber.setText("0" + dayOfWeek);

        localCalendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        dayOfWeek = localCalendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfWeek >= 10) wednesdayNumber.setText(dayOfWeek + "");
        else wednesdayNumber.setText("0" + dayOfWeek);

        localCalendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        dayOfWeek = localCalendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfWeek >= 10) thursdayNumber.setText(dayOfWeek + "");
        else thursdayNumber.setText("0" + dayOfWeek);

        localCalendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        dayOfWeek = localCalendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfWeek >= 10) fridayNumber.setText(dayOfWeek + "");
        else fridayNumber.setText("0" + dayOfWeek);
    }

    public void displayList(Day day) {
        displayList(day.getClasses());
    }

    public void displayList(List<Class> classes) {

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ClassAdapter classAdapter = new ClassAdapter(classes, getActivity(), getContext(), this, isDarkMode());
        recyclerView.setAdapter(classAdapter);

    }

    public void generateDays() {
        List<Class> classes = new ArrayList<>();

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
        time3.set(Calendar.HOUR_OF_DAY, 13);
        time3.set(Calendar.MINUTE, 30);
        time4.set(Calendar.HOUR_OF_DAY, 16);
        time4.set(Calendar.MINUTE, 30);
        time5.set(Calendar.HOUR_OF_DAY, 8);
        time5.set(Calendar.MINUTE, 0);
        time6.set(Calendar.HOUR_OF_DAY, 14);
        time6.set(Calendar.MINUTE, 0);

        monday.add(new Class("Maths", "Amphi 2", "M. Corbiere", time1, 30));
        monday.add(new Class("Anglais", "TD4", "M. Corbiere", time2, 150));
        monday.add(new Class("Espagnol", "TD4", "M. Hamon", time3, 180));

        tuesday.add(new Class("Allemand", "TD4", "Mme Vieillard", time3, 180));
        tuesday.add(new Class("Sport", "TD4", "M. Roulin", time4, 90));

        wednesday.add(new Class("Histoire Géo", "TD4", "M. Walkowiak", time1, 180));
        wednesday.add(new Class("Français", "TD4", "M. Ernet", time3, 180));

        thursday.add(new Class("Latin", "TP3", "M. Laforcade", time3, 180));

        friday.add(new Class("Physique Chimie", "TD4", "M. Ernet", time5, 90));
        friday.add(new Class("Musique", "TD4", "M. Walkowiak", time2, 180));
        friday.add(new Class("Art-Plastique", "TD4", "M. Walkowiak", time6, 90));
    }

    public void selectDay(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                if (isDarkMode()) mondayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink_dm));
                else mondayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink));
                mondayName.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                mondayNumber.setTextColor(getActivity().getResources().getColor(R.color.pink));
                displayList(monday);
                break;
            case Calendar.TUESDAY:
                if (isDarkMode()) tuesdayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink_dm));
                else tuesdayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink));
                tuesdayName.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                tuesdayNumber.setTextColor(getActivity().getResources().getColor(R.color.pink));
                displayList(tuesday);
                break;
            case Calendar.WEDNESDAY:
                if (isDarkMode()) wednesdayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink_dm));
                else wednesdayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink));
                wednesdayName.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                wednesdayNumber.setTextColor(getActivity().getResources().getColor(R.color.pink));
                displayList(wednesday);
                break;
            case Calendar.THURSDAY:
                if (isDarkMode()) thursdayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink_dm));
                else thursdayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink));
                thursdayName.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                thursdayNumber.setTextColor(getActivity().getResources().getColor(R.color.pink));
                displayList(thursday);
                break;
            default:
                if (isDarkMode()) fridayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink_dm));
                else fridayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink));
                fridayName.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                fridayNumber.setTextColor(getActivity().getResources().getColor(R.color.pink));
                displayList(friday);
                break;

        }
    }

    public void unselectAllDays() {
        if (isDarkMode()) {
            header.setBackground(getActivity().getResources().getDrawable(R.drawable.header_dark));
            mondayLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.day_background_dark));
            tuesdayLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.day_background_dark));
            wednesdayLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.day_background_dark));
            thursdayLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.day_background_dark));
            fridayLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.day_background_dark));
        }
        else {
            header.setBackground(getActivity().getResources().getDrawable(R.drawable.header_light));
            mondayLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.day_background_light));
            tuesdayLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.day_background_light));
            wednesdayLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.day_background_light));
            thursdayLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.day_background_light));
            fridayLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.day_background_light));
        }

        mondayName.setTextColor(getActivity().getResources().getColor(R.color.grey));
        tuesdayName.setTextColor(getActivity().getResources().getColor(R.color.grey));
        wednesdayName.setTextColor(getActivity().getResources().getColor(R.color.grey));
        thursdayName.setTextColor(getActivity().getResources().getColor(R.color.grey));
        fridayName.setTextColor(getActivity().getResources().getColor(R.color.grey));

        if (isDarkMode()) {
            mondayNumber.setTextColor(getActivity().getResources().getColor(R.color.white));
            tuesdayNumber.setTextColor(getActivity().getResources().getColor(R.color.white));
            wednesdayNumber.setTextColor(getActivity().getResources().getColor(R.color.white));
            thursdayNumber.setTextColor(getActivity().getResources().getColor(R.color.white));
            fridayNumber.setTextColor(getActivity().getResources().getColor(R.color.white));
        } else {
            mondayNumber.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
            tuesdayNumber.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
            wednesdayNumber.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
            thursdayNumber.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
            fridayNumber.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
        }

    }

    public boolean isDarkMode() {
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                return true;
            default:
                return false;
        }
    }
}