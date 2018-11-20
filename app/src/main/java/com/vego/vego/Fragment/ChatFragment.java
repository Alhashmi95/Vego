package com.vego.vego.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.vego.vego.Activity.InsideChatActivity;
import com.vego.vego.Activity.UpdateProfileActivity;
import com.vego.vego.Adapters.MessageListAdapter;
import com.vego.vego.R;
import com.vego.vego.Request.FirebaseSendNotification;
import com.vego.vego.model.Chat;
import com.vego.vego.model.DietDay;
import com.vego.vego.model.UserInfo;
import com.vego.vego.response.ResponseFcmToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment implements ResponseFcmToken {
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

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;

    ArrayList<UserInfo> userInfos = new ArrayList<>();

    ArrayList<String> usersUID = new ArrayList<>();

    ArrayList<String> adminUid = new ArrayList<>();

    ArrayList<Chat> messagesArray = new ArrayList<>();

    String isAdmin, userUid, currentDate, currentTime , message;

    boolean checker = false;

    int j=0;
    private String receiverToken;

    ProgressDialog p;


    public ChatFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bubbleschat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

        //bubbles
        mMessageRecycler = (RecyclerView) view.findViewById(R.id.reyclerview_message_list);

        mMessageAdapter = new MessageListAdapter(getContext(), messagesArray);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(layoutManager);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageAdapter.notifyDataSetChanged();


        root = FirebaseDatabase.getInstance().getReference().child("chat");

        p = new ProgressDialog(getContext());
        p.setMessage("جاري التحميل");
        p.show();
        p.setCancelable(false);





//        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                "mailto","admin@gmail.com", null));
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sci-FIT support");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
//        startActivity(Intent.createChooser(emailIntent, "Send email..."));

        getAllAdminsUid();





        //root = FirebaseDatabase.getInstance().getReference().child("MainChatRoom");

//        listView_chat = view.findViewById(R.id.listView_chat);
        input_msg = view.findViewById(R.id.et_chatbox);
        btn_send_msg = view.findViewById(R.id.button_chatbox_send);

        name = "Ayman";
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("users")
                .child(firebaseAuth.getUid()).child("Profile");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);


                name = userinfo.getName();

                //start chat ++++++++++++++++++++++++++++++++++++++++
                //avoid crash context.lang
                if (getActivity() != null) {
                    arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list_chat);
                }
                //listView_chat.setAdapter(arrayAdapter);

                btn_send_msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(input_msg.getText().toString().isEmpty()){


                        }else {

                            //getting current date and time
                            Calendar calForDate = Calendar.getInstance();
                            DateFormat currentDateFormat2 = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
                            //currentDateFormat2.setTimeZone();
                            currentDate = currentDateFormat2.format(calForDate.getTime());

                            Calendar calForTime = Calendar.getInstance();
                            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("MMM d, yyyy HH:mm:ss",Locale.ENGLISH);
                            currentTime = currentTimeFormat.format(calForTime.getTime());


                            Map<String, Object> map = new HashMap<String, Object>();
                            temp_key = root.push().getKey();
                            root.updateChildren(map);


                            //for (int i = 0; i < adminUid.size(); i++) {
                                DatabaseReference message_root = root.child(firebaseAuth.getCurrentUser()
                                        .getUid()).child("Masseges").child(temp_key);

                                message = input_msg.getText().toString();
                                String newMessage = message.replace("\n", "");

                                Map<String, Object> map2 = new HashMap<String, Object>();
                                map2.put("sendername", name);
                                map2.put("msg", newMessage);
                                map2.put("senderId", firebaseAuth.getCurrentUser().getUid());
                                // map2.put("createdAt", currentTime);
                                map2.put("date", currentTime);
                                map2.put("tokenSender",FirebaseInstanceId.getInstance().getToken());



                            //}

                            DatabaseReference tokenRef = firebaseDatabase.getReference().child("Ayman")
                                    .child(adminUid.get(0)).child("userToken");

                            tokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null) {
                                        receiverToken = dataSnapshot.getValue().toString();

                                        map2.put("tokenReceiver",receiverToken);
                                        message_root.updateChildren(map2);


                                        sendNotification("لديك رسالة جديدة من " + name, newMessage,
                                                receiverToken, firebaseAuth.getUid(), name, FirebaseInstanceId.getInstance().getToken());
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                            input_msg.setText("");
                        }


                    }
                });
//                root.child("RXj8wS9C8gfJ4QFpRF0VSwct63p2" + " : " + firebaseAuth
//                        .getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        Add_Chat(dataSnapshot);
//                        mMessageAdapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                        Add_Chat(dataSnapshot);
//                        mMessageAdapter.notifyDataSetChanged();
//
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });



                root.child(firebaseAuth.getCurrentUser().getUid()).child("Masseges")
                        .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Add_Chat(dataSnapshot);
                        mMessageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Add_Chat(dataSnapshot);
                        mMessageAdapter.notifyDataSetChanged();

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
                root.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.child(firebaseAuth.getCurrentUser().getUid()).exists()){
                            p.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



            private String chat_msg, chat_user_name;

            private void Add_Chat(final DataSnapshot dataSnapshot) {
                   // if (input_msg.length() > 0) {
                //check if there's a new message =====================================
                root.child(firebaseAuth.getCurrentUser().getUid()).child("Masseges")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        j = (int) dataSnapshot.getChildrenCount();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                        Iterator i = dataSnapshot.getChildren().iterator();
                      //  input_msg.setText("");
                        Chat c = new Chat();

//                        c.setMessageChat(message);
//                        c.setCreatedAt(currentTime);
//                        c.setUserUID(firebaseAuth.getCurrentUser().getUid());

                        //messagesArray.add(c);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setStackFromEnd(true);
                        mMessageRecycler.setLayoutManager(layoutManager);

                        mMessageAdapter.notifyDataSetChanged();

//                        //==================================================================
//                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
//                        DatabaseReference ref2, ref3, ref4;
//                        ref2 = ref1.child("users");
//
//
//                        ref2.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                // Result will be holded Here
//                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                                    //Loop 1 to go through all the child nodes of users to get uid (key)
//                                    usersUID.add(dsp.getKey());
//                                    for (DataSnapshot dspProfile : dsp.getChildren()) {
//                                        //loop 2 to go through all the child nodes of Profile node
//
//                                        if (dspProfile.getKey().equals("Profile"))
//                                            userInfos.add(dspProfile.getValue(UserInfo.class));
//                                    }
//
//
//                                }
//                                for (int i = 0; i < userInfos.size(); i++) {
//                                    String t = userInfos.get(i).getUid();
//                                    String v = usersUID.get(i);
//                                    if (userInfos.get(i).getUid().equals(usersUID.get(i))) {
//                                        isAdmin = userInfos.get(i).getAdmin();
//                                    }
//                                    if (isAdmin.equals("true")) {
//                                        userUid = usersUID.get(i);
//                                        adminUid.add(userUid);
//                                    }
//                                }
//                            }
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//                                    //handle databaseError
//                                }
//                            });

                        DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                                .child("chat").child(firebaseAuth.getCurrentUser().getUid()).child("Masseges");

                        Query lastQuery = db.orderByKey().limitToLast(1);

                        //get last element of messages
                        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {



                                if(messagesArray.size() == j){

                                }else {

                                    try {

                                        Chat account = dataSnapshot.getChildren().iterator().next()
                                                .getValue(Chat.class);

                                        messagesArray.add(account);


                                    } catch (Throwable ignored) {
                                    }
                                }


//                                String message = dataSnapshot.child("message").getValue().toString();
                              //  Chat c2 = dataSnapshot.getValue(Chat.class);

//                                c2.setUserUID(firebaseAuth.getCurrentUser().getUid());

                             //   messagesArray.add(c2);


                                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                layoutManager.setStackFromEnd(true);
                                mMessageRecycler.setLayoutManager(layoutManager);

                                mMessageAdapter.notifyDataSetChanged();
                                p.dismiss();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //Handle possible errors.
                            }
                        });


                        //اي رسايل نظهر؟ يعني حقت ايت ادمن
//                    if (dataSnapshot.getKey().equals(adminUid.get(0) + " : " + firebaseAuth.getUid())) {
//                        String tt = adminUid.get(0);
//                        root.child(adminUid.get(0) + " : " + firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
//                                int j = (int) dataSnapshot2.getChildrenCount();
//                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                    c.setNameChat((String) (ds.child("name").getValue(String.class)));
//                                    c.setMessageChat((String) (ds.child("msg")).getValue(String.class));
//
//                                    Chat testChat = ds.getValue(Chat.class);
//
//                                    messagesArray.add(testChat);
//
//                                    mMessageAdapter = new MessageListAdapter(getContext(), messagesArray);
//                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//                                    layoutManager.setStackFromEnd(true);
//                                    mMessageRecycler.setLayoutManager(layoutManager);
//                                    mMessageRecycler.setAdapter(mMessageAdapter);
//
//
//                                    mMessageAdapter.notifyDataSetChanged();
//
//
////                    @Override
////                    public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                    }
////                });
//
//
////                                list_chat.add(c.getnameChat() + " : " + c.getMessageChat());
////                                arrayAdapter.notifyDataSetChanged();
////                                listView_chat.setSelection(list_chat.size());
//                                }
//                                checker = false;
//
//
//                                if (messagesArray.size() > j) {
//                                    messagesArray.clear();
//                                }
//                                if (messagesArray.size() == 0) {
//                                    Add_Chat(dataSnapshot);
//                                }
//
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
                        //    }
            //    }
            }
        });

    }

    private ArrayList<String> getAllAdminsUid() {
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
                //حدد اي من الادمنز تنعرض رسايله
                root.child(firebaseAuth.getCurrentUser().getUid()).child("Masseges")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        j = (int) dataSnapshot.getChildrenCount();

                        for (DataSnapshot ds : dataSnapshot.getChildren()){


                            messagesArray.add(ds.getValue(Chat.class));

                            mMessageAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    /* userlist will store all values of users, then point to every userlist item
    and get mobile numbers*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
            }
        });
        return adminUid;
    }

    private void sendNotification(
            String title, String message, String deviceId,
            String channelID,
            String senderName,
            String senderFCMToken
    ) {


        FirebaseSendNotification fsn = new FirebaseSendNotification(
                Objects.requireNonNull(getActivity()), title, message, deviceId,
                channelID, senderName, senderFCMToken
        );
        fsn.setResponseFcmToken(this);
        fsn.onResponse();



    }

    @Override
    public void isSuccessfulSendNotification() {

    }
}
