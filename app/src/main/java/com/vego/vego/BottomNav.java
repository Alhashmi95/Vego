package com.vego.vego;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

public class BottomNav extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    FirebaseAuth firebaseAuth;



    BottomNavigationView bTV = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);



        bTV = findViewById(R.id.bNavigation);
        bTV.setOnNavigationItemSelectedListener(navListener);
        //+++++++++++++++defult Fragment ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        getSupportFragmentManager().beginTransaction().replace(R.id.fContr , new FragmentHome()).commit();


        firebaseAuth = FirebaseAuth.getInstance();



        //to add Drawer ++++++++++++++++++++++++++++++++++++++++++++++++++++
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
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Profile");
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
                                startActivity(new Intent(BottomNav.this, ProfileActivity.class));
                                break;
                            case 3:
                               Logout();
                               break;
                        }
                        return true;
                    }
                })
                .build();


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

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, HomeActivity.class));
    }
}
