package com.vego.vego.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vego.vego.R;
import com.vego.vego.model.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


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
    List<String> list;
    String choosenUser, choosenDay;
    Spinner spSelectUser;
    ArrayAdapter<String> spinnerArrayAdapter;
    DatabaseReference usersprofile;
    FirebaseAuth firebaseAuth;


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
        return inflater.inflate(R.layout.fragment_support, container, false);




    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





        getUsers();
        selectedUser();





        listView_chat = view.findViewById(R.id.listView_chat);
        input_msg = view.findViewById(R.id.input_msg);
        btn_send_msg = view.findViewById(R.id.btn_send_msg);





    }
    public void chat(final String userUID){


        firebaseAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference().child("MainChatRoom");

        //add the id of sender and reciver
//        root.child(firebaseAuth.getUid()+" : "+ choosenUser).child("adminID").setValue(firebaseAuth.getUid());
//        root.child(firebaseAuth.getUid()+" : "+ choosenUser).child("userID").setValue(choosenUser);


        name = "(Admin)";

        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list_chat);
        listView_chat.setAdapter(arrayAdapter);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                //DatabaseReference message_root = root.child(temp_key);
                DatabaseReference message_root = root.child(firebaseAuth.getUid()+" : "+ choosenUser);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", name);
                map2.put("msg", input_msg.getText().toString());

                message_root.updateChildren(map2);
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
    public void getUsers(){
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users");

        arrayList=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayList.add(0,"اختر متدرب");
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
                    arrayList.add(dataSnapshot1.getKey());
                    spinnerArrayAdapter.notifyDataSetChanged();

//                    Log.d("test","this is size of arr: "+ array.length);


                    Log.d("test","this is uid :"+dataSnapshot1.getKey());
                }
                //  UserInfo userinfo = dataSnapshot.getValue(dataSnapshot.getKey());




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FragmentSupport.this.getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String selectedUser(){
        final Spinner spSelectUser = getView().findViewById(R.id.spinner);

        // Initializing an ArrayAdapter
        Log.d("test","frist  "+arrayList.size());
        spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.support_simple_spinner_dropdown_item,arrayList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spSelectUser.setAdapter(spinnerArrayAdapter);
        spinnerArrayAdapter.notifyDataSetChanged();

//        Log.d("test",""+arrayList.size());




        spSelectUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                String s = arrayList.get(position);
                Log.d("test","thid dfjkdl : "+s);
                choosenUser = selectedItemText;
//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                        "mailto",choosenUser, null));
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sci-FIT support");
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
//                startActivity(Intent.createChooser(emailIntent, "Send email..."));

                usersprofile = FirebaseDatabase.getInstance().getReference();
                if (position!=0)
                    chat(choosenUser);
                //Log.d("test","this is details " +usersprofile.child(choosenUser).child("profile"));
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
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

    private void Add_Chat(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();
        input_msg.setText("");
        while (i.hasNext()) {

            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot) i.next()).getValue();

            list_chat.add(chat_user_name + " : " + chat_msg);
            arrayAdapter.notifyDataSetChanged();
            listView_chat.setSelection(list_chat.size());
        }
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
