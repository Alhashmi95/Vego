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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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
import com.vego.vego.R;
import com.vego.vego.Request.FirebaseSendNotification;
import com.vego.vego.Storage.UserSharedPreferences;
import com.vego.vego.model.Chat;
import com.vego.vego.model.UserInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSupport.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSupport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSupport extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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

    int j = 0;

    ArrayList<String> usersUID = new ArrayList<>();

    ArrayList<String> adminUid = new ArrayList<>();

    ArrayList<UserInfo> userInfos = new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentSupport() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSupport.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSupport newInstance(String param1, String param2) {
        FragmentSupport fragment = new FragmentSupport();
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

        //Retrieve the value
//        value = getArguments().getString("userUID");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bubbleschatadmin, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        firebaseAuth = FirebaseAuth.getInstance();

        root = FirebaseDatabase.getInstance().getReference().child("chat");


        getUsers();
        selectedUser();


        //bubbles
        mMessageRecycler = (RecyclerView) view.findViewById(R.id.reyclerview_message_list);

        mMessageAdapter = new MessageListAdapter(getContext(), messagesArray);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(layoutManager);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageAdapter.notifyDataSetChanged();


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference d = firebaseDatabase.getReference().child("Ayman");

//        d.setValue(c);


        input_msg = view.findViewById(R.id.et_chatbox);
        btn_send_msg = view.findViewById(R.id.button_chatbox_send);


    }

    private void getMessages() {
        String s = firebaseAuth.getCurrentUser().getUid();
        //حدد اي من الادمنز تنعرض رسايله
        root.child(choosenUser).child("Masseges")
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

        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list_chat);
        //listView_chat.setAdapter(arrayAdapter);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (input_msg.length() == 0) {

                } else {
                    String dd = FirebaseInstanceId.getInstance().getToken();
                    //send notification
                    FirebaseSendNotification firebaseSendNotification = new FirebaseSendNotification(
                            getActivity(), name, input_msg.getText().toString(), FirebaseInstanceId.getInstance().getToken(),
                            firebaseAuth.getCurrentUser().getUid() + " : " + choosenUser, "ahmad",
                            FirebaseInstanceId.getInstance().getToken());
                    firebaseSendNotification.onResponse();

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
                    DatabaseReference message_root = root.child(choosenUser).child("Masseges").child(temp_key);
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("sendername", name);
                    map2.put("msg", newMessage);
                    map2.put("senderId", firebaseAuth.getCurrentUser().getUid());
                    // map2.put("createdAt", currentTime);
                    map2.put("date", currentTime);
                    map2.put("tokenSender", aaa);
                    map2.put("tokenReceiver", "");


                    message_root.updateChildren(map2);
                }
                input_msg.setText("");
            }
        });


        root.child(choosenUser).child("Masseges")
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

                choosenUser = UserSharedPreferences.fetchChosenUser(Objects.requireNonNull(getContext()));
                for (int i = 0; i < arrayList2.size(); i++) {
                    if (choosenUser.equals(arrayList2.get(i))) {
                        spSelectUser.setSelection(i);
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FragmentSupport.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String selectedUser() {
        spSelectUser = getView().findViewById(R.id.spinner);

        // Initializing an ArrayAdapter
        Log.d("test", "frist  " + arrayList.size());
        spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(), R.layout.support_simple_spinner_dropdown_item, arrayList) {
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
                UserSharedPreferences.storeChosenUser(getContext(), choosenUser);

//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                        "mailto",choosenUser, null));
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sci-FIT support");
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
//                startActivity(Intent.createChooser(emailIntent, "Send email..."));

                usersprofile = FirebaseDatabase.getInstance().getReference();
                if (position != 0)
                    chat(choosenUser);
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
                            (getActivity().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
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
        root.child(choosenUser).child("Masseges")
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
                .child("chat").child(choosenUser).child("Masseges");

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


                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setStackFromEnd(true);
                mMessageRecycler.setLayoutManager(layoutManager);

                mMessageAdapter.notifyDataSetChanged();
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
                    for (DataSnapshot dspProfile : dsp.getChildren()) {
                        //loop 2 to go through all the child nodes of Profile node

                        if (dspProfile.getKey().equals("Profile"))
                            userInfos.add(dspProfile.getValue(UserInfo.class));
                    }


                }
                for (int i = 0; i < userInfos.size(); i++) {
                    String t = userInfos.get(i).getUid();
                    String v = usersUID.get(i);
                    if (userInfos.get(i).getUid().equals(usersUID.get(i))) {
                        isAdmin = userInfos.get(i).getAdmin();
                    }
                    if (isAdmin.equals("true")) {
                        userUid = usersUID.get(i);
                        adminUid.add(userUid);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
            }
        });
        return adminUid;
    }
}
