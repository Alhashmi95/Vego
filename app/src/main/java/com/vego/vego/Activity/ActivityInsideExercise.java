package com.vego.vego.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.vego.vego.Adapters.ExerciseAdapter;
import com.vego.vego.Adapters.ExerciseDetailsAdapter;
import com.vego.vego.R;
import com.vego.vego.model.exercise;
import com.vego.vego.model.sets;

import java.util.ArrayList;
import java.util.List;

public class ActivityInsideExercise extends AppCompatActivity {
    private ExerciseDetailsAdapter adapter;
    RecyclerView recyclerView;
    TextView exNumberTextView, exNameTextView, tvTotalVolume;
    ImageView imageViewEx;
    double totalVolume, sumVolume = 0;
    Button calVAndR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_exercise);

        recyclerView = findViewById(R.id.recyclerInsideWorkout);
        exNumberTextView = findViewById(R.id.exNumbertextView);
        exNameTextView = findViewById(R.id.textViewExNameUser);
        imageViewEx = findViewById(R.id.imageViewEximage);
        tvTotalVolume = findViewById(R.id.tvTotalVolume);
        calVAndR = findViewById(R.id.btnCalVolumeAndRM1);

        calVAndR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // calculateVolumeAndRM1();
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = this.getIntent();
        //here we receive array of objects from daysAdapter
        //because daysAdapter has an object of DietDay which contains DayMeals array of objects
        ArrayList<sets> setsList = (ArrayList<sets>) intent.getSerializableExtra("exSets");
        //get day number from الكلاس المعضل (daysAdapter)
        String exNumber = intent.getStringExtra("exNumber");
        String exName = intent.getStringExtra("exName");
        String exImage = intent.getStringExtra("exImage");
        Log.d("test", "this is image   " + exImage);
        int exNo = Integer.parseInt(exNumber) + 1;

        exNumberTextView.setText(" التمرين " + String.valueOf(exNo));
        exNameTextView.setText(exName);

//        Picasso.get()
//                .load(exImage)
//                .placeholder(R.drawable.ic_loading)
//                .fit()
//                .centerCrop()
//                .into(imageViewEx);

        Ion.with(this)
                .load(exImage)
                .withBitmap()
                .placeholder(R.drawable.spinner_drawable)
                .intoImageView(imageViewEx);


        adapter = new ExerciseDetailsAdapter(setsList, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }

    public void calculateVolumeAndRM1() {

                if (adapter.getSetsArray() != null) {
                        for (int i = 0; i < adapter.getSetsArray().size(); i++) {
                            if(!adapter.getSetsArray().get(i).getVolume().isEmpty()) {
                                Log.d("test","VVVOLLLUME:  "+adapter.getSetsArray().get(i).getVolume());
                                Log.d("test","SUM V:  "+sumVolume);
                                totalVolume = Double.valueOf(adapter.getSetsArray().get(i).getVolume());
                                Log.d("test","TOTal V:  "+totalVolume);
                                sumVolume = sumVolume + totalVolume;
                        }
                        tvTotalVolume.setText(String.valueOf(sumVolume));
                    }
                }
    }
}
