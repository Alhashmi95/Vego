package com.vego.vego.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vego.vego.Adapters.MusclesAdapter;
import com.vego.vego.R;
import com.vego.vego.model.Mucsle;
import com.vego.vego.model.exercise;

import java.util.ArrayList;

public class FragmentWallet extends Fragment {
    ArrayList<exercise> ex2List = new ArrayList<>();
    RecyclerView myrv;
    MusclesAdapter myAdapter;
    ArrayList<Mucsle> MuList = new ArrayList<>();
    String mucsleName;

    ProgressDialog p;


    static int sizeMuList = 0;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet , container,false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        p = new ProgressDialog(getContext());
        p.setTitle("تحميل");
        p.setMessage("جاري التحميل ...");
        p.show();


            myrv = view.findViewById(R.id.recyclerview_id);


        myrv.setLayoutManager(new GridLayoutManager(getContext(),3));

//        Intent intent= getActivity().getIntent();
//
//
//        mucsleName = intent.getStringExtra("mucsleName");
//




        //   ex2List.add(new exercise("lfdg","pflg","lf"));






        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        //go to the child where you want to retreive values of
        DatabaseReference usersRef = rootRef.child("exerciesForALL");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){


                    ex2List.add(ds.getValue(exercise.class));

                }


                Log.d("test", "this is ex2Meals   : "+ex2List.size());


//                                "اكتاف",
//                                 "باي",
//                                "بطن",
//                               "ترابيس",
//                                "تراي",
//                                "ذراع",
//                              "صدر",
//                                "ظهر",
//                                "قدم",
//                               "عضلة السمانة",







                MuList.add(new Mucsle("اكتاف",R.drawable.mu_shoulders,ex2List));
                MuList.add(new Mucsle("باي",R.drawable.mu_biceps,ex2List));
                MuList.add(new Mucsle("بطن",R.drawable.mu_abs,ex2List));
                MuList.add(new Mucsle("ترابيس",R.drawable.mu_trabiz,ex2List));
                MuList.add(new Mucsle("تراي",R.drawable.mu_triceps,ex2List));
                MuList.add(new Mucsle("صدر",R.drawable.mu_chest,ex2List));
                MuList.add(new Mucsle("ظهر",R.drawable.mu_bavk,ex2List));
                MuList.add(new Mucsle("افخاذ",R.drawable.mu_quadriceps,ex2List));
                MuList.add(new Mucsle("سواعد",R.drawable.mu_swa3d,ex2List));
                MuList.add(new Mucsle("كارديو",R.drawable.ic_cardio,ex2List));
                MuList.add(new Mucsle("عضلة السمانة",R.drawable.mu_gastrocnemius,ex2List));



                // sizeMuList = MuList.size();



//                for(int i =0 ; i<MuList.size(); i++) {
//                    if (mucsleName.equals(MuList.get(i).getMucsleName())) {
//
//                    }
//                }


                //Set adapter
                myAdapter = new MusclesAdapter(FragmentWallet.this.getContext(),MuList);
                myrv.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();

                p.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);








//            ex2List.add(new exercise("54","fdkjfkld","354"));
//            ex2List.add(new exercise("54","glgkhgf","354"));
//            ex2List.add(new exercise("54","fdgfdkgklf","354"));
//            ex2List.add(new exercise("54","gkjdfgkjdf","354"));
//            ex2List.add(new exercise("54","reioeiu","354"));



        }
    }
