package com.vego.vego.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vego.vego.Adapters.ExerciseAdapter;
import com.vego.vego.R;
import com.vego.vego.model.exercise;

import java.util.ArrayList;
import java.util.List;

public class ActivityWorkoutUser extends AppCompatActivity {
    private ExerciseAdapter adapter;
    RecyclerView recyclerView;
    TextView dayTextView, noFreeEx;
    String mucsleName;

    ArrayList<exercise> muExList = new ArrayList<>();

    ArrayList<exercise> exList = new ArrayList<>();
    List<exercise> exListForAll = new ArrayList<>();


    String month, week, day, dayIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_user);

        noFreeEx = findViewById(R.id.tv_noFreeEx);
        noFreeEx.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.exRecycler);
        dayTextView = findViewById(R.id.daytv2);
        getValuesfromAdapter();


    }

    private void getValuesfromAdapter() {
        Intent intent = this.getIntent();
        //here we receive array of objects from daysAdapter
        //because daysAdapter has an object of DietDay which contains DayMeals array of objects

        exList = (ArrayList<exercise>) intent.getSerializableExtra("DayExercise");
        exListForAll = (List<exercise>) intent.getSerializableExtra("DayExercise2");

        //get day number from الكلاس المعضل (daysAdapter)
        day = intent.getStringExtra("day");
        dayTextView.setText(" يوم " + day);

        mucsleName = intent.getStringExtra("mucsleName");

        month = intent.getStringExtra("month");
        week = intent.getStringExtra("week");
        dayIndex = intent.getStringExtra("dayIndex");

        displayExOrExforAll();

    }

    private void displayExOrExforAll() {
        if (exListForAll == null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ExerciseAdapter(exList, this, month, week, day, dayIndex);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        if (exListForAll != null) {
            dayTextView.setText(" التمارين ");

            for (int i = 0; i < exListForAll.size(); i++) {
                if (mucsleName.equals(exListForAll.get(i).getTargetedmuscle())) {

                    exercise e = exListForAll.get(i);

                    //save the name of selected mu from 3 lines adapter
                    String selectedMu = exListForAll.get(i).getTargetedmuscle();

                    muExList.add(e);


                }
            }
        }
        //if we clicked on ExforAll mucsles exList will be empty
        if (exList == null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ExerciseAdapter(muExList, this, month, week, day, dayIndex);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            if (muExList.size() == 0) {
                noFreeEx.setVisibility(View.VISIBLE);
            }
        }
    }
}
