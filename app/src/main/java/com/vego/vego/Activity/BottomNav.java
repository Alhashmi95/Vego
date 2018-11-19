package com.vego.vego.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;
import com.vego.vego.Fragment.ChatFragment;
import com.vego.vego.Fragment.DietFragment;
import com.vego.vego.Fragment.FragmentExercises;
import com.vego.vego.Fragment.FragmentHome;
import com.vego.vego.Fragment.FragmentWallet;
import com.vego.vego.Fragment.TrackProgressFragmentUser;
import com.vego.vego.R;
import com.vego.vego.model.UserInfo;

import java.util.Objects;

public class BottomNav extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    UserInfo userInfo;
    private GoogleApiClient mGoogleApiClient;



    //step 1 (on back twice exit)
    private boolean backPressedToExitOnce = false;
    private Toast toast = null;

    AccountHeader headerResult;




    DatabaseReference databaseReference;

    BottomNavigationView bTV = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(BottomNav.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

                rootRef = rootRef.child("Ayman").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("userToken");

                rootRef.setValue(newToken);

            }
        });


        closeAppwhenBackISClickedTwice();


        bTV = findViewById(R.id.bNavigation);
        bTV.setOnNavigationItemSelectedListener(navListener);


        //+++++++++++++++defult Fragment ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        getSupportFragmentManager().beginTransaction().replace(R.id.fContr, new TrackProgressFragmentUser()).commit();

        toolbar = findViewById(R.id.actionBarUser);
//        toolbar.setTitle("Drawer Demo");



        firebaseAuth = FirebaseAuth.getInstance();


        //to add Drawer ++++++++++++++++++++++++++++++++++++++++++++++++++++


        //get User data +++++++++++++++++++++++++++++++++++++++++++++++
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("users")
                .child(firebaseAuth.getCurrentUser().getUid()).child("Profile");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userInfo = dataSnapshot.getValue(UserInfo.class);

                // Create the AccountHeader
                if(userInfo.getImage() != null) {
                    //initialize and create the image loader logic
                    DrawerImageLoader.init(new AbstractDrawerImageLoader() {
                        @Override
                        public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                            Picasso.get().load(userInfo.getImage()).placeholder(placeholder).into(imageView);
                        }

                        @Override
                        public void cancel(ImageView imageView) {
                            Picasso.get().cancelRequest(imageView);
                        }

    /*
    @Override
    public Drawable placeholder(Context ctx) {
        return super.placeholder(ctx);
    }

    @Override
    public Drawable placeholder(Context ctx, String tag) {
        return super.placeholder(ctx, tag);
    }
    */
                    });

                    headerResult = new AccountHeaderBuilder()
                            .withActivity(BottomNav.this)
                            .withHeaderBackground(R.drawable.header)
                            .addProfiles(
                                    new ProfileDrawerItem().withName(userInfo.getName())
                                            .withEmail(firebaseAuth.getCurrentUser().getEmail())
                                            .withIcon(Uri.parse(userInfo.getImage()))

                            )
                            .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                                @Override
                                public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                                    return false;
                                }
                            })
                            .build();
                }else {
                    headerResult = new AccountHeaderBuilder()
                            .withActivity(BottomNav.this)
                            .withHeaderBackground(R.drawable.header)
                            .addProfiles(
                                    new ProfileDrawerItem().withName(userInfo.getName())
                                            .withEmail(firebaseAuth.getCurrentUser().getEmail())
                                            .withIcon(R.drawable.profile_pic_large)

                            )
                            .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                                @Override
                                public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                                    return false;
                                }
                            })
                            .build();
                }

                //create the drawer and remember the `Drawer` result object
                //if you want to update the items at a later time it is recommended to keep it in a variable
                PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("الصفحة الرئيسية");
                PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("الملف الشخصي");
                PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("تسجيل الخروج");


                //     toolbar.setNavigationIcon(R.drawable.icons_calendar_px_);

                Drawer result = new DrawerBuilder()
                        .withActivity(BottomNav.this)
                        .withAccountHeader(headerResult)
                        .withToolbar(toolbar)
                        .addDrawerItems(
                                item1.withIcon(R.drawable.ic_home_black_24dp)
                                , item2.withIcon(R.drawable.profile_ic2)
                                , item3.withIcon(R.drawable.logout_ic)
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
                toolbar.setNavigationIcon(R.drawable.ic_action_name);


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

    private void closeAppwhenBackISClickedTwice() {

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
                case R.id.ExerciseForAll:
                    f = new FragmentWallet();
                    break;
            }
            replaceFragment(f, null, true, true);
            return true;}
    };

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, HomeActivity.class));



        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                        Toast.makeText(getApplicationContext(),"تم تسجيل الخروج",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);
                    }
                });

        // Facebook Sign out
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }
    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }



    //Adding on back method to close App +++++++++++++++++++++++++++++++++++++++++++++
    //step 2
    @Override
    public void onBackPressed() {
        if (backPressedToExitOnce) {
            super.onBackPressed();
        } else {
            this.backPressedToExitOnce = true;
            showToast("اضغط مرة اخرى لإغلاق التطبيق");
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backPressedToExitOnce = false;
                }
            }, 2000);
        }
    }
    /**
     * Created to make sure that you toast doesn't show miltiple times, if user pressed back
     * button more than once.
     * @param message Message to show on toast.
     */
    private void showToast(String message) {
        if (this.toast == null) {
            // Create toast if found null, it would he the case of first call only
            this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        } else if (this.toast.getView() == null) {
            // Toast not showing, so create new one
            this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        } else {
            // Updating toast message is showing
            this.toast.setText(message);
        }

        // Showing toast finally
        this.toast.show();
    }
    /**
     * Kill the toast if showing. Supposed to call from onPause() of activity.
     * So that toast also get removed as activity goes to background, to improve
     * better app experiance for user
     */
    private void killToast() {
        if (this.toast != null) {
            this.toast.cancel();
        }
    }
    @Override
    protected void onPause() {
        killToast();
        super.onPause();
    }
    /**
     * replace or add fragment to the container
     *
     * @param fragment pass android.support.v4.app.Fragment
     * @param bundle pass your extra bundle if any
     * @param popBackStack if true it will clear back stack
     * @param findInStack if true it will load old fragment if found
     */
    public void replaceFragment(Fragment fragment, @Nullable Bundle bundle, boolean popBackStack, boolean findInStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        String tag = fragment.getClass().getName();
        Fragment parentFragment;
        if (findInStack && fm.findFragmentByTag(tag) != null) {
            parentFragment = fm.findFragmentByTag(tag);
        } else {
            parentFragment = fragment;
        }
        // if user passes the @bundle in not null, then can be added to the fragment
        if (bundle != null)
            parentFragment.setArguments(bundle);
        else parentFragment.setArguments(null);
        // this is for the very first fragment not to be added into the back stack.
        if (popBackStack) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            ft.addToBackStack(parentFragment.getClass().getName() + "");
        }
        ft.replace(R.id.fContr, parentFragment, tag);
        ft.commit();
        fm.executePendingTransactions();
    }
}
