package com.vego.vego.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vego.vego.R;

public class SignupActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private EditText confirmPassword, userPassword, userEmail;
    private Button signupBtn;
    private TextView signinTxt;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;
    
    private DatabaseReference databaseReference;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private CallbackManager mCallbackManager;
    private LoginButton loginButton;



    String email, confirm, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        progressDialog = new ProgressDialog(this);

        facebookLogin();
        setupUIViews();



        firebaseAuth = FirebaseAuth.getInstance();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){

                    progressDialog.setMessage("جاري التسجيل ... برجى الانتظار");
                    progressDialog.show();


                    //upload data to firebase
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                                startActivity(new Intent(SignupActivity.this, UserDetails.class));

                            }else {
                                Toast.makeText(SignupActivity.this, "فشل التسجيل .. الرجاء المحاولة مرة أخرى", Toast.LENGTH_SHORT).show();
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

    private void facebookLogin() {
        // Facebook Login
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        LoginButton mFacebookSignInButton = (LoginButton) findViewById(R.id.facebook_button);
        mFacebookSignInButton.setReadPermissions("email");

        mFacebookSignInButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progressDialog.setMessage("جاري تسجيل الدخول ... يرجى الانتظار");
                progressDialog.show();
                progressDialog.setCancelable(false);
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                firebaseAuthWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

    }
    private void firebaseAuthWithFacebook(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()
                                            && dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile")
                                            .child("isAdmin").getValue().equals("false")) {
                                        //SignupActivity.this.startActivity(new Intent(SignupActivity.this, BottomNav.class));

                                        Intent intent = new Intent(SignupActivity.this, BottomNav.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                        progressDialog.dismiss();

                                        //SignupActivity.this.finish();

                                    } else if (dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()
                                            && dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile")
                                            .child("isAdmin").getValue().equals("true")) {
                                        //SignupActivity.this.startActivity(new Intent(SignupActivity.this, AdminActivity.class));

                                        Intent intent = new Intent(SignupActivity.this, AdminActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                        progressDialog.dismiss();
                                        //SignupActivity.this.finish();

                                    } else if (!dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()) {
                                        //SignupActivity.this.startActivity(new Intent(SignupActivity.this, UserDetails.class));

                                        Intent intent = new Intent(SignupActivity.this, UserDetails.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                        progressDialog.dismiss();
                                        //SignupActivity.this.finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()
                                            && dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile")
                                            .child("isAdmin").getValue().equals("false") ){
                                        //SignupActivity.this.startActivity(new Intent(SignupActivity.this, BottomNav.class));

                                        Intent intent = new Intent(SignupActivity.this, BottomNav.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                        progressDialog.dismiss();

                                        //SignupActivity.this.finish();

                                    }else if(dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()
                                            && dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile")
                                            .child("isAdmin").getValue().equals("true") ){
                                        //SignupActivity.this.startActivity(new Intent(SignupActivity.this, AdminActivity.class));

                                        Intent intent = new Intent(SignupActivity.this, AdminActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                        progressDialog.dismiss();
                                        //SignupActivity.this.finish();

                                    }else if(!dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()) {
                                        //SignupActivity.this.startActivity(new Intent(SignupActivity.this, UserDetails.class));

                                        Intent intent = new Intent(SignupActivity.this, UserDetails.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                        progressDialog.dismiss();
                                        //SignupActivity.this.finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed
                Log.e(TAG, "Google Sign In failed.");
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_button:
                signIn();
                break;
            default:
                return;
        }
    }

    private Boolean validate(){
        Boolean result = false;

        confirm = confirmPassword.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();


        if(confirm.isEmpty() || password.isEmpty() || email.isEmpty()){
            //Toast.makeText(this, "الرجاء ادخال البريد الالكتروني وكلمة السر", Toast.LENGTH_SHORT).show();
            userEmail.setError("الرجاء ادخال البريد الالكتروني");
            userPassword.setError("الرجاء ادخال كلمة مرور");
        }else{
            if(userPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                result = true;
            }
            else{
                confirmPassword.setError("تأكد من ادخال كلمة السر بشكل صحيح");

            }
        }

        return result;
    }
}
