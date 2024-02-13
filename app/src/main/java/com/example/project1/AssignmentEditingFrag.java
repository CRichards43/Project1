package com.example.project1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project1.databinding.FragmentAssignmentEditingBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.net.CookieHandler;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;


public class AssignmentEditingFrag extends Fragment {

    private FragmentAssignmentEditingBinding binding;
    private int dueHour,dueMinute;
    private MainActivity mainActive;
    private Spinner EditSpinner, DeleteSpinner;
    private ArrayList<Assignments> assignmentsHere;

    private static ArrayList<Exam> exams = new ArrayList<>();
    private LocalTime dueTime;
    private LocalDate dueDay;
    private String[] assignmentArray;
    private Assignments selectedAssignment;
    private Assignments selectedDeletedAssignment;
    private Toast message;
    private View rootView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        mainActive = new MainActivity();
        rootView = inflater.inflate(R.layout.fragment_assignment_editing, container, false);
        TextInputLayout locationText = rootView.findViewById(R.id.LocationInputLayout);


        assignmentsHere = MainActivity.getAssignments();

        EditSpinner = (Spinner) rootView.findViewById(R.id.EditSpinnerForAssignEdit);

        DeleteSpinner = (Spinner) rootView.findViewById(R.id.DeleteSpinnerForAssignEdit);


        ArrayList<Class> classesHere = MainActivity.getClasses();
        assignmentArray = new String[assignmentsHere.size() + 1];
        assignmentArray[0] = "Please Choose an Assignment!";
        for (int i = 0; i < assignmentsHere.size(); i ++) {
            assignmentArray[i + 1] = assignmentsHere.get(i).getTitle() + " - " + assignmentsHere.get(i).getDueClass().getClassName();;
        }
        ArrayAdapter<CharSequence> adapter;
        adapter = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_spinner_item, assignmentArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditSpinner.setAdapter(adapter);
        DeleteSpinner.setAdapter(adapter);
        /*setUpSpinners(view);*/

        EditSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    selectedAssignment = MainActivity.getAssignments().get(position - 1);
                    if(selectedAssignment.isExam()) {
                        locationText.setVisibility(View.VISIBLE);
                    } else {
                        locationText.setVisibility(View.INVISIBLE);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Button submitButton = (Button) rootView.findViewById(R.id.submitButtonForAssignEdit);
        submitButton.setOnClickListener(v -> {
            String assignNameAdded = ((TextInputEditText) rootView.findViewById(R.id.titleInputForAssignEdit)).getText().toString().trim();
            if(!(assignNameAdded == null || assignNameAdded.equals(""))) {
                selectedAssignment.setTitle(assignNameAdded);
            }
            if(dueTime != null) {
                selectedAssignment.setDueTime(dueTime);
            }

            String locNameAdded = ((TextInputEditText) rootView.findViewById(R.id.LocationInputForAssignEdit)).getText().toString().trim();
            if (selectedAssignment.isExam() &&(! (locNameAdded == null || locNameAdded.equals("")))) {
                ((Exam)selectedAssignment).setLocation(locNameAdded);
            }

            NavHostFragment.findNavController(AssignmentEditingFrag.this)
                    .navigate(R.id.action_assignmentEditingFrag2_to_assignmentFrag);
        });

        DeleteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    return;
                }
                selectedDeletedAssignment = assignmentsHere.get(position - 1);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirm Deletion");
                builder.setMessage("Do you want to delete assignment: " + selectedDeletedAssignment.getTitle() + "?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the user input from the EditText

                        MainActivity.removeFromAssignment(position - 1);
                        NavHostFragment.findNavController(AssignmentEditingFrag.this)
                                .navigate(R.id.action_assignmentEditingFrag2_to_assignmentFrag);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the user input from the EditText
                    }
                });
                builder.show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        binding = FragmentAssignmentEditingBinding.inflate(inflater, container, false);

        rootView.findViewById(R.id.HomeButtonForAssignEditing).setOnClickListener(v -> {
            NavHostFragment.findNavController(AssignmentEditingFrag.this)
                    .navigate(R.id.action_assignmentEditingFrag2_to_FirstFragment);
        });
        rootView.findViewById(R.id.CalendarButtonForAssignEditing).setOnClickListener(v -> {
            NavHostFragment.findNavController(AssignmentEditingFrag.this)
                    .navigate(R.id.action_assignmentEditingFrag2_to_examFrag);
        });
        rootView.findViewById(R.id.TODOButtonForAssignEditing).setOnClickListener(v -> {
            NavHostFragment.findNavController(AssignmentEditingFrag.this)
                    .navigate(R.id.action_assignmentEditingFrag2_to_toDoFrag);
        });
        rootView.findViewById(R.id.AssignmentTabForAssignEditing).setOnClickListener(v -> {
            NavHostFragment.findNavController(AssignmentEditingFrag.this)
                    .navigate(R.id.action_assignmentEditingFrag2_to_assignmentCreationFrag);
        });
        return rootView;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.DatePickingButtonForAssignEdit).setOnClickListener( v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dueDay = LocalDate.of(year, month, dayOfMonth);
                    String text = "The due date is " + dateFormater(dueDay) + "?";
                    message = new Toast(getContext());
                    message.setText(text);
                    message.setDuration(Toast.LENGTH_LONG);
                    message.show();
                }
            }, year, month, day);

            datePickerDialog.show();
        });

        view.findViewById(R.id.TimePickerButtonForAssignmentsEdit).setOnClickListener( v -> {
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    dueTime = LocalTime.of(hourOfDay, minute, 59);
                    dueHour = hourOfDay;
                    dueMinute = minute;
                    message = new Toast(view.getContext());
                    if (dueHour < 12) {
                        if (dueHour == 0) {
                            dueHour = 12;
                        }
                        message.setText((String.format("You selected %2d:%02d AM as your starting time", dueHour, dueMinute)));
                        message.setDuration(Toast.LENGTH_LONG);
                    } else {
                        if (dueHour == 12) {
                            dueHour = 24;
                        }
                        message.setText((String.format("You selected %2d:%02d PM as your starting time", dueHour - 12, dueMinute)));
                        message.setDuration(Toast.LENGTH_LONG);
                    }
                    message.show();
                }
            };
            int style = TimePickerDialog.THEME_HOLO_DARK;
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), style, onTimeSetListener, dueHour, dueMinute, false);
            timePickerDialog.setTitle("Start Time Selector");
            timePickerDialog.show();
        });

    }

    private String dateFormater(LocalDate date) {
        String day;
        if (date.getDayOfMonth() % 10 == 1) {
            day = String.format("%dst", date.getDayOfMonth());
        } else if (date.getDayOfMonth() % 10 == 2) {
            day = String.format("%dnd", date.getDayOfMonth());
        } else if (date.getDayOfMonth() % 10 == 3) {
            day = String.format("%drd", date.getDayOfMonth());
        } else {
            day = String.format("%drd", date.getDayOfMonth());
        }
        String month = date.getMonth().toString();
        return month + " " + day + ", " + (date.getYear());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}