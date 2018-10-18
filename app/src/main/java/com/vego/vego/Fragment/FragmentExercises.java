package com.vego.vego.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vego.vego.Activity.BottomNav;
import com.vego.vego.Adapters.DaysAdapter;
import com.vego.vego.Adapters.DaysAdapterExercise;
import com.vego.vego.Adapters.toolbarAdapter;
import com.vego.vego.R;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.Exercises;
import com.vego.vego.model.MonthExercise;
import com.vego.vego.model.elements;
import com.vego.vego.model.meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FragmentExercises extends Fragment {
    private RecyclerView recyclerView;
    private DaysAdapterExercise adapter;
    private ArrayList<Exercises> dayExercises = new ArrayList<Exercises>();
    private FragmentManager fragmentManager;

    //==============================
    //============================================================
    int position =0;
    int positionWeek =0;

    String chosenMonth= "Month 1", chosenWeek = "Week 1";

    TabLayout tabLayout, tabLayoutWeek;

    Button addMonth, removeMonth;

    ViewPager viewPager;

    int counterMonth =0;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercises, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //add tabLayout
        tabLayout = view.findViewById(R.id.tablayout);

        tabLayoutWeek = view.findViewById(R.id.tablayoutWeek);

        viewPager = view.findViewById(R.id.viewpager);

        addMonth = view.findViewById(R.id.btn_addMonth);

        removeMonth = view.findViewById(R.id.btn_removeMonth);


//        toolbarAdapter = new toolbarAdapter(getContext());




        tabLayout.addTab(tabLayout.newTab().setText("month 1"));



        tabLayoutWeek.addTab(tabLayoutWeek.newTab().setText("week 1"));

        tabLayoutWeek.addTab(tabLayoutWeek.newTab().setText("week 2"));

        tabLayoutWeek.addTab(tabLayoutWeek.newTab().setText("week 3"));

        tabLayoutWeek.addTab(tabLayoutWeek.newTab().setText("week 4"));

        getMonthTabs();
        tabListener();
        selectMonth();


        //==============================================================

        getExercisesForUser();



        recyclerView = view.findViewById(R.id.recyclerViewEx);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        //move data to bottomNav Activity



    }

    private void getExercisesForUser() {
        dayExercises = new ArrayList<>();
        //delcare firebase auth and database reference to retreive data
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        //go to the child where you want to retreive values of
        DatabaseReference usersRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Exercises")
                .child(chosenMonth).child(chosenWeek);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    dayExercises.add(ds.getValue(Exercises.class));

                }
//                MonthExercise test = new MonthExercise("1", dayExercises);
//                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//                DatabaseReference monthCountRef = rootRef.child("Ayman");
//
//                monthCountRef.setValue(test);


                //Set adapter
                adapter = new DaysAdapterExercise(dayExercises,getContext(), fragmentManager,chosenMonth,chosenWeek);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);

    }

    private void tabListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tabLayout.getSelectedTabPosition();
                selectMonth();
                getExercisesForUser();


//                if(tabLayout.getSelectedTabPosition() != 0) {
//                    final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//                    alert.setMessage("ستفقد جميع البيانات .. الرجاء التأكد من الضغط على زر (حفظ الوجبات) قبل المتابعة");
//                    alert.setTitle("تنبيه");
//
//
//                    alert.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//
//                        }
//
//
//                    });
//                    alert.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            int tabIndex = tabLayout.getSelectedTabPosition() - 1;
//
//                          //  tabLayout.getTabAt(tabIndex);
//
//                            viewPager.setCurrentItem(tabIndex);
//
//                            toolbarAdapter.notifyDataSetChanged();
//
//                        }
//                    });
//
//                    alert.show();
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //================================================ tab weeks
        tabLayoutWeek.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                positionWeek = tabLayoutWeek.getSelectedTabPosition();
                selectMonth();
                getExercisesForUser();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void getMonthTabs() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference monthCountRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Exercises");

        monthCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                counterMonth = (int) dataSnapshot.getChildrenCount();

                for(int i = 1; i< counterMonth; i++){
                    tabLayout.addTab(tabLayout.newTab().setText("month "+ String.valueOf(i+1)));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void selectMonth() {


        if(position == 0) {
            chosenMonth = "Month 1";
            if (positionWeek == 0) {
                chosenWeek = "Week 1";
            }
            if (positionWeek == 1) {
                chosenWeek = "Week 2";
            }
            if (positionWeek == 2) {
                chosenWeek = "Week 3";
            }
            if (positionWeek == 3) {
                chosenWeek = "Week 4";
            }
        }
        if (position == 1) {
            chosenMonth = "Month 2";
            if (positionWeek == 0) {
                chosenWeek = "Week 1";
            }
            if (positionWeek == 1) {
                chosenWeek = "Week 2";
            }
            if (positionWeek == 2) {
                chosenWeek = "Week 3";
            }
            if (positionWeek == 3) {
                chosenWeek = "Week 4";

            }
        }
        if (position == 2) {
            chosenMonth = "Month 3";
            if (positionWeek == 0) {
                chosenWeek = "Week 1";
            }
            if (positionWeek == 1) {
                chosenWeek = "Week 2";

            }
            if (positionWeek == 2) {
                chosenWeek = "Week 3";

            }
            if (positionWeek == 3) {
                chosenWeek = "Week 4";
            }
        }
    }
}
