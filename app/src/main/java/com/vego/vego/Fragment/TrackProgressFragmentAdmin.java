package com.vego.vego.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vego.vego.R;
import com.vego.vego.model.UserInfo;

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

        currentWeight = view.findViewById(R.id.tvCurrentWeight);
        previousWeight = view.findViewById(R.id.tvPreviousWeight);
        perfectWeight = view.findViewById(R.id.tvPerfectWeight);

        updateWeights = view.findViewById(R.id.btnUpdateWeight);


        radioButtonTrue = view.findViewById(R.id.radio_true);
        radioButtonFalse = view.findViewById(R.id.radio_false);

     //   getWeights();
    }

//    public void changeRole(){
//        radioButtonTrue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!choosenUser.equals("اختر متدرب")) {
//                    String s = "this is true";
//                    Toast.makeText(getContext(), "helooooo    " + s, Toast.LENGTH_LONG).show();
//
//                    role = true;
//
//                    //set admin = true to specific user
//                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                    DatabaseReference databasaeReference = firebaseDatabase.getReference();
//
//
//
//                    databasaeReference.child("users").child(choosenUser).child("Profile").child("isAdmin")
//                            .setValue(String.valueOf(role));
//                }else {
//                    Toast.makeText(getContext(), "please choose user to set as admin", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//        radioButtonFalse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!choosenUser.equals("اختر متدرب")) {
//                    String s = "this is faaalse";
//                    Toast.makeText(getContext(), "helooooo    " + s, Toast.LENGTH_LONG).show();
//
//                    role = false;
//                    //set admin = true to specific user
//                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                    DatabaseReference databasaeReference = firebaseDatabase.getReference();
//
//
//
//                    databasaeReference.child("users").child(choosenUser).child("Profile").child("isAdmin")
//                            .setValue(String.valueOf(role));
//                }else {
//                    Toast.makeText(getContext(), "please choose user to set as admin", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//
//    }
    public void getWeights(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("users")
                .child(firebaseAuth.getUid()).child("Profile");



        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);


                currentWeight.setText(userinfo.getWeight());
                perfectWeight.setText(userinfo.getAdminBrief());
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
