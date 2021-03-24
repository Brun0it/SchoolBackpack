package com.theog.schoolbackpack.ui.edt;

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

import com.theog.schoolbackpack.controller.NewDevoirDialog;
import com.theog.schoolbackpack.model.Class;
import com.theog.schoolbackpack.controller.ClassAdapter;
import com.theog.schoolbackpack.model.DataBaseManager;
import com.theog.schoolbackpack.R;
import com.theog.schoolbackpack.model.Devoir;
import com.theog.schoolbackpack.model.Subject;
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
    private ImageView currentDate;
    private Calendar calendar;
    private int weekNmb;
    private final List<Devoir> devoirs = new ArrayList<>();
    private FloatingActionButton newDevoir;

    public Calendar getCalendar() {
        return calendar;
    }

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
        newDevoir.setOnClickListener(v -> new NewDevoirDialog().showDialog(this));

        if (isDarkMode()) {
            previousWeek.setColorFilter(Color.argb(255, 255, 255, 255));
            nextWeek.setColorFilter(Color.argb(255, 255, 255, 255));
        }

        //generateDays();
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

    public void displayList(@NotNull int idJour) {
        DataBaseManager db = new DataBaseManager(requireActivity());
        displayList(db.getShortedClasses(idJour));
        db.close();
    }

    public void displayList(List<Class> classes) {

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ClassAdapter classAdapter = new ClassAdapter(classes, devoirs, calendar, getActivity(), getContext(), this, isDarkMode());
        recyclerView.setAdapter(classAdapter);

    }

    public void generateDays() {
        /*Calendar time1 = Calendar.getInstance();
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
        time6.set(Calendar.MINUTE, 0);*/

        DataBaseManager db = new DataBaseManager(requireActivity());
        List<Subject> subjects = db.getSubjects();
        db.close();

        /*Calendar time0 = Calendar.getInstance();
        time0.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        monday.add(new Class(time0.get(Calendar.DAY_OF_WEEK),francais, "A21", time1, 60));
        monday.add(new Class(time0.get(Calendar.DAY_OF_WEEK),techno, "B12", time2, 60));
        monday.add(new Class(time0.get(Calendar.DAY_OF_WEEK),anglais, "C15", time4, 60));
        monday.add(new Class(time0.get(Calendar.DAY_OF_WEEK),maths, "E24", time5, 120));

        time0.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        tuesday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), histoireGeo, "B13", time1, 60));
        tuesday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), anglais, "A12", time2, 60));
        tuesday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), lv2, "A01", time3, 60));
        tuesday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), artplastique, "B07", time5, 120));

        time0.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        wednesday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), francais, "A21", time1, 60));
        wednesday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), histoireGeo, "B13", time2, 60));
        wednesday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), lv2, "D12", time4, 60));

        time0.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        thursday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), maths, "C13", time1, 60));
        thursday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), techno, "B13", time3, 60));
        thursday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), francais, "D12", time4, 60));
        thursday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), musique, "B07", time6, 60));

        time0.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        friday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), anglais, "E15", time2, 60));
        friday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), sport, "Gymnase", time3, 120));
        friday.add(new Class(time0.get(Calendar.DAY_OF_WEEK), maths, "B13", time5, 60));
        */

        /*
        Devoir eval1 = new Devoir(subjects.get(0), Calendar.getInstance(), "eval1", true);
        Devoir exo1 = new Devoir(subjects.get(3), Calendar.getInstance(), "exo1", false);
        Devoir exo2 = new Devoir(subjects.get(1), Calendar.getInstance(), "exo2", false);
        Devoir exo3 = new Devoir(subjects.get(2), Calendar.getInstance(), "exo3", false);

        devoirs.add(eval1);
        devoirs.add(exo1);
        devoirs.add(exo2);
        devoirs.add(exo3);
         */
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
                displayList(Calendar.MONDAY);
                break;
            case 3:
                if (isDarkMode()) tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                tuesdayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                tuesdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(Calendar.TUESDAY);
                break;
            case 4:
                if (isDarkMode()) wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                wednesdayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                wednesdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(Calendar.WEDNESDAY);
                break;
            case 5:
                if (isDarkMode()) thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                thursdayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                thursdayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(Calendar.THURSDAY);
                break;
            case 6:
                if (isDarkMode()) fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink_dm));
                else fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_pink));
                fridayName.setTextColor(requireActivity().getResources().getColor(R.color.dark_grey));
                fridayNumber.setTextColor(requireActivity().getResources().getColor(R.color.pink));
                displayList(Calendar.FRIDAY);
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