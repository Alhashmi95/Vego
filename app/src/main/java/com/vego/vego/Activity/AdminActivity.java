package com.vego.vego.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.vego.vego.Fragment.FragmentHome;
import com.vego.vego.Fragment.FragmentSetting;
import com.vego.vego.Fragment.FragmentWallet;
import com.vego.vego.R;

public class AdminActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    BottomNavigationView navAdmin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_bar);

        navAdmin = findViewById(R.id.bNavigation);
        navAdmin.setOnNavigationItemSelectedListener(navListener);
        //+++++++++++++++defult Fragment ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        getSupportFragmentManager().beginTransaction().replace(R.id.fContr , new FragmentHome()).commit();

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

    public void openDrawer(View view) {
    }
}
