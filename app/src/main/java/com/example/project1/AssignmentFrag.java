package com.example.project1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project1.databinding.FragmentAssignmentBinding;
import com.example.project1.databinding.FragmentAssignmentCreationBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class AssignmentFrag extends Fragment {

    private FragmentAssignmentBinding binding;
    private MainActivity mainActive;
    private Assignments selectedCompleteAssign;
    private LocalTime dueTime;
    private LocalDate dueDay;
    private String[] assignmentArray, classArray;
    private Assignments selectedAssignment;
    private Assignments selectedDeletedAssignment;
    private Toast message;
    private static LayoutInflater inflater;
    private static LinearLayout linearLayout;
    private static ArrayAdapter<CharSequence> filterAdapter, assignAdapter;
    private static View rootView;
    private boolean allItems = false;
    private Spinner assignmentSpinner, filterSpinner;
    private ViewGroup container;
    private boolean isBackwards = false;
    private Class selectedClass = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_assignment, container, false);
        this.inflater = inflater;
        this.container = container;
        binding = FragmentAssignmentBinding.inflate(inflater, container, false);
        linearLayout = rootView.findViewById(R.id.assignmentHolderLayout);
        int size = MainActivity.assignments.size();
        baseUpdateScreen(null, false,false);


        return rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = rootView.findViewById(R.id.assignmentHolderLayout);

        filterSpinner = rootView.findViewById(R.id.filterSpinnerForClass11111);

        classArray = new String[MainActivity.classes.size() + 1];
        classArray[0] = "Please Select a class!";
        for (int i = 0; i < MainActivity.classes.size(); i ++) {
            classArray[i + 1] = MainActivity.classes.get(i).getClassName();
        }
        ArrayAdapter<CharSequence> adapter;
        filterAdapter = new ArrayAdapter<CharSequence>(rootView.getContext(), android.R.layout.simple_spinner_item, classArray);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(filterAdapter);


        baseUpdateScreen(null, false, false);
        rootView.findViewById(R.id.AssignmentRadioButton).setOnClickListener(v ->{
            allItems = true;
            baseUpdateScreen(selectedClass, allItems, isBackwards);
        });
        rootView.findViewById(R.id.ImcompleteOnlyButton).setOnClickListener(v ->{
            allItems = false;
            baseUpdateScreen(selectedClass, allItems, isBackwards);
        });
        rootView.findViewById(R.id.furthestOutButton).setOnClickListener(v -> {
            isBackwards = true;
            baseUpdateScreen(selectedClass, allItems, isBackwards);

        });
        rootView.findViewById(R.id.closestDate).setOnClickListener(v ->{
            isBackwards = false;
            baseUpdateScreen(selectedClass, allItems, isBackwards);
        });

        binding = FragmentAssignmentBinding.inflate(inflater, container, false);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    selectedClass = null;
                    baseUpdateScreen(selectedClass, allItems, isBackwards);
                    return;
                }
                selectedClass = MainActivity.getClasses().get(position - 1);
                baseUpdateScreen(selectedClass, allItems, isBackwards);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });






        view.findViewById(R.id.EditAssignmentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AssignmentFrag.this)
                        .navigate(R.id.action_assignmentFrag_to_assignmentEditingFrag2);
            }
        });
        view.findViewById(R.id.HomeButtonForAssignment).setOnClickListener(v -> {
            NavHostFragment.findNavController(AssignmentFrag.this)
                    .navigate(R.id.action_assignmentFrag_to_FirstFragment);
        });
        view.findViewById(R.id.CalendarButtonForAssignment).setOnClickListener(v -> {
            NavHostFragment.findNavController(AssignmentFrag.this)
                    .navigate(R.id.action_assignmentFrag_to_examFrag);
        });
        view.findViewById(R.id.TODOButtonForAssignment).setOnClickListener(v -> {
            NavHostFragment.findNavController(AssignmentFrag.this)
                    .navigate(R.id.action_assignmentFrag_to_toDoFrag);
        });
        view.findViewById(R.id.button4ForAssignment).setOnClickListener(v -> {
            NavHostFragment.findNavController(AssignmentFrag.this)
                    .navigate(R.id.action_assignmentFrag_to_assignmentCreationFrag);
        });
    }

    public void baseUpdateScreen(Class selectedClass, boolean allItems, boolean isBackwards) {
        ArrayList<Assignments> assignments = MainActivity.getAssignments();
        if(!allItems) {
            assignments = findCompleted(assignments);
        }
        if(selectedClass != null) {
            assignments = findByClass(selectedClass, assignments);
        }
        if (isBackwards) {
            updateScreenBackwards(assignments);
        } else {
            updateScreen(assignments);
        }
    }

    private ArrayList<Assignments> findByClass(Class selectedClass,ArrayList<Assignments> arrayList) {
        ArrayList<Assignments> assignmentsHere = new ArrayList<>();
        for(int i = 0; i < arrayList.size(); i++) {
            if (selectedClass.equals(arrayList.get(i).getDueClass())) {
                assignmentsHere.add(arrayList.get(i));
            }
        }
        return assignmentsHere;
    }

    public ArrayList<Assignments> findCompleted(ArrayList<Assignments> arrayList) {
        ArrayList<Assignments> assignmentsHere = new ArrayList<>();
        for(int i = 0; i < arrayList.size(); i++) {
            if (!arrayList.get(i).isComplete()) {
                assignmentsHere.add(arrayList.get(i));
            }
        }
        return assignmentsHere;
    }
    private void updateScreenBackwards(ArrayList<Assignments> assignments) {
        linearLayout.removeAllViews();
        for(int i = assignments.size() - 1; i >= 0; i--) {
            linearLayout.addView(createAssignmentTemplate(assignments.get(i)));
        }
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.invalidate();
        linearLayout.requestLayout();
        rootView.invalidate();
        rootView.requestLayout();
    }



    public void updateScreen(ArrayList<Assignments> assignments) {
        linearLayout.removeAllViews();
        for(int i = 0; i < assignments.size(); i++) {
            linearLayout.addView(createAssignmentTemplate(assignments.get(i)));
        }
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.invalidate();
        linearLayout.requestLayout();
        rootView.invalidate();
        rootView.requestLayout();
    }


    protected View createAssignmentTemplate(Assignments testAssignment) {
        View cardView = inflater.inflate(R.layout.assignment_template, null);
        TextView assignmentName = cardView.findViewById(R.id.assignmentNamePlaceHolder);
        TextView dueClass = cardView.findViewById(R.id.classDue);
        TextView weekDays = cardView.findViewById(R.id.dueDate);
        TextView times = cardView.findViewById(R.id.timeText);
        TextView locationText = cardView.findViewById(R.id.locationText);
        cardView.setBackgroundColor(Color.BLACK);

        if(testAssignment instanceof Exam) {
            locationText.setText(((Exam)testAssignment).getLocation());
            locationText.setVisibility(View.VISIBLE);
        }
        assignmentName.setText(testAssignment.getTitle());
        dueClass.setText(testAssignment.getDueClass().toString());
        weekDays.setText(testAssignment.getDueDateAsString());
        times.setText(testAssignment.getTimeAsString());

        cardView.setVisibility(View.VISIBLE);
        cardView.findViewById(R.id.completetionCheckbox).setOnClickListener(v -> {
            testAssignment.complete();
            baseUpdateScreen(selectedClass, allItems, isBackwards);
        });
        return cardView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}