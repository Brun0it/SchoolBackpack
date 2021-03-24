package com.theog.schoolbackpack.ui.profil;

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

import com.theog.schoolbackpack.R;
import com.theog.schoolbackpack.controller.AddClassDialog;
import com.theog.schoolbackpack.controller.ClassAdapter;
import com.theog.schoolbackpack.controller.CreateSubjectDialog;
import com.theog.schoolbackpack.controller.MatiereAdapter;
import com.theog.schoolbackpack.model.Class;
import com.theog.schoolbackpack.model.DataBaseManager;
import com.theog.schoolbackpack.model.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class ProfilFragment extends Fragment {

    //private ProfilViewModel profilViewModel;
    private ConstraintLayout mainPage, edtPage, matPage, header, headerEdt, headerMat, mondayLayout, tuesdayLayout, wednesdayLayout, thursdayLayout, fridayLayout;
    private LinearLayout edt, mat;
    private FloatingActionButton addCours, addMatiere;
    private ImageView backEdt, backMat;
    RecyclerView recyclerView, recyclerViewMat;
    private Calendar calendar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //profilViewModel = new ViewModelProvider(this).get(ProfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profil, container, false);

        mainPage = root.findViewById(R.id.mainPage);
        edtPage = root.findViewById(R.id.edtPage);
        matPage = root.findViewById(R.id.matieresPage);
        header = root.findViewById(R.id.header);
        headerEdt = root.findViewById(R.id.headerEdt);
        headerMat = root.findViewById(R.id.headerMat);
        edt = root.findViewById(R.id.edt);
        mat = root.findViewById(R.id.matieres);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerViewMat = root.findViewById(R.id.recyclerViewMatieres);
        edt.setOnClickListener(v -> displayPage(edtPage));
        mat.setOnClickListener(v -> displayPage(matPage));
        addCours = root.findViewById(R.id.addCours);
        addCours.setOnClickListener(v -> addCours());
        addMatiere = root.findViewById(R.id.addMatiere);
        addMatiere.setOnClickListener(v -> addMatiere());
        backEdt = root.findViewById(R.id.backEdt);
        backMat = root.findViewById(R.id.backMat);
        backEdt.setOnClickListener(v -> displayPage(mainPage));
        backMat.setOnClickListener(v -> displayPage(mainPage));

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

    public void displayMat() {
        DataBaseManager db = new DataBaseManager(requireActivity());
        List<Subject> matieres = db.getSubjects();
        db.close();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewMat.setLayoutManager(layoutManager);

        MatiereAdapter matiereAdapter = new MatiereAdapter(matieres, this, isDarkMode());
        recyclerViewMat.setAdapter(matiereAdapter);
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
            headerMat.setBackground(requireActivity().getDrawable(R.drawable.header_dark));
            mondayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            tuesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            wednesdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            thursdayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
            fridayLayout.setBackground(requireActivity().getDrawable(R.drawable.day_background_dark));
        } else {
            header.setBackground(requireActivity().getDrawable(R.drawable.header_light));
            headerEdt.setBackground(requireActivity().getDrawable(R.drawable.header_light));
            headerMat.setBackground(requireActivity().getDrawable(R.drawable.header_light));
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
            displayList(calendar.get(Calendar.DAY_OF_WEEK));
            db.close();
        } else if (page == matPage) {
            displayMat();
        }
    }

    public void hideAllPages() {
        mainPage.setVisibility(View.GONE);
        edtPage.setVisibility(View.GONE);
        matPage.setVisibility(View.GONE);
    }

    public void addCours() {
        new AddClassDialog().showDialog(this, calendar.get(Calendar.DAY_OF_WEEK));
    }

    public void addMatiere() {
        new CreateSubjectDialog().showDialog(this);
    }

    public boolean isDarkMode() {
        return (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }
}