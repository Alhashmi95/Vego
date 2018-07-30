package com.vego.vego.Fragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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



    public FragmentMealIngr() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_day_meals, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
