package com.example.project1;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


import com.example.project1.databinding.ClassTemplateBinding;
import com.example.project1.databinding.FragmentFirstBinding;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ViewGroup container;

    protected static View view;
    protected static LayoutInflater inflater;
    protected static LinearLayout linearLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        view = inflater.inflate(R.layout.fragment_first, container, false);
        this.container = container;
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        linearLayout = view.findViewById(R.id.ClassLayoutDemo111111111111);

        ArrayList<Class> classes = MainActivity.classes;

        updateScreen();
        return view;

    }
    public static void updateScreen() {
        linearLayout.removeAllViews();
        for(int i = 0; i < MainActivity.classes.size(); i++) {
            linearLayout.addView(createClassTemplate(MainActivity.classes.get(i)));
        }
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.invalidate();
        linearLayout.requestLayout();
        view.invalidate();
        view.requestLayout();
        view.findViewById(R.id.scrollViewHolder).invalidate();
        view.findViewById(R.id.scrollViewHolder).requestLayout();
    }

    protected static View createClassTemplate(Class testClass) {
        View cardView = inflater.inflate(R.layout.class_template, null);
        TextView className = cardView.findViewById(R.id.ClassNameInput);
        TextView profName = cardView.findViewById(R.id.instructorName);
        TextView weekDays = cardView.findViewById(R.id.daysOfWeek);
        TextView times = cardView.findViewById(R.id.timeText);
        TextView locationText = cardView.findViewById(R.id.locationText);
        cardView.setBackgroundColor(Color.BLACK);

        className.setText(testClass.getClassName());
        profName.setText(testClass.getProf());
        weekDays.setText(testClass.getDaysToString());
        times.setText(testClass.getTimeAsString());
        locationText.setText(testClass.getLocation());
        cardView.setVisibility(View.VISIBLE);

        return cardView;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.AddAssignmentButton).setOnClickListener( v -> {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_assignmentCreationFrag);

        });

        view.findViewById(R.id.AssignmentButtonForHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_assignmentFrag);
            }
        });
        view.findViewById(R.id.EditClassButtonForHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_classEditingFrag);
            }
        });
        view.findViewById(R.id.ExamButtonForHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_examFrag);
            }
        });
        view.findViewById(R.id.TODOButtonForHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_toDoFrag);
            }
        });
        view.findViewById(R.id.HomeButtonForHome1111).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_self);
            }
        });
        view.findViewById(R.id.NewClassButtonForHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_classCreationFrag);
            }
        });
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}