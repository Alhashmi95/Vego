package com.vego.vego;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserDetails extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    // define spinner
    Spinner spActivity ;
    Spinner spGoal ;

    private EditText userNameTxt, userWeight, userAge, userHeight;
    private String name, age,weight, height, userActivity, userGoal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        setupUIViews();



//
        // Get reference of widgets from XML layout
        final Spinner spActivity = (Spinner) findViewById(R.id.spinner2);
        final Spinner spGoal = (Spinner) findViewById(R.id.spinner3);


        // Initializing a String Array
        String[] activity = new String[]{
                "مستوى النشاط",
                "مرتفع",
                "متوسط",
                "منخفض",
                "خامل"
        };
        String[] goal = new String[]{
                "هدفك من التطبيق",
                "زيادة الوزن",
                "انقاص الوزن",
                "زيادة عضلات",
                "المحافظة على الوزن الحالي"
        };

        //For first spinner +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        final List<String> plantsList = new ArrayList<>(Arrays.asList(activity));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spActivity.setAdapter(spinnerArrayAdapter);

        spActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                userActivity = selectedItemText;
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //For second spinner +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        final List<String> goalList = new ArrayList<>(Arrays.asList(goal));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this,R.layout.spinner_item,goalList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_item);
        spGoal.setAdapter(spinnerArrayAdapter2);

        spGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                userGoal= selectedItemText;
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //End spinner




        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, HomeActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void setupUIViews() {
        userNameTxt = (EditText) findViewById(R.id.userNameTxt);
        userAge = (EditText) findViewById(R.id.ageTxt);
        userWeight = (EditText) findViewById(R.id.weightTxt);
        userHeight = (EditText) findViewById(R.id.heightTxt);
        spActivity = (Spinner)findViewById(R.id.spinner2);
        spGoal = (Spinner)findViewById(R.id.spinner3);

    }
    private void uploadUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databasaeReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserInfo userInfo = new UserInfo(name, weight, height, age, userActivity, userGoal);
        databasaeReference.child("users").child(firebaseAuth.getUid()).setValue(userInfo);
    }
    private Boolean validate(){
        Boolean result = false;

        name = userNameTxt.getText().toString();
        height = userHeight.getText().toString();
        age = userAge.getText().toString();
        weight = userWeight.getText().toString();

        if(name.isEmpty() || height.isEmpty() || age.isEmpty() || weight.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }

    public void registerBtn(View view) {
        if(validate()){
            //upload data to firebase
            Toast.makeText(this, "Data Registered successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, BottomNav.class));
            uploadUserData();
        }
    }

}
