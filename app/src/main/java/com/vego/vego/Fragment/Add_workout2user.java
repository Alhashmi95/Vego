package com.vego.vego.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vego.vego.Activity.AddNewExerciseActivity;
import com.vego.vego.Activity.AdminActivity;
import com.vego.vego.Activity.LoginActivity;
import com.vego.vego.Activity.SignupActivity;
import com.vego.vego.Adapters.DaysAdapter;
import com.vego.vego.Adapters.NewElementAdapter;
import com.vego.vego.Adapters.NewSetAdapter;
import com.vego.vego.R;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.elements;
import com.vego.vego.model.exercise;
import com.vego.vego.model.meal;
import com.vego.vego.model.sets;

import java.util.ArrayList;
import java.util.List;


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
    private DaysAdapter adapter;
    private List<DietDay> dayList;
    DatabaseReference usersprofile;
    Button addNewSet, addNewExercise, saveExercise;
    FirebaseDatabase firebaseDatabaseExercise = FirebaseDatabase.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    ArrayList<String> arrayListNamesEx, arrayList;
    ArrayAdapter<String> spinnerArrayAdapter;
    String choosenUser, choosenDay, choosenEx, choosenExNumber, getChoosenExNumberIndex, choosenMu;
    Spinner spSelectUser, spSelectDay, spSelectEx, spSelectMu,spSelectExName;
    ArrayList exNames = new ArrayList();
    ArrayList<exercise> arrayListObjectEx = new ArrayList<>();
    exercise eTest;
    ImageView imageViewEx;
    TextView textViewExName;
    ArrayList<sets> setsArrayList = new ArrayList<>();
    NewSetAdapter newSetAdapter;
    ArrayList<sets> list = new ArrayList<>();
    int indexofExercise = 0;

    String exName;
    String imgUrl;
    String exMu;
    String s;

    FirebaseDatabase firebaseDatabaseMeal = FirebaseDatabase.getInstance();





    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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


//        sets s = new sets();
//        list.add(s);


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


    }
    public void saveExerciseToUser(){
        saveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if there's a chosen user and a chosen day
                if(choosenUser.equals("اختر متدرب") || choosenDay.equals("اختر يوم") ||
                        choosenMu.equals("اختر عضلة") || (choosenExNumber.equals("اختر تمرين"))
                        || choosenEx.equals("اختر اسم التمرين")){
                    Toast.makeText(getContext(),"please choose all spinners",Toast.LENGTH_SHORT).show();
                }else {


                    DatabaseReference databaseReferenceDay = firebaseDatabaseMeal.getReference().child("users")
                            .child(choosenUser).child("Exercises").child(choosenDay).child("day");



                    //add meals to corresponding user and day
                    DatabaseReference databaseReference1 = firebaseDatabaseMeal.getReference().child("users")
                            .child(choosenUser).child("Exercises").child(choosenDay).child("exercise")
                            .child(getChoosenExNumberIndex);

                    int chossenDayInt =Integer.valueOf(choosenDay)+1;
                    databaseReferenceDay.setValue(String.valueOf(chossenDayInt));

                    eTest.setSets(setsArrayList);

                    databaseReference1.setValue(eTest);
                    Toast.makeText(getContext(),"successfully added exercise to user",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getContext(), AdminActivity.class);

                    getContext().startActivity(intent);


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
                "صدر",
                "بطن",
                "ذراع",
                "ظهر",
                "قدم",
                "ترابيس",
                "تراي",
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
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spSelectMu.setAdapter(spinnerArrayAdapter);

        spSelectMu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                choosenMu = selectedItemText;
                // If user change the default selection


                arrayListNamesEx = new ArrayList<>();




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
        String[] day = new String[]{
                "اختر تمرين",
                "تمرين 1",
                "تمرين 2",
                "تمرين 3",
                "تمرين 4",
                "تمرين 5",
                "تمرين 6",
                "تمرين 7",
                "تمرين 8",
                "تمرين 9",
        };
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
                    getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item
                    , arrayListNamesEx) {
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
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            spSelectExName.setAdapter(spinnerArrayAdapter);
            spinnerArrayAdapter.notifyDataSetChanged();

            spSelectExName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItemText = (String) parent.getItemAtPosition(position);
                    choosenEx = selectedItemText;
                    //because first element is reserved as hint
                    for (int i = 0; i < arrayListNamesEx.size() - 1; i++) {
                        if (choosenEx.equals(arrayListObjectEx.get(i).getExername())) {
                            eTest = (arrayListObjectEx.get(i));
                            // Log.d("test","this is chosen meal Object"+arrayListMealsObject.get(i).getName());


                            exName = eTest.getExername();
                            imgUrl = eTest.getImage();
                            exMu = eTest.getTargetedmuscle();

                            exercise ex = new exercise();
                            ex.setExername(exName);


                            //get exercise name and its image
                            textViewExName.setText(exName);
                            Picasso.get()
                                    .load(imgUrl)
                                    .placeholder(R.drawable.ic_loading)
                                    .fit()
                                    .centerCrop()
                                    .into(imageViewEx);


                        }
                    }
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
        // Initializing an ArrayAdapter
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
                choosenUser = selectedItemText;
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

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users");

        arrayList=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayList.add(0,"اختر متدرب");
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    arrayList.add(dataSnapshot1.getKey());
                    spinnerArrayAdapter.notifyDataSetChanged();

//                    Log.d("test","this is size of arr: "+ array.length);


                    Log.d("test","this is uid :"+dataSnapshot1.getKey());
                }
                //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Add_workout2user.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
}