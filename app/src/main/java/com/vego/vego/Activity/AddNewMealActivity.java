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
        , FragmentAddMealIng.passArrayListEle {

    AppBarLayout appBarLayout;
    TabLayout tableLayout;
    ViewPager viewPager;
    ArrayList<ingredients> importedIngredientsArrayList = new ArrayList<>();
    ArrayList<meal> mealArrayList = new ArrayList<>();
    ArrayList<elements> importedElementsArrayList = new ArrayList<>();
    String n, c;
    Button saveMeal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meal);

        //connect views to layout
        appBarLayout = findViewById(R.id.appbarid);
        tableLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        saveMeal = findViewById(R.id.saveMealBtn);


        //add Fragments
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentAddMealDetailes(), "Meal Details");
        adapter.addFragment(new FragmentAddMealIng(), "Meal Ingredients");


        //set adapter
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);


        saveMeal.setEnabled(false);


        //   mealArrayListTest = populateList();

        saveMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meal m = new meal();

                if (!importedIngredientsArrayList.isEmpty() && !importedElementsArrayList.isEmpty()) {

//                    HashMap<String,ArrayList> hashMap=new HashMap<>();
//                    hashMap.put("age",importedElementsArrayList);
//                    hashMap.put("weight",importedIngredientsArrayList);
                    //   meal m = new meal();

                    ArrayList test1=importedIngredientsArrayList;
                    ArrayList test2=importedElementsArrayList;
                    m.setCal(c);
                    m.setName(n);
                    m.setingredients(test1);
                    m.setElement(test2);
                    //                  mealArrayListTest.add(new meal(c, n, importedElementsArrayList,
                    //                        importedIngredientsArrayList));.
                    mealArrayList.add(m);






                    for (int i =0;i<mealArrayList.size();i++){
                        for (int j=0;j<mealArrayList.get(i).getElement().size();j++){
                            Log.d("test","d7om   "+mealArrayList.get(i).getElement().get(j).getAmount());
                            Log.d("test","d7om   "+mealArrayList.get(i).getElement().get(j).getName());
                        }

                        for (int j=0;j<mealArrayList.get(i).getingredients().size();j++){
                            Log.d("test","d7om   "+mealArrayList.get(i).getingredients().get(j).getQuantity());
                            Log.d("test","d7om   "+mealArrayList.get(i).getingredients().get(j).getType());

                        }

                    }

                    Log.d("test", "this is imported eleList" + importedElementsArrayList);
                    Log.d("test", "this is imported eleList" + importedIngredientsArrayList);

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference d = firebaseDatabase.getReference();

                    d.child("meals").setValue(mealArrayList);
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
        importedIngredientsArrayList=new ArrayList<>();
        importedIngredientsArrayList.addAll(ing);

        for (int j = 0; j < importedIngredientsArrayList.size(); j++) {
            Log.d("test", "d7om   " +importedIngredientsArrayList.get(j).getType());
            Log.d("test", "d7om   " +importedIngredientsArrayList.get(j).getQuantity());
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