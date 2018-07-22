package com.vego.vego.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vego.vego.R;

public class HomeActivity extends AppCompatActivity {    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }



    public void signinTxt(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

    }

    public void signupTxt(View view) {
        startActivity(new Intent(getApplicationContext(), SignupActivity.class));

    }


}
