package com.vego.vego.Activity;

import android.content.Intent;
import android.os.Parcelable;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddNewMealActivity extends AppCompatActivity implements FragmentAddMealDetailes.passArrayListIng
        ,FragmentAddMealIng.passArrayListEle {

    AppBarLayout appBarLayout;
    TabLayout tableLayout;
    ViewPager viewPager;
    ArrayList<ingredients> importedIngredientsArrayList;
    ArrayList<meal> mealArrayList = new ArrayList<>(), mealArrayListTest;
    ArrayList<elements> importedElementsArrayList;
    String n, c;
    Button saveMeal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_ingr);

        //connect views to layout
        appBarLayout = findViewById(R.id.appbarid);
        tableLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        saveMeal = findViewById(R.id.saveMealBtn);







        //add Fragments
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentAddMealDetailes(),"Meal Details");
        adapter.addFragment(new FragmentAddMealIng(), "Meal Ingredients");



        //set adapter
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);


        saveMeal.setEnabled(false);


        mealArrayListTest = populateList();




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
        importedIngredientsArrayList = ing;

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
        importedElementsArrayList = ele;

        c = totalCal;





        if(!importedIngredientsArrayList.isEmpty() && !importedElementsArrayList.isEmpty()){
            saveMeal.setEnabled(true);

            saveMeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    HashMap<String,ArrayList> hashMap=new HashMap<>();
//                    hashMap.put("age",importedElementsArrayList);
//                    hashMap.put("weight",importedIngredientsArrayList);
                    meal m = new meal();
                    m.setCal(c);
                    m.setName(n);
                    m.setingredients(importedIngredientsArrayList);
                    m.setElement(importedElementsArrayList);
//                    mealArrayListTest.add(new meal(c, n, importedElementsArrayList,
//                            importedIngredientsArrayList));
                    mealArrayListTest.add(m);

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference d = firebaseDatabase.getReference();

//                    d.child("meals").setValue(mealArrayListTest);
//                    d.child("meals").child("element").setValue(hashMap);
                }
            });
        }

        Log.d("test","this is imported eleList"+ ele.get(0).getName());
        Log.d("test","this is imported eleList"+ ele.get(0).getAmount());


        //uploadMeal();
    }
    public void uploadMeal() {
//        meal mealTest1 = new meal("3490", "kofta", importedElementsArrayList,
//                importedIngredientsArrayList);
//        mealArrayList.add(mealTest1);





        Log.d("test","this is mealArrayList"+mealArrayList.size());
//        for (int i = 0; i < mealArrayList.size(); i++) {
//            mealTest = new meal(c, n, importedElementsArrayList,
//                    importedIngredientsArrayList);
//
//
//        }
        //mealArrayList.add(mealTest);

    }
}
