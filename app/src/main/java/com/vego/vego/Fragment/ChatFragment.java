package com.vego.vego.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.vego.vego.Activity.UpdateProfileActivity;
import com.vego.vego.R;
import com.vego.vego.model.Chat;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    private DatabaseReference root;
    private String temp_key;
    private Button btn_send_msg;
    private EditText input_msg;
    private ListView listView_chat;
    ArrayList<String> list_chat = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private String name;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    ArrayList<UserInfo> userInfos = new ArrayList<>();

    ArrayList<String> usersUID = new ArrayList<>();

    ArrayList<String> adminUid = new ArrayList<>();

    ArrayList<Chat> messagesArray = new ArrayList<>();

    String isAdmin, userUid;


    public ChatFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

//        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                "mailto","admin@gmail.com", null));
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sci-FIT support");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
//        startActivity(Intent.createChooser(emailIntent, "Send email..."));

        getAllAdminsUid();

        root = FirebaseDatabase.getInstance().getReference().child("MainChatRoom");

        listView_chat = view.findViewById(R.id.listView_chat);
        input_msg = view.findViewById(R.id.input_msg);
        btn_send_msg = view.findViewById(R.id.btn_send_msg);

        name = "Ayman";
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("users")
                .child(firebaseAuth.getUid()).child("Profile");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);


                name = userinfo.getName();

                //start chat ++++++++++++++++++++++++++++++++++++++++
                //avoid crash context.lang
                if (getActivity() != null) {
                    arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list_chat);
                }
                listView_chat.setAdapter(arrayAdapter);

                btn_send_msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String, Object> map = new HashMap<String, Object>();
                        temp_key = root.push().getKey();
                        root.updateChildren(map);

                        for (int i = 0; i < adminUid.size(); i++) {
                            DatabaseReference message_root = root.child(adminUid.get(i) + " : " +
                                    firebaseAuth.getCurrentUser().getUid()).child(temp_key);

                            Map<String, Object> map2 = new HashMap<String, Object>();
                            map2.put("name", name);
                            map2.put("msg", input_msg.getText().toString());

                            message_root.updateChildren(map2);
                        }


                    }
                });


                root.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Add_Chat(dataSnapshot);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Add_Chat(dataSnapshot);

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


            private String chat_msg, chat_user_name;

            private void Add_Chat(final DataSnapshot dataSnapshot) {

                Iterator i = dataSnapshot.getChildren().iterator();
                input_msg.setText("");
                final Chat c = new Chat();

                //اي رسايل نظهر؟ يعني حقت ايت ادمن
                if (dataSnapshot.getKey().equals(adminUid.get(0) + " : " + firebaseAuth.getUid())) {
                    String tt= adminUid.get(0);
                    root.child(adminUid.get(0) + " : " + firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            int j = (int) dataSnapshot2.getChildrenCount();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                c.setNameChat((String) (ds.child("name").getValue(String.class)));
                                c.setMessageChat((String) (ds.child("msg")).getValue(String.class));

//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


                                list_chat.add(c.getnameChat() + " : " + c.getMessageChat());
                                arrayAdapter.notifyDataSetChanged();
                                listView_chat.setSelection(list_chat.size());
                            }
                            if (list_chat.size() > j) {
                                list_chat.clear();
                            }
                            if (list_chat.size() == 0) {
                                Add_Chat(dataSnapshot);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }

    private void getAllAdminsUid() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref2, ref3, ref4;
        ref2 = ref1.child("users");


        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    //Loop 1 to go through all the child nodes of users to get uid (key)
                    usersUID.add(dsp.getKey());
                    for(DataSnapshot dspProfile : dsp.getChildren()){
                        //loop 2 to go through all the child nodes of Profile node

                        if(dspProfile.getKey().equals("Profile"))
                        userInfos.add(dspProfile.getValue(UserInfo.class));
                    }


                }
                for(int i =0; i < userInfos.size(); i++){
                    String t = userInfos.get(i).getUid();
                    String v = usersUID.get(i);
                    if(userInfos.get(i).getUid().equals(usersUID.get(i))){
                        isAdmin =userInfos.get(i).getAdmin();
                    }
                    if(isAdmin.equals("true")){
                        userUid = usersUID.get(i);
                        adminUid.add(userUid);
                    }
                }
    /* userlist will store all values of users, then point to every userlist item
    and get mobile numbers*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
            }
        });
    }
}
