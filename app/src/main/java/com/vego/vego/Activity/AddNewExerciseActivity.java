package com.vego.vego.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vego.vego.Fragment.Add_workout2user;
import com.vego.vego.R;
import com.vego.vego.model.exercise;
import com.vego.vego.model.meal;
import com.vego.vego.model.sets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddNewExerciseActivity extends AppCompatActivity {
    Button saveNewEx;
    EditText exName;
    Spinner selectMu;
    ImageView imageViewEx;
    String choosenMu, exerciseName, exUrl;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    StorageReference exRef;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();
    int indexofImages =0;




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                imageViewEx.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exercise);

        saveNewEx = findViewById(R.id.saveNewExBtn);
        exName = findViewById(R.id.txtExName);
        selectMu = findViewById(R.id.spinnerMu);
        imageViewEx = findViewById(R.id.imageViewNewEx);


        imageViewEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        selectMu();
        FirebaseDatabase f = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = f.getReference().child("exercies");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Note ** : ondatachange discards the value of arraylist after it finishs
                indexofImages = (int) dataSnapshot.getChildrenCount();
                Log.d("test","this is count : "+String.valueOf(indexofImages));

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        saveEx();




    }
    public void saveEx(){
        saveNewEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (exName.getText().toString().isEmpty() || selectMu.equals("اختر عضلة") || imagePath == null) {
                    Toast.makeText(AddNewExerciseActivity.this, "please enter all details including image",
                            Toast.LENGTH_LONG).show();
                }else {
                    exerciseName = exName.getText().toString();

                    // here we upload meal pics
                    exRef = storageReference.child("exercises/").child(String.valueOf(indexofImages));
                    UploadTask uploadTask = (UploadTask) exRef.putFile(imagePath);

                    if (uploadTask != null && uploadTask.isInProgress()) {
                        Toast.makeText(AddNewExerciseActivity.this, "upload is in progress .. please wait", Toast.LENGTH_LONG).show();

                    }

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return exRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                exUrl = downloadUri.toString();
                                Toast.makeText(AddNewExerciseActivity.this, "upload successeded", Toast.LENGTH_SHORT).show();
                                exercise e = new exercise();

//                                sets[] setsArray = new sets[] {new sets("","", "","") };

                              //  List setsList = new ArrayList<sets>(Arrays.asList(setsArray));


                                e.setExername(exerciseName);
                                e.setTargetedmuscle(choosenMu);
                                e.setImage(exUrl);
                             //   e.setSets((ArrayList<sets>) setsList);

                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference d = firebaseDatabase.getReference();


                                //upload meal to firebase
                                d.child("exercies").child(String.valueOf(indexofImages)).setValue(e);


                                Intent intent= new Intent(AddNewExerciseActivity.this,
                                        AdminActivity.class);
//                                intent.putExtra("image",exUrl);
//                                intent.putExtra("name",exerciseName);
//                                intent.putExtra("choosenMu",choosenMu);
//                                intent.putExtra("index",String.valueOf(indexofImages));
                                v.getContext().startActivity(intent);
                            } else {
                                // Handle failures
                                Toast.makeText(AddNewExerciseActivity.this, "upload failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }
        });

    }
    public void getCountImages(){

    }
    public void selectMu() {
        String[] day = new String[]{
                "اختر عضلة",
                "صدر",
                "بطن",
                "ذراع",
                "ظهر",
                "قدم",
                "ترابيس",
                "تراي",
        };
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, day) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        selectMu.setAdapter(spinnerArrayAdapter);

        selectMu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                choosenMu = selectedItemText;
                // If user change the default selection


                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    Toast.makeText
                            (AddNewExerciseActivity.this, "Selected : " + selectedItemText,
                                    Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
