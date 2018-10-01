package com.vego.vego.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vego.vego.Adapters.DaysAdapter;
import com.vego.vego.Adapters.IngredientsAdapter;
import com.vego.vego.Adapters.toolbarAdapter;
import com.vego.vego.Adapters.toolbarAdapterWeek;
import com.vego.vego.R;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.meal;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {
    private RecyclerView recyclerView;
    private DaysAdapter adapter;
    private List<DietDay> dayList;
    private FragmentManager fragmentManager;
    private ArrayList<meal> mealsList;
    public static ArrayList<ingredients> mealsIngrList;

    //============================================================
    int position =0;
    int positionWeek =0;

    String chosenMonth= "Month 1", chosenWeek = "Week 1";

    TabLayout tabLayout, tabLayoutWeek;

    toolbarAdapter toolbarAdapter;
    com.vego.vego.Adapters.toolbarAdapterWeek toolbarAdapterWeek;

    Button addMonth, removeMonth;

    ViewPager viewPager;

    int counterMonth =0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentHome() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home2, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //adding toolbar
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

        getMealsForUser();




        // هذا الكود لربط الكارد فيو
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        //Generate sample data


//        for (int i = 0; i < 7; i++) {
//            dayList.add(new DietDay("Item " + (i + 1), "Welcome to Torisan channel, this is description of item " + (i+1)
//             ,"عدد الوجبات "+3
//            ) );
//        }





    }

    private void getMonthTabs() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference monthCountRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Diet");

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

    private void getMealsForUser() {
        //delcare firebase auth and database reference to retreive data
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        //go to the child where you want to retreive values of
        DatabaseReference usersRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Diet")
                .child(chosenMonth).child(chosenWeek);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dayList = new ArrayList<DietDay>();
                // to identify which child should we pick in dayMeals
                int i=0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    dayList.add(ds.getValue(DietDay.class));
                    mealsList = new ArrayList<meal>();
                    int j=0;

                    for(DataSnapshot ds2 : ds.child("dayMeals").getChildren()) {
                        mealsList.add(ds2.getValue(meal.class));

                    }
                    dayList.get(i).setDayMeals(mealsList);

                    i++;
                }

                //Set adapter
                adapter = new DaysAdapter(dayList,getContext(), fragmentManager);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersRef.addListenerForSingleValueEvent(eventListener);
    }

    private void tabListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tabLayout.getSelectedTabPosition();
                selectMonth();
                getMealsForUser();


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
                getMealsForUser();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
