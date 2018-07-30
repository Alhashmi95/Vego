package com.vego.vego.Fragment;

<<<<<<< HEAD
import android.app.FragmentManager;
=======
import android.content.Intent;
>>>>>>> 660004a7933c931083ff5bfd97e4ed044b272405
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

<<<<<<< HEAD
import com.vego.vego.Adapters.MealDetailesAdapter;
import com.vego.vego.Adapters.MealIngAdapter;
=======
import com.vego.vego.Adapters.ElementsAdapter;
import com.vego.vego.Adapters.IngredientsAdapter;
>>>>>>> 660004a7933c931083ff5bfd97e4ed044b272405
import com.vego.vego.R;
import com.vego.vego.model.elements;
import com.vego.vego.model.ingredients;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 660004a7933c931083ff5bfd97e4ed044b272405

public class FragmentMealsDetails extends Fragment {
    View view;
    private ElementsAdapter adapter;
    private RecyclerView recyclerView;

    private RecyclerView recyclerView_mealDetailes;
    private MealDetailesAdapter adapter;

    private FragmentManager fragmentManager;
    private List<elements> mealElementList;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealElementList = new ArrayList<>();
        // للتجربة اضافة في مكونات الوجبة اذا زبطت نضيفها من الفاير بيس
        elements n = new elements ("1000","مجموع السعرات");
        elements n2 = new elements ("500","بروتين");
        mealElementList.add(n);
        mealElementList.add(n2);




        // هذا الكود لربط الكارد فيو
        recyclerView_mealDetailes = view.findViewById(R.id.recyclerMealDetailes);
        recyclerView_mealDetailes.setHasFixedSize(true);
        recyclerView_mealDetailes.setLayoutManager(new LinearLayoutManager(getContext()));

        //Set adapter
        adapter = new MealDetailesAdapter(mealElementList,getContext());
        recyclerView_mealDetailes.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}
