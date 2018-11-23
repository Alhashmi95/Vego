package com.vego.vego.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vego.vego.Adapters.ViewPagerAdapter;
import com.vego.vego.Fragment.CopyExerciseFragment;
import com.vego.vego.Fragment.CopyMealFragment;
import com.vego.vego.Fragment.FragmentMealIngr;
import com.vego.vego.Fragment.FragmentMealsDetails;
import com.vego.vego.R;

public class CopyExMeActivity extends AppCompatActivity {

    TabLayout tableLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_ex_me);

        tableLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);


        //add Fragments
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CopyMealFragment(),"نسخ الجدول الغذائي");
        adapter.addFragment(new CopyExerciseFragment(), "نسخ جدول التمارين");

        //set adapter
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);

    }
}
