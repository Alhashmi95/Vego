package com.vego.vego.Fragment;

import android.content.Intent;
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
import com.vego.vego.Adapters.DaysAdapterExercise;
import com.vego.vego.Adapters.RecyclerViewAdapter;
import com.vego.vego.R;
import com.vego.vego.model.Exercises;
import com.vego.vego.model.Mucsle;
import com.vego.vego.model.exercise;
import com.vego.vego.model.sets;

import java.util.ArrayList;

public class FragmentWallet extends Fragment {
    ArrayList<exercise> ex2List = new ArrayList<>();
    RecyclerView myrv;
    RecyclerViewAdapter myAdapter;
    ArrayList<Mucsle> MuList = new ArrayList<>();
    String mucsleName;

    static int sizeMuList = 0;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet , container,false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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



                MuList.add(new Mucsle("بطن",R.drawable.age,ex2List));
                MuList.add(new Mucsle("تراي",R.drawable.age,ex2List));
                MuList.add(new Mucsle("صدر",R.mipmap.ic_muchest,ex2List));
                MuList.add(new Mucsle("ترابيس",R.drawable.age,ex2List));
                MuList.add(new Mucsle("قدم",R.drawable.age,ex2List));
                MuList.add(new Mucsle("ذراع",R.drawable.age,ex2List));
                MuList.add(new Mucsle("راس",R.drawable.age,ex2List));
                MuList.add(new Mucsle("زعبولة",R.drawable.age,ex2List));

               // sizeMuList = MuList.size();



//                for(int i =0 ; i<MuList.size(); i++) {
//                    if (mucsleName.equals(MuList.get(i).getMucsleName())) {
//
//                    }
//                }


                //Set adapter
                myAdapter = new RecyclerViewAdapter(FragmentWallet.this.getContext(),MuList);
                myrv.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();


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
