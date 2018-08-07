package com.vego.vego.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vego.vego.Adapters.MealsAdapter;
import com.vego.vego.Adapters.ViewPagerAdapter;
import com.vego.vego.Fragment.FragmentAddMealDetailes;
import com.vego.vego.Fragment.FragmentAddMealIng;
import com.vego.vego.Fragment.FragmentMealIngr;
import com.vego.vego.Fragment.FragmentMealsDetails;
import com.vego.vego.R;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.elements;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.meal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Step 5 implement the method
public class AddNewMealActivity extends AppCompatActivity implements FragmentAddMealDetailes.passArrayListIng
        , FragmentAddMealIng.passArrayListEle {

    AppBarLayout appBarLayout;
    TabLayout tableLayout;
    ViewPager viewPager;
    ArrayList<ingredients> importedIngredientsArrayList = new ArrayList<>();
    ArrayList<meal> mealArrayList = new ArrayList<>();
    ArrayList<elements> importedElementsArrayList = new ArrayList<>();
    String n, c,mealUrl;
    Button saveMeal;
    meal m;
    StorageReference mealRef;
    int test = 0;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();
    ImageView imageMeal;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    ProgressBar progressBar;
    ProgressDialog p;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                imageMeal.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meal);


        //connect views to layout
        appBarLayout = findViewById(R.id.appbarid);
        tableLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        saveMeal = findViewById(R.id.saveMealBtn);
        imageMeal = findViewById(R.id.mealImg);
        progressBar = new ProgressBar(this);

        imageMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });


        //add Fragments
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentAddMealDetailes(), "Meal Details");
        adapter.addFragment(new FragmentAddMealIng(), "Meal Ingredients");


        //set adapter
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);


        saveMeal.setEnabled(false);

        FirebaseDatabase f = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = f.getReference().child("meals");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Note ** : ondatachange discards the value of arraylist after it finishs
                test = (int) dataSnapshot.getChildrenCount();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });



        //   mealArrayListTest = populateList();

        saveMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!importedIngredientsArrayList.isEmpty() && !importedElementsArrayList.isEmpty() && imagePath != null) {



                    // here we upload meal pics
                      mealRef = storageReference.child("meals/").child(String.valueOf(test));
                    UploadTask uploadTask = (UploadTask) mealRef.putFile(imagePath);

                    if(uploadTask != null && uploadTask.isInProgress()){

                        p = new ProgressDialog(v.getContext());
                        p.setTitle("Uploading");
                        p.setMessage("Uploading data...");
                        p.show();
                        Toast.makeText(AddNewMealActivity.this, "upload is in progress .. please wait", Toast.LENGTH_LONG).show();

                    }

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return mealRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                mealUrl = downloadUri.toString();
                                Toast.makeText(AddNewMealActivity.this, "upload successeded", Toast.LENGTH_SHORT).show();
                                p.dismiss();
                            meal m = new meal();

                            ArrayList test1 = importedIngredientsArrayList;
                            ArrayList test2 = importedElementsArrayList;
                            m.setCal(c);
                            m.setName(n);
                            m.setingredients(test1);
                            m.setElements(test2);
                            m.setImage(mealUrl);

                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference d = firebaseDatabase.getReference();



                            //upload meal to firebase
                            d.child("meals").child(String.valueOf(test)).setValue(m);
                                startActivity(new Intent(AddNewMealActivity.this, AdminActivity.class));
                            } else {
                                // Handle failures
                                Toast.makeText(AddNewMealActivity.this, "upload failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
//
// .addOnSuccessListener(
//                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    mealRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            Uri downloadUrl = uri;
//                                            //Do what you want with the url
//                                            mealUrl=mealRef.getDownloadUrl().toString();
//                            meal m = new meal();
//
//                            ArrayList test1 = importedIngredientsArrayList;
//                            ArrayList test2 = importedElementsArrayList;
//                            m.setCal(c);
//                            m.setName(n);
//                            m.setingredients(test1);
//                            m.setElements(test2);
//                            m.setImage(mealUrl);
//
//                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                            DatabaseReference d = firebaseDatabase.getReference();
//
//
//
//                            //upload meal to firebase
//                            d.child("meals").child(String.valueOf(test)).setValue(m);
//                                        }
//                                    });
//                                }
//                            });
                            //.addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    progressBar.setProgress(0);
//                                }
//                            },5000);
//                            Toast.makeText(AddNewMealActivity.this, "upload successeded", Toast.LENGTH_SHORT).show();
//                            mealUrl=mealRef.getDownloadUrl().toString();
//                            meal m = new meal();
//
//                            ArrayList test1 = importedIngredientsArrayList;
//                            ArrayList test2 = importedElementsArrayList;
//                            m.setCal(c);
//                            m.setName(n);
//                            m.setingredients(test1);
//                            m.setElements(test2);
//                            m.setImage(mealUrl);
//
//                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                            DatabaseReference d = firebaseDatabase.getReference();
//
//
//
//                            //upload meal to firebase
//                            d.child("meals").child(String.valueOf(test)).setValue(m);
//                        }
//                    })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(AddNewMealActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                }
//                            })
//                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                                double progress = (100.0*taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
////                                progressBar.setProgress((int) progress);
//                                }
//                            });
                    //StorageReference mealUrlRef = firebaseStorage.getReference() ;


//                    storageReference.child("meals").child(String.valueOf(test)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                        mealUrl = uri.toString();
//                        }
//                    });

//                    uploadTask.addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(AddNewMealActivity.this, "meal Registered failed", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(AddNewMealActivity.this, "meal Registered successfully", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) /
//                                    taskSnapshot.getTotalByteCount();
//
//                            progressBar.setProgress((int) progress);
//                        }
//                    });



//                    meal m = new meal();
//
//                    ArrayList test1 = importedIngredientsArrayList;
//                    ArrayList test2 = importedElementsArrayList;
//                    m.setCal(c);
//                    m.setName(n);
//                    m.setingredients(test1);
//                    m.setElements(test2);
//                    m.setImage(mealUrl);
                    //                  mealArrayListTest.add(new meal(c, n, importedElementsArrayList,
                    //                        importedIngredientsArrayList));.
                    //mealArrayList.add(m);


//                    for (int i = 0; i < mealArrayList.size(); i++) {
//                        for (int j = 0; j < mealArrayList.get(i).getElements().size(); j++) {
//                            Log.d("test", "d7om   " + mealArrayList.get(i).getElements().get(j).getAmount());
//                            Log.d("test", "d7om   " + mealArrayList.get(i).getElements().get(j).getName());
//                        }
//
//                        for (int j = 0; j < mealArrayList.get(i).getingredients().size(); j++) {
//                            Log.d("test", "d7om   " + mealArrayList.get(i).getingredients().get(j).getQuantity());
//                            Log.d("test", "d7om   " + mealArrayList.get(i).getingredients().get(j).getType());
//
//                        }
//
//                    }

//                    Log.d("test", "this is imported eleList" + importedElementsArrayList);
//                    Log.d("test", "this is imported eleList" + importedIngredientsArrayList);

//                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                    DatabaseReference d = firebaseDatabase.getReference();
//
//
//
//                    //upload meal to firebase
//                    d.child("meals").child(String.valueOf(test)).setValue(m);
                }
//                    d.child("meals").child("element").setValue(hashMap);
            }
        });
    }

    private ArrayList<meal> populateList() {


        saveMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meal mele = new meal();
                mealArrayList.add(mele);
            }
        });
        return mealArrayList;
    }

    @Override
    public void passArrayListIng(ArrayList<ingredients> ing, String mealName) {

        // Assign values to new arraylist
        m = new meal();

        importedIngredientsArrayList = new ArrayList<>();
        importedIngredientsArrayList.addAll(ing);

        for (int j = 0; j < importedIngredientsArrayList.size(); j++) {
            Log.d("test", "d7om   " + importedIngredientsArrayList.get(j).getType());
            Log.d("test", "d7om   " + importedIngredientsArrayList.get(j).getQuantity());
        }

        n = mealName;


//        Log.d("test","this is imported ingList"+ ing.get(0).getType());
//        Log.d("test","this is imported ingList"+ ing.get(0).getQuantity());
//
//
//        Log.d("test","this is imported ingList"+ ing.get(1).getType());
//        Log.d("test","this is imported ingList"+ ing.get(1).getQuantity());


    }

    @Override
    public void passArrayListEle(ArrayList<elements> ele, String totalCal) {

        m = new meal();

        importedElementsArrayList.clear();
        importedElementsArrayList.addAll(ele);

        c = totalCal;


        saveMeal.setEnabled(true);




        //uploadMeal();
    }

    public void uploadMeal() {
//        meal mealTest1 = new meal("3490", "kofta", importedElementsArrayList,
//                importedIngredientsArrayList);
//        mealArrayList.add(mealTest1);


        Log.d("test", "this is mealArrayList" + mealArrayList.size());
//        for (int i = 0; i < mealArrayList.size(); i++) {
//            mealTest = new meal(c, n, importedElementsArrayList,
//                    importedIngredientsArrayList);
//
//
//        }
        //mealArrayList.add(mealTest);

    }
}
