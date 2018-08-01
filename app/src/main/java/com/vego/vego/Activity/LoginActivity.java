package com.vego.vego.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vego.vego.R;

public class LoginActivity extends AppCompatActivity {

    private Button signinBtn;
    private EditText emailTxt, passwordTxt;
    private TextView signupTxt;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        setupUIViews();

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //check if user already logged in
        FirebaseUser user = firebaseAuth.getCurrentUser();


//        //Warning change this later
//        if(user != null){
//            finish();
//            startActivity(new Intent(LoginActivity.this, UserDetails.class));
//        }
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(emailTxt.getText().equals("") || passwordTxt.getText().equals("")){
//                    Toast.makeText(getApplicationContext(), "kgdjgkdjfig", Toast.LENGTH_SHORT).show();
//                }else
//                emailTxt.setText("wakka-2@hotmail.com");
//                passwordTxt.setText("1234567890");
                validate(emailTxt.getText().toString(), passwordTxt.getText().toString());
            }
        });

        signupTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });




    }

    private void setupUIViews() {
        emailTxt = (EditText) findViewById(R.id.emailTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        signinBtn = (Button) findViewById(R.id.signinBtn);
        signupTxt = (TextView) findViewById(R.id.signupTxt);

    }
    private void validate(String username, String password){




        if(!username.isEmpty() && !password.isEmpty()) {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                                //databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                               if(dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()){
//                                    startActivity(new Intent(LoginActivity.this, BottomNav.class));
//
//                               }else {
//                                    startActivity(new Intent(LoginActivity.this, UserDetails.class));
//                              }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });


                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, UserDetails.class));
                    } else {
                        progressDialog.hide();
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }else {
            passwordTxt.setError("please enter your password");
            emailTxt.setError("please enter your email");
        }
    }
}