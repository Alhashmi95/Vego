package com.vego.vego.Activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import com.vego.vego.Adapters.ViewPagerAdapter;
import com.vego.vego.Fragment.FragmentMealIngr;
import com.vego.vego.Fragment.FragmentMealsDetails;
import com.vego.vego.R;

public class MealIngrActivity extends AppCompatActivity {

    AppBarLayout appBarLayout;
    TabLayout tableLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_ingr);

        //connect views to layout
        appBarLayout = findViewById(R.id.appbarid);
        tableLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        //add Fragments
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentMealsDetails(),"Meal Details");
        adapter.addFragment(new FragmentMealIngr(), "Meal Ingredients");

        //set adapter
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
    }
}
