package com.vego.vego.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vego.vego.R;

public class MainActivity extends AppCompatActivity {
    //seconds for splash screen
    private static int SPLASH_TIME_OUT = 1000;
    private FirebaseAuth mAuth;
    private DatabaseReference mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!= null) {
            mUser = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("Profile").child("isAdmin");
        }
        if(currentUser!=null){
        mUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("true")){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent homeIntent = new Intent(MainActivity.this, AdminActivity.class);
                            startActivity(homeIntent);
                            finish();
                        }
                    },SPLASH_TIME_OUT);
                }else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent homeIntent = new Intent(MainActivity.this, BottomNav.class);
                            startActivity(homeIntent);
                            finish();
                        }
                    },SPLASH_TIME_OUT);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        }else {
            //start new handler to show our splash screen
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }





    }

}
