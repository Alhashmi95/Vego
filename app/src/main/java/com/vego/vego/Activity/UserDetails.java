package com.vego.vego.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.vego.vego.R;
import com.vego.vego.model.Exercises;
import com.vego.vego.model.elements;
import com.vego.vego.model.exercise;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.UserInfo;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class UserDetails extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    // define spinner
    Spinner spActivity ;
    Spinner spGoal ;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    DayMeals meal;




    ingredients[] ingredients = new ingredients[] {new ingredients("549","Ma39oob")};
    ingredients[] ingredients2 = new ingredients[] {new ingredients("439","cheickn")};
    ingredients[] ingredients3 = new ingredients[] {new ingredients("493","sinapon")};

    List mealIngrList = new ArrayList<ingredients>(Arrays.asList(ingredients));
    List mealIngrList2 = new ArrayList<ingredients>(Arrays.asList(ingredients2));
    List mealIngrList3 = new ArrayList<ingredients>(Arrays.asList(ingredients3));


    elements[] elements = new elements[] {new elements("cals","439"),
            new elements("carbo","50")};

    List mealElementList = new ArrayList<elements>(Arrays.asList(elements));


            DayMeals[] dayMealsDay1 = new DayMeals[] {new DayMeals("meal1day1","1",
                    R.drawable.setting, (ArrayList<ingredients>) mealIngrList2, (ArrayList<elements>) mealElementList), //add get(0)
            new DayMeals("meal2day1","2",R.drawable.profile, (ArrayList<ingredients>) mealIngrList,
                    (ArrayList<elements>) mealElementList)};

    List dayMealsD1 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay1));

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    DayMeals[] dayMealsDay2 = new DayMeals[] {new DayMeals("meal1day2","22",R.drawable.setting
    ,(ArrayList<ingredients>) mealIngrList3, (ArrayList<elements>) mealElementList)

    };

    List dayMealsD2 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay2));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    DayMeals[] dayMealsDay3 = new DayMeals[] {new DayMeals("fds","meal1day3",R.drawable.setting,
            (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList),
            new DayMeals("meal2day3","333",R.drawable.home_w,(ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList)
    };

    List dayMealsD3 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay3));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    DayMeals[] dayMealsDay4 = new DayMeals[] {new DayMeals("meal1day4","9328",R.drawable.setting,
            (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList),
            new DayMeals("fdskjdfs","928",R.drawable.home_w,(ArrayList<ingredients>) mealIngrList2, (ArrayList<elements>) mealElementList)

    };

    List dayMealsD4 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay4));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    DayMeals[] dayMealsDay5 = new DayMeals[] {new DayMeals("fds","9328",R.drawable.setting
            ,(ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList),
            new DayMeals("fdskjdfs","928",R.drawable.home_w,
                    (ArrayList<ingredients>) mealIngrList3, (ArrayList<elements>) mealElementList)

    };

    List dayMealsD5 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay5));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    DayMeals[] dayMealsDay6 = new DayMeals[] {new DayMeals("fds","9328",R.drawable.setting,
            (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList)

    };

    List dayMealsD6 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay6));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    DayMeals[] dayMealsDay7 = new DayMeals[] {new DayMeals("fds","9328",R.drawable.setting,
            (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList),
            new DayMeals("fdskjdfs","928",R.drawable.home_w,(ArrayList<ingredients>) mealIngrList,
                    (ArrayList<elements>) mealElementList)
    };

    List dayMealsD7 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay7));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////



//




    private EditText userNameTxt, userWeight, userAge, userHeight;
    private String name, age,weight, height, userActivity, userGoal;

//    DietDay[] dietDay = new DietDay[] {new DietDay("1","389", "4",(ArrayList<DayMeals>) dayMealsD1),
//            new DietDay("2","23","3",(ArrayList<DayMeals>) dayMealsD2),
//            new DietDay("3","12334", "242",(ArrayList<DayMeals>) dayMealsD3),
//            new DietDay("4","2443","34",(ArrayList<DayMeals>) dayMealsD4),
//            new DietDay("5","253948","31",(ArrayList<DayMeals>) dayMealsD5),
//            new DietDay("6","27","33",(ArrayList<DayMeals>) dayMealsD6),
//            new DietDay("7","287","34",(ArrayList<DayMeals>) dayMealsD7),
//    };

    sets sTest = new sets(" "," "," "," ");
    sets[] setsArray = new sets[] {new sets("1","389", "4"," "),
            new sets("1","389", "4",""),
            new sets("1","389", "4",""),
            new sets("1","389", "4",""),
            new sets("1","389", "4",""),
            new sets("1","389", "4",""),
            new sets("1","389", "4",""),
    };

    List setsList = new ArrayList<sets>(Arrays.asList(setsArray));

//    exercise[] exArray = new exercise[] {new exercise("1","ظهر", (ArrayList<sets>) setsList,""),
//            new exercise("1","389", (ArrayList<sets>) setsList,""),
//            new exercise("fkdlj","صدر", (ArrayList<sets>) setsList,""),
//            new exercise("klfjf","بطن", (ArrayList<sets>) setsList,""),
//            new exercise("fdjkh","راس", (ArrayList<sets>) setsList,""),
//
//    };
//
//    List exList = new ArrayList<exercise>(Arrays.asList(exArray));




//    Exercises[] workoutDay = new Exercises[] {new Exercises("1","389", "4",
//            (ArrayList<exercise>) exList),
//            new Exercises("2","389", "4", (ArrayList<exercise>) exList),
//            new Exercises("3","389", "4", (ArrayList<exercise>) exList),
//            new Exercises("4","389", "4", (ArrayList<exercise>) exList),
//            new Exercises("5","389", "4", (ArrayList<exercise>) exList),
//            new Exercises("6","389", "4", (ArrayList<exercise>) exList),
//            new Exercises("7","389", "4", (ArrayList<exercise>) exList),
//
//    };
//
//    List exBigList = new ArrayList<Exercises>(Arrays.asList(workoutDay));


    ArrayList dietList ;

    ArrayList workoutList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        //dietList= new ArrayList<DietDay>(Arrays.asList(dietDay));
       // workoutList= new ArrayList<Exercises>(Arrays.asList(workoutDay));

        setupUIViews();

        ///////////////////////////////////////////////////////////////////////////////////





//
        // Get reference of widgets from XML layout
        final Spinner spActivity = (Spinner) findViewById(R.id.spinner2);
        final Spinner spGoal = (Spinner) findViewById(R.id.spinner3);


        // Initializing a String Array
        String[] activity = new String[]{
                "مستوى النشاط",
                "مرتفع",
                "متوسط",
                "منخفض",
                "خامل"
        };
        String[] goal = new String[]{
                "هدفك من التطبيق",
                "زيادة الوزن",
                "انقاص الوزن",
                "زيادة عضلات",
                "المحافظة على الوزن الحالي"
        };

        //For first spinner +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        final List<String> plantsList = new ArrayList<>(Arrays.asList(activity));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spActivity.setAdapter(spinnerArrayAdapter);

        spActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                userActivity = selectedItemText;
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
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
                this,R.layout.support_simple_spinner_dropdown_item,goalList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spGoal.setAdapter(spinnerArrayAdapter2);

        spGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                userGoal= selectedItemText;
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //End spinner




        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, HomeActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void setupUIViews() {
        userNameTxt = (EditText) findViewById(R.id.userNameTxt);
        userAge = (EditText) findViewById(R.id.ageTxt);
        userWeight = (EditText) findViewById(R.id.weightTxt);
        userHeight = (EditText) findViewById(R.id.heightTxt);
        spActivity = (Spinner)findViewById(R.id.spinner2);
        spGoal = (Spinner)findViewById(R.id.spinner3);

    }
    private void uploadUserData(){
//        dietDay[0].getDayMeals().get(1).getIngredients().add(new ingredients("3","food","3333","33","444"
//                ,"34"));

        //name of variables(age, weight ..etc) MUST MATCH the names in FIREBASE IDDDIIIOOOOTTT
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databasaeReference = firebaseDatabase.getReference();
        UserInfo userInfo = new UserInfo(name, weight, height, age, userActivity, userGoal,
                (ArrayList<DietDay>) dietList,"false",firebaseAuth.getUid()," "
                ,"wait for admin...",firebaseAuth.getCurrentUser().getEmail());

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("age",userInfo.age);
        hashMap.put("height",userInfo.height);
        hashMap.put("name",userInfo.name);
        hashMap.put("weight",userInfo.weight);
        hashMap.put("userActivity", userActivity);
        hashMap.put("userGoal", userGoal);
        hashMap.put("isAdmin", String.valueOf(false));
        hashMap.put("uid",userInfo.getUid());
        hashMap.put("previousWeight",userInfo.getPreviousWeight());
        hashMap.put("adminBrief",userInfo.getAdminBrief());
        hashMap.put("userEmail",userInfo.getEmail());


        HashMap<String,String> hashMap2=new HashMap<>();
      //  hashMap.put("img",dayMeals.getImg());



        DatabaseReference mealRef = firebaseDatabase.getReference();
        //mealRef.child("meals").setValue(dietList);

        //databasaeReference.child("users").child(firebaseAuth.getUid()).setValue(userInfo);
        databasaeReference.child("users").child(firebaseAuth.getUid()).child("Profile").setValue(hashMap);
     //   databasaeReference.child("users").child(firebaseAuth.getUid()).child("Exercises").setValue(exBigList);
      //  databasaeReference.child("users").child(firebaseAuth.getUid()).child("Diet").child("0").child("dayMeals").setValue(hashMap2);



    }
    private Boolean validate(){
        Boolean result = false;
//
//        userNameTxt.setText("fjl");
//        userHeight.setText("38");
//        userAge.setText("489");
//        userWeight.setText("49");
//
//        spActivity.setSelection(2);
//        spGoal.setSelection(3);

        name = userNameTxt.getText().toString();
        height = userHeight.getText().toString();
        age = userAge.getText().toString();
        weight = userWeight.getText().toString();



        if(name.isEmpty() || height.isEmpty() || age.isEmpty() || weight.isEmpty()
                || userActivity.equals("مستوى النشاط") || userGoal.equals("هدفك من التطبيق")){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }

    public void registerBtn(View view) {
        if(validate()){
            //upload data to firebase
            uploadUserData();
            Toast.makeText(this, "Data Registered successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, BottomNav.class));
        }
    }

}
