package com.vego.vego.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.vego.vego.Fragment.AddMealsFragment;
import com.vego.vego.Fragment.Add_workout2user;
import com.vego.vego.Fragment.FragmentAddWorkout;
import com.vego.vego.Fragment.FragmentSupport;
import com.vego.vego.Fragment.TrackProgressFragmentAdmin;
import com.vego.vego.R;

public class AdminActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    BottomNavigationView navAdmin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);



//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.custom_bar);

        navAdmin = findViewById(R.id.bNavAdmin);
        navAdmin.setOnNavigationItemSelectedListener(navListener);

        //+++++++++++++++defult Fragment ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        getSupportFragmentManager().beginTransaction().replace(R.id.fCenterAdmin , new AddMealsFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment f = null;
            switch (menuItem.getItemId()){
                case R.id.workouts:
                    //delete fragmentAddWorkout
                    f = new Add_workout2user();
                    break;
                case R.id.diet:
                    f = new AddMealsFragment();
                    break;
                case R.id.support:
                    f = new FragmentSupport();
                    break;
                case R.id.trackProgress:
                    f = new TrackProgressFragmentAdmin();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fCenterAdmin , f).commit()  ;
            return true;}
    };

    public void openDrawer(View view) {
    }
}
