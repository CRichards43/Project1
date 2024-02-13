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

import com.example.project1.databinding.FragmentExamBinding;


public class ExamFrag extends Fragment {

    private FragmentExamBinding binding;
    private static LinearLayout linearLayout;
    private static View rootView;
    private static LayoutInflater inflater;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        this.inflater = inflater;
        rootView = inflater.inflate(R.layout.fragment_exam, container, false);
        binding = FragmentExamBinding.inflate(inflater, container, false);
        return rootView;
    }

    public static void updateScreen(boolean allAssignments) {
        linearLayout.removeAllViews();
        for(int i = 0; i < MainActivity.assignments.size(); i++) {
            if (MainActivity.assignments.get(i) instanceof Exam) {
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
        rootView.invalidate();
        rootView.requestLayout();
    }

    protected static View createAssignmentTemplate(Assignments testAssignment) {
        View cardView = inflater.inflate(R.layout.assignment_template, null);
        TextView assignmentName = cardView.findViewById(R.id.assignmentNamePlaceHolder);
        TextView dueClass = cardView.findViewById(R.id.classDue);
        TextView weekDays = cardView.findViewById(R.id.dueDate);
        TextView times = cardView.findViewById(R.id.timeText);
        TextView locationText = cardView.findViewById(R.id.locationText);
        cardView.setBackgroundColor(Color.BLACK);

        locationText.setText(((Exam)testAssignment).getLocation());
        locationText.setVisibility(View.VISIBLE);
        assignmentName.setText(testAssignment.getTitle());
        dueClass.setText(testAssignment.getDueClass().toString());
        weekDays.setText(testAssignment.getDueDateAsString());
        times.setText(testAssignment.getTimeAsString());

        cardView.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.VISIBLE);
        cardView.findViewById(R.id.completetionCheckbox).setOnClickListener(v -> {
            testAssignment.complete();
            updateScreen(false);
        });
        return cardView;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = rootView.findViewById(R.id.examTemplateHolder);


        updateScreen(false);
        view.findViewById(R.id.HomeButtonForExam).setOnClickListener( v -> {
            NavHostFragment.findNavController(ExamFrag.this)
                    .navigate(R.id.action_examFrag_to_FirstFragment);

                }
        );
        view.findViewById(R.id.TODOButtonForExam).setOnClickListener( v -> {
                    NavHostFragment.findNavController(ExamFrag.this)
                            .navigate(R.id.action_examFrag_to_toDoFrag);
                }
        );
        view.findViewById(R.id.ExamButtonForExam).setOnClickListener( v -> {
            NavHostFragment.findNavController(ExamFrag.this)
                    .navigate(R.id.action_examFrag_self);
        });
        view.findViewById(R.id.AssignmentTabForExam).setOnClickListener( v -> {
            NavHostFragment.findNavController(ExamFrag.this)
                    .navigate(R.id.action_examFrag_to_assignmentCreationFrag);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}