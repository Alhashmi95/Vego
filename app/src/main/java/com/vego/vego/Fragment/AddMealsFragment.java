package com.vego.vego.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.vego.vego.Activity.ProfileActivity;
import com.vego.vego.R;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.MealIngr;
import com.vego.vego.model.UserInfo;

import java.util.ArrayList;
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
    String choosenUser, choosenDay;
    Spinner spSelectUser, spSelectDay;
    ArrayAdapter<String> spinnerArrayAdapter;
    UserInfo userinfo;
    DatabaseReference usersprofile;
    TextView profileName;
    TextView profileAge;
    TextView profileWeight;
    TextView profileHeight;
    TextView profileActivity;
    TextView profileGoal;

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

        getUsers();

        selectedUser();

        selectedDay();

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


        final Spinner spActivity = getView().findViewById(R.id.selectUser);
        final Spinner spGoal = getView().findViewById(R.id.spinner3);


        //String[] usersArr = arrayList.toArray(new String[0]);













    }
    public void userProfile(final String choosenUser){
        Log.d("test","this is path : "+usersprofile);
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users")
                .child(choosenUser).child("Profile");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               ;
             //   Log.d("test", userinfo.getName());

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);



                profileName.setText("Name: " + userinfo.getName());
                profileAge.setText("Age: " + userinfo.getAge());
                profileWeight.setText("Weight: " + userinfo.getWeight());
                profileHeight.setText("Height : "+ userinfo.getHeight());
                profileActivity.setText("مستوى النشاط: "+ userinfo.getUserActivity());
                profileGoal.setText("الهدف: "+userinfo.getUserGoal());
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
                "choose day...",
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
        final Spinner spSelectUser = getView().findViewById(R.id.selectUser);

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
        spSelectUser.setAdapter(spinnerArrayAdapter);
        spinnerArrayAdapter.notifyDataSetChanged();

//        Log.d("test",""+arrayList.size());




        spSelectUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                String s = arrayList.get(position);
                Log.d("test","thid dfjkdl : "+s);
                choosenUser = selectedItemText;
                usersprofile = FirebaseDatabase.getInstance().getReference();
                if (position!=0)
                userProfile(choosenUser);
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
    }

    public void getUsers(){
        firebaseDatabase = FirebaseDatabase.getInstance();

         DatabaseReference databaseReference = firebaseDatabase.getReference().child("users");

        arrayList=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayList.add(0,"اختر متدرب");
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
                    arrayList.add(dataSnapshot1.getKey());
                    spinnerArrayAdapter.notifyDataSetChanged();

//                    Log.d("test","this is size of arr: "+ array.length);


                    Log.d("test","this is uid :"+dataSnapshot1.getKey());
                }
                //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddMealsFragment.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
}
