package com.vego.vego;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.jaredrummler.materialspinner.MaterialSpinner;


public class UserDetails extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    private FirebaseAuth firebaseAuth;

    private EditText userNameTxt, userWeight, userAge, userHeight;
    private String name, age,weight, height, userActivity, item2;
    MaterialSpinner spinnerActivity, spinnerGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        setupUIViews();

        spinnerActivity = (MaterialSpinner) findViewById(R.id.spinner2);
        spinnerActivity.setItems("مستوى النشاط", "مرتفع", "متوسط", "منخفض", "خامل");
        spinnerActivity.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        spinnerGoal = (MaterialSpinner) findViewById(R.id.spinner3);
        spinnerGoal.setItems("الهدف من التطبيق", "زيادة وزن", "زيادة عضل", "المحافظة على الوزن الحالي", "انقاص الوزن");
        spinnerGoal.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbarT);
        toolbar.setTitle("Drawer Demo");

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Ayman Qaid").withEmail("aymanlord09@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Settings");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Logout");


//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1, item2, item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position) {
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                Logout();
                        }
                        return true;
                    }
                })
                .build();
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
        userNameTxt = (EditText) findViewById(R.id.usernameTxt);
        userAge = (EditText) findViewById(R.id.ageTxt);
        userWeight = (EditText) findViewById(R.id.weightTxt);
        userHeight = (EditText) findViewById(R.id.heightTxt);
    }
    private void uploadUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databasaeReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserInfo userInfo = new UserInfo(name, weight, height, age, userActivity);
        databasaeReference.setValue(userInfo);
    }
    private Boolean validate(){
        Boolean result = false;

        name = userNameTxt.getText().toString();
        height = userHeight.getText().toString();
        age = userAge.getText().toString();
        weight = userWeight.getText().toString();
        spinnerActivity.getText().toString();

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
