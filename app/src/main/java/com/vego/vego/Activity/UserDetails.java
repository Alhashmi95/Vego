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
import com.vego.vego.model.MonthExercise;
import com.vego.vego.model.MonthMeal;
import com.vego.vego.model.elements;
import com.vego.vego.model.exercise;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.UserInfo;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.meal;
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
    ArrayList<exercise> exerList = new ArrayList<>();





    ingredients[] ingredients = new ingredients[] {new ingredients("549","Ma39oob")};
    ingredients[] ingredients2 = new ingredients[] {new ingredients("","")};
    ingredients[] ingredients3 = new ingredients[] {new ingredients("493","sinapon")};

    List mealIngrList = new ArrayList<ingredients>(Arrays.asList(ingredients));
    List mealIngrList2 = new ArrayList<ingredients>(Arrays.asList(ingredients2));
    List mealIngrList3 = new ArrayList<ingredients>(Arrays.asList(ingredients3));


    elements[] elements = new elements[] {new elements("","")};

    List mealElementList = new ArrayList<elements>(Arrays.asList(elements));


            DayMeals[] dayMealsDay1 = new DayMeals[] {new DayMeals("","",
                    "", (ArrayList<ingredients>) mealIngrList2, (ArrayList<elements>) mealElementList),//add get(0)
            };

    List dayMealsD1 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay1));

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

//    DayMeals[] dayMealsDay2 = new DayMeals[] {new DayMeals("meal1day2","22",""
//    ,(ArrayList<ingredients>) mealIngrList3, (ArrayList<elements>) mealElementList)
//
//    };
//
//    List dayMealsD2 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay2));
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////
//    DayMeals[] dayMealsDay3 = new DayMeals[] {new DayMeals("fds","meal1day3","",
//            (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList),
//            new DayMeals("meal2day3","333","",(ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList)
//    };
//
//    List dayMealsD3 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay3));
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////
//    DayMeals[] dayMealsDay4 = new DayMeals[] {new DayMeals("meal1day4","9328","",
//            (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList),
//            new DayMeals("fdskjdfs","928","",(ArrayList<ingredients>) mealIngrList2, (ArrayList<elements>) mealElementList)
//
//    };
//
//    List dayMealsD4 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay4));
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    DayMeals[] dayMealsDay5 = new DayMeals[] {new DayMeals("fds","9328",""
//            ,(ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList),
//            new DayMeals("fdskjdfs","928","",
//                    (ArrayList<ingredients>) mealIngrList3, (ArrayList<elements>) mealElementList)
//
//    };
//
//    List dayMealsD5 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay5));
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////
//    DayMeals[] dayMealsDay6 = new DayMeals[] {new DayMeals("fds","9328","",
//            (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList)
//
//    };
//
//    List dayMealsD6 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay6));
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    DayMeals[] dayMealsDay7 = new DayMeals[] {new DayMeals("fds","9328","",
//            (ArrayList<ingredients>) mealIngrList, (ArrayList<elements>) mealElementList),
//            new DayMeals("fdskjdfs","928","",(ArrayList<ingredients>) mealIngrList,
//                    (ArrayList<elements>) mealElementList)
//    };
//
//    List dayMealsD7 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay7));
//
//    //////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//
////




    private EditText userNameTxt, userWeight, userAge, userHeight;
    private String name, age,weight, height, userActivity, userGoal;



    sets sTest = new sets("","","","");
    sets[] setsArray = new sets[] {new sets("","", "",""),
    };

    List setsList = new ArrayList<sets>(Arrays.asList(setsArray));


//            new exercise("1","389", (ArrayList<sets>) setsList,""),
//            new exercise("fkdlj","صدر", (ArrayList<sets>) setsList,""),
//            new exercise("klfjf","بطن", (ArrayList<sets>) setsList,""),
//            new exercise("fdjkh","راس", (ArrayList<sets>) setsList,""),

//    };
//
//    List exList = new ArrayList<exercise>(Arrays.asList(exArray));
//
//
//
    ArrayList dietList ;
Exercises[] workoutDay;
    List exBigList;
    List dietBigList;
//
//    ArrayList workoutList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

            DietDay[] dietDay = new DietDay[] {new DietDay("","", "",(ArrayList<com.vego.vego.model.meal>) dayMealsD1),
                    new DietDay("","", "",(ArrayList<com.vego.vego.model.meal>) dayMealsD1),
                    new DietDay("","", "",(ArrayList<com.vego.vego.model.meal>) dayMealsD1),
                    new DietDay("","", "",(ArrayList<com.vego.vego.model.meal>) dayMealsD1),
                    new DietDay("","", "",(ArrayList<com.vego.vego.model.meal>) dayMealsD1),
                    new DietDay("","", "",(ArrayList<com.vego.vego.model.meal>) dayMealsD1),
                    new DietDay("","", "",(ArrayList<com.vego.vego.model.meal>) dayMealsD1),
    };
        dietBigList = new ArrayList<DietDay>(Arrays.asList(dietDay));



        exercise t = new exercise("","","");
        t.setSets((ArrayList<sets>) setsList);

        exerList.add(t);

        workoutDay = new Exercises[] {new Exercises("","", "",
                (ArrayList<exercise>) exerList),
                new Exercises("","", "", (ArrayList<exercise>) exerList),
                new Exercises("","", "", (ArrayList<exercise>) exerList),
                new Exercises("","", "", (ArrayList<exercise>) exerList),
                new Exercises("","", "", (ArrayList<exercise>) exerList),
                new Exercises("","", "", (ArrayList<exercise>) exerList),
                new Exercises("","", "", (ArrayList<exercise>) exerList),

        };

        exBigList = new ArrayList<Exercises>(Arrays.asList(workoutDay));


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
                "خسارة الدهون والمحافظة على العضلات",
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
                ,"الرجاء الانتظار لحين وضع برنامج حمية من قبل المدرب...",firebaseAuth.getCurrentUser().getEmail());

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
        hashMap.put("userEmail",userInfo.getUserEmail());


        HashMap<String,String> hashMap2=new HashMap<>();
      //  hashMap.put("img",dayMeals.getImg());



        DatabaseReference mealRef = firebaseDatabase.getReference();
        //mealRef.child("meals").setValue(dietList);

        //databasaeReference.child("users").child(firebaseAuth.getUid()).setValue(userInfo);
        databasaeReference.child("users").child(firebaseAuth.getUid()).child("Profile").setValue(hashMap);

        DietDay d = new DietDay("","","", (ArrayList<com.vego.vego.model.meal>) dayMealsD1);
        Exercises e = new Exercises();
        MonthExercise test = new MonthExercise((ArrayList<Exercises>) exBigList,(ArrayList<Exercises>)
                exBigList,(ArrayList<Exercises>)exBigList,(ArrayList<Exercises>)exBigList);
        ArrayList<MonthExercise> a = new ArrayList<>();
        a.add(test);


        MonthMeal meal = new MonthMeal((ArrayList<DietDay>)dietBigList,(ArrayList<DietDay>)dietBigList
                ,(ArrayList<DietDay>)dietBigList,(ArrayList<DietDay>)dietBigList);
        ArrayList<MonthMeal> b = new ArrayList<>();
        b.add(meal);

        databasaeReference.child("users").child(firebaseAuth.getUid()).child("Diet").setValue(b);

        databasaeReference.child("users").child(firebaseAuth.getUid()).child("Exercises").setValue(a);
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

        name = userNameTxt.getText().toString().trim();
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
