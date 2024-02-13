package com.example.project1;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project1.databinding.FragmentClassCreationBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;


public class ClassCreationFrag extends Fragment {

    private FragmentClassCreationBinding ClassCreationRealBinding;
    private LocalTime startTime, endTime;
    private int startHour,startMinute,endHour,endMinute;

    private MainActivity mainActivity = new MainActivity();
    private View rootView;
    private MainActivity mainActive;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        ClassCreationRealBinding = FragmentClassCreationBinding.inflate(inflater, container, false);
        rootView = inflater.inflate(R.layout.fragment_class_creation, container, false);







        return ClassCreationRealBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.submitClassButton111111).setOnClickListener(v -> {
            TextInputEditText classNameInput = (TextInputEditText)view.findViewById(R.id.ClassNameInput);
            TextInputEditText profNameInput = (TextInputEditText)view.findViewById(R.id.ProfessorInput);
            TextInputEditText locNameInput = (TextInputEditText)view.findViewById(R.id.LocationInput);

            String className = classNameInput.getText().toString();
            String profName = (profNameInput).getText().toString();
            String location = (locNameInput).getText().toString();
            ArrayList<DayOfWeek> days = new ArrayList<>();
            if (((CheckBox)view.findViewById(R.id.checkBox)).isChecked()) {
                days.add(DayOfWeek.MONDAY);
            }
            if (((CheckBox)view.findViewById(R.id.checkBox2)).isChecked()) {
                days.add(DayOfWeek.TUESDAY);
            }
            if (((CheckBox)view.findViewById(R.id.checkBox3)).isChecked()) {
                days.add(DayOfWeek.WEDNESDAY);
            }
            if (((CheckBox)view.findViewById(R.id.checkBox4)).isChecked()) {
                days.add(DayOfWeek.THURSDAY);
            }
            if (((CheckBox)view.findViewById(R.id.checkBox5)).isChecked()) {
                days.add(DayOfWeek.FRIDAY);
            }
            Class addedClass = new Class(className, profName,  days, startTime, endTime, location);
            MainActivity.addToClasses(addedClass);
            NavHostFragment.findNavController(ClassCreationFrag.this)
                    .navigate(R.id.action_classCreationFrag_to_FirstFragment);
        });
        view.findViewById(R.id.StartTimeButton11111).setOnClickListener(v -> {
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    startHour = hourOfDay;
                    startMinute = minute;
                    startTime = LocalTime.of(startHour, startMinute);
                    Toast message = new Toast(rootView.getContext());
                    if (startHour < 12) {
                        if (startHour == 0) {
                            startHour = 12;
                        }
                        String test = (String.format("You selected %2d:%02d AM as your starting time", startHour, startMinute));
                        message.setText(test);
                        message.setDuration(Toast.LENGTH_LONG);
                    } else {
                        if (startHour == 12) {
                            startHour = 24;
                        }
                        String text = (String.format("You selected %2d:%02d PM as your starting time", startHour - 12, startMinute));
                        message.setText(text);
                        message.setDuration(Toast.LENGTH_LONG);
                    }
                    message.show();
                }
            };
            int style = TimePickerDialog.THEME_HOLO_DARK;
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), style, onTimeSetListener, startHour, startMinute, false);
            timePickerDialog.setTitle("Start Time Selector");
            timePickerDialog.show();
        });

        ((Button) view.findViewById(R.id.EndTimeBtn1111)).setOnClickListener(v -> {
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    endHour = hourOfDay;
                    endMinute = minute;
                    endTime = LocalTime.of(endHour, endMinute);
                    Toast message = new Toast(rootView.getContext());
                    if (endHour < 12) {
                        if (endHour == 0) {
                            endHour = 12;
                        }
                        String test = (String.format("You selected %2d:%02d AM as your ending time", endHour, endMinute));
                        message.setText(test);
                        message.setDuration(Toast.LENGTH_LONG);
                    } else {
                        if (endHour == 12) {
                            endHour = 24;
                        }
                        String test = (String.format("You selected %2d:%02d PM as your ending time", endHour - 12, endMinute));
                        message.setText(test);
                        message.setDuration(Toast.LENGTH_LONG);
                    }
                    message.show();
                }
            };
            int style = TimePickerDialog.THEME_HOLO_DARK;
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), style, onTimeSetListener, endHour, endMinute, false);
            timePickerDialog.setTitle("Start Time Selector");
            timePickerDialog.show();
        });
        view.findViewById(R.id.HomeButtonForClassCreation).setOnClickListener( v -> {
            NavHostFragment.findNavController(ClassCreationFrag.this)
                    .navigate(R.id.action_classCreationFrag_to_FirstFragment);
        });

        view.findViewById(R.id.TODOButtonForClassCreation).setOnClickListener( v -> {
            NavHostFragment.findNavController(ClassCreationFrag.this)
                    .navigate(R.id.action_classCreationFrag_to_toDoFrag);
        });
        view.findViewById(R.id.ExamButtonForClassCreation).setOnClickListener( v -> {
            NavHostFragment.findNavController(ClassCreationFrag.this)
                    .navigate(R.id.action_classCreationFrag_to_examFrag);
        });
        view.findViewById(R.id.AddButtonForClassCreation).setOnClickListener( v -> {
            NavHostFragment.findNavController(ClassCreationFrag.this)
                    .navigate(R.id.action_classCreationFrag_to_assignmentCreationFrag);
        });
    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ClassCreationRealBinding = null;
    }

}