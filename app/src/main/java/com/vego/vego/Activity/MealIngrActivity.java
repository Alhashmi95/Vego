package com.vego.vego.Activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vego.vego.Adapters.ViewPagerAdapter;
import com.vego.vego.Fragment.FragmentMealIngr;
import com.vego.vego.Fragment.FragmentMealsDetails;
import com.vego.vego.R;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.meal;

import java.util.ArrayList;

public class MealIngrActivity extends AppCompatActivity implements FragmentMealIngr.passMealImage {

    AppBarLayout appBarLayout;
    TabLayout tableLayout;
    ViewPager viewPager;
    ImageView imageViewMeal;
    String imageURL, mealNoTest;
    TextView textViewMealNo;
    Button btnNext ;
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
        btnNext=findViewById(R.id.btnNext);
        Intent intent = getIntent();

        final String mealNo =intent.getStringExtra("position");
        final ArrayList<meal> mealsList = (ArrayList<meal>) intent.getSerializableExtra("meals list");
        final int mealN = Integer.parseInt(mealNo)+1;
//

        textViewMealNo.setText(" وجبة "+String.valueOf(mealN));




        //add Fragments
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentMealIngr(),"مكونات الوجبة");
        adapter.addFragment(new FragmentMealsDetails(), "القيم الغذائية");

        //set adapter
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mealsList.size()>mealN){
                Intent intent= new Intent(getBaseContext(), MealIngrActivity.class);
                intent.putExtra("DayMeals",mealsList.get(mealN).getingredients());
                intent.putExtra("DayMealsElements",mealsList.get(mealN).getElements());
                intent.putExtra("name",mealsList.get(mealN).getName());
                intent.putExtra("position",String.valueOf(mealN));
                intent.putExtra("image",mealsList.get(mealN).getImage());
                intent.putExtra("meals list",mealsList);
                v.getContext().startActivity(intent);
                finish();}
                else {
                    Toast.makeText(getBaseContext(),"This is your last meal",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
