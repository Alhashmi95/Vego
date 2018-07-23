package com.vego.vego.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vego.vego.R;

public class SignupActivity extends AppCompatActivity {

    private EditText confirmPassword, userPassword, userEmail;
    private Button signupBtn;
    private TextView signinTxt;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;


    String email, confirm, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        progressDialog = new ProgressDialog(this);


        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){

                    progressDialog.setMessage("Registering ... please wait");
                    progressDialog.show();


                    //upload data to firebase
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                                startActivity(new Intent(SignupActivity.this, UserDetails.class));

                            }else {
                                Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                progressDialog.hide();

                            }
                        }
                    });
                }
            }
        });
        signinTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }
    private void setupUIViews(){
        confirmPassword = (EditText)findViewById(R.id.userNameTxt);
        userPassword = (EditText)findViewById(R.id.passwordTxt);
        userEmail = (EditText)findViewById(R.id.emailTxt);
        signupBtn = (Button)findViewById(R.id.signupBtn);
        signinTxt = (TextView)findViewById(R.id.signinTxt);
    }
    private Boolean validate(){
        Boolean result = false;

        confirm = confirmPassword.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();


        if(confirm.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            userEmail.setError("enter your email");
            userPassword.setError("enter your password");
        }else{
            if(userPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                result = true;
            }
            else{

                Toast.makeText(this, "password doesnt match", Toast.LENGTH_SHORT).show();
                confirmPassword.setError("password doesnt match");

            }
        }

        return result;
    }
}
