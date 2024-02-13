package com.example.project1;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project1.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputEditText;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    protected static Bundle instance;
    private Class selectedClass;
    private String startTime,endTime;
    protected static boolean firstFragCreated = true;
    protected static ArrayList<Class> classes = new ArrayList<>();
    protected static ArrayList<Assignments> assignments = new ArrayList<>();
    public static void removeFromAssignment(int i) {
        assignments.remove(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = savedInstanceState;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(MainActivity.firstFragCreated) {
            ArrayList<DayOfWeek> days1 = new ArrayList<DayOfWeek>();
            days1.add(DayOfWeek.THURSDAY);
            days1.add(DayOfWeek.TUESDAY);

            MainActivity.addToClasses(new Class("CS2340 Section D", "Pedro Guillermo Feijóo-García", days1, LocalTime.of(14, 0), LocalTime.of(15, 15), "Instructional Ceneter 103"));
            MainActivity.addToClasses(new Class("MATH2551 Section L", "Ho Law", days1, LocalTime.of(15, 30), LocalTime.of(16, 45), "Weber SST III 2"));
            MainActivity.addToClasses(new Class("LING 3100 Section A", "Hyoun-A Joo", days1, LocalTime.of(18, 30), LocalTime.of(19, 45), "Swann 106"));


            Exam tempExams = new Exam(classes.get(1), "Midterm 1", LocalDate.of(2024, Month.FEBRUARY, 6), LocalTime.of(15, 30, 00), "Weber III 2");

            MainActivity.addToAssignments(new Assignments(classes.get(0), "Finish Backend Code", LocalDate.of(2024, Month.FEBRUARY, 2), LocalTime.of(23, 59, 59), true));
            MainActivity.addToAssignments(new Assignments(classes.get(0), "Project 1", LocalDate.of(2024, Month.FEBRUARY, 6), LocalTime.of(23, 59, 00), false));
            MainActivity.addToAssignments(tempExams);
            MainActivity.firstFragCreated = false;
        }




        setSupportActionBar(binding.toolbar);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

       /* binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public static void removeView(Class selectedClass) {
        FirstFragment fragment = new FirstFragment();
        FirstFragment.linearLayout.removeView(fragment.createClassTemplate(selectedClass));
        FirstFragment.updateScreen();
    }

    public static ArrayList<Assignments> getAssignments() {
        return assignments;
    }

    public static ArrayList<Class> getClasses() {
        return classes;
    }


    public static void addToAssignments(Assignments added) {
        int i = 0;
        if (assignments.size() == 0) {
            assignments.add(added);
            return;
        }
        while((i != assignments.size()) && (assignments.get(i).getDate().isBefore(added.getDate()))) {
            i++;
        }
        if (i == assignments.size()) {
            assignments.add(i, added);
            return;
        }
        if(assignments.get(i).getDate().isEqual(added.getDate())) {
            while((i != assignments.size()) && (assignments.get(i).getTime().isBefore(added.getTime()))) {
                i++;
            }
        }
        assignments.add(i, added);
    }

    public static void addToClasses(Class addedClass) {
        classes.add(addedClass);
    }

    public static void removeFromClass(int position) {
        classes.remove(position);
        Class removedClass = classes.get(position);
        for(int i = 0; i < assignments.size(); i++) {
            if(removedClass.equals(assignments.get(i).getDueClass())) {
                assignments.remove(position);
            }
        }
    }
}