package com.example.project1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project1.databinding.FragmentToDoBinding;

public class ToDoFrag extends Fragment {

    private FragmentToDoBinding binding;
    private static LinearLayout linearLayout;
    private static View view;
    private static LayoutInflater inflater;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        this.inflater = inflater;
        view = inflater.inflate(R.layout.fragment_assignment, container, false);
        binding = FragmentToDoBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }


    public static void updateScreen(boolean allAssignments) {
        linearLayout.removeAllViews();
        for(int i = 0; i < MainActivity.assignments.size(); i++) {
            if(MainActivity.assignments.get(i).isToDo()) {
                if (allAssignments) {
                    linearLayout.addView(createAssignmentTemplate(MainActivity.assignments.get(i)));
                } else if(!MainActivity.assignments.get(i).isComplete()) {
                    linearLayout.addView(createAssignmentTemplate(MainActivity.assignments.get(i)));
                }
            }
        }
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.invalidate();
        linearLayout.requestLayout();
        view.invalidate();
        view.requestLayout();
    }

    protected static View createAssignmentTemplate(Assignments testAssignment) {
        View cardView = inflater.inflate(R.layout.assignment_template, null);
        TextView assignmentName = cardView.findViewById(R.id.assignmentNamePlaceHolder);
        TextView dueClass = cardView.findViewById(R.id.classDue);
        TextView weekDays = cardView.findViewById(R.id.dueDate);
        TextView times = cardView.findViewById(R.id.timeText);
        TextView locationText = cardView.findViewById(R.id.locationText);
        cardView.setBackgroundColor(Color.BLACK);

        assignmentName.setText(testAssignment.getTitle());
        dueClass.setText(testAssignment.getDueClass().toString());
        weekDays.setText(testAssignment.getDueDateAsString());
        times.setText(testAssignment.getTimeAsString());

        cardView.setVisibility(View.VISIBLE);
        cardView.findViewById(R.id.completetionCheckbox).setOnClickListener(v -> {
            testAssignment.complete();
            updateScreen(false);
        });
        return cardView;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        linearLayout = view.findViewById(R.id.toDoListHolder);
        updateScreen(false);
        ((Button)view.findViewById(R.id.HomeButtonForTODO)).setOnClickListener( v -> {
            NavHostFragment.findNavController(ToDoFrag.this)
                    .navigate(R.id.action_toDoFrag_to_FirstFragment);
        });
        view.findViewById(R.id.AssignmentTabForTODO).setOnClickListener( v -> {
            NavHostFragment.findNavController(ToDoFrag.this)
                    .navigate(R.id.action_toDoFrag_to_assignmentCreationFrag);
        });
        view.findViewById(R.id.TODOButtonForTODO).setOnClickListener( v -> {
            NavHostFragment.findNavController(ToDoFrag.this)
                    .navigate(R.id.action_toDoFrag_self);
        });
        view.findViewById(R.id.CalendarButtonForTODO).setOnClickListener( v -> {
            NavHostFragment.findNavController(ToDoFrag.this)
                    .navigate(R.id.action_toDoFrag_to_examFrag);
        });

        view.findViewById(R.id.ToDoEditButtonTODO).setOnClickListener( v -> {
            NavHostFragment.findNavController(ToDoFrag.this)
                    .navigate(R.id.action_toDoFrag_to_assignmentEditingFrag2);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}