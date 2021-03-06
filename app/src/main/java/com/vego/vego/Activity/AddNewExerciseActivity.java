package com.vego.vego.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.VideoView;

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
    Button saveNewEx, saveNewExForAll;
    EditText exName;
    Spinner selectMu;
    ImageView imageViewEx;
    VideoView videoViewEx;
    TextView addVid;
    String choosenMu, exerciseName, exUrl;
    private static int PICK_IMAGE = 123;
    private static int PICK_VIDEO = 100;
    Uri imagePath,videoPath;
    StorageReference exRef;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();
    int indexofImages =0;
    int indexofImagesForAll =0;
    ProgressDialog p;
    AlertDialog alertDialog1;
    CharSequence[] values = {"صورة"};




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                //---------------------------------------------commented
         //       videoViewEx.setBackgroundResource(android.R.color.transparent);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                imageViewEx.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }else if( resultCode==RESULT_OK && requestCode==PICK_VIDEO&&data.getData()!= null){
//            videoPath= data.getData();
//            try{
//                imageViewEx.setImageResource(android.R.color.transparent);
//                videoViewEx.setVideoURI(videoPath);
//                videoViewEx.start();
//            }catch(Exception e){
//                e.printStackTrace();
//            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exercise);

        saveNewEx = findViewById(R.id.saveNewExBtn);
        saveNewExForAll = findViewById(R.id.saveNewExForAllBtn);
        exName = findViewById(R.id.txtExName);
        selectMu = findViewById(R.id.spinnerMu);
        imageViewEx = findViewById(R.id.imageViewNewEx);

        imageViewEx.setImageResource(R.drawable.addpic2);
       // videoViewEx = findViewById(R.id.videoViewNewEx);
//        addVid = findViewById(R.id.addVid);

//        imageViewEx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
//            }
//        });
        //----------------------------load video or image--------------------------------------------------------
        imageViewEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddNewExerciseActivity.this);

                builder.setTitle("Choose Media");

                builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch(item)
                        {
                            case 0:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "اختر صورة للتمرين"), PICK_IMAGE);
                                break;
//                            case 1:
//                                 Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//                               startActivityForResult(Intent.createChooser(i,"Select Video"),PICK_VIDEO);
//                                break;

                        }
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = builder.create();
                alertDialog1.show();


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
        FirebaseDatabase f2 = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference2 = f2.getReference().child("exerciesForALL");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Note ** : ondatachange discards the value of arraylist after it finishs
                indexofImagesForAll = (int) dataSnapshot.getChildrenCount();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        saveEx();

        saveExForAll();






    }
    public void saveExForAll() {
        saveNewExForAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (exName.getText().toString().isEmpty() || selectMu.equals("اختر عضلة") || imagePath == null) {
                    Toast.makeText(AddNewExerciseActivity.this, "الرجاء ادخال جميع التفاصيل بما في ذلك صورة التمرين",
                            Toast.LENGTH_LONG).show();
                } else {
                    exerciseName = exName.getText().toString();

                    p = new ProgressDialog(AddNewExerciseActivity.this);
                    p.setTitle("رفع التمرين");
                    p.setMessage("جاري الرفع...");
                    p.show();
                    p.setCanceledOnTouchOutside(false);
                    saveNewEx.setVisibility(View.INVISIBLE);
                    saveNewExForAll.setVisibility(View.INVISIBLE);


                    // here we upload meal pics
                    exRef = storageReference.child("exercisesForALL/").child(String.valueOf(indexofImagesForAll));

                    UploadTask uploadTask = (UploadTask) exRef.putFile(imagePath);
                    if (uploadTask != null && uploadTask.isInProgress()) {
                        Toast.makeText(AddNewExerciseActivity.this, "جاري الرفع الرجاء الانتظار..", Toast.LENGTH_LONG).show();

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
                                Toast.makeText(AddNewExerciseActivity.this, "تم رفع تمرين عام", Toast.LENGTH_SHORT).show();
                                exercise e = new exercise();
                                saveNewEx.setVisibility(View.VISIBLE);
                                saveNewExForAll.setVisibility(View.VISIBLE);

                                sets[] setsArray = new sets[]{new sets("1234", "", "", "")};

                                List setsList = new ArrayList<sets>(Arrays.asList(setsArray));


                                e.setExername(exerciseName);
                                e.setTargetedmuscle(choosenMu);
                                e.setImage(exUrl);
                                e.setSets((ArrayList<sets>) setsList);

                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference d = firebaseDatabase.getReference();


                                //upload meal to firebase
                                d.child("exerciesForALL").child(String.valueOf(indexofImagesForAll)).setValue(e);


                                finish();
//                                Intent intent= new Intent(AddNewExerciseActivity.this,
//                                        AdminActivity.class);
//                                intent.putExtra("image",exUrl);
//                                intent.putExtra("name",exerciseName);
//                                intent.putExtra("choosenMu",choosenMu);
//                                intent.putExtra("index",String.valueOf(indexofImages));
                                // v.getContext().startActivity(intent);
                            } else {
                                // Handle failures
                                Toast.makeText(AddNewExerciseActivity.this, "فشل الرفع", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

                //-------------------------------------commented
//                if (imagePath == null) {
//                    if (exName.getText().toString().isEmpty() || selectMu.equals("اختر عضلة") || videoPath == null) {
//                        Toast.makeText(AddNewExerciseActivity.this, "please enter all details including video",
//                                Toast.LENGTH_LONG).show();
//                    } else {
//                        exerciseName = exName.getText().toString();
//
//                        p = new ProgressDialog(v.getContext());
//                        p.setTitle("Uploading");
//                        p.setMessage("Uploading data...");
//                        p.show();
//                        p.setCanceledOnTouchOutside(false);
//                        saveNewEx.setVisibility(View.INVISIBLE);
//                        saveNewExForAll.setVisibility(View.INVISIBLE);
//
//
//                        // here we upload meal pics
//                        exRef = storageReference.child("exercises/").child(String.valueOf(indexofImages));
//
//                        UploadTask uploadTask = (UploadTask) exRef.putFile(videoPath);
//                        if (uploadTask != null && uploadTask.isInProgress()) {
//                            Toast.makeText(AddNewExerciseActivity.this, "upload is in progress .. please wait", Toast.LENGTH_LONG).show();
//
//                        }
//
//                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                            @Override
//                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                if (!task.isSuccessful()) {
//                                    throw task.getException();
//                                }
//
//                                // Continue with the task to get the download URL
//                                return exRef.getDownloadUrl();
//                            }
//                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Uri> task) {
//                                if (task.isSuccessful()) {
//                                    Uri downloadUri = task.getResult();
//                                    exUrl = downloadUri.toString();
//                                    Toast.makeText(AddNewExerciseActivity.this, "تم رفع تمرين عام", Toast.LENGTH_SHORT).show();
//                                    exercise e = new exercise();
//                                    saveNewEx.setVisibility(View.VISIBLE);
//                                    saveNewExForAll.setVisibility(View.VISIBLE);
//
//                                    sets[] setsArray = new sets[]{new sets("1234", "", "", "")};
//
//                                    List setsList = new ArrayList<sets>(Arrays.asList(setsArray));
//
//
//                                    e.setExername(exerciseName);
//                                    e.setTargetedmuscle(choosenMu);
//                                    e.setImage("");
//                                    e.setVideo(exUrl);
//                                    e.setSets((ArrayList<sets>) setsList);
//
//                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                                    DatabaseReference d = firebaseDatabase.getReference();
//
//
//                                    //upload meal to firebase
//                                    d.child("exerciesForALL").child(String.valueOf(indexofImagesForAll)).setValue(e);
//
//
//                                    finish();
////                                Intent intent= new Intent(AddNewExerciseActivity.this,
////                                        AdminActivity.class);
////                                intent.putExtra("image",exUrl);
////                                intent.putExtra("name",exerciseName);
////                                intent.putExtra("choosenMu",choosenMu);
////                                intent.putExtra("index",String.valueOf(indexofImages));
//                                    // v.getContext().startActivity(intent);
//                                } else {
//                                    // Handle failures
//                                    Toast.makeText(AddNewExerciseActivity.this, "upload failed", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        });
//                    }
//                }
                //----------------------------------------------------------------------------
            }
        });

    }

    public void saveEx(){
        saveNewEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (exName.getText().toString().isEmpty() || selectMu.equals("اختر عضلة") || imagePath == null) {
                    Toast.makeText(AddNewExerciseActivity.this, "الرجاء ادخال جميع التفاصيل بما في ذلك صورة التمرين",
                            Toast.LENGTH_LONG).show();
                }else {
                    exerciseName = exName.getText().toString();

                    p = new ProgressDialog(v.getContext());
                    p.setTitle("رفع التمرين");
                    p.setMessage("جاري الرفع...");
                    p.show();
                    p.setCanceledOnTouchOutside(false);
                    saveNewEx.setVisibility(View.INVISIBLE);
                    saveNewExForAll.setVisibility(View.INVISIBLE);

                    // here we upload meal pics
                    exRef = storageReference.child("exercises/").child(String.valueOf(indexofImages));
                    UploadTask uploadTask = (UploadTask) exRef.putFile(imagePath);

                    if (uploadTask != null && uploadTask.isInProgress()) {
                        Toast.makeText(AddNewExerciseActivity.this, "جاري الرفع الرجاء الانتظار..", Toast.LENGTH_LONG).show();

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
                                Toast.makeText(AddNewExerciseActivity.this, "تم الرفع", Toast.LENGTH_SHORT).show();
                                exercise e = new exercise();

                                saveNewEx.setVisibility(View.VISIBLE);
                                saveNewExForAll.setVisibility(View.VISIBLE);

                                p.dismiss();

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

                                finish();
//                                Intent intent= new Intent(AddNewExerciseActivity.this,
//                                        AdminActivity.class);
//                                intent.putExtra("image",exUrl);
//                                intent.putExtra("name",exerciseName);
//                                intent.putExtra("choosenMu",choosenMu);
//                                intent.putExtra("index",String.valueOf(indexofImages));
                          //      v.getContext().startActivity(intent);
                            } else {
                                // Handle failures
                                Toast.makeText(AddNewExerciseActivity.this, "فشل الرفع", Toast.LENGTH_SHORT).show();

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
                "اكتاف",
                "باي",
                "بطن",
                "ترابيس",
                "تراي",
                "سواعد",
                "صدر",
                "ظهر",
                "افخاذ",
                "عضلة السمانة",
                "كارديو",
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
                            (AddNewExerciseActivity.this, "تم اختيار عضلة : " + selectedItemText,
                                    Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
