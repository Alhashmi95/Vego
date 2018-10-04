package com.vego.vego.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.vego.vego.Activity.UpdateProfileActivity;
import com.vego.vego.R;
import com.vego.vego.model.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrackProgressFragmentUser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrackProgressFragmentUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackProgressFragmentUser extends Fragment {
    private EditText currentWeight;
    private TextView previousWeight, perfectWeight;
    private Button updateWeights;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    String previousW;
    ProgressDialog p;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TrackProgressFragmentUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackProgressFragmentUser.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackProgressFragmentUser newInstance(String param1, String param2) {
        TrackProgressFragmentUser fragment = new TrackProgressFragmentUser();
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
        return inflater.inflate(R.layout.fragment_track_progress_fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentWeight = view.findViewById(R.id.tvCurrentWeight);
        previousWeight = view.findViewById(R.id.tvPreviousWeight);
        perfectWeight = view.findViewById(R.id.tvPerfectWeight);

        perfectWeight.setMovementMethod(new ScrollingMovementMethod());

        updateWeights = view.findViewById(R.id.btnUpdateWeight);

        p = new ProgressDialog(getContext());
        p.setTitle("Loading");
        p.setMessage("Fetching data...");
        p.show();


        getWeights();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("users")
                .child(firebaseAuth.getUid()).child("Profile");



        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);



                previousW =userinfo.getWeight();

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databasaeReference = firebaseDatabase.getReference();


                databasaeReference.child("users").child(firebaseAuth.getUid()).child("Profile")
                        .child("previousWeight")
                        .setValue(previousW);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TrackProgressFragmentUser.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });



        updateWeight();
    }
    public void getWeights(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("users")
                .child(firebaseAuth.getUid()).child("Profile");



        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);


             //   currentWeight.setText(userinfo.getPreviousWeight());
                perfectWeight.setText(userinfo.getAdminBrief());
                previousWeight.setText(userinfo.getPreviousWeight());

                p.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TrackProgressFragmentUser.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void updateWeight(){
        updateWeights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cuWeight = currentWeight.getText().toString().trim();

              //  previousWeight.setText(cuWeight);

                    //set admin = true to specific user
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databasaeReference = firebaseDatabase.getReference();



                    databasaeReference.child("users").child(firebaseAuth.getUid()).child("Profile").child("weight")
                            .setValue(cuWeight);


                    Toast.makeText(TrackProgressFragmentUser.this.getContext(),"تم تحديث الوزن",Toast.LENGTH_LONG).show();



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
