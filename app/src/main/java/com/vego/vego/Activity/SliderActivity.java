package com.vego.vego.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vego.vego.Adapters.SliderAdapter;
import com.vego.vego.R;

public class SliderActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout linearLayout;

    private SliderAdapter sliderAdapter;

    private TextView[] dots;

    private Button btnNext, btnPrev,btnFinish;

    int currentPage;

    String isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        Intent intent = getIntent();
        isAdmin = intent.getStringExtra("isAdmin");

//        //check if first run
//        SharedPreferences settings=getSharedPreferences("prefs",0);
//        boolean firstRun=settings.getBoolean("firstRun",false);
//        if(firstRun==false)//if running for first time
//        //Splash will load for first time
//        {
//            SharedPreferences.Editor editor=settings.edit();
//            editor.putBoolean("firstRun",true);
//            editor.apply();
////            Intent i=new Intent(SliderActivity.this,Splash.class);
////            startActivity(i);
////            finish();
//        }
//        else
//        {
//            if (isAdmin.equals("false")) {
//                Intent i = new Intent(SliderActivity.this,BottomNav.class);
//                startActivity(i);
//                finish();
//            }else if (isAdmin.equals("true")) {
//                Intent i = new Intent(SliderActivity.this,AdminActivity.class);
//                startActivity(i);
//                finish();
//            }
//        }

        viewPager = findViewById(R.id.sliderViewPager);
        linearLayout = findViewById(R.id.dots);

        sliderAdapter = new SliderAdapter(this,isAdmin);
        viewPager.setAdapter(sliderAdapter);

        btnNext = findViewById(R.id.btn_next);
        btnPrev = findViewById(R.id.btn_prev);
        btnFinish = findViewById(R.id.btn_finish);

        btnPrev.setVisibility(View.INVISIBLE);
        btnFinish.setVisibility(View.INVISIBLE);

        showDots(0);

        viewPager.addOnPageChangeListener(viewListner);

        clickListners();


    }

    private void clickListners() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPage + 1);
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPage - 1);
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdmin.equals("false")) {
                    Intent i = new Intent(SliderActivity.this,BottomNav.class);
                    startActivity(i);
                    finish();
                }else if (isAdmin.equals("true")) {
                    Intent i = new Intent(SliderActivity.this,AdminActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    public void showDots(int postion) {
        dots = new TextView[5];
        linearLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.blackGrey));

            linearLayout.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[postion].setTextColor(getResources().getColor(R.color.md_white_1000));
        }
    }

    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            showDots(position);

            currentPage = position;

            if (position == 0) {
                btnNext.setEnabled(true);
                btnPrev.setEnabled(false);
                btnPrev.setVisibility(View.INVISIBLE);

                btnNext.setText("Next");
                btnPrev.setText("");
            } else if (position == dots.length - 1) {
                btnNext.setEnabled(false);
                btnPrev.setEnabled(true);
                btnPrev.setVisibility(View.VISIBLE);

                //btnNext.setText("Finish");
                btnNext.setVisibility(View.INVISIBLE);
                btnFinish.setVisibility(View.VISIBLE);
                btnPrev.setText("Back");
            } else {
                btnNext.setEnabled(true);
                btnPrev.setEnabled(true);
                btnPrev.setVisibility(View.VISIBLE);

                btnNext.setText("Next");
                btnPrev.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
