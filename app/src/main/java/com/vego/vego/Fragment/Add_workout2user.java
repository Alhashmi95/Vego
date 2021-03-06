package com.vego.vego.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.firebase.database.ValueEventListener;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.spyhunter99.supertooltips.ToolTip;
import com.spyhunter99.supertooltips.ToolTipManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vego.vego.Activity.ActivityInsideExercise;
import com.vego.vego.Activity.AddNewExerciseActivity;
import com.vego.vego.Activity.AdminActivity;
import com.vego.vego.Activity.LoginActivity;
import com.vego.vego.Activity.SignupActivity;
import com.vego.vego.Adapters.DaysAdapter;
import com.vego.vego.Adapters.NewElementAdapter;
import com.vego.vego.Adapters.NewSetAdapter;
import com.vego.vego.Adapters.toolbarAdapter;
import com.vego.vego.R;
import com.vego.vego.Storage.UserSharedPreferences;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.Exercises;
import com.vego.vego.model.MonthExercise;
import com.vego.vego.model.MonthMeal;
import com.vego.vego.model.UserInfo;
import com.vego.vego.model.elements;
import com.vego.vego.model.exercise;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.meal;
import com.vego.vego.model.sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Add_workout2user.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Add_workout2user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_workout2user extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    DatabaseReference usersprofile;
    Button addNewSet, addNewExercise, saveExercise;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    ArrayList<String> arrayListNamesEx, arrayList, arrayList2;
    ArrayAdapter<String> spinnerArrayAdapter;
    String choosenUser, choosenDay, choosenEx, choosenExNumber, getChoosenExNumberIndex, choosenMu;
    Spinner spSelectUser, spSelectDay, spSelectEx, spSelectMu,spSelectExName;
    ArrayList exNames = new ArrayList();
    ArrayList<exercise> arrayListObjectEx = new ArrayList<>();
    exercise eTest;
    ImageView imageViewEx;
    TextView textViewExName;
    private ArrayList<sets> setsArrayList;
    NewSetAdapter newSetAdapter;
    ArrayList<sets> list = new ArrayList<>();
    int indexofExercise = 0;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceExCount;
    ArrayList ex = new ArrayList();

    String exName;
    String imgUrl;
    String exMu;
    String s;
   // int exCounterD1=0,exCounterD2=0,exCounterD3=0,exCounterD4=0,exCounterD5=0,exCounterD6=0,exCounterD7=0;
    int tt=1;
    TextView profileName;
    TextView profileAge;
    TextView profileWeight;
    TextView profileHeight;
    TextView profileActivity;
    TextView profileGoal;

    boolean checker;

    ProgressBar progressBar;

    ArrayList<Exercises> bigExList = new ArrayList<>();


    ArrayList<exercise> t= new ArrayList<>();

    int exCouunt = 0;



    FirebaseDatabase firebaseDatabaseMeal = FirebaseDatabase.getInstance();

    int indexOfExDay1=0,indexOfExDay2=0,indexOfExDay3=0,indexOfExDay4=0,indexOfExDay5=0, indexOfExDay6=0,
    indexOfExDay7=0;

    private DatabaseReference root;

    private DatabaseReference rootUserEx;

    //==============================================================

    int position =0;
    int positionWeek =0;


    String chosenMonth= "0", chosenWeek = "week1";


    TabLayout tabLayout, tabLayoutWeek;

    com.vego.vego.Adapters.toolbarAdapter toolbarAdapter;
    com.vego.vego.Adapters.toolbarAdapterWeek toolbarAdapterWeek;

    Button addMonth, removeMonth;

    ViewPager viewPager;

    int counterMonth =2;

    List dietBigList;

    ArrayList<exercise> exerList = new ArrayList<>();

    Exercises[] workoutDay;

    List exBigList;



    ArrayList<MonthExercise> monthEx = new ArrayList<>();

    ProgressDialog p;





    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private exercise exObject;
    private ToolTipManager tooltips;
    private ViewGroup rootLayout;

    public Add_workout2user() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_workout2user.
     */
    // TODO: Rename and change types and number of parameters
    public static Add_workout2user newInstance(String param1, String param2) {
        Add_workout2user fragment = new Add_workout2user();
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
        return inflater.inflate(R.layout.fragment_add_workout2user, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //add tabLayout
        tabLayout = view.findViewById(R.id.tablayout);

        tabLayoutWeek = view.findViewById(R.id.tablayoutWeek);

        viewPager = view.findViewById(R.id.viewpager);

        addMonth = view.findViewById(R.id.btn_addMonth);

        removeMonth = view.findViewById(R.id.btn_removeMonth);


//        toolbarAdapter = new toolbarAdapter(getContext());




        tabLayout.addTab(tabLayout.newTab().setText("الشهر 1"));



        tabLayoutWeek.addTab(tabLayoutWeek.newTab().setText("الاسبوع 1"));

        tabLayoutWeek.addTab(tabLayoutWeek.newTab().setText("الاسبوع 2"));

        tabLayoutWeek.addTab(tabLayoutWeek.newTab().setText("الاسبوع 3"));

        tabLayoutWeek.addTab(tabLayoutWeek.newTab().setText("الاسبوع 4"));


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

        spSelectUser = view.findViewById(R.id.spinnerUser);
        spSelectDay = view.findViewById(R.id.spinnerDay);
        spSelectEx = view.findViewById(R.id.spinnerExerciseNo);
        spSelectMu = view.findViewById(R.id.spinnertargetedMu);
        spSelectExName = view.findViewById(R.id.spinnerExerciseName);

        addNewExercise = view.findViewById(R.id.addNewExerciseBtn);
        addNewSet = view.findViewById(R.id.addSetBtn);
        saveExercise = view.findViewById(R.id.saveExercise);

        imageViewEx = view.findViewById(R.id.imageViewEx);
        textViewExName = view.findViewById(R.id.textViewExName);

        recyclerView = view.findViewById(R.id.recyclerSetsAdmin);

        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        //rootLayout = view.findViewById(R.id.root);

        tooltips = new ToolTipManager(getActivity());

        p = new ProgressDialog(getContext());

        p.setTitle("تحميل");
        p.setMessage("يرجى الانتظار");
        p.show();
        p.setCancelable(false);

        childlistrner();




//        sets s2 = new sets();
//        list.add(s2);



        saveExercise.setVisibility(view.INVISIBLE);
        setsArrayList = populateList();



        newSetAdapter = new NewSetAdapter(setsArrayList, getContext());
        recyclerView.setAdapter(newSetAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));


        addNewEx();
        getUsers();
        selectedUser();
        selectedDay();
        exerciseNumber();
        selectMu();
        getCountImages();
        saveExerciseToUser();

        addNewSet.setVisibility(View.INVISIBLE);










        //===================================== Defalut for Exercises

        sets[] setsArray = new sets[] {new sets("","", "","")};

        List setsList = new ArrayList<sets>(Arrays.asList(setsArray));

        exercise t = new exercise("","","");
        t.setSets((ArrayList<sets>) setsList);

        exerList.add(t);

        workoutDay = new Exercises[] {new Exercises("","", "",
                (ArrayList<exercise>) exerList),
                new Exercises("","", "", (ArrayList<exercise>) exerList),
                new Exercises("","", "", (ArrayList<exercise>) exerList),
                new Exercises("","", "", (ArrayList<exercise>) exerList),
                new Exercises("","", "", (ArrayList<exercise>) exerList),
                new Exercises("","", "", (ArrayList<exercise>) exerList),
                new Exercises("","", "", (ArrayList<exercise>) exerList),

        };

        exBigList = new ArrayList<Exercises>(Arrays.asList(workoutDay));







    }
    private void getMonthTabs() {
        for(int  i = tabLayout.getTabCount(); i > 1; i--){
            tabLayout.removeTabAt(i-1);
        }
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference monthCountRef = rootRef.child("users").child(choosenUser).child("Exercises");

        monthCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                counterMonth = (int) dataSnapshot.getChildrenCount();

                for(int i = 1; i< counterMonth; i++){
                    tabLayout.addTab(tabLayout.newTab().setText("الشهر "+ String.valueOf(i+1)));

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
                if(tabLayout.getTabCount() == 1){
                    Toast.makeText(getContext(),
                            "يجب ان تحتوي على عنصر واحد على الاقل",Toast.LENGTH_SHORT).show();

                }else{
                    removeMonth.setEnabled(false);

                    int index = tabLayout.getTabCount();
                    index--;

                    tabLayout.removeTabAt(index);

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databasaeReference = firebaseDatabase.getReference();
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                    databasaeReference.child("users").child(choosenUser).child("Exercises")
                            .child(String.valueOf(index)).removeValue().addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    getMonthTabs();
                                    removeMonth.setEnabled(true);
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
                if(counterMonth  <=3) {
                    addMonth.setEnabled(false);
                    //tabLayout.addTab(tabLayout.newTab().setText("month " + String.valueOf(counterMonth)));

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databasaeReference = firebaseDatabase.getReference();


                    DatabaseReference monthCount = databasaeReference.child("users").child(choosenUser)
                            .child("Exercises");

                    monthCount.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int monthCountIndex = (int)dataSnapshot.getChildrenCount();

                            MonthExercise ex = new MonthExercise((ArrayList<Exercises>) exBigList,(ArrayList<Exercises>)
                                    exBigList,(ArrayList<Exercises>)exBigList,(ArrayList<Exercises>)exBigList);

                            databasaeReference.child("users").child(choosenUser)
                                    .child("Exercises").child(String.valueOf(monthCountIndex)).setValue(ex)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    getMonthTabs();
                                    addMonth.setEnabled(true);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else {
                    Toast.makeText(getContext(),
                            "لا يمكنك اضافة اكثر من 4 " +
                                    "أشهر",Toast.LENGTH_SHORT).show();
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
                selectMonth();
                getExCount();


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
                getExCount();
               // getMonthTabs();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void childlistrnerForEx() {
        rootUserEx = FirebaseDatabase.getInstance().getReference().child(choosenUser)
                .child("Exercises").child(chosenMonth).child(chosenWeek).child(choosenDay).child("exercise");
        rootUserEx.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getExCount();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getExCount();
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
        root = FirebaseDatabase.getInstance().getReference().child("exercise");
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            getExNames();
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

    private void getExCount() {
        tt = 1;
        ex.clear();

        ex.add(0,"اختر تمرين");
        ex.add("تمرين 1");


        t= new ArrayList<>();
        FirebaseDatabase f = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = f.getReference().child("users")
                .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                .child(choosenDay).child("exercise");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // bigExList.add(dataSnapshot.getValue(Exercises.class));

                exCouunt = (int) dataSnapshot.getChildrenCount();


//                for(int i = 0; i < bigExList.size(); i++) {
//                    t = (bigExList.get(i).getExercise());
//                }
                for(int i =0 ; i < exCouunt; i++) {
                    tt = tt + 1;
                    ex.add("تمرين " + String.valueOf(tt));
                    spinnerArrayAdapter.notifyDataSetChanged();
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void userProfile(String choosenUser){
        Log.d("test","this is path : "+usersprofile);
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users")
                .child(choosenUser).child("Profile");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ;
                //   Log.d("test", userinfo.getName());

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);



                profileName.setText(userinfo.getName());
                profileAge.setText(userinfo.getAge()+" سنة");
                profileWeight.setText(userinfo.getWeight()+" كغ");
                profileHeight.setText(userinfo.getHeight()+" سم");
                profileActivity.setText(userinfo.getUserActivity());
                profileGoal.setText(userinfo.getUserGoal());

                tooltips = new ToolTipManager(getActivity());
                profileGoal.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (userinfo.getUserGoal().equals("خسارة الدهون والمحافظة على العضلات") ||
                                userinfo.getUserGoal().equals("المحافظة على الوزن الحالي")) {
                            ToolTip toolTip = new ToolTip()
                                    .withText(userinfo.getUserGoal())
                                    .withColor(Color.GRAY) //or whatever you want
                                    .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW)
                                    .withTextColor(Color.WHITE)
                                    .withPosition(ToolTip.Position.LEFT)
                                    .withShadow();
                            tooltips.showToolTip(toolTip, spSelectUser);
                        }
                        return true;
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext().getApplicationContext(), databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void saveExerciseToUser(){
        saveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.show();
                p.setMessage("جاري الحفظ ..");
                p.setCancelable(false);
//                for (int i = 0; i < newSetAdapter.getSetsArray().size(); i++) {
//                    //check if there's any null edit text in all cardviews of meal elements
//                    if (newSetAdapter.getSetsArray().get(i).getReps().equals("")) {
//                        Toast.makeText(getContext(), "please enter reps...",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }

                // check if there's a chosen user and a chosen day
                if(exObject == null) {
                    if (choosenUser.equals("اختر متدرب") || choosenDay.equals("اختر يوم") ||
                            choosenMu.equals("اختر عضلة") || (choosenExNumber.equals("اختر تمرين"))
                            || choosenEx.equals("اختر اسم التمرين")) {
                        Toast.makeText(getContext(), "الرجاء تحديد جميع التفاصيل من القوائم في الأعلى", Toast.LENGTH_SHORT).show();
                        p.dismiss();
                    } else {


                        DatabaseReference databaseReferenceDay = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                .child(choosenDay).child("day");

                        DatabaseReference databaseReferenceMu = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek).child(choosenDay).child("targetedmuscles");

                        databaseReferenceExCount = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek).child(choosenDay).child("exercisecount");


                        //add meals to corresponding user and day
                        DatabaseReference databaseReference1 = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                .child(choosenDay).child("exercise")
                                .child(getChoosenExNumberIndex);
                        if (choosenDay.equals("0")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay1 = (int) dataSnapshot.getChildrenCount();

                                    Log.d("test", "CCCCOOOUUNNNTT : " + indexOfExDay1);

                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });

                            // exCounterD1 = exCounterD1 +1;
                        }
                        if (choosenDay.equals("1")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay2 = (int) dataSnapshot.getChildrenCount();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });
                        }
                        if (choosenDay.equals("2")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay3 = (int) dataSnapshot.getChildrenCount();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });
                        }
                        if (choosenDay.equals("3")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay4 = (int) dataSnapshot.getChildrenCount();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });
                        }
                        if (choosenDay.equals("4")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay5 = (int) dataSnapshot.getChildrenCount();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });
                        }
                        if (choosenDay.equals("5")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay6 = (int) dataSnapshot.getChildrenCount();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });
                        }
                        if (choosenDay.equals("6")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay7 = (int) dataSnapshot.getChildrenCount();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });
                        }


                        int chossenDayInt = Integer.valueOf(choosenDay) + 1;
                        databaseReferenceDay.setValue(String.valueOf(chossenDayInt));


                        //   databaseReferenceExCount.setValue(String.valueOf(exCounterD1));


                        for (int i = 0; i < setsArrayList.size(); i++) {
                            if (newSetAdapter.getSetsArray().get(i).getReps().isEmpty()) {
                                Toast.makeText(getContext(), "الرجاء تحديد جميع التكرارات", Toast.LENGTH_SHORT).show();
                                p.dismiss();
                                checker = false;
                            } else {
                                checker = true;
                            }

                        }
                        if (checker) {
                            eTest.setSets(setsArrayList);
 //                           exObject.setSets(setsArrayList);


                            databaseReferenceMu.setValue(eTest.getTargetedmuscle());


                            databaseReference1.setValue(eTest).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "تم اضافة التمرين", Toast.LENGTH_SHORT).show();
                                    p.dismiss();

                                    //getUsers();
                                    childlistrnerForEx();


//                                spSelectDay.setSelection(0);
//                                spSelectEx.setSelection(0);
//                                spSelectMu.setSelection(0);


                                    if (choosenDay.equals("0")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay1));
                                    }
                                    if (choosenDay.equals("1")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay2));
                                    }
                                    if (choosenDay.equals("2")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay3));
                                    }
                                    if (choosenDay.equals("3")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay4));
                                    }
                                    if (choosenDay.equals("4")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay5));
                                    }
                                    if (choosenDay.equals("5")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay6));
                                    }
                                    if (choosenDay.equals("6")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay7));
                                    }


                                    //add if statment


//                                tt = tt + 1;
//                                ex.add("تمرين "+String.valueOf(tt));

                                    //   getExCount();


                                    //                     spinnerArrayAdapter.notifyDataSetChanged();

//                            Intent intent= new Intent(getContext(), AdminActivity.class);
//
//                            getContext().startActivity(intent);
                                }
                            });
                            //getUsers();
                            childlistrnerForEx();
                            getExCount();


                        }


                    }
                }else {
                    if (choosenUser.equals("اختر متدرب") || choosenDay.equals("اختر يوم") ||
                             (choosenExNumber.equals("اختر تمرين"))) {
                        Toast.makeText(getContext(), "الرجاء تحديد اليوم ورقم التمرين", Toast.LENGTH_SHORT).show();
                        p.dismiss();
                    } else {


                        DatabaseReference databaseReferenceDay = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                .child(choosenDay).child("day");

                        DatabaseReference databaseReferenceMu = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek).child(choosenDay).child("targetedmuscles");

                        databaseReferenceExCount = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek).child(choosenDay).child("exercisecount");


                        //add meals to corresponding user and day
                        DatabaseReference databaseReference1 = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                .child(choosenDay).child("exercise")
                                .child(getChoosenExNumberIndex);
                        if (choosenDay.equals("0")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay1 = (int) dataSnapshot.getChildrenCount();

                                    Log.d("test", "CCCCOOOUUNNNTT : " + indexOfExDay1);

                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });

                            // exCounterD1 = exCounterD1 +1;
                        }
                        if (choosenDay.equals("1")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay2 = (int) dataSnapshot.getChildrenCount();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });
                        }
                        if (choosenDay.equals("2")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay3 = (int) dataSnapshot.getChildrenCount();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });
                        }
                        if (choosenDay.equals("3")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay4 = (int) dataSnapshot.getChildrenCount();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });
                        }
                        if (choosenDay.equals("4")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay5 = (int) dataSnapshot.getChildrenCount();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });
                        }
                        if (choosenDay.equals("5")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay6 = (int) dataSnapshot.getChildrenCount();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });
                        }
                        if (choosenDay.equals("6")) {
                            FirebaseDatabase f = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = f.getReference().child("users")
                                    .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                                    .child(choosenDay).child("exercise");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Note ** : ondatachange discards the value of arraylist after it finishs
                                    indexOfExDay7 = (int) dataSnapshot.getChildrenCount();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }


                            });
                        }


                        int chossenDayInt = Integer.valueOf(choosenDay) + 1;
                        databaseReferenceDay.setValue(String.valueOf(chossenDayInt));


                        //   databaseReferenceExCount.setValue(String.valueOf(exCounterD1));


                        for (int i = 0; i < setsArrayList.size(); i++) {
                            if (newSetAdapter.getSetsArray().get(i).getReps().isEmpty()) {
                                Toast.makeText(getContext(), "الرجاء تحديد جميع التكرارات", Toast.LENGTH_SHORT).show();
                                p.dismiss();
                                checker = false;
                            } else {
                                checker = true;
                            }

                        }
                        if (checker) {
                            eTest.setSets(setsArrayList);
                            exObject.setSets(setsArrayList);


                            databaseReferenceMu.setValue(eTest.getTargetedmuscle());


                            databaseReference1.setValue(eTest).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "تم اضافة التمرين", Toast.LENGTH_SHORT).show();
                                    p.dismiss();

                                    //getUsers();
                                    childlistrnerForEx();


//                                spSelectDay.setSelection(0);
//                                spSelectEx.setSelection(0);
//                                spSelectMu.setSelection(0);


                                    if (choosenDay.equals("0")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay1));
                                    }
                                    if (choosenDay.equals("1")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay2));
                                    }
                                    if (choosenDay.equals("2")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay3));
                                    }
                                    if (choosenDay.equals("3")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay4));
                                    }
                                    if (choosenDay.equals("4")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay5));
                                    }
                                    if (choosenDay.equals("5")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay6));
                                    }
                                    if (choosenDay.equals("6")) {
                                        databaseReferenceExCount.setValue(String.valueOf(indexOfExDay7));
                                    }


                                    //add if statment


//                                tt = tt + 1;
//                                ex.add("تمرين "+String.valueOf(tt));

                                    //   getExCount();


                                    //                     spinnerArrayAdapter.notifyDataSetChanged();

//                            Intent intent= new Intent(getContext(), AdminActivity.class);
//
//                            getContext().startActivity(intent);
                                }
                            });
                            //getUsers();
                            childlistrnerForEx();
                            getExCount();


                        }


                    }

                }
//                eTest.setSets(setsArrayList);
//
//
//                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                DatabaseReference d = firebaseDatabase.getReference();
//
//
//                //upload meal to firebase
//                d.child("exercies").child(String.valueOf(indexofExercise)).setValue(eTest);
            }
        });
    }
    public void selectMu() {
        String[] day = new String[]{
                "اختر عضلة",
                "اكتاف",
                "باي",
                "بطن",
                "ترابيس",
                "تراي",
                "سواعد",
                "صدر",
                "ظهر",
                "افخاذ",
                "عضلة السمانة",
                "كارديو",
        };
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
        spSelectMu.setAdapter(spinnerArrayAdapter);

        spSelectMu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                choosenMu = selectedItemText;
                // If user change the default selection


                arrayListNamesEx = new ArrayList<>();

                addNewSet.setVisibility(View.INVISIBLE);




                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    Toast.makeText
                            (getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                    Log.d("test","vkffffffff"+ choosenMu);

                }
                getExNames();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void exerciseNumber(){
        ex.add(0,"اختر تمرين");
        ex.add("تمرين 1");

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
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.support_simple_spinner_dropdown_item,ex){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spSelectEx.setAdapter(spinnerArrayAdapter);

        spSelectEx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                choosenExNumber = selectedItemText;




                // If user change the default selection
                if(choosenExNumber.equals("تمرين 1"))
                    getChoosenExNumberIndex = "0";
                if(choosenExNumber.equals("تمرين 2"))
                    getChoosenExNumberIndex = "1";
                if(choosenExNumber.equals("تمرين 3"))
                    getChoosenExNumberIndex = "2";
                if(choosenExNumber.equals("تمرين 4"))
                    getChoosenExNumberIndex = "3";
                if(choosenExNumber.equals("تمرين 5"))
                    getChoosenExNumberIndex = "4";
                if(choosenExNumber.equals("تمرين 6"))
                    getChoosenExNumberIndex = "5";
                if(choosenExNumber.equals("تمرين 7"))
                    getChoosenExNumberIndex = "6";
                if(choosenExNumber.equals("تمرين 8"))
                    getChoosenExNumberIndex = "7";
                if(choosenExNumber.equals("تمرين 9"))
                    getChoosenExNumberIndex = "8";
                if(choosenExNumber.equals("تمرين 10"))
                    getChoosenExNumberIndex = "9";
                if(choosenExNumber.equals("تمرين 11"))
                    getChoosenExNumberIndex = "10";
                if(choosenExNumber.equals("تمرين 12"))
                    getChoosenExNumberIndex = "11";
                if(choosenExNumber.equals("تمرين 13"))
                    getChoosenExNumberIndex = "12";
                if(choosenExNumber.equals("تمرين 14"))
                    getChoosenExNumberIndex = "13";


                // First item is disable and it is used for hint
                if(position > 0){
                    retriveExtoEdit();
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

    }

    public void getCountImages() {
        FirebaseDatabase f = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = f.getReference().child("exercies");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Note ** : ondatachange discards the value of arraylist after it finishs
                indexofExercise = (int) dataSnapshot.getChildrenCount();
                Log.d("test","this is count : "+String.valueOf(indexofExercise));
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    private ArrayList<sets> populateList() {


        addNewSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExercise.setVisibility(v.VISIBLE);
                sets s2 = new sets();
                list.add(s2);
                newSetAdapter.notifyDataSetChanged();
            }
        });
        return list;
    }
    public void getExNames(){

        exNames.add(0,"اختر وجبة");
        FirebaseDatabase f = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = f.getReference().child("exercies");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Note ** : ondatachange discards the value of arraylist after it finishs

                arrayListObjectEx.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
                    arrayListObjectEx.add(dataSnapshot1.getValue(exercise.class));
                    exNames.add(dataSnapshot1.getValue(exercise.class).getExername());
                    Log.d("test","this is EEEEEEEe : "+dataSnapshot1.getValue(exercise.class));
//                    spinnerArrayAdapter2.notifyDataSetChanged();




                }
                selectedEx();




            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });

    }
    public void selectedEx(){

//        do
//        {
//            selectMu();
//            Log.d("test","vkffffffff"+ choosenMu);
//            break;
//        }
//        while (choosenMu.equals("اختر عضلة"));


        // Initializing an ArrayAdapter
        spSelectExName.setEnabled(false);
        if(!choosenMu.equals("اختر عضلة")) {
            spSelectExName.setEnabled(true);
            arrayListNamesEx = new ArrayList<>();


            arrayListNamesEx.clear();


            arrayListNamesEx.add(0, "اختر اسم التمرين");





            for (int i = 0; i < arrayListObjectEx.size(); i++) {
                Log.d("test", "this is size of MUNAme  :   " + choosenMu);
                if (choosenMu.equals(arrayListObjectEx.get(i).getTargetedmuscle())) {

                    s = arrayListObjectEx.get(i).getExername().toString();

                    arrayListNamesEx.add(s);
                }

            }



            spinnerArrayAdapter = new ArrayAdapter<String>(
                    getContext(), R.layout.support_simple_spinner_dropdown_item, arrayListNamesEx) {
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
            spSelectExName.setAdapter(spinnerArrayAdapter);
            spinnerArrayAdapter.notifyDataSetChanged();

            spSelectExName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItemText = (String) parent.getItemAtPosition(position);
                    choosenEx = selectedItemText;

                    addNewSet.setVisibility(View.VISIBLE);
                    //because first element is reserved as hint
                    for (int i = 0; i < arrayListObjectEx.size(); i++) {
                        Log.d("test","this is chosen EXXXXX Object"+arrayListObjectEx.get(i).getExername());
                        Log.d("test","this is chosen SSSSSIIZEE Object   "+arrayListObjectEx.size());
                        Log.d("test","this is chosen SSSSSIIZEE22 Object   "+arrayListNamesEx.size());


                        if (choosenEx.equals(arrayListObjectEx.get(i).getExername())) {
                            progressBar.setVisibility(View.VISIBLE);
                            eTest = (arrayListObjectEx.get(i));


                            exName = eTest.getExername();
                            imgUrl = eTest.getImage();
                            exMu = eTest.getTargetedmuscle();

                            exercise ex = new exercise();
                            ex.setExername(exName);


                            //get exercise name and its image
                            textViewExName.setText(exName);
//                            Picasso.get()
//                                    .load(imgUrl)
//                                    .placeholder(R.drawable.ic_loading)
//                                    .fit()
//                                    .centerCrop()
//                                    .into(imageViewEx);
                            Ion.with(getContext())
                                    .load(imgUrl)
                                    .withBitmap()
                                    .intoImageView(imageViewEx).setCallback(new FutureCallback<ImageView>() {
                                @Override
                                public void onCompleted(Exception e, ImageView result) {
                                    progressBar.setVisibility(View.GONE);

                                }
                            });


                        }
                    }
                    clickToEnlarge();
                    // If user change the default selection
                    // First item is disable and it is used for hint
                    if (position > 0) {
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
        }
    }
    public void selectedDay(){
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
        // Initializing an ArrayAdapter
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(), R.layout.support_simple_spinner_dropdown_item, day) {
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
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
                if(choosenDay.equals("Day1"))
                    choosenDay = "0";
                if(choosenDay.equals("Day2"))
                    choosenDay = "1";
                if(choosenDay.equals("Day3"))
                    choosenDay = "2";
                if(choosenDay.equals("Day4"))
                    choosenDay = "3";
                if(choosenDay.equals("Day5"))
                    choosenDay = "4";
                if(choosenDay.equals("Day6"))
                    choosenDay = "5";
                if(choosenDay.equals("Day7"))
                    choosenDay = "6";

                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getActivity().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                    //------------------------------------------------------------------
                    //get exercise count after we choose a day
                    getExCount();
                    spSelectEx.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void selectedUser(){
        // Initializing an ArrayAdapter
        Log.d("test","frist  "+arrayList.size());
        spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.support_simple_spinner_dropdown_item,arrayList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    //  tv.setTextColor(Color.GRAY);
                }
                else {
                    //   tv.setTextColor(Color.BLACK);
                }
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
                Log.d("test","thid dfjkdl : "+s);
                choosenUser = arrayList2.get(position);
                UserSharedPreferences.storeChosenUser(getContext(),choosenUser);
                // choosenUser = selectedItemText;
                usersprofile = FirebaseDatabase.getInstance().getReference();
                usersprofile.child(choosenUser);
                userProfile(choosenUser);

                getMonthTabs();

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

    public void addNewEx(){
        addNewExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddNewExerciseActivity.class));
            }
        });
    }

    public void getUsers(){
        firebaseDatabase = FirebaseDatabase.getInstance();

        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                arrayList.add(0, "اختر متدرب");
//                arrayList2.add(0, "اختر متدرب");


//                arrayList.clear();
//                arrayList2.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String email =  dataSnapshot1.child("Profile").child("userEmail").getValue(String.class);

                    Log.d("test", "this is DATAAADDDD&&&OOOOMMM :" +  dataSnapshot1.getKey());

                    Log.d("test", "this is DATAAADDDD&&&OOOOMMM :" +
                            dataSnapshot1.child("Profile").child("userEmail").getValue(String.class));


                    UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);
                    // arrayList.add(dataSnapshot1.getKey());


                    if(!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(dataSnapshot1.getKey())) {
                        arrayList.add(dataSnapshot1.child("Profile").child("userEmail").getValue(String.class));
                        arrayList2.add(dataSnapshot1.getKey());
                        spinnerArrayAdapter.notifyDataSetChanged();
                    }
                    //get chosenUser from shared preferences




//                    Log.d("test","this is size of arr: "+ array.length);


                    //      Log.d("test", "this is uid :" + dataSnapshot1.getKey());


                }
                p.dismiss();
                choosenUser = UserSharedPreferences.fetchChosenUser(Objects.requireNonNull(getContext()));
                for(int i =0; i < arrayList2.size(); i++){
                    if(choosenUser.equals(arrayList2.get(i))){
                        spSelectUser.setSelection(i);
                    }
                }
                //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Add_workout2user.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void clickToEnlarge() {
        imageViewEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPic();
            }
        });
    }
    private void showPic() {
        final Dialog nagDialog = new Dialog(getActivity()
                ,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // nagDialog.setCancelable(false);
        nagDialog.setCanceledOnTouchOutside(true);
        nagDialog.setContentView(R.layout.preview_image);
        Button btnClose = nagDialog.findViewById(R.id.btnIvClose);
        ImageView ivPreview = nagDialog.findViewById(R.id.iv_preview_image);
        final ProgressBar progressBar = nagDialog.findViewById(R.id.progressBar2);


        Ion.with(this)
                .load(imgUrl)
                .progressBar(progressBar)
                .withBitmap()
                // .placeholder(R.drawable.ic_loading)
                .intoImageView(ivPreview)
                .setCallback(new FutureCallback<ImageView>() {
                    @Override
                    public void onCompleted(Exception e, ImageView result) {
                        progressBar.setVisibility(View.GONE);
                    }
                });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                nagDialog.dismiss();
            }
        });
        nagDialog.show();
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
    private void retriveExtoEdit(){

        progressBar.setVisibility(View.VISIBLE);

        //reach to meal to edit
        DatabaseReference databaseReference1 = firebaseDatabaseMeal.getReference().child("users")
                .child(choosenUser).child("Exercises").child(chosenMonth).child(chosenWeek)
                .child(choosenDay).child("exercise").child(getChoosenExNumberIndex);

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setsArrayList.clear();
                exObject = dataSnapshot.getValue(exercise.class);
                if (exObject != null && !exObject.getImage().isEmpty()) {
//                    recyclerViewIngr.setVisibility(View.VISIBLE);
//                    recyclerViewIngr.setVisibility(View.VISIBLE);

                    addNewSet.setVisibility(View.VISIBLE);

                    setsArrayList.addAll(exObject.getSets());


                    showExDetails();

                    textViewExName.setText(exObject.getExername());
                    Picasso.get()
                            .load(exObject.getImage())
                            .fit()
                            .centerCrop()
                            .into(imageViewEx, new Callback() {
                                @Override
                                public void onSuccess() {
                                    progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });


                }else {
                    saveExercise.setVisibility(View.INVISIBLE);
                    textViewExName.setText("");
                    imageViewEx.setImageResource(R.drawable.welcome_screen);
                    progressBar.setVisibility(View.INVISIBLE);
                    newSetAdapter = new NewSetAdapter(setsArrayList, getContext());
                    recyclerView.setAdapter(newSetAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        
    }
    private void showExDetails(){
        saveExercise.setVisibility(View.VISIBLE);

        if (getActivity()!=null) {

            newSetAdapter = new NewSetAdapter(setsArrayList, this.getContext());
            recyclerView.setAdapter(newSetAdapter);
            newSetAdapter.notifyDataSetChanged();

            //exObject.setSets(null);
            eTest = exObject;

        }
    }

}
