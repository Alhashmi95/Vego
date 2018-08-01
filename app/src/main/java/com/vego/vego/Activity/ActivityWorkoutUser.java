package com.vego.vego.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.vego.vego.Adapters.ExerciseAdapter;
import com.vego.vego.Adapters.MealsAdapter;
import com.vego.vego.R;
import com.vego.vego.model.exercise;
import com.vego.vego.model.meal;

import java.util.List;

public class ActivityWorkoutUser extends AppCompatActivity {
    private ExerciseAdapter adapter;
    RecyclerView recyclerView;
    TextView dayTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_user);

        recyclerView = findViewById(R.id.exRecycler);
        dayTextView = findViewById(R.id.daytv2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent=this.getIntent();
        //here we receive array of objects from daysAdapter
        //because daysAdapter has an object of DietDay which contains DayMeals array of objects
        List<exercise> exList= (List<exercise>) intent.getSerializableExtra("DayExercise");
        //get day number from الكلاس المعضل (daysAdapter)
        String day = intent.getStringExtra("day");
        dayTextView.setText(" يوم "+day);
        adapter = new ExerciseAdapter(exList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
