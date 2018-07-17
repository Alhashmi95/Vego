package com.vego.vego;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class BottomNav extends AppCompatActivity {

    BottomNavigationView bTV = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        bTV = findViewById(R.id.bNavigation);
        bTV.setOnNavigationItemSelectedListener(navListener);
        //+++++++++++++++defult Fragment ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        getSupportFragmentManager().beginTransaction().replace(R.id.fContr , new FragmentHome()).commit()  ;


    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment f = null;
            switch (menuItem.getItemId()){
                case R.id.wallet:
                    f = new FragmentWallet();
                    break;
                case R.id.setting:
                    f = new FragmentSetting();
                    break;
                case R.id.home:
                    f = new FragmentHome();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fContr , f).commit()  ;
            return true;}
    };
}
