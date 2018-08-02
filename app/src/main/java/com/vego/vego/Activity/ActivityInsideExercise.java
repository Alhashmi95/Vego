package com.vego.vego.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.vego.vego.Adapters.ExerciseAdapter;
import com.vego.vego.Adapters.ExerciseDetailsAdapter;
import com.vego.vego.R;
import com.vego.vego.model.exercise;
import com.vego.vego.model.sets;

import java.util.List;

public class ActivityInsideExercise extends AppCompatActivity {
    private ExerciseDetailsAdapter adapter;
    RecyclerView recyclerView;
    TextView exNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_exercise);

        recyclerView = findViewById(R.id.recyclerInsideWorkout);
        exNumberTextView = findViewById(R.id.exNumbertextView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent=this.getIntent();
        //here we receive array of objects from daysAdapter
        //because daysAdapter has an object of DietDay which contains DayMeals array of objects
        List<sets> setsList= (List<sets>) intent.getSerializableExtra("exSets");
        //get day number from الكلاس المعضل (daysAdapter)
        String exNumber = intent.getStringExtra("exNumber");
        int exNo = Integer.parseInt(exNumber)+1;

        exNumberTextView.setText(" التمرين "+String.valueOf(exNo));
        adapter = new ExerciseDetailsAdapter(setsList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}