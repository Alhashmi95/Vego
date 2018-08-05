package com.vego.vego.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.vego.vego.Activity.AddNewMealActivity;
import com.vego.vego.R;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.UserInfo;
import com.vego.vego.model.meal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
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
    Spinner spSelectUser, spSelectDay;
    ArrayAdapter<String> spinnerArrayAdapter,spinnerArrayAdapter2, spinnerArrayAdapter3
            ,spinnerArrayAdapter4;
    UserInfo userinfo;
    DatabaseReference usersprofile;
    TextView profileName;
    TextView profileAge;
    TextView profileWeight;
    TextView profileHeight;
    TextView profileActivity;
    TextView profileGoal;
    ArrayList<String> arrayListMeals=new ArrayList<>();
    ArrayList<meal> arrayListMealsObject=new ArrayList<>();
    DatabaseReference databaseReferenceAddMeal;
    Button addMealForUser, addNewMeal;
    ArrayList m = new ArrayList();
    ArrayList m2 = new ArrayList();
    ArrayList m3 = new ArrayList();
    ArrayList m4 = new ArrayList();
    ArrayList m5 = new ArrayList();
    meal mTest, mTest2, mTest3,mTest4,mTest5;
    FirebaseDatabase firebaseDatabaseMeal = FirebaseDatabase.getInstance();
    String mealNo1,mealNo2,mealNo3,mealNo4,mealNo5;
    ArrayList<String> arrayList2;






    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentHome.OnFragmentInteractionListener mListener;

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

         profileName = view.findViewById(R.id.tvProfileName);
        profileAge = view.findViewById(R.id.tvProfileAge);
        profileWeight = view.findViewById(R.id.tvWeight);
        profileHeight = view.findViewById(R.id.tvProfileHeight);
        profileActivity = view.findViewById(R.id.tvprofileActivity);
        profileGoal = view.findViewById(R.id.tvprofileGoal);
        spSelectUser = view.findViewById(R.id.selectUser);

//        radioButtonTrue = view.findViewById(R.id.radio_true);
//        radioButtonFalse = view.findViewById(R.id.radio_false);



        addNewMeal=view.findViewById(R.id.addNewMeal);

        addMealForUser = view.findViewById(R.id.saveDayMeal);


        getUsers();

        selectedUser();

        selectedDay();

        getMeal();
//
        selectedMeal();

//        changeRole();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference uidRef = usersRef.child("ZU7wS37XJUU6oeueYlciKWxx5X23");
        DatabaseReference zone1Ref = uidRef.child("Diet");
        DatabaseReference zone1NameRef = zone1Ref.child("0");
        DatabaseReference zone2dayRef = zone1NameRef.child("day");

        zone1NameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("test","this is day : "+ dataSnapshot.child("day").toString());

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

    public ArrayList<meal> getMeal(){

        arrayListMeals.add(0,"اختر وجبة");
//        spinnerArrayAdapter2.notifyDataSetChanged();

        FirebaseDatabase f = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = f.getReference().child("meals");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Note ** : ondatachange discards the value of arraylist after it finishs

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
                    arrayListMealsObject.add(dataSnapshot1.getValue(meal.class));
                    arrayListMeals.add(dataSnapshot1.getValue(meal.class).getName());
                    Log.d("test","this is meals : "+dataSnapshot1.getValue(meal.class));
//                    spinnerArrayAdapter2.notifyDataSetChanged();




                }
              selectedMeal();




            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });
        Log.d("test","this is size of arrMeal OBBBBJJJEEECCTtrtrt45454: "+ arrayListMealsObject.size());

        return arrayListMealsObject;
    }
    public void selectedMeal(){
        //This is first spinner (to add meal one to a specific user and day +++++++++++++++++++++++++++++++
        spSelectUser = getView().findViewById(R.id.selectMeal);
        m = new ArrayList<>();
        m.add(0,"اختر وجبة");


        // Initializing an ArrayAdapter
        for(int i =0; i<arrayListMealsObject.size(); i++){
            String s = arrayListMealsObject.get(i).getName().toString();
          //  Log.d("test","this is size of naaaaaaaaame  :   "+ arrayListMealsObject.get(i).getName());
            m.add(s);
        }
            spinnerArrayAdapter2 = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item
                ,m) {
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
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_item);
        spSelectUser.setAdapter(spinnerArrayAdapter2);
        spinnerArrayAdapter2.notifyDataSetChanged();

        spSelectUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                String s = arrayListMeals.get(position);
                choosenMeal = selectedItemText;
                firebaseDatabaseMeal = FirebaseDatabase.getInstance();

                //because first element is reserved as hint
                for(int i =0; i < m.size()-1; i++){
                    if(choosenMeal.equals(arrayListMealsObject.get(i).getName())){
                        mTest = (arrayListMealsObject.get(i));
                        // Log.d("test","this is chosen meal Object"+arrayListMealsObject.get(i).getName());
                    }
                }
                if(!spSelectUser.equals("اختر وجبة")){
                    mealNo1 = "0";
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
                if(position > 0){
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
        //This is second spinner (to add meal one to a specific user and day +++++++++++++++++++++++++++++++
        final Spinner spSelectUser2 = getView().findViewById(R.id.selectMeal2);
        m2 = new ArrayList<>();
        m2.add(0,"اختر وجبة");


        // Initializing an ArrayAdapter
        for(int i =0; i<arrayListMealsObject.size(); i++){
            String s = arrayListMealsObject.get(i).getName().toString();
            //  Log.d("test","this is size of naaaaaaaaame  :   "+ arrayListMealsObject.get(i).getName());
            m2.add(s);
        }
        spinnerArrayAdapter3 = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item
                ,m2) {
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
        spinnerArrayAdapter3.setDropDownViewResource(R.layout.spinner_item);
        spSelectUser2.setAdapter(spinnerArrayAdapter3);
        spinnerArrayAdapter3.notifyDataSetChanged();

        spSelectUser2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                String s = arrayListMeals.get(position);
                choosenMeal = selectedItemText;
                firebaseDatabaseMeal = FirebaseDatabase.getInstance();

                //because first element is reserved as hint
                for(int i =0; i < m.size()-1; i++){
                    if(choosenMeal.equals(arrayListMealsObject.get(i).getName())){
                        mTest2 = (arrayListMealsObject.get(i));
                        // Log.d("test","this is chosen meal Object"+arrayListMealsObject.get(i).getName());
                    }
                }
                if(!spSelectUser2.equals("اختر وجبة")){
                    mealNo2 = "1";
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
                if(position > 0){
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
        //This is third spinner (to add meal one to a specific user and day +++++++++++++++++++++++++++++++
        final Spinner spSelectUser3 = getView().findViewById(R.id.selectMeal3);
        m2 = new ArrayList<>();
        m2.add(0,"اختر وجبة");


        // Initializing an ArrayAdapter
        for(int i =0; i<arrayListMealsObject.size(); i++){
            String s = arrayListMealsObject.get(i).getName().toString();
            //  Log.d("test","this is size of naaaaaaaaame  :   "+ arrayListMealsObject.get(i).getName());
            m2.add(s);
        }
        spinnerArrayAdapter4 = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item
                ,m2) {
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
        spinnerArrayAdapter4.setDropDownViewResource(R.layout.spinner_item);
        spSelectUser3.setAdapter(spinnerArrayAdapter4);
        spinnerArrayAdapter4.notifyDataSetChanged();

        spSelectUser3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                String s = arrayListMeals.get(position);
                choosenMeal = selectedItemText;
                firebaseDatabaseMeal = FirebaseDatabase.getInstance();

                //because first element is reserved as hint
                for(int i =0; i < m.size()-1; i++){
                    if(choosenMeal.equals(arrayListMealsObject.get(i).getName())){
                        mTest3 = (arrayListMealsObject.get(i));
                        // Log.d("test","this is chosen meal Object"+arrayListMealsObject.get(i).getName());
                    }
                }
                if(!spSelectUser3.equals("اختر وجبة")){
                    mealNo3 = "2";
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
                if(position > 0){
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
        //This is forth spinner (to add meal one to a specific user and day +++++++++++++++++++++++++++++++
        final Spinner spSelectUser4 = getView().findViewById(R.id.selectMeal4);
        m2 = new ArrayList<>();
        m2.add(0,"اختر وجبة");


        // Initializing an ArrayAdapter
        for(int i =0; i<arrayListMealsObject.size(); i++){
            String s = arrayListMealsObject.get(i).getName().toString();
            //  Log.d("test","this is size of naaaaaaaaame  :   "+ arrayListMealsObject.get(i).getName());
            m2.add(s);
        }
        spinnerArrayAdapter4 = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item
                ,m2) {
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
        spinnerArrayAdapter4.setDropDownViewResource(R.layout.spinner_item);
        spSelectUser4.setAdapter(spinnerArrayAdapter4);
        spinnerArrayAdapter4.notifyDataSetChanged();

        spSelectUser4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                String s = arrayListMeals.get(position);
                choosenMeal = selectedItemText;
                firebaseDatabaseMeal = FirebaseDatabase.getInstance();

                //because first element is reserved as hint
                for(int i =0; i < m.size()-1; i++){
                    if(choosenMeal.equals(arrayListMealsObject.get(i).getName())){
                        mTest4 = (arrayListMealsObject.get(i));
                        // Log.d("test","this is chosen meal Object"+arrayListMealsObject.get(i).getName());
                    }
                }
                if(!spSelectUser4.equals("اختر وجبة")){
                    mealNo4 = "3";
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
                if(position > 0){
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
        //This is third spinner (to add meal one to a specific user and day +++++++++++++++++++++++++++++++
        final Spinner spSelectUser5 = getView().findViewById(R.id.selectMeal5);
        m2 = new ArrayList<>();
        m2.add(0,"اختر وجبة");


        // Initializing an ArrayAdapter
        for(int i =0; i<arrayListMealsObject.size(); i++){
            String s = arrayListMealsObject.get(i).getName().toString();
            //  Log.d("test","this is size of naaaaaaaaame  :   "+ arrayListMealsObject.get(i).getName());
            m2.add(s);
        }
        spinnerArrayAdapter4 = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item
                ,m2) {
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
        spinnerArrayAdapter4.setDropDownViewResource(R.layout.spinner_item);
        spSelectUser5.setAdapter(spinnerArrayAdapter4);
        spinnerArrayAdapter4.notifyDataSetChanged();

        spSelectUser5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                String s = arrayListMeals.get(position);
                choosenMeal = selectedItemText;
                firebaseDatabaseMeal = FirebaseDatabase.getInstance();

                //because first element is reserved as hint
                for(int i =0; i < m.size()-1; i++){
                    if(choosenMeal.equals(arrayListMealsObject.get(i).getName())){
                        mTest5 = (arrayListMealsObject.get(i));
                        // Log.d("test","this is chosen meal Object"+arrayListMealsObject.get(i).getName());
                    }
                }
                if(!spSelectUser5.equals("اختر وجبة")){
                    mealNo5 = "4";
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
                if(position > 0){
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



        addMealForUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if there's a chosen user and a chosen day
                if(choosenUser.equals("اختر متدرب") || choosenDay.equals("اختر يوم")){
                    Toast.makeText(getContext(),"please choose day and user",Toast.LENGTH_SHORT).show();
                }else {
                    //check if all spinners were selected then add the total calories
                    if(mTest != null && mTest2 != null && mTest3 != null && mTest4 != null && mTest5 != null) {
                        //calculating total sum of meals
                        double calSumOfMeals = Double.valueOf(mTest.getCal()) + Double.valueOf(mTest2.getCal())
                                + Double.valueOf(mTest3.getCal()) + Double.valueOf(mTest4.getCal())
                                + Double.valueOf(mTest5.getCal());
                        //Assigning total meals cals to the tree
                        DatabaseReference databaseReferenceTotalCal = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Diet").child(choosenDay).child("totalCals");
                        DatabaseReference databaseReferenceMealCount = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Diet").child(choosenDay).child("mealsCount");

                        //set number of meals
                        int mealCount = Integer.valueOf(mealNo1)+1;

                        databaseReferenceTotalCal.setValue(String.valueOf(calSumOfMeals));
                        databaseReferenceMealCount.setValue(String.valueOf(mealCount));

                    }
                    //check if all spinners were selected then add the total calories
                    if(mTest != null && mTest2 != null && mTest3 != null && mTest4 != null && mTest5 ==null) {
                        //calculating total sum of meals
                        double calSumOfMeals = Double.valueOf(mTest.getCal()) + Double.valueOf(mTest2.getCal())
                                + Double.valueOf(mTest3.getCal()) + Double.valueOf(mTest4.getCal());
                        //Assigning total meals cals to the tree
                        DatabaseReference databaseReferenceTotalCal = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Diet").child(choosenDay).child("totalCals");
                        DatabaseReference databaseReferenceMealCount = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Diet").child(choosenDay).child("mealsCount");

                        //set number of meals
                        int mealCount = Integer.valueOf(mealNo4)+1;

                        databaseReferenceTotalCal.setValue(String.valueOf(calSumOfMeals));
                        databaseReferenceMealCount.setValue(String.valueOf(mealCount));

                    }
                    //check if all spinners were selected then add the total calories
                    if(mTest != null && mTest2 != null && mTest3 != null && mTest4 == null && mTest5 == null) {
                        //calculating total sum of meals
                        double calSumOfMeals = Double.valueOf(mTest.getCal()) + Double.valueOf(mTest2.getCal())
                                + Double.valueOf(mTest3.getCal());
                        //Assigning total meals cals to the tree
                        DatabaseReference databaseReferenceTotalCal = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Diet").child(choosenDay).child("totalCals");
                        DatabaseReference databaseReferenceMealCount = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Diet").child(choosenDay).child("mealsCount");

                        //set number of meals
                        int mealCount = Integer.valueOf(mealNo3)+1;

                        databaseReferenceTotalCal.setValue(String.valueOf(calSumOfMeals));
                        databaseReferenceMealCount.setValue(String.valueOf(mealCount));

                    }
                    //check if all spinners were selected then add the total calories
                    if(mTest != null && mTest2 != null && mTest3 == null && mTest4 == null && mTest5 == null) {
                        //calculating total sum of meals
                        double calSumOfMeals = Double.valueOf(mTest.getCal()) + Double.valueOf(mTest2.getCal());
                        //Assigning total meals cals to the tree
                        DatabaseReference databaseReferenceTotalCal = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Diet").child(choosenDay).child("totalCals");
                        DatabaseReference databaseReferenceMealCount = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Diet").child(choosenDay).child("mealsCount");

                        //set number of meals
                        int mealCount = Integer.valueOf(mealNo2)+1;

                        databaseReferenceTotalCal.setValue(String.valueOf(calSumOfMeals));
                        databaseReferenceMealCount.setValue(String.valueOf(mealCount));

                    }
                    //check if all spinners were selected then add the total calories
                    if(mTest != null && mTest2 == null && mTest3 == null && mTest4 == null && mTest5 == null) {
                        //calculating total sum of meals
                        double calSumOfMeals = Double.valueOf(mTest.getCal());
                        //Assigning total meals cals to the tree
                        DatabaseReference databaseReferenceTotalCal = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Diet").child(choosenDay).child("totalCals");
                        DatabaseReference databaseReferenceMealCount = firebaseDatabaseMeal.getReference().child("users")
                                .child(choosenUser).child("Diet").child(choosenDay).child("mealsCount");

                        //set number of meals
                        int mealCount = Integer.valueOf(mealNo1)+1;

                        databaseReferenceTotalCal.setValue(String.valueOf(calSumOfMeals));
                        databaseReferenceMealCount.setValue(String.valueOf(mealCount));

                    }
                    //check if no meals are selected
                    if(mTest == null && mTest2 == null && mTest3 == null && mTest4 == null && mTest5 == null) {
                        Toast.makeText(getContext(),"please select at least one meal",Toast.LENGTH_SHORT).show();

                    }

                    DatabaseReference databaseReferenceDay = firebaseDatabaseMeal.getReference().child("users")
                            .child(choosenUser).child("Diet").child(choosenDay).child("day");





                    //add meals to corresponding user and day
                    DatabaseReference databaseReference1 = firebaseDatabaseMeal.getReference().child("users")
                            .child(choosenUser).child("Diet").child(choosenDay).child("dayMeals").child(mealNo1);
                    DatabaseReference databaseReference2 = firebaseDatabaseMeal.getReference().child("users")
                            .child(choosenUser).child("Diet").child(choosenDay).child("dayMeals").child(mealNo2);
                    DatabaseReference databaseReference3 = firebaseDatabaseMeal.getReference().child("users")
                            .child(choosenUser).child("Diet").child(choosenDay).child("dayMeals").child(mealNo3);
                    DatabaseReference databaseReference4 = firebaseDatabaseMeal.getReference().child("users")
                            .child(choosenUser).child("Diet").child(choosenDay).child("dayMeals").child(mealNo4);
                    DatabaseReference databaseReference5 = firebaseDatabaseMeal.getReference().child("users")
                            .child(choosenUser).child("Diet").child(choosenDay).child("dayMeals").child(mealNo5);


                    databaseReference1.setValue(mTest);
                    databaseReference2.setValue(mTest2);
                    databaseReference3.setValue(mTest3);
                    databaseReference4.setValue(mTest4);
                    databaseReference5.setValue(mTest5);


                    int chossenDayInt =Integer.valueOf(choosenDay)+1;
                    databaseReferenceDay.setValue(String.valueOf(chossenDayInt));
                }
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
        final Spinner spSelectDay = getView().findViewById(R.id.selectDay);
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,day){
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
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void selectedUser(){
        Spinner spSelectUser = getView().findViewById(R.id.selectUser);

        // Initializing an ArrayAdapter
        Log.d("test","frist  "+arrayList.size());
        spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arrayList){
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
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
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
               // choosenUser = selectedItemText;
                usersprofile = FirebaseDatabase.getInstance().getReference();
                usersprofile.child(choosenUser);
                userProfile(choosenUser);
                //Log.d("test","this is details " +usersprofile.child(choosenUser).child("profile"));
                // If user change the default selection
                // First item is disable and it is used for hint

                    // Notify the selected item text
                    Toast.makeText
                            (getActivity().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
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
                    String email =  dataSnapshot1.child("Profile").child("userEmail").getValue(String.class);

                    Log.d("test", "this is DATAAADDDD&&&OOOOMMM :" +  dataSnapshot1.getKey());

                    Log.d("test", "this is DATAAADDDD&&&OOOOMMM :" +
                            dataSnapshot1.child("Profile").child("userEmail").getValue(String.class));


                    UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);
                   // arrayList.add(dataSnapshot1.getKey());



                    arrayList.add( dataSnapshot1.child("Profile").child("userEmail").getValue(String.class));
                    arrayList2.add(dataSnapshot1.getKey());
                    spinnerArrayAdapter.notifyDataSetChanged();

//                    Log.d("test","this is size of arr: "+ array.length);


              //      Log.d("test", "this is uid :" + dataSnapshot1.getKey());

                    Log.d("test", "this is emails FFGFGGGF :" + userinfo.getEmail());

                }
                //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddMealsFragment.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//    databaseReference.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//
//            arrayList.add(0,"اختر متدرب");
//            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
//
//                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);
//                arrayList.add(dataSnapshot1.getKey());
//                spinnerArrayAdapter.notifyDataSetChanged();
//
////                    Log.d("test","this is size of arr: "+ array.length);
//
//
//                Log.d("test","this is uid :"+dataSnapshot1.getKey());
//            }
//            //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());
//
//
//
//
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            Toast.makeText(AddMealsFragment.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    });

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
}
