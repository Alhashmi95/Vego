package com.vego.vego.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.vego.vego.Fragment.ChatFragment;
import com.vego.vego.Fragment.FragmentExercises;
import com.vego.vego.Fragment.FragmentHome;
import com.vego.vego.Fragment.FragmentWallet;
import com.vego.vego.Fragment.TrackProgressFragmentUser;
import com.vego.vego.R;
import com.vego.vego.model.UserInfo;

public class BottomNav extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    UserInfo userInfo;



    BottomNavigationView bTV = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);



        bTV = findViewById(R.id.bNavigation);
        bTV.setOnNavigationItemSelectedListener(navListener);
        //+++++++++++++++defult Fragment ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        getSupportFragmentManager().beginTransaction().replace(R.id.fContr , new TrackProgressFragmentUser()).commit();


        firebaseAuth = FirebaseAuth.getInstance();



        //to add Drawer ++++++++++++++++++++++++++++++++++++++++++++++++++++
        toolbar = findViewById(R.id.actionBar);
        toolbar.setTitle("Drawer Demo");
      //  toolbar.setNavigationIcon(R.drawable.icons_calendar_px_);


        //get User data +++++++++++++++++++++++++++++++++++++++++++++++
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("users")
                .child(firebaseAuth.getUid()).child("Profile");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userInfo = dataSnapshot.getValue(UserInfo.class);

                // Create the AccountHeader
                AccountHeader headerResult = new AccountHeaderBuilder()
                        .withActivity(BottomNav.this)
                        .withHeaderBackground(R.drawable.header)
                        .addProfiles(
                                new ProfileDrawerItem().withName(userInfo.getName())
                                        .withEmail(firebaseAuth.getCurrentUser().getEmail())
                                        .withIcon(getResources().getDrawable(R.drawable.profile_pic_large))
                        )
                        .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                            @Override
                            public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                                return false;
                            }
                        })
                        .build();
                //create the drawer and remember the `Drawer` result object
                //if you want to update the items at a later time it is recommended to keep it in a variable
                PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
                PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Profile");
                PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Logout");


           //     toolbar.setNavigationIcon(R.drawable.icons_calendar_px_);



                Drawer result = new DrawerBuilder()
                        .withActivity(BottomNav.this)
                        .withAccountHeader(headerResult)
                        .withToolbar(toolbar)
                        .addDrawerItems(
                                item1.withIcon(R.drawable.ic_home_black_24dp)
                                ,item2.withIcon(R.drawable.profile_ic2)
                                ,item3.withIcon(R.drawable.logout_ic)
                        )
                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                // do something with the clicked item :D
                                switch (position) {
                                    case 1:
                                        startActivity(new Intent(BottomNav.this, BottomNav.class));
                                        break;
                                    case 2:
                                        startActivity(new Intent(BottomNav.this, UpdateProfileActivity.class));
                                        break;
                                    case 3:
                                        Logout();
                                        break;
                                }
                                return true;
                            }
                        })

                        .build();

            //    getSupportActionBar().setIcon(R.drawable.icons_calendar_px_);



                //get the DrawerLayout from the Drawer
                DrawerLayout drawerLayout = result.getDrawerLayout();
                //do whatever you want with the Drawer. Like locking it.
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
             //   drawerLayout.setBackgroundColor(R.color.material_drawer_dark_background);
                //or (int lockMode, int edgeGravity)



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });







    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment f = null;
            switch (menuItem.getItemId()){
                case R.id.wallet:
                    f = new FragmentHome();
                    break;
                case R.id.setting:
                    f = new FragmentExercises();
                    break;
                case R.id.trackProgress:
                    f = new TrackProgressFragmentUser();
                    break;
                case R.id.language:
                    f = new ChatFragment();
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
