package com.vego.vego.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.vego.vego.Adapters.MessageListAdapter;
import com.vego.vego.Adapters.UsersAdapter;
import com.vego.vego.Fragment.FragmentSupport;
import com.vego.vego.R;
import com.vego.vego.Request.FirebaseSendNotification;
import com.vego.vego.Storage.UserSharedPreferences;
import com.vego.vego.model.Chat;
import com.vego.vego.model.UserInfo;
import com.vego.vego.response.ResponseFcmToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class InsideChatActivity extends AppCompatActivity implements ResponseFcmToken {

    private DatabaseReference root;
    private String temp_key;
    private Button btn_send_msg;
    private EditText input_msg;
    private ListView listView_chat;
    ArrayList<String> list_chat = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private String name, value;
    private FirebaseDatabase firebaseDatabase;
    ArrayList<String> arrayList;
    ArrayList<String> arrayList2;
    List<String> list;
    String choosenUser, choosenDay;
    Spinner spSelectUser;
    ArrayAdapter<String> spinnerArrayAdapter;
    DatabaseReference usersprofile;
    FirebaseAuth firebaseAuth;

    ArrayList<Chat> messagesArray = new ArrayList<>();


    UserInfo userinfo;

    String isAdmin, userUid, currentDate, currentTime, message;

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;

    String receiverToken;

    int j = 0;

    UserInfo u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bubbleschatadmin);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        input_msg = findViewById(R.id.et_chatbox);
        btn_send_msg = findViewById(R.id.button_chatbox_send);


        root = FirebaseDatabase.getInstance().getReference().child("chat");


//        getUsers();
//        selectedUser();

        Intent i = getIntent();

        u = (UserInfo) i.getSerializableExtra("userList");

        messagesArray.clear();
        //      mMessageAdapter.notifyDataSetChanged();
        getMessages();


        chat(u.getUid());




        //bubbles
        mMessageRecycler = findViewById(R.id.reyclerview_message_list);

        mMessageAdapter = new MessageListAdapter(InsideChatActivity.this, messagesArray);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(layoutManager);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageAdapter.notifyDataSetChanged();


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference d = firebaseDatabase.getReference().child("Ayman");

//        d.setValue(c);




    }

    private void getMessages() {
        String s = firebaseAuth.getCurrentUser().getUid();
        //حدد اي من الادمنز تنعرض رسايله
        root.child(u.getUid()).child("Masseges")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        j = (int) dataSnapshot.getChildrenCount();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {


                            messagesArray.add(ds.getValue(Chat.class));

                            mMessageAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void chat(final String userUID) {


        firebaseAuth = FirebaseAuth.getInstance();

        //add the id of sender and reciver
//        root.child(firebaseAuth.getUid()+" : "+ choosenUser).child("adminID").setValue(firebaseAuth.getUid());
//        root.child(firebaseAuth.getUid()+" : "+ choosenUser).child("userID").setValue(choosenUser);


        name = "(Admin)";

        getNames();

        arrayAdapter = new ArrayAdapter<String>(InsideChatActivity.this, android.R.layout.simple_list_item_1, list_chat);
        //listView_chat.setAdapter(arrayAdapter);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (input_msg.length() == 0) {

                } else {
                    //getting current date and time
                    Calendar calForDate = Calendar.getInstance();
                    DateFormat currentDateFormat2 = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
                    currentDate = currentDateFormat2.format(calForDate.getTime());


                    Calendar calForTime = Calendar.getInstance();
                    SimpleDateFormat currentTimeFormat = new SimpleDateFormat("MMM d, yyyy HH:mm:ss", Locale.ENGLISH);
                    currentTime = currentTimeFormat.format(calForTime.getTime());

//                    SimpleDateFormat d = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
//                    try {
//                        Date dw = d.parse(currentTime);
//                        currentTime = d.format(dw);
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }


                    Map<String, Object> map = new HashMap<String, Object>();
                    temp_key = root.push().getKey();
                    root.updateChildren(map);

                    message = input_msg.getText().toString();
                    String newMessage = message.replace("\n", "");


                    String aaa = FirebaseInstanceId.getInstance().getToken();

                    //DatabaseReference message_root = root.child(temp_key);
                    DatabaseReference message_root = root.child(u.getUid()).child("Masseges").child(temp_key);
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("sendername", name);
                    map2.put("msg", newMessage);
                    map2.put("senderId", firebaseAuth.getCurrentUser().getUid());
                    // map2.put("createdAt", currentTime);
                    map2.put("date", currentTime);
                    map2.put("tokenSender", FirebaseInstanceId.getInstance().getToken());


                    DatabaseReference tokenRef = firebaseDatabase.getReference().child("Ayman")
                            .child(u.getUid()).child("userToken");

                    tokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                receiverToken = dataSnapshot.getValue().toString();

                                sendNotification("لديك رسالة جديدة من " + name, newMessage,
                                        receiverToken, u.getUid(), name, FirebaseInstanceId.getInstance().getToken());

                                map2.put("tokenReceiver", receiverToken);
                                message_root.updateChildren(map2);

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });





                }
                input_msg.setText("");
            }
        });


        root.child(u.getUid()).child("Masseges")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
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

    }

    private void getNames() {
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("users")
                .child(firebaseAuth.getUid()).child("Profile");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userinfo = dataSnapshot.getValue(UserInfo.class);

                name = "(Admin)" + userinfo.getName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                        spinnerArrayAdapter.notifyDataSetChanged();
                    }


//                    Log.d("test","this is size of arr: "+ array.length);


                    //      Log.d("test", "this is uid :" + dataSnapshot1.getKey());

                    //   Log.d("test", "this is emails FFGFGGGF :" + userinfo.getEmail());

                }
                //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());

//                u.getUid() = UserSharedPreferences.fetchChosenUser(Objects.requireNonNull(InsideChatActivity.this));
//                for (int i = 0; i < arrayList2.size(); i++) {
//                    if (choosenUser.equals(arrayList2.get(i))) {
//                        spSelectUser.setSelection(i);
//                    }
//                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(InsideChatActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String selectedUser() {
        spSelectUser = findViewById(R.id.spinner);

        // Initializing an ArrayAdapter
        Log.d("test", "frist  " + arrayList.size());
        spinnerArrayAdapter = new ArrayAdapter<String>(
                InsideChatActivity.this, R.layout.support_simple_spinner_dropdown_item, arrayList) {
            @Override
            public boolean isEnabled(int position) {
//                if(position == 0)
//                {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                }
//                else
//                {
                return true;
                //}
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
                UserSharedPreferences.storeChosenUser(InsideChatActivity.this, choosenUser);

//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                        "mailto",choosenUser, null));
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sci-FIT support");
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
//                startActivity(Intent.createChooser(emailIntent, "Send email..."));

                usersprofile = FirebaseDatabase.getInstance().getReference();
                if (position != 0)

                messagesArray.clear();
                mMessageAdapter.notifyDataSetChanged();
                getMessages();
                //Log.d("test","this is details " +usersprofile.child(choosenUser).child("profile"));
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    //clear arraylist of chat to load new one
                    Toast.makeText
                            (InsideChatActivity.this, "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return choosenUser;
    }


    private String chat_msg, chat_user_name;


    private void Add_Chat(final DataSnapshot dataSnapshot) {
        // if (input_msg.length() > 0) {
        //check if there's a new message =====================================
        root.child(u.getUid()).child("Masseges")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        j = (int) dataSnapshot.getChildrenCount();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        //messagesArray.add(c);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(layoutManager);

        mMessageAdapter.notifyDataSetChanged();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                .child("chat").child(u.getUid()).child("Masseges");

        Query lastQuery = db.orderByKey().limitToLast(1);

        //get last element of messages
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (messagesArray.size() == j) {

                } else {

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


                LinearLayoutManager layoutManager = new LinearLayoutManager(InsideChatActivity.this);
                layoutManager.setStackFromEnd(true);
                mMessageRecycler.setLayoutManager(layoutManager);

                mMessageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Handle possible errors.
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


    private void sendNotification(
            String title, String message, String deviceId,
            String channelID,
            String senderName,
            String senderFCMToken
    ) {


        FirebaseSendNotification fsn = new FirebaseSendNotification(
                InsideChatActivity.this, title, message, deviceId,
                channelID, senderName, senderFCMToken
        );
        fsn.setResponseFcmToken(this);
        fsn.onResponse();



    }

    @Override
    public void isSuccessfulSendNotification() {

    }
}

