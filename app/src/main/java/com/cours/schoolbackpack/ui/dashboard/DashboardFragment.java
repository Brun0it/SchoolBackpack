package com.cours.schoolbackpack.ui.dashboard;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cours.schoolbackpack.Class;
import com.cours.schoolbackpack.ClassAdapter;
import com.cours.schoolbackpack.Day;
import com.cours.schoolbackpack.R;
import com.cours.schoolbackpack.ui.Week;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ConstraintLayout mondayLayout, tuesdayLayout, wednesdayLayout, thursdayLayout, fridayLayout;
    private TextView mondayName, tuesdayName, wednesdayName, thursdayName, fridayName, mondayNumber, tuesdayNumber, wednesdayNumber, thursdayNumber, fridayNumber;
    private Day monday = new Day(), tuesday= new Day(), wednesday= new Day(), thursday= new Day(), friday= new Day();
    private Week week = new Week();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

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
                selectDay(1);
            }
        });
        tuesdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAllDays();
                selectDay(2);
            }
        });
        wednesdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAllDays();
                selectDay(3);
            }
        });
        thursdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAllDays();
                selectDay(4);
            }
        });
        fridayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAllDays();
                selectDay(5);
            }
        });


        unselectAllDays();
        generateDays();
        selectDay(1);
        return root;
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
        time1.set(Calendar.HOUR_OF_DAY, 8);
        time1.set(Calendar.MINUTE, 0);
        time2.set(Calendar.HOUR_OF_DAY, 9);
        time2.set(Calendar.MINUTE, 30);
        time3.set(Calendar.HOUR_OF_DAY, 10);
        time3.set(Calendar.MINUTE, 15);
        monday.add(new Class("iOS", "A21", "M. Walkowiak", time1, 90));
        monday.add(new Class("Communication", "B19", "Mme Vieillard", time2, 45));
        monday.add(new Class("Programmation répartie", "D13", "M. Ernet", time3, 180));

        tuesday.add(new Class("ABD", "A21", "M. Walkowiak", time1, 30));
        tuesday.add(new Class("DEF", "B19", "Mme Vieillard", time2, 60));
        tuesday.add(new Class("GHI", "D13", "M. Ernet", time3, 180));
    }

    public void selectDay(int number) {
        switch (number) {
            case 1:
                mondayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink));
                mondayName.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                mondayNumber.setTextColor(getActivity().getResources().getColor(R.color.pink));
                displayList(monday);
                break;
            case 2:
                tuesdayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink));
                tuesdayName.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                tuesdayNumber.setTextColor(getActivity().getResources().getColor(R.color.pink));
                displayList(tuesday);
                break;
            case 3:
                wednesdayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink));
                wednesdayName.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                wednesdayNumber.setTextColor(getActivity().getResources().getColor(R.color.pink));
                displayList(wednesday);
                break;
            case 4:
                thursdayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink));
                thursdayName.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                thursdayNumber.setTextColor(getActivity().getResources().getColor(R.color.pink));
                displayList(thursday);
                break;
            default:
                fridayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_pink));
                fridayName.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                fridayNumber.setTextColor(getActivity().getResources().getColor(R.color.pink));
                displayList(friday);
                break;

        }
    }

    public void unselectAllDays() {
        mondayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_light));
        tuesdayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_light));
        wednesdayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_light));
        thursdayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_light));
        fridayLayout.setBackground(getActivity().getDrawable(R.drawable.day_background_light));

        mondayName.setTextColor(getActivity().getResources().getColor(R.color.grey));
        tuesdayName.setTextColor(getActivity().getResources().getColor(R.color.grey));
        wednesdayName.setTextColor(getActivity().getResources().getColor(R.color.grey));
        thursdayName.setTextColor(getActivity().getResources().getColor(R.color.grey));
        fridayName.setTextColor(getActivity().getResources().getColor(R.color.grey));

        mondayNumber.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
        tuesdayNumber.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
        wednesdayNumber.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
        thursdayNumber.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
        fridayNumber.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
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