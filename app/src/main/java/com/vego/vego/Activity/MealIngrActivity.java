package com.vego.vego.Activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vego.vego.Adapters.ViewPagerAdapter;
import com.vego.vego.Fragment.FragmentMealIngr;
import com.vego.vego.Fragment.FragmentMealsDetails;
import com.vego.vego.R;

public class MealIngrActivity extends AppCompatActivity implements FragmentMealIngr.passMealImage {

    AppBarLayout appBarLayout;
    TabLayout tableLayout;
    ViewPager viewPager;
    ImageView imageViewMeal;
    String imageURL, mealNoTest;
    TextView textViewMealNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_ingr);

        //connect views to layout
        appBarLayout = findViewById(R.id.appbarid);
        tableLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        imageViewMeal = findViewById(R.id.mealimg);
        textViewMealNo = findViewById(R.id.mealNo);

        Intent intent = getIntent();

        String mealNo =intent.getStringExtra("position");

        int mealN = Integer.parseInt(mealNo)+1;
//
        textViewMealNo.setText(" وجبة "+String.valueOf(mealN));




        //add Fragments
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentMealIngr(),"Meal Ingredients");
        adapter.addFragment(new FragmentMealsDetails(), "Meal Elements");

        //set adapter
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void passMealImage(String mealImage) {
        imageURL = mealImage;
      //  mealNoTest = mealNo;

        Picasso.get()
                .load(imageURL)
                .placeholder(R.drawable.ic_loading)
                .fit()
                .centerCrop()
                .into(imageViewMeal);
//

    }
}
