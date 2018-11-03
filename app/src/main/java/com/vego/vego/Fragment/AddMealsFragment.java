package com.vego.vego.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vego.vego.Activity.AddNewMealActivity;
import com.vego.vego.Adapters.DaysAdapter;
import com.vego.vego.Adapters.ElementsAdapter;
import com.vego.vego.Adapters.IngredientsAdapter;
import com.vego.vego.Adapters.toolbarAdapter;
import com.vego.vego.Adapters.toolbarAdapterWeek;
import com.vego.vego.R;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.MonthMeal;
import com.vego.vego.model.UserInfo;
import com.vego.vego.model.elements;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.meal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


/**j
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddMealsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddMealsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMealsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    ArrayList<String> arrayList;
    List<String> list;
    String choosenUser, choosenDay, choosenMeal;
    Spinner spSelectUser, spSelectDay, spMealsCount, spSelectMealName;
    ArrayAdapter<String> spinnerArrayAdapter, spinnerArrayAdapter2, spinnerArrayAdapterMealCount, spinnerArrayAdapter4;
    UserInfo userinfo;
    DatabaseReference usersprofile;
    TextView profileName;
    TextView profileAge;
    TextView profileWeight;
    TextView profileHeight;
    TextView profileActivity;
    TextView profileGoal;
    ArrayList<String> arrayListMeals = new ArrayList<>();
    ArrayList<meal> arrayListMealsObject = new ArrayList<>();
    DatabaseReference databaseReferenceAddMeal;
    Button addMealForUser, addNewMeal;
    ArrayList m = new ArrayList();
    ArrayList m2 = new ArrayList();
    ArrayList m3 = new ArrayList();
    ArrayList m4 = new ArrayList();
    ArrayList m5 = new ArrayList();
    meal mTest, mTest2, mTest3, mTest4, mTest5;
    FirebaseDatabase firebaseDatabaseMeal = FirebaseDatabase.getInstance();
    String mealNo1, mealNo2, mealNo3, mealNo4, mealNo5;
    ArrayList<String> arrayList2;

    ProgressDialog p;

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    ArrayList<String> fragmentArrayListTitles = new ArrayList<>();

    int position = 0;
    int positionWeek = 0;

    DietFragmentAdmin DietFragment;

    DatabaseReference thirdMonthRef, secondMonthRef, firstMonthRef;

    DatabaseReference firstMonthWeek1, firstMonthWeek2, firstMonthWeek3, firstMonthWeek4;
    DatabaseReference secondMonthWeek1, secondMonthWeek2, secondMonthWeek3, secondMonthWeek4;
    DatabaseReference thirdMonthWeek1, thirdMonthWeek2, thirdMonthWeek3, thirdMonthWeek4;

    String chosenMonth = "0", chosenWeek = "week1";


    TabLayout tabLayout, tabLayoutWeek;

    toolbarAdapter toolbarAdapter;
    com.vego.vego.Adapters.toolbarAdapterWeek toolbarAdapterWeek;

    Button addMonth, removeMonth;

    ViewPager viewPager;

    int counterMonth = 2;

    ArrayList me = new ArrayList();
    int mm = 1;
    int meCount = 0;

    String chosenMeNumber;

    TextView cals, mealName;
    ImageView mealImg;

    ProgressBar progressBar;

    String getChoosenMeNumberIndex;

    String totalCals;


    ArrayList<meal> mealsList = new ArrayList<>();
    ArrayList<DietDay> dayMeals = new ArrayList<>();

    double calSumOfMeals = 0;

    int mealAll;

    private IngredientsAdapter adapterIngr;
    private ElementsAdapter adapterDet;

    DayMeals dayMealObject;

    List dietBigList;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentHome.OnFragmentInteractionListener mListener;
    private RecyclerView recyclerViewIngr, recyclerViewDet;
    private ArrayList<MonthMeal> monthMeals = new ArrayList<>();
    private int monthCountIndex = 0;

    public AddMealsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMealsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMealsFragment newInstance(String param1, String param2) {
        AddMealsFragment fragment = new AddMealsFragment();
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
        return inflater.inflate(R.layout.fragment_add_meals, container, false);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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


        addNewMonth();
        removeMonth();
        tabListener();


        //==============================================================


        profileName = view.findViewById(R.id.tvProfileName);
        profileAge = view.findViewById(R.id.tvProfileAge);
        profileWeight = view.findViewById(R.id.tvWeight);
        profileHeight = view.findViewById(R.id.tvProfileHeight);
        profileActivity = view.findViewById(R.id.tvprofileActivity);
        profileGoal = view.findViewById(R.id.tvprofileGoal);
        spSelectUser = view.findViewById(R.id.selectUser);

        spMealsCount = view.findViewById(R.id.selectMeal);
        spSelectMealName = view.findViewById(R.id.selectMealName);

        cals = view.findViewById(R.id.textViewShortDesc);
        mealName = view.findViewById(R.id.textViewTitle);
        mealImg = view.findViewById(R.id.imageView);

        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);


        recyclerViewIngr = view.findViewById(R.id.rv_mealIngr);
        recyclerViewDet = view.findViewById(R.id.rv_mealDet);

        // هذا الكود لربط الكارد فيو
        recyclerViewIngr.setHasFixedSize(true);
        recyclerViewIngr.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewDet.setHasFixedSize(true);
        recyclerViewDet.setLayoutManager(new LinearLayoutManager(getContext()));


        DietFragment = ((DietFragmentAdmin) AddMealsFragment.this.getParentFragment());


//        radioButtonTrue = view.findViewById(R.id.radio_true);
//        radioButtonFalse = view.findViewById(R.id.radio_false);


        addNewMeal = view.findViewById(R.id.addNewMeal);

        addMealForUser = view.findViewById(R.id.saveDayMeal);

        p = new ProgressDialog(getContext());
        p.setTitle("Loading");
        p.setMessage("Fetching data...");
        p.show();


        getUsers();

        selectedUser();

        selectedDay();

        mealNumber();

        getMeal();
//
        selectedMeal();

        childlistrner();


        //==========================Default 4 weeks meals ==============================
        ingredients[] ingredients2 = new ingredients[]{new ingredients("", "")};
        List mealIngrList2 = new ArrayList<ingredients>(Arrays.asList(ingredients2));

        elements[] elements = new elements[]{new elements("", "")};
        List mealElementList = new ArrayList<elements>(Arrays.asList(elements));

        DayMeals[] dayMealsDay1 = new DayMeals[]{new DayMeals("", "",
                "", (ArrayList<ingredients>) mealIngrList2, (ArrayList<elements>) mealElementList),//add get(0)
        };

        List dayMealsD1 = new ArrayList<DayMeals>(Arrays.asList(dayMealsDay1));


        DietDay[] dietDay = new DietDay[]{new DietDay("", "", "", (ArrayList<com.vego.vego.model.meal>) dayMealsD1),
                new DietDay("", "", "", (ArrayList<com.vego.vego.model.meal>) dayMealsD1),
                new DietDay("", "", "", (ArrayList<com.vego.vego.model.meal>) dayMealsD1),
                new DietDay("", "", "", (ArrayList<com.vego.vego.model.meal>) dayMealsD1),
                new DietDay("", "", "", (ArrayList<com.vego.vego.model.meal>) dayMealsD1),
                new DietDay("", "", "", (ArrayList<com.vego.vego.model.meal>) dayMealsD1),
                new DietDay("", "", "", (ArrayList<com.vego.vego.model.meal>) dayMealsD1),
        };
        dietBigList = new ArrayList<DietDay>(Arrays.asList(dietDay));


        // getTotalCal();


//        changeRole();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference uidRef = usersRef.child("ZU7wS37XJUU6oeueYlciKWxx5X23");
        DatabaseReference zone1Ref = uidRef.child("Diet");
        DatabaseReference zone1NameRef = zone1Ref.child("0");
        DatabaseReference zone2dayRef = zone1NameRef.child("day");

        zone1NameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("test", "this is day : " + dataSnapshot.child("day").toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });


        //String[] usersArr = arrayList.toArray(new String[0]);


        addNewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddNewMealActivity.class));


            }
        });


    }

    private void getMonthTabs() {
        for (int i = tabLayout.getTabCount(); i > 1; i--) {
            tabLayout.removeTabAt(i - 1);
        }
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference monthCountRef = rootRef.child("users").child(choosenUser).child("Diet");

        monthCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                counterMonth = (int) dataSnapshot.getChildrenCount();

                for (int i = 1; i < counterMonth; i++) {
                    tabLayout.addTab(tabLayout.newTab().setText("month " + String.valueOf(i + 1)));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void removeMonth() {
        removeMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabLayout.getTabCount() == 1) {
                    Toast.makeText(getContext(),
                            "يجب ان تحتوي على عنصر واحد على الاقل", Toast.LENGTH_SHORT).show();

                } else {
                    int index = tabLayout.getTabCount();
                    index--;

                    tabLayout.removeTabAt(index);

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databasaeReference = firebaseDatabase.getReference();
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                    databasaeReference.child("users").child(choosenUser).child("Diet")
                            .child(String.valueOf(index)).removeValue().addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    getMonthTabs();
                                }
                            }
                    );


                    counterMonth--;

                }
                getUsers();
            }
        });
    }

    private void addNewMonth() {
        counterMonth = tabLayout.getTabCount();
        addMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counterMonth <= 12) {
                    //tabLayout.addTab(tabLayout.newTab().setText("month " + String.valueOf(counterMonth)));


                    //add 4 weeks by defalut

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databasaeReference = firebaseDatabase.getReference();


                    DatabaseReference monthCount = databasaeReference.child("users").child(choosenUser)
                            .child("Diet");

                    monthCount.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            monthCountIndex = (int) dataSnapshot.getChildrenCount();

                            MonthMeal meal = new MonthMeal((ArrayList<DietDay>) dietBigList, (ArrayList<DietDay>) dietBigList
                                    , (ArrayList<DietDay>) dietBigList, (ArrayList<DietDay>) dietBigList);

                            databasaeReference.child("users").child(choosenUser)
                                    .child("Diet").child(String.valueOf(monthCountIndex)).setValue(meal)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            getMonthTabs();
                                        }
                                    });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                } else {
                    Toast.makeText(getContext(),
                            "لا يمكنك اضافة اكثر من 12 شهر", Toast.LENGTH_SHORT).show();
                }
                //databasaeReference.child("users").child(firebaseAuth.getUid()).child("Exercises").setValue(a);


                counterMonth++;
                getUsers();


            }
        });

    }

    private void tabListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tabLayout.getSelectedTabPosition();
                getMeCount();

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
                getMeCount();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public ArrayList<meal> getMeal() {
        arrayListMealsObject = new ArrayList<>();

        arrayListMeals.add(0, "اختر وجبة");
//        spinnerArrayAdapter2.notifyDataSetChanged();

        FirebaseDatabase f = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = f.getReference().child("meals");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Note ** : ondatachange discards the value of arraylist after it finishs

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (arrayListMealsObject.size() < mealAll)
                        arrayListMealsObject.add(dataSnapshot1.getValue(meal.class));
                    arrayListMeals.add(dataSnapshot1.getValue(meal.class).getName());
                    Log.d("test", "this is meals : " + dataSnapshot1.getValue(meal.class));
//                    spinnerArrayAdapter2.notifyDataSetChanged();


                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        Log.d("test", "this is size of arrMeal OBBBBJJJEEECCTtrtrt45454: " + arrayListMealsObject.size());


        return arrayListMealsObject;
    }

    public void selectedMeal() {
        if (mealAll > arrayListMealsObject.size() || arrayListMealsObject.size() > mealAll) {
            arrayListMealsObject.clear();
            getMeal();
        }
        //This is first spinner (to add meal one to a specific user and day +++++++++++++++++++++++++++++++
        m = new ArrayList<>();
        m.add(0, "اختر اسم الوجبة");


        // Initializing an ArrayAdapter
        for (int i = 0; i < arrayListMealsObject.size(); i++) {
            String s = arrayListMealsObject.get(i).getName().toString();
            //  Log.d("test","this is size of naaaaaaaaame  :   "+ arrayListMealsObject.get(i).getName());
            m.add(s);
        }
        spinnerArrayAdapter2 = new ArrayAdapter<String>(
                getContext(), R.layout.support_simple_spinner_dropdown_item
                , m) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spSelectMealName.setAdapter(spinnerArrayAdapter2);
        spinnerArrayAdapter2.notifyDataSetChanged();

        spSelectMealName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                String s = arrayListMeals.get(position);
                choosenMeal = selectedItemText;
                firebaseDatabaseMeal = FirebaseDatabase.getInstance();

                //because first element is reserved as hint
                for (int i = 0; i < m.size() - 1; i++) {
                    if (choosenMeal.equals(arrayListMealsObject.get(i).getName())) {
                        mTest = (arrayListMealsObject.get(i));

                        //display meal details
                        cals.setText(mTest.getCal());
                        mealName.setText(mTest.getName());

                        Picasso.get()
                                .load(mTest.getImage())
                                .fit()
                                .centerCrop()
                                .into(mealImg, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        progressBar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError(Exception e) {

                                    }
                                });

                        //display meal ingr and details
                        recyclerViewIngr.setVisibility(View.VISIBLE);
                        recyclerViewDet.setVisibility(View.VISIBLE);

                        adapterIngr = new IngredientsAdapter(mTest.getingredients(), getContext());
                        recyclerViewIngr.setAdapter(adapterIngr);
                        adapterIngr.notifyDataSetChanged();

                        adapterDet = new ElementsAdapter(mTest.getElements(), getContext());
                        recyclerViewDet.setAdapter(adapterDet);
                        adapterDet.notifyDataSetChanged();


                        // Log.d("test","this is chosen meal Object"+arrayListMealsObject.get(i).getName());
                    }
                }


                // Log.d("test","check check ::  "+choosenUser + "   "+ choosenDay);


//                    DatabaseReference databaseReferenceT = firebaseDatabaseMeal.getReference().child("meals");
//
//                    databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
//                                arrayListMealsObject.add(dataSnapshot1.getValue(meal.class));
//                                Log.d("test","this is whole meals : "+dataSnapshot1.getValue(meal.class).toString());
//                                spinnerArrayAdapter2.notifyDataSetChanged();
//                                Log.d("test","this is size of arrMealObject: "+ arrayListMealsObject.size());
//
//
//                            }
//                            //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());
//
//
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            Toast.makeText(AddMealsFragment.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
                //Log.d("test","this is details " +usersprofile.child(choosenUser).child("profile"));
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    progressBar.setVisibility(View.VISIBLE);
                    // Notify the selected item text
                    Toast.makeText
                            (getActivity().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        //This is second spinner (to add meal one to a specific user and day +++++++++++++++++++++++++++++++
//        final Spinner spSelectUser2 = getView().findViewById(R.id.selectMeal2);
//        m2 = new ArrayList<>();
//        m2.add(0,"اختر وجبة");
//
//
//        // Initializing an ArrayAdapter
//        for(int i =0; i<arrayListMealsObject.size(); i++){
//            String s = arrayListMealsObject.get(i).getName().toString();
//            //  Log.d("test","this is size of naaaaaaaaame  :   "+ arrayListMealsObject.get(i).getName());
//            m2.add(s);
//        }
//        spinnerArrayAdapter3 = new ArrayAdapter<String>(
//                getContext(),R.layout.support_simple_spinner_dropdown_item
//                ,m2) {
//            @Override
//            public boolean isEnabled(int position){
//                if(position == 0)
//                {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                }
//                else
//                {
//                    return true;
//                }
//            }
//            @Override
//            public View getDropDownView(int position, View convertView,
//                                        ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//                TextView tv = (TextView) view;
//                if(position == 0){
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                }
//                else {
//                    tv.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
//        };
//        spinnerArrayAdapter3.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spSelectUser2.setAdapter(spinnerArrayAdapter3);
//        spinnerArrayAdapter3.notifyDataSetChanged();
//
//        spSelectUser2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItemText = (String) parent.getItemAtPosition(position);
//                String s = arrayListMeals.get(position);
//                choosenMeal = selectedItemText;
//                firebaseDatabaseMeal = FirebaseDatabase.getInstance();
//
//                //because first element is reserved as hint
//                for(int i =0; i < m.size()-1; i++){
//                    if(choosenMeal.equals(arrayListMealsObject.get(i).getName())){
//                        mTest2 = (arrayListMealsObject.get(i));
//                        // Log.d("test","this is chosen meal Object"+arrayListMealsObject.get(i).getName());
//                    }
//                }
//                if(!spSelectUser2.equals("اختر وجبة")){
//                    mealNo2 = "1";
//                }
//
//                // Log.d("test","check check ::  "+choosenUser + "   "+ choosenDay);
//
////                    DatabaseReference databaseReferenceT = firebaseDatabaseMeal.getReference().child("meals");
////
////                    databaseReference.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(DataSnapshot dataSnapshot) {
////                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
////                                arrayListMealsObject.add(dataSnapshot1.getValue(meal.class));
////                                Log.d("test","this is whole meals : "+dataSnapshot1.getValue(meal.class).toString());
////                                spinnerArrayAdapter2.notifyDataSetChanged();
////                                Log.d("test","this is size of arrMealObject: "+ arrayListMealsObject.size());
////
////
////                            }
////                            //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());
////
////
////
////
////                        }
////
////                        @Override
////                        public void onCancelled(DatabaseError databaseError) {
////                            Toast.makeText(AddMealsFragment.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
////                        }
////                    });
//                //Log.d("test","this is details " +usersprofile.child(choosenUser).child("profile"));
//                // If user change the default selection
//                // First item is disable and it is used for hint
//                if(position > 0){
//                    // Notify the selected item text
//                    Toast.makeText
//                            (getActivity().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        //This is third spinner (to add meal one to a specific user and day +++++++++++++++++++++++++++++++
//        final Spinner spSelectUser3 = getView().findViewById(R.id.selectMeal3);
//        m2 = new ArrayList<>();
//        m2.add(0,"اختر وجبة");
//
//
//        // Initializing an ArrayAdapter
//        for(int i =0; i<arrayListMealsObject.size(); i++){
//            String s = arrayListMealsObject.get(i).getName().toString();
//            //  Log.d("test","this is size of naaaaaaaaame  :   "+ arrayListMealsObject.get(i).getName());
//            m2.add(s);
//        }
//        spinnerArrayAdapter4 = new ArrayAdapter<String>(
//                getContext(),R.layout.support_simple_spinner_dropdown_item
//                ,m2) {
//            @Override
//            public boolean isEnabled(int position){
//                if(position == 0)
//                {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                }
//                else
//                {
//                    return true;
//                }
//            }
//            @Override
//            public View getDropDownView(int position, View convertView,
//                                        ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//                TextView tv = (TextView) view;
//                if(position == 0){
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                }
//                else {
//                    tv.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
//        };
//        spinnerArrayAdapter4.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spSelectUser3.setAdapter(spinnerArrayAdapter4);
//        spinnerArrayAdapter4.notifyDataSetChanged();
//
//        spSelectUser3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItemText = (String) parent.getItemAtPosition(position);
//                String s = arrayListMeals.get(position);
//                choosenMeal = selectedItemText;
//                firebaseDatabaseMeal = FirebaseDatabase.getInstance();
//
//                //because first element is reserved as hint
//                for(int i =0; i < m.size()-1; i++){
//                    if(choosenMeal.equals(arrayListMealsObject.get(i).getName())){
//                        mTest3 = (arrayListMealsObject.get(i));
//                        // Log.d("test","this is chosen meal Object"+arrayListMealsObject.get(i).getName());
//                    }
//                }
//                if(!spSelectUser3.equals("اختر وجبة")){
//                    mealNo3 = "2";
//                }
//
//                // Log.d("test","check check ::  "+choosenUser + "   "+ choosenDay);
//
////                    DatabaseReference databaseReferenceT = firebaseDatabaseMeal.getReference().child("meals");
////
////                    databaseReference.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(DataSnapshot dataSnapshot) {
////                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
////                                arrayListMealsObject.add(dataSnapshot1.getValue(meal.class));
////                                Log.d("test","this is whole meals : "+dataSnapshot1.getValue(meal.class).toString());
////                                spinnerArrayAdapter2.notifyDataSetChanged();
////                                Log.d("test","this is size of arrMealObject: "+ arrayListMealsObject.size());
////
////
////                            }
////                            //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());
////
////
////
////
////                        }
////
////                        @Override
////                        public void onCancelled(DatabaseError databaseError) {
////                            Toast.makeText(AddMealsFragment.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
////                        }
////                    });
//                //Log.d("test","this is details " +usersprofile.child(choosenUser).child("profile"));
//                // If user change the default selection
//                // First item is disable and it is used for hint
//                if(position > 0){
//                    // Notify the selected item text
//                    Toast.makeText
//                            (getActivity().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        //This is forth spinner (to add meal one to a specific user and day +++++++++++++++++++++++++++++++
//        final Spinner spSelectUser4 = getView().findViewById(R.id.selectMeal4);
//        m2 = new ArrayList<>();
//        m2.add(0,"اختر وجبة");
//
//
//        // Initializing an ArrayAdapter
//        for(int i =0; i<arrayListMealsObject.size(); i++){
//            String s = arrayListMealsObject.get(i).getName().toString();
//            //  Log.d("test","this is size of naaaaaaaaame  :   "+ arrayListMealsObject.get(i).getName());
//            m2.add(s);
//        }
//        spinnerArrayAdapter4 = new ArrayAdapter<String>(
//                getContext(),R.layout.support_simple_spinner_dropdown_item
//                ,m2) {
//            @Override
//            public boolean isEnabled(int position){
//                if(position == 0)
//                {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                }
//                else
//                {
//                    return true;
//                }
//            }
//            @Override
//            public View getDropDownView(int position, View convertView,
//                                        ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//                TextView tv = (TextView) view;
//                if(position == 0){
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                }
//                else {
//                    tv.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
//        };
//        spinnerArrayAdapter4.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spSelectUser4.setAdapter(spinnerArrayAdapter4);
//        spinnerArrayAdapter4.notifyDataSetChanged();
//
//        spSelectUser4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItemText = (String) parent.getItemAtPosition(position);
//                String s = arrayListMeals.get(position);
//                choosenMeal = selectedItemText;
//                firebaseDatabaseMeal = FirebaseDatabase.getInstance();
//
//                //because first element is reserved as hint
//                for(int i =0; i < m.size()-1; i++){
//                    if(choosenMeal.equals(arrayListMealsObject.get(i).getName())){
//                        mTest4 = (arrayListMealsObject.get(i));
//                        // Log.d("test","this is chosen meal Object"+arrayListMealsObject.get(i).getName());
//                    }
//                }
//                if(!spSelectUser4.equals("اختر وجبة")){
//                    mealNo4 = "3";
//                }
//
//                // Log.d("test","check check ::  "+choosenUser + "   "+ choosenDay);
//
////                    DatabaseReference databaseReferenceT = firebaseDatabaseMeal.getReference().child("meals");
////
////                    databaseReference.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(DataSnapshot dataSnapshot) {
////                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
////                                arrayListMealsObject.add(dataSnapshot1.getValue(meal.class));
////                                Log.d("test","this is whole meals : "+dataSnapshot1.getValue(meal.class).toString());
////                                spinnerArrayAdapter2.notifyDataSetChanged();
////                                Log.d("test","this is size of arrMealObject: "+ arrayListMealsObject.size());
////
////
////                            }
////                            //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());
////
////
////
////
////                        }
////
////                        @Override
////                        public void onCancelled(DatabaseError databaseError) {
////                            Toast.makeText(AddMealsFragment.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
////                        }
////                    });
//                //Log.d("test","this is details " +usersprofile.child(choosenUser).child("profile"));
//                // If user change the default selection
//                // First item is disable and it is used for hint
//                if(position > 0){
//                    // Notify the selected item text
//                    Toast.makeText
//                            (getActivity().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        //This is third spinner (to add meal one to a specific user and day +++++++++++++++++++++++++++++++
//        final Spinner spSelectUser5 = getView().findViewById(R.id.selectMeal5);
//        m2 = new ArrayList<>();
//        m2.add(0,"اختر وجبة");
//
//
//        // Initializing an ArrayAdapter
//        for(int i =0; i<arrayListMealsObject.size(); i++){
//            String s = arrayListMealsObject.get(i).getName().toString();
//            //  Log.d("test","this is size of naaaaaaaaame  :   "+ arrayListMealsObject.get(i).getName());
//            m2.add(s);
//        }
//        spinnerArrayAdapter4 = new ArrayAdapter<String>(
//                getContext(),R.layout.support_simple_spinner_dropdown_item
//                ,m2) {
//            @Override
//            public boolean isEnabled(int position){
//                if(position == 0)
//                {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                }
//                else
//                {
//                    return true;
//                }
//            }
//            @Override
//            public View getDropDownView(int position, View convertView,
//                                        ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//                TextView tv = (TextView) view;
//                if(position == 0){
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                }
//                else {
//                    tv.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
//        };
//        spinnerArrayAdapter4.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spSelectUser5.setAdapter(spinnerArrayAdapter4);
//        spinnerArrayAdapter4.notifyDataSetChanged();
//
//        spSelectUser5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItemText = (String) parent.getItemAtPosition(position);
//                String s = arrayListMeals.get(position);
//                choosenMeal = selectedItemText;
//                firebaseDatabaseMeal = FirebaseDatabase.getInstance();
//
//                //because first element is reserved as hint
//                for(int i =0; i < m.size()-1; i++){
//                    if(choosenMeal.equals(arrayListMealsObject.get(i).getName())){
//                        mTest5 = (arrayListMealsObject.get(i));
//                        // Log.d("test","this is chosen meal Object"+arrayListMealsObject.get(i).getName());
//                    }
//                }
//                if(!spSelectUser5.equals("اختر وجبة")){
//                    mealNo5 = "4";
//                }
//
//                // Log.d("test","check check ::  "+choosenUser + "   "+ choosenDay);
//
////                    DatabaseReference databaseReferenceT = firebaseDatabaseMeal.getReference().child("meals");
////
////                    databaseReference.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(DataSnapshot dataSnapshot) {
////                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
////                                arrayListMealsObject.add(dataSnapshot1.getValue(meal.class));
////                                Log.d("test","this is whole meals : "+dataSnapshot1.getValue(meal.class).toString());
////                                spinnerArrayAdapter2.notifyDataSetChanged();
////                                Log.d("test","this is size of arrMealObject: "+ arrayListMealsObject.size());
////
////
////                            }
////                            //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());
////
////
////
////
////                        }
////
////                        @Override
////                        public void onCancelled(DatabaseError databaseError) {
////                            Toast.makeText(AddMealsFragment.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
////                        }
////                    });
//                //Log.d("test","this is details " +usersprofile.child(choosenUser).child("profile"));
//                // If user change the default selection
//                // First item is disable and it is used for hint
//                if(position > 0){
//                    // Notify the selected item text
//                    Toast.makeText
//                            (getActivity().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        addMealForUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choosenUser != null) {
                    selectMonth();
                    calSumOfMeals = 0;
                }
                // check if there's a chosen user and a chosen day
                if (choosenUser.equals("اختر متدرب") || choosenDay.equals("اختر يوم")) {
                    Toast.makeText(getContext(), "please choose day and user", Toast.LENGTH_SHORT).show();
                } else {
                    p.show();
                    p.setMessage("جاري الحفظ ..");
                    p.setCancelable(false);
                    //check if all spinners were selected then add the total calories
//                    if(mTest != null && mTest2 != null && mTest3 != null && mTest4 != null && mTest5 != null) {
//                        //calculating total sum of meals
//                        double calSumOfMeals = Double.valueOf(mTest.getCal()) + Double.valueOf(mTest2.getCal())
//                                + Double.valueOf(mTest3.getCal()) + Double.valueOf(mTest4.getCal())
//                                + Double.valueOf(mTest5.getCal());
//                        //Assigning total meals cals to the tree
//                        DatabaseReference databaseReferenceTotalCal = firebaseDatabaseMeal.getReference().child("users")
//                                .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
//                                .child(choosenDay).child("totalCals");
//                        DatabaseReference databaseReferenceMealCount = firebaseDatabaseMeal.getReference().child("users")
//                                .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
//                                .child(choosenDay).child("mealsCount");
//
//                        //set number of meals
//                        int mealCount = Integer.valueOf(mealNo5)+1;
//
//                        databaseReferenceTotalCal.setValue(String.valueOf(calSumOfMeals));
//                        databaseReferenceMealCount.setValue(String.valueOf(mealCount));
//
//                    }
//                    //check if all spinners were selected then add the total calories
//                    if(mTest != null && mTest2 != null && mTest3 != null && mTest4 != null && mTest5 ==null) {
//                        //calculating total sum of meals
//                        double calSumOfMeals = Double.valueOf(mTest.getCal()) + Double.valueOf(mTest2.getCal())
//                                + Double.valueOf(mTest3.getCal()) + Double.valueOf(mTest4.getCal());
//                        //Assigning total meals cals to the tree
//                        DatabaseReference databaseReferenceTotalCal = firebaseDatabaseMeal.getReference().child("users")
//                                .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
//                                .child(choosenDay).child("totalCals");
//                        DatabaseReference databaseReferenceMealCount = firebaseDatabaseMeal.getReference().child("users")
//                                .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
//                                .child(choosenDay).child("mealsCount");
//
//                        //set number of meals
//                        int mealCount = Integer.valueOf(mealNo4)+1;
//
//                        databaseReferenceTotalCal.setValue(String.valueOf(calSumOfMeals));
//                        databaseReferenceMealCount.setValue(String.valueOf(mealCount));
//
//                    }
//                    //check if all spinners were selected then add the total calories
//                    if(mTest != null && mTest2 != null && mTest3 != null && mTest4 == null && mTest5 == null) {
//                        //calculating total sum of meals
//                        double calSumOfMeals = Double.valueOf(mTest.getCal()) + Double.valueOf(mTest2.getCal())
//                                + Double.valueOf(mTest3.getCal());
//                        //Assigning total meals cals to the tree
//                        DatabaseReference databaseReferenceTotalCal = firebaseDatabaseMeal.getReference().child("users")
//                                .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
//                                .child(choosenDay).child("totalCals");
//                        DatabaseReference databaseReferenceMealCount = firebaseDatabaseMeal.getReference().child("users")
//                                .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
//                                .child(choosenDay).child("mealsCount");
//
//                        //set number of meals
//                        int mealCount = Integer.valueOf(mealNo3)+1;
//
//                        databaseReferenceTotalCal.setValue(String.valueOf(calSumOfMeals));
//                        databaseReferenceMealCount.setValue(String.valueOf(mealCount));
//
//                    }
//                    //check if all spinners were selected then add the total calories
//                    if(mTest != null && mTest2 != null && mTest3 == null && mTest4 == null && mTest5 == null) {
//                        //calculating total sum of meals
//                        double calSumOfMeals = Double.valueOf(mTest.getCal()) + Double.valueOf(mTest2.getCal());
//                        //Assigning total meals cals to the tree
//                        DatabaseReference databaseReferenceTotalCal = firebaseDatabaseMeal.getReference().child("users")
//                                .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
//                                .child(choosenDay).child("totalCals");
//                        DatabaseReference databaseReferenceMealCount = firebaseDatabaseMeal.getReference().child("users")
//                                .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
//                                .child(choosenDay).child("mealsCount");
//
//                        //set number of meals
//                        int mealCount = Integer.valueOf(mealNo2)+1;
//
//                        databaseReferenceTotalCal.setValue(String.valueOf(calSumOfMeals));
//                        databaseReferenceMealCount.setValue(String.valueOf(mealCount));
//
//                    }

                    //check if no meals are selected
                    if (mTest == null && mTest2 == null && mTest3 == null && mTest4 == null && mTest5 == null) {
                        Toast.makeText(getContext(), "please select at least one meal", Toast.LENGTH_SHORT).show();

                    }

                    DatabaseReference databaseReferenceDay = firebaseDatabaseMeal.getReference().child("users")
                            .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
                            .child(choosenDay).child("day");


                    //add meals to corresponding user and day
                    DatabaseReference databaseReference1 = firebaseDatabaseMeal.getReference().child("users")
                            .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
                            .child(choosenDay).child("dayMeals").child(getChoosenMeNumberIndex);

//                    DatabaseReference databaseReference2 = firebaseDatabaseMeal.getReference().child("users")
//                            .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
//                            .child(choosenDay).child("dayMeals").child(mealNo2);
//
//                    DatabaseReference databaseReference3 = firebaseDatabaseMeal.getReference().child("users")
//                            .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
//                            .child(choosenDay).child("dayMeals").child(mealNo3);
//
//                    DatabaseReference databaseReference4 = firebaseDatabaseMeal.getReference().child("users")
//                            .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
//                            .child(choosenDay).child("dayMeals").child(mealNo4);
//
//                    DatabaseReference databaseReference5 = firebaseDatabaseMeal.getReference().child("users")
//                            .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
//                            .child(choosenDay).child("dayMeals").child(mealNo5);


                    databaseReference1.setValue(mTest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            getMeCount();
                            DatabaseReference databaseReferenceMealCount = firebaseDatabaseMeal.getReference().child("users")
                                    .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("mealsCount");

                            databaseReferenceMealCount.setValue(String.valueOf(meCount));
                        }
                    });
//                    databaseReference2.setValue(mTest2);
                    //check if all spinners were selected then add the total calories
                    if (mTest != null) {
                        //calculating total sum of meals
                        calSumOfMeals = 0;
                        //Assigning total meals cals to the tree
                        final DatabaseReference databaseReferenceTotalCal = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
                                .child(choosenDay).child("totalCals");


                        //set number of meals
//                        int mealCount = Integer.valueOf(mealNo1)+1;
                        //go to the child where you want to retreive values of
                        mealsList = new ArrayList<>();
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference usersRef = rootRef.child("users").child(choosenUser).child("Diet")
                                .child(chosenMonth).child(chosenWeek).child(choosenDay).child("dayMeals");
                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                                    //dayMeals.add(ds2.getValue(DietDay.class));
//                                ds2.child("dayMeals").getValue(meal.class);
                                    mealsList.add(ds2.getValue(meal.class));
                                    mealsList.size();
                                }
                                for (int i = 0; i < mealsList.size(); i++) {

                                    calSumOfMeals = calSumOfMeals + Double.valueOf(mealsList.get(i).getCal());
                                }

                                databaseReferenceTotalCal.setValue(String.valueOf(calSumOfMeals));
                                calSumOfMeals = 0;

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        };
                        usersRef.addListenerForSingleValueEvent(eventListener);


                    }
//                    databaseReference3.setValue(mTest3);
//                    databaseReference4.setValue(mTest4);
//                    databaseReference5.setValue(mTest5);
                    getUsers();


                    Toast.makeText(getContext(), "تم حفظ الوجبة", Toast.LENGTH_LONG).show();
                    p.dismiss();
                    childlistrnerForMe();


                    int chossenDayInt = Integer.valueOf(choosenDay) + 1;
                    databaseReferenceDay.setValue(String.valueOf(chossenDayInt));
                    getUsers();
                }
            }
        });
    }

    public void userProfile(String choosenUser) {
        Log.d("test", "this is path : " + usersprofile);
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users")
                .child(choosenUser).child("Profile");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ;
                //   Log.d("test", userinfo.getName());

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);


                profileName.setText(userinfo.getName());
                profileAge.setText(userinfo.getAge());
                profileWeight.setText(userinfo.getWeight());
                profileHeight.setText(userinfo.getHeight());
                profileActivity.setText(userinfo.getUserActivity());
                profileGoal.setText(userinfo.getUserGoal());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext().getApplicationContext(), databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void selectedDay() {
        spMealsCount.setEnabled(false);
        String[] day = new String[]{
                "اختر يوم",
                "Day1",
                "Day2",
                "Day3",
                "Day4",
                "Day5",
                "Day6",
                "Day7",
        };
        final Spinner spSelectDay = getView().findViewById(R.id.selectDay);
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(), R.layout.support_simple_spinner_dropdown_item, day) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spSelectDay.setAdapter(spinnerArrayAdapter);

        spSelectDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                choosenDay = selectedItemText;
                // If user change the default selection
                if (choosenDay.equals("Day1"))
                    choosenDay = "0";
                if (choosenDay.equals("Day2"))
                    choosenDay = "1";
                if (choosenDay.equals("Day3"))
                    choosenDay = "2";
                if (choosenDay.equals("Day4"))
                    choosenDay = "3";
                if (choosenDay.equals("Day5"))
                    choosenDay = "4";
                if (choosenDay.equals("Day6"))
                    choosenDay = "5";
                if (choosenDay.equals("Day7"))
                    choosenDay = "6";

                // First item is disable and it is used for hint
                if (position > 0) {
                    calSumOfMeals = 0;
                    getMeCount();
                    // Notify the selected item text
                    // Initializing an ArrayAdapter
                    if (!choosenDay.equals("اختر يوم")) {
                        spMealsCount.setEnabled(true);
                    }
                    Toast.makeText
                            (getActivity().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void selectedUser() {
        Spinner spSelectUser = getView().findViewById(R.id.selectUser);

        // Initializing an ArrayAdapter
        Log.d("test", "frist  " + arrayList.size());
        spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(), R.layout.support_simple_spinner_dropdown_item, arrayList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
//                if(position == 0){
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                }
//                else {
//                    tv.setTextColor(Color.BLACK);
//                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spSelectUser.setAdapter(spinnerArrayAdapter);
        spinnerArrayAdapter.notifyDataSetChanged();

//        Log.d("test",""+arrayList.size());


        spSelectUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                String s = arrayList.get(position);
                Log.d("test", "thid dfjkdl : " + s);
                choosenUser = arrayList2.get(position);
                // choosenUser = selectedItemText;
                usersprofile = FirebaseDatabase.getInstance().getReference();
                usersprofile.child(choosenUser);
                userProfile(choosenUser);

                //refresh meal count
                getMeCount();

                getMonthTabs();

                //selectMonth();
                //Log.d("test","this is details " +usersprofile.child(choosenUser).child("profile"));
                // If user change the default selection
                // First item is disable and it is used for hint

                // Notify the selected item text
//                Toast.makeText
//                        (getActivity().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
//                        .show();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getUsers() {
        firebaseDatabase = FirebaseDatabase.getInstance();

        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                arrayList.add(0, "اختر متدرب");
//                arrayList2.add(0, "اختر متدرب");

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String email = dataSnapshot1.child("Profile").child("userEmail").getValue(String.class);

                    Log.d("test", "this is DATAAADDDD&&&OOOOMMM :" + dataSnapshot1.getKey());

                    Log.d("test", "this is DATAAADDDD&&&OOOOMMM :" +
                            dataSnapshot1.child("Profile").child("userEmail").getValue(String.class));


                    UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);
                    // arrayList.add(dataSnapshot1.getKey());


                    arrayList.add(dataSnapshot1.child("Profile").child("userEmail").getValue(String.class));
                    arrayList2.add(dataSnapshot1.getKey());
                    spinnerArrayAdapter.notifyDataSetChanged();

                    p.dismiss();

//                    Log.d("test","this is size of arr: "+ array.length);


                    //      Log.d("test", "this is uid :" + dataSnapshot1.getKey());

                    //   Log.d("test", "this is emails FFGFGGGF :" + userinfo.getEmail());

                }
                //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddMealsFragment.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    @Override
//    public void onButtonClick() {
//        position = DietFragment.getCount();
//        positionWeek = DietFragment.getCountWeek();
//        if(choosenUser != null) {
//            selectMonth();
//            Toast.makeText(getContext(), chosenMonth +" "+ chosenWeek + " Selected", Toast.LENGTH_SHORT).show();
//        }
//    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void getMeCount() {
        mm = 1;
        me.clear();

        me.add(0, "اختر وجبة");
        me.add("وجبة 1");


        FirebaseDatabase f = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = f.getReference().child("users")
                .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
                .child(choosenDay).child("dayMeals");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // bigExList.add(dataSnapshot.getValue(Exercises.class));

                meCount = (int) dataSnapshot.getChildrenCount();


//                for(int i = 0; i < bigExList.size(); i++) {
//                    t = (bigExList.get(i).getExercise());
//                }
                for (int i = 0; i < meCount; i++) {
                    mm = mm + 1;
                    me.add("وجبة " + String.valueOf(mm));
                    spinnerArrayAdapterMealCount.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void mealNumber() {


//            arrayListNamesEx = new ArrayList<>();
//
//
//            arrayListNamesEx.clear();

        me.add(0, "اختر وجبة");
        me.add("وجبة 1");
//                "اختر تمرين",
//                "تمرين 1",
//                "تمرين 2",
//                "تمرين 3",
//                "تمرين 4",
//                "تمرين 5",
//                "تمرين 6",
//                "تمرين 7",
//                "تمرين 8",
//                "تمرين 9";
        // Initializing an ArrayAdapter
        //2 problems with the font ,, getcontext() instead of ( getActivity().getApplicationContext() )
        //replace item_spinner with support_simple_spinner_dropdown_item
        spinnerArrayAdapterMealCount = new ArrayAdapter<String>(
                getContext(), R.layout.support_simple_spinner_dropdown_item, me) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapterMealCount.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spMealsCount.setAdapter(spinnerArrayAdapterMealCount);

        spMealsCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                chosenMeNumber = selectedItemText;
                // If user change the default selection
                // If user change the default selection
                if (chosenMeNumber.equals("وجبة 1"))
                    getChoosenMeNumberIndex = "0";
                if (chosenMeNumber.equals("وجبة 2"))
                    getChoosenMeNumberIndex = "1";
                if (chosenMeNumber.equals("وجبة 3"))
                    getChoosenMeNumberIndex = "2";
                if (chosenMeNumber.equals("وجبة 4"))
                    getChoosenMeNumberIndex = "3";
                if (chosenMeNumber.equals("وجبة 5"))
                    getChoosenMeNumberIndex = "4";
                if (chosenMeNumber.equals("وجبة 6"))
                    getChoosenMeNumberIndex = "5";
                if (chosenMeNumber.equals("وجبة 7"))
                    getChoosenMeNumberIndex = "6";
                if (chosenMeNumber.equals("وجبة 8"))
                    getChoosenMeNumberIndex = "7";
                if (chosenMeNumber.equals("وجبة 9"))
                    getChoosenMeNumberIndex = "8";
                if (chosenMeNumber.equals("وجبة 10"))
                    getChoosenMeNumberIndex = "9";
                if (chosenMeNumber.equals("وجبة 11"))
                    getChoosenMeNumberIndex = "10";
                if (chosenMeNumber.equals("وجبة 12"))
                    getChoosenMeNumberIndex = "11";
                if (chosenMeNumber.equals("وجبة 13"))
                    getChoosenMeNumberIndex = "12";
                if (chosenMeNumber.equals("وجبة 14"))
                    getChoosenMeNumberIndex = "13";
                if (chosenMeNumber.equals("وجبة 15"))
                    getChoosenMeNumberIndex = "14";


                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    selectedMeal();
                    retriveMealToEdit();
                    Toast.makeText
                            (getActivity().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void childlistrnerForMe() {
        DatabaseReference rootUserEx = FirebaseDatabase.getInstance().getReference().child(choosenUser)
                .child("Diet").child(chosenMonth).child(chosenWeek).child(choosenDay).child("dayMeals");
        rootUserEx.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getMeCount();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getMeCount();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void childlistrner() {
        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("meals");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mealAll = (int)dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getAllMealsCount();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getAllMealsCount();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void getAllMealsCount(){
        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("meals");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mealAll = (int)dataSnapshot.getChildrenCount();
                selectedMeal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void retriveMealToEdit(){
        progressBar.setVisibility(View.VISIBLE);

        //reach to meal to edit
        DatabaseReference databaseReference1 = firebaseDatabaseMeal.getReference().child("users")
                .child(choosenUser).child("Diet").child(chosenMonth).child(chosenWeek)
                .child(choosenDay).child("dayMeals").child(getChoosenMeNumberIndex);

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dayMealObject = dataSnapshot.getValue(DayMeals.class);
                if (dayMealObject != null && !dayMealObject.getImage().isEmpty()) {
                    recyclerViewIngr.setVisibility(View.VISIBLE);
                    recyclerViewIngr.setVisibility(View.VISIBLE);


                    assert dayMealObject != null;

                    showIngrsAndDetails();

                    mealName.setText(dayMealObject.getName());
                    cals.setText(dayMealObject.getCal());
                    Picasso.get()
                            .load(dayMealObject.getImage())
                            .fit()
                            .centerCrop()
                            .into(mealImg, new Callback() {
                                @Override
                                public void onSuccess() {
                                    progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });


                }else {
                    mealName.setText("");
                    cals.setText("");
                    mealImg.setImageResource(R.drawable.ic_meal);
                    recyclerViewIngr.setVisibility(View.GONE);
                    recyclerViewDet.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void showIngrsAndDetails(){
        recyclerViewIngr.setVisibility(View.VISIBLE);
        recyclerViewDet.setVisibility(View.VISIBLE);


        // هذا الكود لربط الكارد فيو

        adapterIngr = new IngredientsAdapter(dayMealObject.getIngredients(),getContext());
        recyclerViewIngr.setAdapter(adapterIngr);
        adapterIngr.notifyDataSetChanged();

        adapterDet = new ElementsAdapter(dayMealObject.getElements(),getContext());
        recyclerViewDet.setAdapter(adapterDet);
        adapterDet.notifyDataSetChanged();
    }
    private void selectMonth() {


        if(position == 0) {
            chosenMonth = "0";
            if (positionWeek == 0) {
                chosenWeek = "week1";
            }
            if (positionWeek == 1) {
                chosenWeek = "week2";
            }
            if (positionWeek == 2) {
                chosenWeek = "week3";
            }
            if (positionWeek == 3) {
                chosenWeek = "week4";
            }
        }
        if (position == 1) {
            chosenMonth = "1";
            if (positionWeek == 0) {
                chosenWeek = "week1";
            }
            if (positionWeek == 1) {
                chosenWeek = "week2";
            }
            if (positionWeek == 2) {
                chosenWeek = "week3";
            }
            if (positionWeek == 3) {
                chosenWeek = "week4";
            }
        }
        if (position == 2) {
            chosenMonth = "2";
            if (positionWeek == 0) {
                chosenWeek = "week1";
            }
            if (positionWeek == 1) {
                chosenWeek = "week2";
            }
            if (positionWeek == 2) {
                chosenWeek = "week3";
            }
            if (positionWeek == 3) {
                chosenWeek = "week4";
            }
        }
        if(position == 3) {
            chosenMonth = "3";
            if (positionWeek == 0) {
                chosenWeek = "week1";
            }
            if (positionWeek == 1) {
                chosenWeek = "week2";
            }
            if (positionWeek == 2) {
                chosenWeek = "week3";
            }
            if (positionWeek == 3) {
                chosenWeek = "week4";
            }
        }
        if (position == 4) {
            chosenMonth = "4";
            if (positionWeek == 0) {
                chosenWeek = "week1";
            }
            if (positionWeek == 1) {
                chosenWeek = "week2";
            }
            if (positionWeek == 2) {
                chosenWeek = "week3";
            }
            if (positionWeek == 3) {
                chosenWeek = "week4";
            }
        }
        if (position == 5) {
            chosenMonth = "5";
            if (positionWeek == 0) {
                chosenWeek = "week1";
            }
            if (positionWeek == 1) {
                chosenWeek = "week2";
            }
            if (positionWeek == 2) {
                chosenWeek = "week3";
            }
            if (positionWeek == 3) {
                chosenWeek = "week4";
            }
        }
        if(position == 6) {
            chosenMonth = "6";
            if (positionWeek == 0) {
                chosenWeek = "week1";
            }
            if (positionWeek == 1) {
                chosenWeek = "week2";
            }
            if (positionWeek == 2) {
                chosenWeek = "week3";
            }
            if (positionWeek == 3) {
                chosenWeek = "week4";
            }
        }
        if (position == 7) {
            chosenMonth = "7";
            if (positionWeek == 0) {
                chosenWeek = "week1";
            }
            if (positionWeek == 1) {
                chosenWeek = "week2";
            }
            if (positionWeek == 2) {
                chosenWeek = "week3";
            }
            if (positionWeek == 3) {
                chosenWeek = "week4";
            }
        }
        if (position == 8) {
            chosenMonth = "8";
            if (positionWeek == 0) {
                chosenWeek = "week1";
            }
            if (positionWeek == 1) {
                chosenWeek = "week2";
            }
            if (positionWeek == 2) {
                chosenWeek = "week3";
            }
            if (positionWeek == 3) {
                chosenWeek = "week4";
            }
        }
        if(position == 9) {
            chosenMonth = "9";
            if (positionWeek == 0) {
                chosenWeek = "week1";
            }
            if (positionWeek == 1) {
                chosenWeek = "week2";
            }
            if (positionWeek == 2) {
                chosenWeek = "week3";
            }
            if (positionWeek == 3) {
                chosenWeek = "week4";
            }
        }
        if (position == 10) {
            chosenMonth = "10";
            if (positionWeek == 0) {
                chosenWeek = "week1";
            }
            if (positionWeek == 1) {
                chosenWeek = "week2";
            }
            if (positionWeek == 2) {
                chosenWeek = "week3";
            }
            if (positionWeek == 3) {
                chosenWeek = "week4";
            }
        }
        if (position == 11) {
            chosenMonth = "11";
            if (positionWeek == 0) {
                chosenWeek = "week1";
            }
            if (positionWeek == 1) {
                chosenWeek = "week2";
            }
            if (positionWeek == 2) {
                chosenWeek = "week3";
            }
            if (positionWeek == 3) {
                chosenWeek = "week4";
            }
        }
    }
}
