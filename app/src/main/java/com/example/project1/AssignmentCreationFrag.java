package com.example.project1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.project1.databinding.FragmentAssignmentCreationBinding;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

public class AssignmentCreationFrag extends Fragment {

    private LocalDate dueDate = LocalDate.now();
    private LocalTime dueTime = LocalTime.now();
    private String startTime;
    private int startHour,startMinute;
    private Toast message;
    private Spinner classSpinner;
    private ArrayList<Class> classesHere;
    private String[] classArray;
    private Class selectedClass;
    private static View rootView;
    private FragmentAssignmentCreationBinding binding;
    private RadioButton assignButton, examButtom,toDoRadio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAssignmentCreationBinding.inflate(inflater, container, false);


        rootView = inflater.inflate(R.layout.fragment_assignment_creation, container, false);
        classesHere = MainActivity.getClasses();
        classSpinner = rootView.findViewById(R.id.ClassSpinnerAssignCreation11111);


        classArray = new String[classesHere.size() + 1];
        classArray[0] = "Please select a class";
        for (int i = 0; i < classesHere.size(); i ++) {
            classArray[i + 1] = classesHere.get(i).getClassName();
        }
        ArrayAdapter<CharSequence> adapter;
        adapter = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_spinner_item, classArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);


        assignButton = rootView.findViewById(R.id.AssignmentRadioButton);
        examButtom = rootView.findViewById(R.id.ExamRadioButton);
        toDoRadio = rootView.findViewById(R.id.ToDoRadio);
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    return;
                }
                selectedClass = MainActivity.getClasses().get(position - 1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        rootView.findViewById(R.id.DatePickingButtonForAssignCreate).setOnClickListener( v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dueDate = LocalDate.of(year, month, dayOfMonth);
                }
            }, year, month, day);

            datePickerDialog.show();
        });

        rootView.findViewById(R.id.submitButtonForAssignCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean isToDO = false;
                Assignments added;
                String title = ((EditText) rootView.findViewById(R.id.titleInputForAssignCreate)).getText().toString();
                if (!examButtom.isChecked()) {
                    if (toDoRadio.isChecked()) {
                        isToDO = true;
                    }
                    added = new Assignments(selectedClass, title, dueDate,  dueTime, isToDO);
                } else {
                    String location = ((EditText) rootView.findViewById(R.id.LocationInputForAssignCreate)).getText().toString();
                    added = new Exam(selectedClass, title, dueDate,  dueTime, location);
                }
                MainActivity.addToAssignments(added);
                NavHostFragment.findNavController(AssignmentCreationFrag.this)
                        .navigate(R.id.action_assignmentCreationFrag_to_assignmentFrag);
            }
        });



        return rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.EditAssignmentButtonForAssignCreate).setOnClickListener(v -> {
            NavHostFragment.findNavController(AssignmentCreationFrag.this)
                    .navigate(R.id.action_assignmentCreationFrag_to_assignmentEditingFrag2);
        });

        view.findViewById(R.id.AssignmentRadioButton).setOnClickListener(v -> {
                assignButton.setChecked(true);
                examButtom.setChecked(false);
                toDoRadio.setChecked(false);
                view.findViewById(R.id.LocationInputLayout).setVisibility(View.INVISIBLE);
        });
        view.findViewById(R.id.ExamRadioButton).setOnClickListener(v -> {
            examButtom.setChecked(true);
            assignButton.setChecked(false);
            toDoRadio.setChecked(false);
            view.findViewById(R.id.LocationInputLayout).setVisibility(View.VISIBLE);
        });
        view.findViewById(R.id.ToDoRadio).setOnClickListener(v -> {
            toDoRadio.setChecked(true);
            assignButton.setChecked(false);
            examButtom.setChecked(false);
            view.findViewById(R.id.LocationInputLayout).setVisibility(View.INVISIBLE);
        });
        view.findViewById(R.id.HomeNavButtonForAssignCreation).setOnClickListener( v -> {
            NavHostFragment.findNavController(AssignmentCreationFrag.this)
                    .navigate(R.id.action_assignmentCreationFrag_to_FirstFragment);
        });
        view.findViewById(R.id.ExamNavButtonForAssignCreation).setOnClickListener( v -> {
            NavHostFragment.findNavController(AssignmentCreationFrag.this)
                    .navigate(R.id.action_assignmentCreationFrag_to_examFrag);
        });
        view.findViewById(R.id.TODONavButtonForAssignCreation).setOnClickListener( v -> {
            NavHostFragment.findNavController(AssignmentCreationFrag.this)
                    .navigate(R.id.action_assignmentCreationFrag_to_toDoFrag);
        });
        view.findViewById(R.id.AssignmentNavButtonForAssignCreation).setOnClickListener( v -> {
            NavHostFragment.findNavController(AssignmentCreationFrag.this)
                    .navigate(R.id.action_assignmentCreationFrag_self);
        });
        view.findViewById(R.id.DatePickingButtonForAssignCreate).setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dueDate = LocalDate.of(year, month + 1, dayOfMonth);
                    String text = dateFormater(dueDate);
                    message = new Toast(getContext());
                    message.setText(text);
                    message.setDuration(Toast.LENGTH_LONG);
                    message.show();
                }
            };
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            int style = AlertDialog.THEME_HOLO_LIGHT;
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), style, onDateSetListener, year, month, day);
            datePickerDialog.show();

        });
        ((Button) view.findViewById(R.id.TimePickerButtonForAssignmentsCreate)).setOnClickListener(v -> {
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    dueTime = LocalTime.of(hourOfDay, minute, 59);
                    startHour = hourOfDay;
                    startMinute = minute;
                    Toast message = new Toast(getContext());
                    if (startHour < 12) {
                        if (startHour == 0) {
                            startHour = 12;
                        }
                        startTime = (String.format("You selected %2d:%02d AM as time due", startHour, startMinute));
                        message.setText(startTime);
                        message.setDuration(Toast.LENGTH_LONG);
                    } else {
                        if (startHour == 12) {
                            startHour = 24;
                        }
                        startTime = (String.format("You selected %2d:%02d PM as time due", startHour - 12, startMinute));
                        message.setText(startTime);
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
    }

    private String dateFormater(LocalDate date) {
        String month = date.getMonth().toString();
        return month + " " + date.getDayOfMonth() + ", " + (date.getYear());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}