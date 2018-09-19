package com.vego.vego.Fragment;

import android.app.Activity;
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

import com.vego.vego.R;
import com.vego.vego.model.ingredients;

import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;

import com.vego.vego.Adapters.IngredientsAdapter;

public class FragmentMealIngr extends Fragment{
    View view;
   private IngredientsAdapter adapter;
    private RecyclerView recyclerView;
    ArrayList<ingredients> importedIngredientsArrayList;
    private TextView mealName;
    //step 3
    FragmentMealIngr.passMealImage listner;



    private RecyclerView recyclerView_mealIng;
    //private MealIngAdapterUser adapter;

    private FragmentManager fragmentManager;
    private List<ingredients> mealsIngrList;

    public FragmentMealIngr() {
    }
    //step 1 to copy array to activity
    public interface passMealImage {
        public void passMealImage(String mealImage);

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




        mealName = view.findViewById(R.id.showMealName);

//        imageViewMeal = view.findViewById(R.id.imgMeal);

        // هذا الكود لربط الكارد فيو
        recyclerView = view.findViewById(R.id.recyclerMealIngs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                // Set adapter (After we get the arrayList)
        Intent intent=this.getActivity().getIntent();
       // int position = intent.getIntExtra("position",0);
        ArrayList<ingredients> mealsList = (ArrayList<ingredients>) intent.getSerializableExtra("DayMeals");


        String imageURL = intent.getStringExtra("image");

        listner.passMealImage(imageURL);

//
//        Picasso.get()
//                .load(imageURL)
//                .fit()
//                .centerCrop()
//                .into(imageViewMeal);


        importedIngredientsArrayList = FragmentHome.mealsIngrList;
        //List<ingredients> ingrList
        adapter = new IngredientsAdapter(mealsList,getContext());
       recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //set meal name from meals adapter (mealsAdapter passes the name of meal)
        mealName.setText(intent.getStringExtra("name"));




    }

    @Override
    //step 2 ....
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        listner = (FragmentMealIngr.passMealImage) activity;
        try {

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must override passArrayListIng...");
        }
    }

    }



