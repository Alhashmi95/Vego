package com.vego.vego.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vego.vego.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private Button signinBtn;
    private EditText emailTxt, passwordTxt;
    private TextView signupTxt, reset;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private CallbackManager mCallbackManager;
    private LoginButton loginButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupUIViews();


        facebookLogin();

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        signupTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, PasswordActivity.class));
            }
        });

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



        // Google Sign-In
        // Assign fields
        SignInButton mGoogleSignInButton = findViewById(R.id.google_button);



        // Set click listeners
        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setMessage("Signing in.. please wait");
                progressDialog.setCancelable(false);
                signIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void facebookLogin() {
        // Facebook Login
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        LoginButton mFacebookSignInButton = (LoginButton) findViewById(R.id.facebook_button);
        mFacebookSignInButton.setReadPermissions("email", "public_profile", "user_birthday", "user_friends");

        mFacebookSignInButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progressDialog.show();
                progressDialog.setMessage("Signing in.. please wait");
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
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()
                                            && dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile")
                                            .child("isAdmin").getValue().equals("false")) {
                                        //LoginActivity.this.startActivity(new Intent(LoginActivity.this, BottomNav.class));

                                        Intent intent = new Intent(LoginActivity.this, BottomNav.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                        progressDialog.dismiss();

                                        //LoginActivity.this.finish();

                                    } else if (dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()
                                            && dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile")
                                            .child("isAdmin").getValue().equals("true")) {
                                        //LoginActivity.this.startActivity(new Intent(LoginActivity.this, AdminActivity.class));

                                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                        progressDialog.dismiss();
                                        //LoginActivity.this.finish();

                                    } else if (!dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()) {
                                        //LoginActivity.this.startActivity(new Intent(LoginActivity.this, UserDetails.class));

                                        Intent intent = new Intent(LoginActivity.this, UserDetails.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                        progressDialog.dismiss();
                                        //LoginActivity.this.finish();
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
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()
                                            && dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile")
                                            .child("isAdmin").getValue().equals("false") ){
                                        //LoginActivity.this.startActivity(new Intent(LoginActivity.this, BottomNav.class));

                                        Intent intent = new Intent(LoginActivity.this, BottomNav.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                        progressDialog.dismiss();

                                        //LoginActivity.this.finish();

                                    }else if(dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()
                                            && dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile")
                                            .child("isAdmin").getValue().equals("true") ){
                                        //LoginActivity.this.startActivity(new Intent(LoginActivity.this, AdminActivity.class));

                                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                        progressDialog.dismiss();
                                        //LoginActivity.this.finish();

                                    }else if(!dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()) {
                                        //LoginActivity.this.startActivity(new Intent(LoginActivity.this, UserDetails.class));

                                        Intent intent = new Intent(LoginActivity.this, UserDetails.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                        progressDialog.dismiss();
                                        //LoginActivity.this.finish();
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


    private void setupUIViews() {
        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        signinBtn = findViewById(R.id.signinBtn);
        signupTxt = findViewById(R.id.signupTxt);
        reset = findViewById(R.id.txtForget);

    }
    private void validate(String username, String password){




        if(!username.isEmpty() && !password.isEmpty()) {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               if(dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()
                                       && dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile")
                                       .child("isAdmin").getValue().equals("false") ){
                                    //LoginActivity.this.startActivity(new Intent(LoginActivity.this, BottomNav.class));

                                   Intent intent = new Intent(LoginActivity.this, BottomNav.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(intent);

                                   progressDialog.dismiss();

                                   //LoginActivity.this.finish();

                               }else if(dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()
                                       && dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile")
                                       .child("isAdmin").getValue().equals("true") ){
                                   //LoginActivity.this.startActivity(new Intent(LoginActivity.this, AdminActivity.class));

                                   Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(intent);

                                   progressDialog.dismiss();
                                   //LoginActivity.this.finish();

                              }else if(!dataSnapshot.child("users").child(firebaseAuth.getUid()).child("Profile").exists()) {
                                   //LoginActivity.this.startActivity(new Intent(LoginActivity.this, UserDetails.class));

                                   Intent intent = new Intent(LoginActivity.this, UserDetails.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(intent);

                                   progressDialog.dismiss();
                                   //LoginActivity.this.finish();
                               }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                     //   startActivity(new Intent(LoginActivity.this, UserDetails.class));
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
}