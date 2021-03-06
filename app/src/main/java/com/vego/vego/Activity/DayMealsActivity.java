package com.vego.vego.Activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.vego.vego.Adapters.MealsAdapter;
import com.vego.vego.R;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.meal;

import java.util.ArrayList;
import java.util.List;

public class DayMealsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private MealsAdapter adapter;
    RecyclerView recyclerView;
    TextView dayTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_meals);
        recyclerView = findViewById(R.id.mealrecycler);
        dayTextView = findViewById(R.id.daytv);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent=this.getIntent();
        //here we receive array of objects from daysAdapter
        //because daysAdapter has an object of DietDay which contains DayMeals array of objects
        ArrayList<meal>mealsList= (ArrayList<meal>) intent.getSerializableExtra("DayMeals");
        //get day number from الكلاس المعضل (daysAdapter)
        String day = intent.getStringExtra("day");
        dayTextView.setText(" يوم "+day);
        adapter = new MealsAdapter(mealsList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        tabLayout = findViewById(R.id.tablayout_id);
        appBarLayout = findViewById(R.id.appbarid);

        //Adapter setup
    }
}
