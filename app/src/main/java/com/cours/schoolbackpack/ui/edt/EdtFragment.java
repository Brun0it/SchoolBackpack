package com.cours.schoolbackpack.ui.edt;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cours.schoolbackpack.controller.NewDevoirDialog;
import com.cours.schoolbackpack.model.Class;
import com.cours.schoolbackpack.controller.ClassAdapter;
import com.cours.schoolbackpack.model.Day;
import com.cours.schoolbackpack.R;
import com.cours.schoolbackpack.model.Devoir;
import com.cours.schoolbackpack.model.Subject;
import com.cours.schoolbackpack.model.Week;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EdtFragment extends Fragment {

    private EdtViewModel edtViewModel;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ConstraintLayout header, mondayLayout, tuesdayLayout, wednesdayLayout, thursdayLayout, fridayLayout;
    private TextView mondayName, tuesdayName, wednesdayName, thursdayName, fridayName, mondayNumber, tuesdayNumber, wednesdayNumber, thursdayNumber, fridayNumber, weekTextView;
    private ImageButton previousWeek, nextWeek;
    private final Day monday = new Day();
    private final Day tuesday = new Day();
    private final Day wednesday = new Day();
    private final Day thursday = new Day();
    private final Day friday = new Day();
    private final Week week = new Week();
    private ImageView currentDate;
    private Calendar calendar;
    private int weekNmb;
    private final List<Devoir> devoirs = new ArrayList<>();
    private FloatingActionButton newDevoir;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        edtViewModel =
                new ViewModelProvider(this).get(EdtViewModel.class);
        View root = inflater.inflate(R.layout.fragment_edt, container, false);

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

        localCalendar.set(Calendar.DAY_OF_WEEK, saveDayOfWeek);

        selectDay();
    }

    public void displayList(@NotNull Day day) {
        displayList(day.getClasses());
    }

    public void displayList(List<Class> classes) {

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ClassAdapter classAdapter = new ClassAdapter(classes, devoirs, calendar, getActivity(), getContext(), this, isDarkMode());
        recyclerView.setAdapter(classAdapter);

    }

    public void generateDays() {
        Calendar time1 = Calendar.getInstance();
        Calendar time2 = Calendar.getInstance();
        Calendar time3 = Calendar.getInstance();
        Calendar time4 = Calendar.getInstance();
        Calendar time5 = Calendar.getInstance();
        Calendar time6 = Calendar.getInstance();
        time1.set(Calendar.HOUR_OF_DAY, 8);
        time1.set(Calendar.MINUTE, 0);
        time2.set(Calendar.HOUR_OF_DAY, 9);
        time2.set(Calendar.MINUTE, 0);
        time3.set(Calendar.HOUR_OF_DAY, 10);
        time3.set(Calendar.MINUTE, 0);
        time4.set(Calendar.HOUR_OF_DAY, 11);
        time4.set(Calendar.MINUTE, 0);
        time5.set(Calendar.HOUR_OF_DAY, 13);
        time5.set(Calendar.MINUTE, 0);
        time6.set(Calendar.HOUR_OF_DAY, 14);
        time6.set(Calendar.MINUTE, 0);


        Subject francais = new Subject("Français", "Mme Martin");
        Subject techno = new Subject("Technologie", "M. Dupont");
        Subject anglais = new Subject("Anglais", "Mrs Smith");
        Subject maths = new Subject("Mathématiques", "Mme Durand");
        Subject svt = new Subject("SVT", "M. Jardin");
        Subject histoireGeo = new Subject("Histoire Géo", "M. Bernard");
        Subject lv2 = new Subject("LV2", "Mme Dubois");
        Subject artplastique = new Subject("Arts Plastiques", "M. Thomas");
        Subject musique = new Subject("Musique", "Mme Petit");
        Subject sport = new Subject("Sport", "M. Hamon");

        monday.add(new Class(francais, "A21", time1, 60));
        monday.add(new Class(techno, "B12", time2, 60));
        monday.add(new Class(anglais, "C15", time4, 60));
        monday.add(new Class(maths, "E24", time5, 120));

        tuesday.add(new Class(histoireGeo, "B13", time1, 60));
        tuesday.add(new Class(anglais, "A12", time2, 60));
        tuesday.add(new Class(lv2, "A01", time3, 60));
        tuesday.add(new Class(artplastique, "B07", time5, 120));

        wednesday.add(new Class(francais, "A21", time1, 60));
        wednesday.add(new Class(histoireGeo, "B13", time2, 60));
        wednesday.add(new Class(lv2, "D12", time4, 60));

        thursday.add(new Class(maths, "C13", time1, 60));
        thursday.add(new Class(techno, "B13", time3, 60));
        thursday.add(new Class(francais, "D12", time4, 60));
        thursday.add(new Class(musique, "B07", time6, 60));

        friday.add(new Class(anglais, "E15", time2, 60));
        friday.add(new Class(sport, "Gymnase", time3, 120));
        friday.add(new Class(maths, "B13", time5, 60));

        Devoir eval1 = new Devoir(lv2, Calendar.getInstance(), "eval1", true);
        Devoir exo1 = new Devoir(maths, Calendar.getInstance(), "exo1", false);
        Devoir exo2 = new Devoir(maths, Calendar.getInstance(), "exo2", false);
        Devoir exo3 = new Devoir(francais, Calendar.getInstance(), "exo3", false);

        devoirs.add(eval1);
        devoirs.add(exo1);
        devoirs.add(exo2);
        devoirs.add(exo3);
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
                displayList(monday);
                break;
            case 3:
                if (isDarkMode()) tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                tuesdayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                tuesdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(tuesday);
                break;
            case 4:
                if (isDarkMode()) wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                wednesdayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                wednesdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(wednesday);
                break;
            case 5:
                if (isDarkMode()) thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                thursdayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                thursdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(thursday);
                break;
            case 6:
                if (isDarkMode()) fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                fridayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                fridayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(friday);
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
}