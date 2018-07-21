package com.vego.vego;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vego.vego.model.DayMeals;

import java.util.List;

public class DayMealsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private MealsAdapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_meals);
        recyclerView = findViewById(R.id.mealrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent=this.getIntent();
        List<DayMeals>mealsList= (List<DayMeals>) intent.getSerializableExtra("DayMeals");
        adapter = new MealsAdapter(mealsList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        tabLayout = findViewById(R.id.tablayout_id);
        appBarLayout = findViewById(R.id.appbarid);

        //Adapter setup
    }
}
