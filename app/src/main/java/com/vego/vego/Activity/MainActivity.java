package com.vego.vego.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vego.vego.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    //seconds for splash screen
    private static int SPLASH_TIME_OUT = 1000;
    private FirebaseAuth mAuth;
    private DatabaseReference mUser;
    private DatabaseReference signedInNoProfile;

    private Boolean checker = false;

    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signedInNoProfile = FirebaseDatabase.getInstance().getReference();

        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.vego.vego",  //Replace your package name here
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {

        }


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            mUser = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("Profile").child("isAdmin");
        }else {
            //start new handler to show our splash screen
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();

                }
            }, SPLASH_TIME_OUT);

        }
        if (currentUser != null) {

            signedInNoProfile = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(currentUser.getUid());
            signedInNoProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child("Profile").exists()) {
                        checker = false;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent homeIntent = new Intent(MainActivity.this, UserDetails.class);
                                startActivity(homeIntent);
                                finish();
                            }
                        }, SPLASH_TIME_OUT);
                    } else {
                        checker = true;
                        mUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (checker) {
                                    if (dataSnapshot.getValue().toString().equals("true")) {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent homeIntent = new Intent(MainActivity.this, AdminActivity.class);
                                                startActivity(homeIntent);
                                                finish();
                                            }
                                        }, SPLASH_TIME_OUT);
                                    } else if (dataSnapshot.getValue().toString().equals("false")) {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent homeIntent = new Intent(MainActivity.this, BottomNav.class);
                                                startActivity(homeIntent);
                                                finish();
                                            }
                                        }, SPLASH_TIME_OUT);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });


        }

    }
}
