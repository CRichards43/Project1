package com.example.project1;

import android.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project1.databinding.FragmentClassEditingBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;



public class ClassEditingFrag extends Fragment {

    private FragmentClassEditingBinding ClassEditingBinding;
    private int startHour, startMinute, endHour, endMinute;

    private LocalTime startTime, endTime;
    private Class selectedClass, selectedDeletedClass;
    private View rootView;
    private MainActivity mainActive;
    private Spinner EditSpinner, DeleteSpinner;

    private String[] classArray;
    private ArrayAdapter<CharSequence> adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActive = new MainActivity();
        View view = inflater.inflate(R.layout.fragment_class_editing, container, false);


        EditSpinner = (Spinner) view.findViewById(R.id.EditSpinner);

        DeleteSpinner = (Spinner) view.findViewById(R.id.DeleteSpinner);

        classArray = new String[MainActivity.getClasses().size() + 1];
        classArray[0] = "Please Select a Class!";
        for (int i = 0; i < MainActivity.getClasses().size(); i ++) {
            classArray[i + 1] = MainActivity.getClasses().get(i).getClassName();
        }
        adapter = new ArrayAdapter<CharSequence>(requireContext(), android.R.layout.simple_list_item_1, classArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditSpinner.setAdapter(adapter);
        DeleteSpinner.setAdapter(adapter);

        EditSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    selectedClass = MainActivity.getClasses().get(position - 1);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button submitButton = (Button) view.findViewById(R.id.submitButtonForEditClass);
        submitButton.setOnClickListener(v -> {
            if(selectedClass == null) {
                return;
            }
            String classNameAdded = ((TextInputEditText) view.findViewById(R.id.ClassNameInput)).getText().toString().trim();
            if(!(classNameAdded == null || classNameAdded.equals(""))) {
                selectedClass.setClassName(classNameAdded);
            }
            String profNameAdded = ((TextInputEditText) view.findViewById(R.id.ProfessorInput)).getText().toString().trim();
            if(!(profNameAdded == null || (profNameAdded.trim().equals("")))) {
                selectedClass.setProfName(profNameAdded);
            }
            if(startTime != null) {
                selectedClass.setStartTime(startTime);
            }
            if(endTime != null) {
                selectedClass.setEndTime(endTime);
            }
            String locNameAdded = ((TextInputEditText) view.findViewById(R.id.LocationInputForClassEdit)).getText().toString().trim();
            if(!(locNameAdded == null || locNameAdded.equals(""))) {
                selectedClass.setLocationfName(locNameAdded);
            }



            ArrayList<DayOfWeek> addedDays = new ArrayList<>();

            CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBoxEdit);
            CheckBox checkBox2 = (CheckBox)view.findViewById(R.id.checkBoxEdit2);
            CheckBox checkBox3 = (CheckBox)view.findViewById(R.id.checkBoxEdit3);
            CheckBox checkBox4 = (CheckBox)view.findViewById(R.id.checkBoxEdit4);
            CheckBox checkBox5 = (CheckBox)view.findViewById(R.id.checkBoxEdit5);

            if (checkBox.isChecked()) {
                addedDays.add(DayOfWeek.MONDAY);
            }
            if (checkBox2.isChecked()) {
                addedDays.add(DayOfWeek.TUESDAY);
            }
            if (checkBox3.isChecked()) {
                addedDays.add(DayOfWeek.WEDNESDAY);
            }
            if (checkBox4.isChecked()) {
                addedDays.add(DayOfWeek.THURSDAY);
            }
            if (checkBox5.isChecked()) {
                addedDays.add(DayOfWeek.FRIDAY);
            }
            if (addedDays.size() != 0) {
                selectedClass.setDays(addedDays);
            }

            NavHostFragment.findNavController(ClassEditingFrag.this)
                    .navigate(R.id.action_classEditingFrag_to_FirstFragment);
        });


        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        DeleteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                selectedDeletedClass = MainActivity.getClasses().get(position - 1);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirm Deletion");
                builder.setMessage("Do you want to delete class: " + selectedDeletedClass.getClassName() + "?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the user input from the EditText
                        MainActivity.removeFromClass(position - 1);
                        /*classArray = new String[MainActivity.classes.size() + 1];
                        classArray[0] = classArray[0] = "Please Select a Class!";
                        for (int i = 0; i < FirstFragment.classes.size(); i++) {
                            classArray[i + 1] = FirstFragment.classes.get(i).toString();
                        }*/
                        adapter.notifyDataSetChanged();
                        FirstFragment.updateScreen();
                        NavHostFragment.findNavController(ClassEditingFrag.this)
                                .navigate(R.id.action_classEditingFrag_to_FirstFragment);

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

        view.findViewById(R.id.AddClassButton).setOnClickListener( v -> {
            NavHostFragment.findNavController(ClassEditingFrag.this)
                    .navigate(R.id.action_classEditingFrag_to_classCreationFrag);
        });

        view.findViewById(R.id.StartTimeBtn11111).setOnClickListener(v -> {
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    startHour = hourOfDay;
                    startMinute = minute;
                    startTime = LocalTime.of(startHour, startMinute);
                    Toast message = new Toast(view.getContext());
                    if (startHour < 12) {
                        if (startHour == 0) {
                            startHour = 12;
                        }
                        message.setText((String.format("You selected %2d:%02d AM as your starting time", startHour, startMinute)));
                        message.setDuration(Toast.LENGTH_LONG);
                    } else {
                        if (startHour == 12) {
                            startHour = 24;
                        }
                        message.setText((String.format("You selected %2d:%02d PM as your starting time", startHour - 12, startMinute)));
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
                    Toast message = new Toast(view.getContext());
                    if (endHour < 12) {
                        if (endHour == 0) {
                            endHour = 12;
                        }
                        message.setText((String.format("You selected %2d:%02d AM as your ending time", endHour, endMinute)));
                        message.setDuration(Toast.LENGTH_LONG);
                    } else {
                        if (endHour == 12) {
                            endHour = 24;
                        }
                        message.setText((String.format("You selected %2d:%02d PM as your ending time", endHour - 12, endMinute)));
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

        view.findViewById(R.id.HomeButton).setOnClickListener( v -> {
            NavHostFragment.findNavController(ClassEditingFrag.this)
                    .navigate(R.id.action_classEditingFrag_to_FirstFragment);
        });

        view.findViewById(R.id.TODOButton).setOnClickListener( v -> {
            NavHostFragment.findNavController(ClassEditingFrag.this)
                    .navigate(R.id.action_classEditingFrag_to_toDoFrag);
        });

        view.findViewById(R.id.CalendarButton).setOnClickListener( v -> {
            NavHostFragment.findNavController(ClassEditingFrag.this)
                    .navigate(R.id.action_classEditingFrag_to_examFrag);
        });

        view.findViewById(R.id.button4).setOnClickListener( v -> {
            NavHostFragment.findNavController(ClassEditingFrag.this)
                    .navigate(R.id.action_classEditingFrag_to_assignmentCreationFrag);
        });
    }

    public View getRootView() {
        return rootView;
    }

    private void setUpSpinners(View view) {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ClassEditingBinding = null;
    }

}