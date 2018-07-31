package com.vego.vego.Fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vego.vego.Adapters.DaysAdapter;
import com.vego.vego.Adapters.MealIngAdapter;
import com.vego.vego.Adapters.MealsAdapter;
import com.vego.vego.Adapters.NewElementAdapter;
import com.vego.vego.Adapters.NewMealAdapter;
import com.vego.vego.R;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.ingredients;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vego.vego.Adapters.DaysAdapter;
import com.vego.vego.Adapters.IngredientsAdapter;
import com.vego.vego.Adapters.MealsAdapter;
import com.vego.vego.Adapters.NewElementAdapter;
import com.vego.vego.R;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.meal;

import java.util.ArrayList;
import java.util.List;

public class FragmentMealIngr extends Fragment{
    View view;
   private IngredientsAdapter adapter;
    private RecyclerView recyclerView;
    ArrayList<ingredients> importedIngredientsArrayList;
    private TextView mealName;



    private RecyclerView recyclerView_mealIng;
    //private MealIngAdapter adapter;

    private FragmentManager fragmentManager;
    private List<ingredients> mealsIngrList;

    public FragmentMealIngr() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_meal_ing, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mealsIngrList = new ArrayList<>();
        // للتجربة اضافة في مكونات الوجبة اذا زبطت نضيفها من الفاير بيس
//        ingredients n = new ingredients ("5","دجاج");
//        ingredients n2 = new ingredients ("7","لحم");
//        mealsIngrList.add(n);
//        mealsIngrList.add(n2);




        // هذا الكود لربط الكارد فيو
//        recyclerView_mealIng = view.findViewById(R.id.recyclerMealIngs);
//        recyclerView_mealIng.setHasFixedSize(true);
//        recyclerView_mealIng.setLayoutManager(new LinearLayoutManager(getContext()));

        //Set adapter
//        adapter = new MealIngAdapter(mealsIngrList,getContext());
//        recyclerView_mealIng.setAdapter(adapter);
//        adapter.notifyDataSetChanged();



        mealName = view.findViewById(R.id.showMealName);

        // هذا الكود لربط الكارد فيو
        recyclerView = view.findViewById(R.id.recyclerMealDetailes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                // Set adapter (After we get the arrayList)
        Intent intent=this.getActivity().getIntent();
       // int position = intent.getIntExtra("position",0);
        ArrayList<ingredients> mealsList = (ArrayList<ingredients>) intent.getSerializableExtra("DayMeals");


        importedIngredientsArrayList = FragmentHome.mealsIngrList;
        //List<ingredients> ingrList
        adapter = new IngredientsAdapter(mealsList,getContext());
       recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //set meal name from meals adapter (mealsAdapter passes the name of meal)
        mealName.setText(intent.getStringExtra("name"));
    }

    }



