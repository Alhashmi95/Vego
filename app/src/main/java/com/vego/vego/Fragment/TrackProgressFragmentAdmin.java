package com.vego.vego.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vego.vego.R;
import com.vego.vego.Storage.UserSharedPreferences;
import com.vego.vego.model.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrackProgressFragmentAdmin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrackProgressFragmentAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackProgressFragmentAdmin extends Fragment {
    private EditText perfectWeight;
    private TextView previousWeight, currentWeight;
    private Button updateWeights;

    RadioButton radioButtonTrue, radioButtonFalse;

    boolean role;

    ArrayList<String> arrayList, arrayList2;
    String choosenUser, choosenUser2, brief;
    Spinner spSelectUser, spSelectDay;
    ArrayAdapter<String> spinnerArrayAdapter;
    ProgressDialog p;

    String masterPassword = "احمد المدني";
    String masterPassEnter = "";

    boolean checker = false;


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TrackProgressFragmentAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackProgressFragmentAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackProgressFragmentAdmin newInstance(String param1, String param2) {
        TrackProgressFragmentAdmin fragment = new TrackProgressFragmentAdmin();
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
        return inflater.inflate(R.layout.fragment_track_progress_fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        p = new ProgressDialog(getContext());
        p.setTitle("تحميل");
        p.setMessage("يرجى الانتظار");
        p.show();
        p.setCancelable(false);

        currentWeight = view.findViewById(R.id.tvCurrentWeight);
        previousWeight = view.findViewById(R.id.tvPreviousWeight);
        perfectWeight = view.findViewById(R.id.etBrief);

        updateWeights = view.findViewById(R.id.btnUpdateWeight);


        radioButtonTrue = view.findViewById(R.id.radio_true);
        radioButtonFalse = view.findViewById(R.id.radio_false);

        firebaseAuth = FirebaseAuth.getInstance();


        getUsers();
        selectedUser();


    }

    public void uploadBrief() {
        updateWeights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!perfectWeight.getText().toString().isEmpty() && perfectWeight.getText().toString().length() > 10) {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databasaeReference = firebaseDatabase.getReference();


                    databasaeReference.child("users").child(choosenUser).child("Profile")
                            .child("adminBrief")
                            .setValue(perfectWeight.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(TrackProgressFragmentAdmin.this.getContext(), "Brief has been successfully added",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(TrackProgressFragmentAdmin.this.getContext(), "Please Enter A Long brief ,,",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void selectedUser() {
        spSelectUser = getView().findViewById(R.id.selectUser);

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
                choosenUser2 = arrayList.get(position);
                UserSharedPreferences.storeChosenUser(getContext(), choosenUser);
                getWeights();
                changeRole();
                uploadBrief();
                // choosenUser = selectedItemText;
//                usersprofile = FirebaseDatabase.getInstance().getReference();
//                usersprofile.child(choosenUser);
//                userProfile(choosenUser);
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
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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


                    if (!firebaseAuth.getCurrentUser().getUid().equals(dataSnapshot1.getKey())) {
                        arrayList.add(dataSnapshot1.child("Profile").child("userEmail").getValue(String.class));
                        arrayList2.add(dataSnapshot1.getKey());
                    }
                    spinnerArrayAdapter.notifyDataSetChanged();

                    p.dismiss();

//                    Log.d("test","this is size of arr: "+ array.length);


                    //      Log.d("test", "this is uid :" + dataSnapshot1.getKey());

                    //   Log.d("test", "this is emails FFGFGGGF :" + userinfo.getEmail());

                }
                choosenUser = UserSharedPreferences.fetchChosenUser(Objects.requireNonNull(getContext()));
                for (int i = 0; i < arrayList2.size(); i++) {
                    if (choosenUser.equals(arrayList2.get(i))) {
                        spSelectUser.setSelection(i);
                    }
                }
                //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TrackProgressFragmentAdmin.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changeRole() {
        radioButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterPassEnter = "";
                //========================================================
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                final EditText edittext = new EditText(getContext());
                alert.setMessage("ادخل الرقم السري");
                alert.setTitle("Master Password");

                alert.setView(edittext);

                alert.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        //OR
                        masterPassEnter = edittext.getText().toString();

                        //=====================================================
                        if (!choosenUser.equals("اختر متدرب") && masterPassEnter.equals(masterPassword)) {
                            String s = choosenUser2;
                            Toast.makeText(getContext(), s + " اصبح الان مدرب ", Toast.LENGTH_LONG).show();

                            role = true;

                            //set admin = true to specific user
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databasaeReference = firebaseDatabase.getReference();


                            databasaeReference.child("users").child(choosenUser).child("Profile").child("isAdmin")
                                    .setValue(String.valueOf(role));
                        } else {
                            Toast.makeText(getContext(), "كلمة المرور غير صحيحة", Toast.LENGTH_LONG).show();
                            radioButtonTrue.setChecked(false);
                        }


                    }


                });
                alert.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    radioButtonTrue.setChecked(false);
                    radioButtonFalse.setChecked(false);
                    }
                });

                alert.show();

            }
        });


        radioButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterPassEnter = "";
                //========================================================
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                final EditText edittext = new EditText(getContext());
                alert.setMessage("ادخل الرقم السري");
                alert.setTitle("Master Password");

                alert.setView(edittext);

                alert.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        //OR
                        masterPassEnter = edittext.getText().toString();

                        //=====================================================
                        if (!choosenUser.equals("اختر متدرب") && masterPassEnter.equals(masterPassword)) {
                            String s = choosenUser2;
                            Toast.makeText(getContext(), s + " اصبح الان متدرب (مستخدم) ", Toast.LENGTH_LONG).show();

                            role = false;

                            //set admin = true to specific user
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databasaeReference = firebaseDatabase.getReference();


                            databasaeReference.child("users").child(choosenUser).child("Profile").child("isAdmin")
                                    .setValue(String.valueOf(role));
                        } else {
                            Toast.makeText(getContext(), "كلمة المرور غير صحيحة", Toast.LENGTH_LONG).show();
                            radioButtonFalse.setChecked(false);
                        }


                    }


                });
                alert.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        radioButtonTrue.setChecked(false);
                        radioButtonFalse.setChecked(false);
                    }
                });

                alert.show();

            }
        });


    }

    public void getWeights() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("users")
                .child(choosenUser).child("Profile");


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);


                currentWeight.setText(userinfo.getWeight());
                //   perfectWeight.setText(userinfo.getAdminBrief());
                previousWeight.setText(userinfo.getPreviousWeight());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
