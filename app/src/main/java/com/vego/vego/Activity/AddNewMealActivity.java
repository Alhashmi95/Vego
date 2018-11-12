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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vego.vego.Adapters.MealsAdapter;
import com.vego.vego.Adapters.NewElementAdapter;
import com.vego.vego.Adapters.NewIngredientAdapter;
import com.vego.vego.Adapters.ViewPagerAdapter;
import com.vego.vego.Fragment.AddMealsFragment;
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

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

//Step 5 implement the method
public class AddNewMealActivity extends AppCompatActivity implements FragmentAddMealDetailes.passArrayListIng
        , FragmentAddMealIng.passArrayListEle {

    AppBarLayout appBarLayout;
    TabLayout tableLayout;
    ViewPager viewPager;
    ArrayList<ingredients> importedIngredientsArrayList = new ArrayList<>();
    ArrayList<meal> mealArrayList = new ArrayList<>();
    ArrayList<elements> importedElementsArrayList = new ArrayList<>();
    String n, c, mealUrl;
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

    RingProgressBar mRingProgressBar;

    EditText etTotalCal;


    private RecyclerView recyclerviewIngs, recyclerViewDet;
    private Button addNewIngBtn, addNewTypeBtn;
    private EditText etmealName;


    // Meal Ings
    NewIngredientAdapter newIngredientAdapter;
    private ArrayList<ingredients> ingrList;
    ArrayList<ingredients> list = new ArrayList<>();

    //meal Det
    NewElementAdapter newElementAdapter;
    private ArrayList<elements> eleList;
    ArrayList<elements> listEle = new ArrayList<>();
    private boolean checker;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
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


        //to prevent open keyboard auto
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        //connect views to layout
        appBarLayout = findViewById(R.id.appbarid);
//        tableLayout = findViewById(R.id.tablayout_id);
//        viewPager = findViewById(R.id.viewpager_id);
        saveMeal = findViewById(R.id.saveMealBtn);
        imageMeal = findViewById(R.id.mealImg);
        etTotalCal = findViewById(R.id.etTotalCal);
        progressBar = new ProgressBar(this);

        imageMeal.setImageResource(R.drawable.addpic2);

//        mRingProgressBar = (RingProgressBar) findViewById(R.id.progress_bar_2);

        //meal Ings views
        recyclerviewIngs = findViewById(R.id.recyclerview);

        addNewIngBtn = findViewById(R.id.addNewIngBtn);

        etmealName = findViewById(R.id.mealName);

        showMealIngs();


        //meal Details views
        recyclerViewDet = findViewById(R.id.recyclerview2);

        addNewTypeBtn = findViewById(R.id.addNewTypeBtn);

        showMealDet();




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
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new FragmentAddMealDetailes(), "مكونات الوجبة");
//        adapter.addFragment(new FragmentAddMealIng(), "القيم الغذائية");
//
//
//        //set adapter
//        viewPager.setAdapter(adapter);
//        tableLayout.setupWithViewPager(viewPager);


        //saveMeal.setEnabled(false);

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
                checkIngListAndEleList();
                if (checker && imagePath != null && !etTotalCal.getText().toString().isEmpty()) {


                        c = etTotalCal.getText().toString();

                        // here we upload meal pics
                        mealRef = storageReference.child("meals/").child(String.valueOf(test));
                        UploadTask uploadTask = (UploadTask) mealRef.putFile(imagePath);

                        if (uploadTask != null && uploadTask.isInProgress()) {
//                            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onProgress(final UploadTask.TaskSnapshot taskSnapshot) {
//                                    mRingProgressBar.setOnProgressListener(new RingProgressBar.OnProgressListener() {
//                                        @Override
//                                        public void progressToComplete() {
//                                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                                            int currentprogress = (int) progress;
//                                            mRingProgressBar.setProgress(currentprogress);
//
//                                        }
//                                    });
//                                }
//                            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
//                                    System.out.println("Upload is paused");
//                                }
//                            });

                            p = new ProgressDialog(v.getContext());
                            p.setTitle("رفع الوجبة");
                            p.setMessage("جاري الرفع...");
                            p.show();
                            p.setCanceledOnTouchOutside(false);
//                            Toast.makeText(AddNewMealActivity.this, "upload is in progress .. please wait",
//                                    Toast.LENGTH_LONG).show();
                            saveMeal.setVisibility(View.INVISIBLE);
                        }

                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot
                                , Task<Uri>>() {
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
                                    Toast.makeText(AddNewMealActivity.this, "تم الرفع",
                                            Toast.LENGTH_SHORT).show();
                                    saveMeal.setVisibility(View.VISIBLE);
                                    p.dismiss();
                                    meal m = new meal();

                                    ArrayList test1 = ingrList;
                                    ArrayList test2 = eleList;
                                    m.setCal(c);
                                    m.setName(n);
                                    m.setingredients(test1);
                                    m.setElements(test2);
                                    m.setImage(mealUrl);

                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference d = firebaseDatabase.getReference();


                                    //upload meal to firebase
                                    d.child("meals").child(String.valueOf(test)).setValue(m);

                                    finish();

                                } else {
                                    // Handle failures
                                    Toast.makeText(AddNewMealActivity.this, "فشل الرفع", Toast.LENGTH_SHORT).show();
                                    saveMeal.setVisibility(View.VISIBLE);
                                    p.dismiss();
                                }
                            }
                        });
                    }else {

                    etTotalCal.setError("الرجاء ادخال مجموع السعرات الحرارية");
                    if(imagePath == null)
                    Toast.makeText(AddNewMealActivity.this, "الرجاء تحديد صورة للوجبة", Toast.LENGTH_SHORT).show();
                }
//                    d.child("meals").child("element").setValue(hashMap);
            }
        });
    }

    private void checkIngListAndEleList() {
        //meal Ings
        //declare boolean checker to see if there are null edit text
        checker = false;
        //check if meal edit text is null
        if (etmealName.getText().toString().isEmpty()) {
            etmealName.setError("الرجاء ادخال اسم الوجبة");
        } else {
            n = etmealName.getText().toString().trim();
        }
        //first we check if arraylist of elements is not null
        //check if there's any null edit text in all cardviews of meal elements3
        checker = true;
        for (int i = 0; i < newIngredientAdapter.getIngredientsArray().size(); i++) {
            //check if there's any null edit text in all cardviews of meal elements
            if (newIngredientAdapter.getIngredientsArray().get(i).getType().isEmpty() ||
                    newIngredientAdapter.getIngredientsArray().get(i).getQuantity().isEmpty()
                    || etmealName.getText().toString().isEmpty()) {
                Toast.makeText(this, "الرجاء ادخال جميع التفاصيل للأصناف",
                        Toast.LENGTH_SHORT).show();
                checker = false;
                break;

            }

        }

//        if (newIngredientAdapter.getIngredientsArray() != null && checker) {
//            Toast.makeText(getContext(), "تم الحفظ",
//                    Toast.LENGTH_SHORT).show();
//            listner.passArrayListIng(newIngredientAdapter.getIngredientsArray(), mealName);
//
//        }

        if (newElementAdapter.getElementssArray() != null) {
            for (int i = 0; i < newElementAdapter.getElementssArray().size(); i++) {
                if (newElementAdapter.getElementssArray().get(i).getName().isEmpty() ||
                        newElementAdapter.getElementssArray().get(i).getAmount().isEmpty()) {
                    Toast.makeText(this, "الرجاء ادخال كافة التفاصيل للعناصر الغذائية",
                            Toast.LENGTH_SHORT).show();
                    checker = false;
                    break;
                }
            }
//            if(checker){
//                sum = 0;
//                for (int i = 0; i < newElementAdapter.getElementssArray().size(); i++) {
//                    double totalCals = Double.valueOf(newElementAdapter.getElementssArray().get(i).getAmount());
//                    sum = sum + totalCals;
//                }

        }
    }

    private void showMealDet() {
        elements e1 = new elements("", "بروتين");
        elements e2 = new elements("", "دهون");
        elements e3 = new elements("", "كربوهيدرات");

//        elements ele = new elements();
//        list.add(ele);
        eleList = new ArrayList<>();


        eleList = populateList();
        eleList.add(e1);
        eleList.add(e2);
        eleList.add(e3);
        newElementAdapter = new NewElementAdapter(eleList, this);
        recyclerViewDet.setAdapter(newElementAdapter);
        recyclerViewDet.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));


    }

    private ArrayList<elements> populateList() {


        addNewTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elements ele = new elements();
                listEle.add(ele);
                newElementAdapter.notifyDataSetChanged();
            }
        });
        return listEle;

    }

    private void showMealIngs() {
        ingredients ing = new ingredients();
        list.add(ing);


        ingrList = populateListIngs();
        newIngredientAdapter = new NewIngredientAdapter(ingrList, this);
        recyclerviewIngs.setAdapter(newIngredientAdapter);
        recyclerviewIngs.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
    }

    private ArrayList<ingredients> populateListIngs() {
        addNewIngBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredients ing = new ingredients();
                list.add(ing);
                newIngredientAdapter.notifyDataSetChanged();
            }
        });
        return list;
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

        // c = totalCal;


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
