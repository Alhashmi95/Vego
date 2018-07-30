package com.vego.vego.Fragment;

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

import com.vego.vego.Adapters.ElementsAdapter;
import com.vego.vego.Adapters.IngredientsAdapter;
import com.vego.vego.R;
import com.vego.vego.model.elements;
import com.vego.vego.model.ingredients;

import java.util.ArrayList;

public class FragmentMealsDetails extends Fragment {
    View view;
    private ElementsAdapter adapter;
    private RecyclerView recyclerView;


    public FragmentMealsDetails() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meals_details, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // هذا الكود لربط الكارد فيو
        recyclerView = view.findViewById(R.id.recyclerMealDetailesElements);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Set adapter (After we get the arrayList)
        Intent intent=this.getActivity().getIntent();
        // int position = intent.getIntExtra("position",0);
        ArrayList<elements> mealsListElements = (ArrayList<elements>) intent.getSerializableExtra("DayMealsElements");


        // importedIngredientsArrayList = FragmentHome.mealsIngrList;
        //List<ingredients> ingrList
        adapter = new ElementsAdapter(mealsListElements,getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
