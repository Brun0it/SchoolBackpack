package com.cours.schoolbackpack.ui.profil;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cours.schoolbackpack.R;
import com.cours.schoolbackpack.controller.AddClassDialog;
import com.cours.schoolbackpack.controller.ClassAdapter;
import com.cours.schoolbackpack.model.Class;
import com.cours.schoolbackpack.model.DataBaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProfilFragment extends Fragment {

    //private ProfilViewModel profilViewModel;
    private ConstraintLayout mainPage, edtPage, header, headerEdt, mondayLayout, tuesdayLayout, wednesdayLayout, thursdayLayout, fridayLayout;
    private LinearLayout edt;
    private FloatingActionButton addCours;
    private ImageView backEdt;
    RecyclerView recyclerView;
    private Calendar calendar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //profilViewModel = new ViewModelProvider(this).get(ProfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profil, container, false);

        mainPage = root.findViewById(R.id.mainPage);
        edtPage = root.findViewById(R.id.edtPage);
        header = root.findViewById(R.id.header);
        headerEdt = root.findViewById(R.id.headerEdt);
        edt = root.findViewById(R.id.edt);
        recyclerView = root.findViewById(R.id.recyclerView);
        edt.setOnClickListener(v -> displayPage(edtPage));
        addCours = root.findViewById(R.id.addCours);
        addCours.setOnClickListener(v -> addCours());
        backEdt = root.findViewById(R.id.backEdt);
        backEdt.setOnClickListener(v -> displayPage(mainPage));

        mondayLayout = root.findViewById(R.id.mondayLayout);
        mondayLayout.setOnClickListener(v -> {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            updateHeader();
        });
        tuesdayLayout = root.findViewById(R.id.tuesdayLayout);
        tuesdayLayout.setOnClickListener(v -> {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            updateHeader();
        });
        wednesdayLayout = root.findViewById(R.id.wednesdayLayout);
        wednesdayLayout.setOnClickListener(v -> {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            updateHeader();
        });
        thursdayLayout = root.findViewById(R.id.thursdayLayout);
        thursdayLayout.setOnClickListener(v -> {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            updateHeader();
        });
        fridayLayout = root.findViewById(R.id.fridayLayout);
        fridayLayout.setOnClickListener(v -> {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            updateHeader();
        });

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        setTheme();
        displayPage(mainPage);
        updateHeader();

        return root;
    }

    public void displayList(int day) {
        DataBaseManager db = new DataBaseManager(requireContext());
        displayList(db.getShortedClasses(day));
        db.close();
    }

    public void displayList(List<Class> classes) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ClassAdapter classAdapter = new ClassAdapter(classes, null, calendar, getActivity(), getContext(), this, isDarkMode());
        recyclerView.setAdapter(classAdapter);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void updateHeader() {
        if (isDarkMode()) {
            mondayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
        } else {
            mondayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
            tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
            wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
            thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
            fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
        }

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.TUESDAY:
                tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.background_pink_day));
                break;
            case Calendar.WEDNESDAY:
                wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.background_pink_day));
                break;
            case Calendar.THURSDAY:
                thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.background_pink_day));
                break;
            case Calendar.FRIDAY:
                fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.background_pink_day));
                break;
            default:
                mondayLayout.setBackground(requireActivity().getDrawable(R.drawable.background_pink_day));
                break;
        }

        displayList(calendar.get(Calendar.DAY_OF_WEEK));
    }

    public void setTheme() {
        if (isDarkMode()) {
            header.setBackground(requireActivity().getDrawable(R.drawable.header_dark));
            headerEdt.setBackground(requireActivity().getDrawable(R.drawable.header_dark));
            mondayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
        } else {
            header.setBackground(requireActivity().getDrawable(R.drawable.header_light));
            headerEdt.setBackground(requireActivity().getDrawable(R.drawable.header_light));
            mondayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
            tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
            wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
            thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
            fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_light));
        }
    }

    public void displayPage(ConstraintLayout page) {
        hideAllPages();
        page.setVisibility(View.VISIBLE);
        if (page == edtPage) {
            DataBaseManager db = new DataBaseManager(requireContext());
            displayList(db.getClasses(calendar.get(Calendar.DAY_OF_WEEK)));
            db.close();
        }
    }

    public void hideAllPages() {
        mainPage.setVisibility(View.GONE);
        edtPage.setVisibility(View.GONE);
    }

    public void addCours() {
        new AddClassDialog().showDialog(this, calendar.get(Calendar.DAY_OF_WEEK));
    }

    public boolean isDarkMode() {
        return (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }
}