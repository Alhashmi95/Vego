package com.vego.vego.Fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vego.vego.Adapters.UsersAdapter;
import com.vego.vego.R;
import com.vego.vego.model.Chat;
import com.vego.vego.model.UserInfo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSupportList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentSupportList extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FirebaseDatabase firebaseDatabase;
    ArrayList<UserInfo> userinfo;
    UsersAdapter adapter,adapter2;
    RecyclerView recyclerView,recyclerView2;
    ArrayList<Chat> c = new ArrayList<>();
    ArrayList<Chat> cEmpty = new ArrayList<>();

    ArrayList<UserInfo> userList = new ArrayList<>();
    ProgressDialog p;

    SwipeRefreshLayout swipeLayout;



    private OnFragmentInteractionListener mListener;
    private ArrayList<UserInfo> uid = new ArrayList<>();
    boolean checker = false;

    public FragmentSupportList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_support_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getUsers();

        recyclerView = view.findViewById(R.id.rv_support);
 //       recyclerView2 = view.findViewById(R.id.rv_support2);

        p = new ProgressDialog(getContext());
        p.setMessage("جاري التحميل... ");
        p.show();
        p.setCancelable(false);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getUsers();
//    }

    public void getUsers() {
        userinfo = new ArrayList<>();
        userList.clear();
        userinfo.clear();

        if(checker) {
            p = new ProgressDialog(getContext());
            p.setMessage("جاري التحميل... ");
            p.show();
        }


        firebaseDatabase = FirebaseDatabase.getInstance();


        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    //Loop 1 to go through all the child nodes of users to get uid (key)
                    for (DataSnapshot dspProfile : dsp.getChildren()) {
                        //loop 2 to go through all the child nodes of Profile node

                        if (dspProfile.getKey().equals("Profile"))
                            userinfo.add(dspProfile.getValue(UserInfo.class));
                    }


                }
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference userRef = rootRef.child("chat");
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            for (int i = 0; i < userinfo.size(); i++) {
                                if (userinfo.get(i).getUid().equals(ds.getKey())) {
                                    Query q = ds.child("Masseges").getRef().orderByKey().limitToLast(1);

                                    int finalI = i;
                                    ValueEventListener valueEventListener = new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                c.add(child.getValue(Chat.class));
                                                userList.add(userinfo.get(finalI));


                                            }
                                            recyclerView.setHasFixedSize(true);
                                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                            adapter = new UsersAdapter(userList, getContext(), c);
                                            recyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();

                                            p.dismiss();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    };
                                    q.addListenerForSingleValueEvent(valueEventListener);
                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                userRef.addValueEventListener(eventListener);

                //usersWithoutChat();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void usersWithoutChat(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child("chat");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(int i =0; i < userinfo.size(); i++){
                    if (!dataSnapshot.child(userinfo.get(i).getUid()).exists()) {
                        Chat emptyChat = new Chat("","","","","","");
                        uid.add(userinfo.get(i));
                        cEmpty.add(emptyChat);

                        recyclerView2.setHasFixedSize(true);
                        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter2 = new UsersAdapter(uid, getContext(), cEmpty);
                        recyclerView2.setAdapter(adapter2);
                        adapter2.notifyDataSetChanged();


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onRefresh() {
        // Reload current fragment
        Fragment frg = null;
        String tag = FragmentSupportList.this.getTag();
        assert getFragmentManager() != null;
        frg = getFragmentManager().findFragmentByTag(tag);
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        // Reload current fragment
//        Fragment frg = null;
//        String tag = FragmentSupportList.this.getTag();
//        assert getFragmentManager() != null;
//        frg = getFragmentManager().findFragmentByTag(tag);
//        final FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(frg);
//        ft.attach(frg);
//        ft.commit();
//    }

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
