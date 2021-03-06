package com.vego.vego.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.vego.vego.R;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.UserInfo;
import com.vego.vego.model.elements;
import com.vego.vego.model.exercise;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.meal;
import com.vego.vego.model.sets;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText newUserName, newUserAge, newUserWeight, newUserHeight;
    Spinner spActivity;
    Spinner spGoal;
    private Button save, reset;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private String userGoal;
    private String userActivity;
    private ImageView profilePic;
    private ImageView previusPic;
    private TextView picPicker;
    private static int PICK_IMAGE = 100;
    StorageReference picRef;
    String picUrl;

    Uri imageUri;
    int indexofPics =0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        newUserName = findViewById(R.id.userNameUpdateTxt);
        newUserWeight = findViewById(R.id.weightTxt2);
        newUserHeight = findViewById(R.id.heightTxt2);
        newUserAge = findViewById(R.id.ageTxt2);
        save = findViewById(R.id.btnSave);
        spActivity = findViewById(R.id.spinner);
        spGoal = findViewById(R.id.spinner4);
        reset = findViewById(R.id.resetPass);
        picPicker = findViewById(R.id.picPicker);
        profilePic = findViewById(R.id.ivProfileUpdate);
        profilePic = profilePic;

        newUserName.requestFocus();


//        updateProfilePic = findViewById(R.id.ivProfileUpdate);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("users")
                .child(firebaseAuth.getUid()).child("Profile");

//        StorageReference storageReference = firebaseStorage.getReference();
//        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).fit().centerCrop().into(profilePic);
//            }
//        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);


                newUserName.setText(userinfo.getName());
                newUserAge.setText(userinfo.getAge());
                newUserWeight.setText(userinfo.getWeight());
                newUserHeight.setText(userinfo.getHeight());

                userGoal = userinfo.getUserGoal();
                userActivity = userinfo.getUserActivity();

                if(userinfo.getUserActivity().equals("مرتفع")){
                    spActivity.setSelection(1);
                }else if(userinfo.getUserActivity().equals("متوسط")){
                    spActivity.setSelection(2);
                }else if(userinfo.getUserActivity().equals("منخفض")) {
                    spActivity.setSelection(3);
                } else if(userinfo.getUserActivity().equals("منخفض جداً")) {
                    spActivity.setSelection(4);
                }


                if(userinfo.getUserGoal().equals("زيادة الوزن")){
                    spGoal.setSelection(1);
                }else if(userinfo.getUserGoal().equals("انقاص الوزن")){
                    spGoal.setSelection(2);
                }else if(userinfo.getUserGoal().equalsIgnoreCase("خسارة الدهون والمحافظة على العضلات")) {
                    spGoal.setSelection(3);
                }else if(userinfo.getUserGoal().equals("المحافظة على الوزن الحالي")) {
                    spGoal.setSelection(4);
                }
                //-----------------------------Load image-------------------------------------------
                if(userinfo.getImage()!= null){
                Picasso.get().load(userinfo.getImage()).fit()
                        .centerCrop().into(profilePic);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UpdateProfileActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        // Initializing a String Array
        String[] activity = new String[]{
                "مستوى النشاط",
                "مرتفع",
                "متوسط",
                "منخفض",
                "منخفض جداً"
        };
        String[] goal = new String[]{
                "هدفك من التطبيق",
                "زيادة الوزن",
                "انقاص الوزن",
                "خسارة الدهون والمحافظة على العضلات",
                "المحافظة على الوزن الحالي"
        };

//        ingredients[] ingredients = new ingredients[]{new ingredients("478", "cheickn")};
//
//        List mealIngrList = new ArrayList<ingredients>(Arrays.asList(ingredients));
//
//        elements[] elements = new elements[]{new elements("cals", "409"),
//                new elements("carbo", "490")};
//        List mealElementList = new ArrayList<elements>(Arrays.asList(elements));
//
//
//        DayMeals[] dayMealsDay1 = new DayMeals[]{new DayMeals("meal1day1", "1",
//                R.drawable.setting, (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList), //add get(0)
//                new DayMeals("meal2day1", "2", R.drawable.profile, (ArrayList<ingredients>) mealIngrList,
//                        (ArrayList<elements>) mealElementList)};

//        List dayMealsD1 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay1));
//
//        //////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        DayMeals[] dayMealsDay2 = new DayMeals[]{new DayMeals("meal1day2", "22", R.drawable.setting
//                , (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList)
//
//        };
//
//        List dayMealsD2 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay2));
//
//        ////////////////////////////////////////////////////////////////////////////////////////////////////////
//        DayMeals[] dayMealsDay3 = new DayMeals[]{new DayMeals("fds", "meal1day3", R.drawable.setting,
//                (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList),
//                new DayMeals("meal2day3", "333", R.drawable.home_w, (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList)
//        };
//
//        List dayMealsD3 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay3));
//
//        ////////////////////////////////////////////////////////////////////////////////////////////////////////
//        DayMeals[] dayMealsDay4 = new DayMeals[]{new DayMeals("meal1day4", "9328", R.drawable.setting,
//                (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList),
//                new DayMeals("fdskjdfs", "928", R.drawable.home_w, (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList)
//
//        };
//
//        List dayMealsD4 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay4));
//
//        ////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        DayMeals[] dayMealsDay5 = new DayMeals[]{new DayMeals("fds", "9328", R.drawable.setting
//                , (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList),
//                new DayMeals("fdskjdfs", "928", R.drawable.home_w,
//                        (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList)
//
//        };
//
//        List dayMealsD5 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay5));
//
//        ////////////////////////////////////////////////////////////////////////////////////////////////////////
//        DayMeals[] dayMealsDay6 = new DayMeals[]{new DayMeals("fds", "9328", R.drawable.setting,
//                (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList)
//
//        };
//
//        List dayMealsD6 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay6));
//
//        ////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        DayMeals[] dayMealsDay7 = new DayMeals[]{new DayMeals("fds", "9328", R.drawable.setting,
//                (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList),
//                new DayMeals("fdskjdfs", "928", R.drawable.home_w, (ArrayList<ingredients>) mealIngrList,
//                        (ArrayList<elements>) mealElementList)
//        };

//        List dayMealsD7 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay7));
//
//        ///////////////////////////////////////////////////////////////////////////////
//
//        final DietDay[] dietDay = new DietDay[]{new DietDay("1", "389", "4", (ArrayList<meal>) dayMealsD1),
//                new DietDay("2", "23", "3", (ArrayList<meal>) dayMealsD2),
//                new DietDay("3", "12334", "242", (ArrayList<meal>) dayMealsD3),
//                new DietDay("4", "2443", "34", (ArrayList<meal>) dayMealsD4),
//                new DietDay("4", "253948", "31", (ArrayList<meal>) dayMealsD5),
//                new DietDay("6", "27", "33", (ArrayList<meal>) dayMealsD6),
//                new DietDay("7", "287", "34", (ArrayList<meal>) dayMealsD7),
//        };

        //For first spinner +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        final List<String> plantsList = new ArrayList<>(Arrays.asList(activity));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, plantsList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spActivity.setAdapter(spinnerArrayAdapter);

        spActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                userActivity = selectedItemText;
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
//                    Toast.makeText
//                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
//                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //For second spinner +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        final List<String> goalList = new ArrayList<>(Arrays.asList(goal));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, goalList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_item);
        spGoal.setAdapter(spinnerArrayAdapter2);

        spGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                userGoal = selectedItemText;
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
//                    Toast.makeText
//                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
//                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //End spinner


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        //  databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                com.vego.vego.model.UserInfo userinfo = dataSnapshot.getValue(com.vego.vego.model.UserInfo.class);
//                newUserName.setText(userinfo.getName());
//                newUserAge.setText(userinfo.getAge());
//                newUserWeight.setText(userinfo.getWeight());
//                newUserHeight.setText(userinfo.getHeight());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(UpdateProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
//            }
//        });

//        final StorageReference storageReference = firebaseStorage.getReference();
//        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).fit().centerCrop().into(updateProfilePic);
//            }
//        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = newUserName.getText().toString();
                String age = newUserAge.getText().toString();
                String weight = newUserWeight.getText().toString();
                String height = newUserHeight.getText().toString();

                if (name.isEmpty() || height.isEmpty() || age.isEmpty() || weight.isEmpty()
                        || userActivity.equals("مستوى النشاط") || userGoal.equals("هدفك من التطبيق")) {
                    Toast.makeText(UpdateProfileActivity.this
                            , "الرجاء ادخل جميع التفاصيل", Toast.LENGTH_SHORT).show();
                } else {
                    //--------------------------------------------commented
                    //updateProfilePic();
                    //----------------------------------------------------

                    DatabaseReference databasaeReferenceName = firebaseDatabase.getReference();

                    databasaeReferenceName.child("users").child(firebaseAuth.getUid()).child("Profile").child("name")
                            .setValue(name);

                    DatabaseReference databasaeReferenceAge = firebaseDatabase.getReference();

                    databasaeReferenceAge.child("users").child(firebaseAuth.getUid()).child("Profile").child("age")
                            .setValue(age);

                    DatabaseReference databasaeReferenceWeight = firebaseDatabase.getReference();

                    databasaeReferenceWeight.child("users").child(firebaseAuth.getUid()).child("Profile").child("weight")
                            .setValue(weight);

                    DatabaseReference databasaeReferenceHeight = firebaseDatabase.getReference();

                    databasaeReferenceHeight.child("users").child(firebaseAuth.getUid()).child("Profile").child("height")
                            .setValue(height);

                    DatabaseReference databasaeReferenceGoal = firebaseDatabase.getReference();

                    databasaeReferenceGoal.child("users").child(firebaseAuth.getUid()).child("Profile").child("userGoal")
                            .setValue(userGoal);

                    DatabaseReference databasaeReferenceActive = firebaseDatabase.getReference();

                    databasaeReferenceActive.child("users").child(firebaseAuth.getUid()).child("Profile").child("userActivity")
                            .setValue(userActivity);

                    //-------------------------------------upload pic--------------------------------------------------------------
                    Toast.makeText(getApplicationContext(), "Data Updated successfully", Toast.LENGTH_SHORT).show();

                    UpdateProfileActivity.this.startActivity(new Intent(UpdateProfileActivity.this, BottomNav.class));

                    finish();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfileActivity.this.startActivity(new Intent(UpdateProfileActivity.this, PasswordActivity.class));
            }
        });


//                ArrayList dietList ;
//                dietList= new ArrayList<DietDay>(Arrays.asList(dietDay));
//
//                com.vego.vego.model.UserInfo userinfo = new com.vego.vego.model.UserInfo(
//                        name, weight, height,age,userActivity,userGoal,(ArrayList<DietDay>) dietList,
//                        "false",firebaseAuth.getUid().toString(),"",""
//                        ,firebaseAuth.getCurrentUser().getEmail());
//
//                HashMap<String,String> hashMap=new HashMap<>();
//                hashMap.put("age",userinfo.age);
//                hashMap.put("height",userinfo.height);
//                hashMap.put("name",userinfo.name);
//                hashMap.put("weight",userinfo.weight);
//                hashMap.put("userActivity", userActivity);
//                hashMap.put("userGoal", userGoal);
//                hashMap.put("isAdmin", String.valueOf(false));
//                hashMap.put("uidUser",userinfo.getUidUser());
//                hashMap.put("previousWeight",userinfo.getPreviousWeight());
//                hashMap.put("adminBrief",userinfo.getAdminBrief());
//                hashMap.put("userEmail",userinfo.getEmail());
//
//                DatabaseReference databasaeReference = firebaseDatabase.getReference();
//
//
//                databasaeReference.child("users").child(firebaseAuth.getUid()).child("Profile").setValue(hashMap);


//                StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
//                UploadTask uploadTask = imageReference.putFile(imagePath);
//                uploadTask.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(UpdateProfileActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                        Toast.makeText(UpdateProfileActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
//                    }
//                });




//        updateProfilePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("images/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
//            }
//        });

        //----------------------------------profilr pic---------------------------------------------
//        picPicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();//(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, PICK_IMAGE);
//
//            }
//        });
    }

    private void updateProfilePic() {
//        if(profilePic.getId()==previusPic.getId()){

            // here we upload meal pics
            picRef = storageReference.child("profile_pic/").child(String.valueOf(0));
            UploadTask uploadTask = (UploadTask) picRef.putFile(imageUri);

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(UpdateProfileActivity.this, "upload is in progress .. please wait", Toast.LENGTH_LONG).show();

            }

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return picRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        picUrl = downloadUri.toString();
                        Toast.makeText(UpdateProfileActivity.this, "upload successeded", Toast.LENGTH_SHORT).show();
                        //  UserInfo u = new UserInfo();
//                        saveNewEx.setVisibility(View.VISIBLE);
//                        saveNewExForAll.setVisibility(View.VISIBLE);

//                        sets[] setsArray = new sets[] {new sets("1234","", "","") };
//
//                        List setsList = new ArrayList<sets>(Arrays.asList(setsArray));


                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference d = firebaseDatabase.getReference();


                        //upload meal to firebase
                        d.child("users").child(firebaseAuth.getCurrentUser().getUid())
                                .child("Profile").child("image").setValue(picUrl);


                        finish();


                    }

                }
            });
            }
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                }
                return super.onOptionsItemSelected(item);
                }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profilePic.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(UpdateProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(UpdateProfileActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
