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

    public ChatFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat , container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","admin@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sci-FIT support");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));

        root = FirebaseDatabase.getInstance().getReference().child("MainChatRoom");

        listView_chat = view.findViewById(R.id.listView_chat);
        input_msg = view.findViewById(R.id.input_msg);
        btn_send_msg = view.findViewById(R.id.btn_send_msg);

        name = "Ayman";
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users")
                .child(firebaseAuth.getUid()).child("Profile");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);


                name = userinfo.getName();

                //start chat ++++++++++++++++++++++++++++++++++++++++
                if(getActivity() != null) {
                    arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list_chat);
                }
                listView_chat.setAdapter(arrayAdapter);

                btn_send_msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String, Object> map = new HashMap<String, Object>();
                        temp_key = root.push().getKey();
                        root.updateChildren(map);

                        DatabaseReference message_root = root.child(temp_key);
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

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
