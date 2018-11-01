package com.vego.vego.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;
import com.squareup.picasso.Picasso;
import com.vego.vego.Adapters.ExerciseAdapter;
import com.vego.vego.Adapters.ExerciseDetailsAdapter;
import com.vego.vego.Adapters.ExerciseDetailsAdapterFree;
import com.vego.vego.Adapters.HistoryAdapter;
import com.vego.vego.Adapters.NewSetAdapter;
import com.vego.vego.R;
import com.vego.vego.model.MonthExercise;
import com.vego.vego.model.elements;
import com.vego.vego.model.exercise;
import com.vego.vego.model.sets;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ActivityInsideExercise extends AppCompatActivity {
    private ExerciseDetailsAdapter adapter;
    private ExerciseDetailsAdapterFree adapterFree;
    RecyclerView recyclerView;
    TextView exNumberTextView, exNameTextView, tvTotalVolume,tvMaxRM1;
    ImageView imageViewEx;
    ProgressBar progressBar;
    double totalVolume, sumVolume = 0,totalWeight =0;
    Button calVAndR, addNewSetBtn;
    double max =0;

    ArrayList<sets> setsList = new ArrayList<>();

    ArrayList<exercise> exList = new ArrayList<>();
    ArrayList<sets> list = new ArrayList<>();

    private ArrayList<sets> setsArrayList;


    String exImage;

    boolean checker = false;

    boolean checkerForRemove;

    Button btnNext,btnPrevious ;

    int exerciseN = 0;
    int exerciseP = 0;

    String month,week,exNumber,day,dayIndex;

    exercise volume1RmEx;
    double sumWeight;

    ArrayList<sets> setsHistory;

    RecyclerView recyclerViewHistory;

    HistoryAdapter historyAdapter;

    TextView historyText;

    SpaceNavigationView spaceNavigationView;

    RelativeLayout rvHistory, rvHistory2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_exercise);

        recyclerView = findViewById(R.id.recyclerInsideWorkout);
        exNumberTextView = findViewById(R.id.exNumbertextView);
        exNameTextView = findViewById(R.id.textViewExNameUser);
        imageViewEx = findViewById(R.id.imageViewEximage);
        tvTotalVolume = findViewById(R.id.tvTotalVolume);
        calVAndR = findViewById(R.id.btnCalVolumeAndRM1);
        tvMaxRM1 = findViewById(R.id.txtMaxRm1);
        progressBar = findViewById(R.id.progressBar);
        addNewSetBtn = findViewById(R.id.btnAddnewSet);

        btnNext=findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPreviousMeal);


        calVAndR.setVisibility(View.INVISIBLE);


        recyclerViewHistory = findViewById(R.id.rv_insideWorkoutHistory);

        historyText = findViewById(R.id.tv_History);

        spaceNavigationView = findViewById(R.id.nav_nextPrev);

        rvHistory = findViewById(R.id.rv_history);
        rvHistory2 = findViewById(R.id.rv_history2);

        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("لتمرين السابق",R.color.transparent));
        spaceNavigationView.addSpaceItem(new SpaceItem("التمرين التالي", R.color.transparent));
        spaceNavigationView.shouldShowFullBadgeText(true);
        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);


                setsArrayList = populateList();



        clickToEnlarge();

        calVAndR.setVisibility(View.GONE);


        //nextAndPreviousExercise();

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                calculateVolumeAndRM1();


//                if(checker == false){
//                    spaceNavigationView.setClickable(true);
//                }
//                else {
//                    spaceNavigationView.setClickable(false);
//
//                }
                Log.d("onCentreButtonClick ", "onCentreButtonClick");
                spaceNavigationView.shouldShowFullBadgeText(true);

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex == 0) {
                    Log.d("onItemClick ", "" + itemIndex + " " + itemName);
                    if (exList.size() > exerciseP && exerciseP >= 0) {
                        Intent intent = new Intent(getApplicationContext(), ActivityInsideExercise.class);

                        intent.putExtra("exSets", exList.get(exerciseP).getSets());
                        intent.putExtra("exName", exList.get(exerciseP).getExername());
                        intent.putExtra("exImage", exList.get(exerciseP).getImage());
                        intent.putExtra("exNumber", String.valueOf(exerciseP));
                        intent.putExtra("exercise list", exList);
                        intent.putExtra("dayIndex", dayIndex);
                        intent.putExtra("month", month);
                        intent.putExtra("week", week);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "هذا اول تمرين", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Log.d("onItemReselected ", "" + itemIndex + " " + itemName);

                    if(exList.size()>exerciseN){
                        Intent intent = new Intent(getApplicationContext(), ActivityInsideExercise.class);

                        intent.putExtra("exSets", exList.get(exerciseN).getSets());
                        intent.putExtra("exName", exList.get(exerciseN).getExername());
                        intent.putExtra("exImage", exList.get(exerciseN).getImage());
                        intent.putExtra("exNumber",String.valueOf(exerciseN));
                        intent.putExtra("exercise list", exList);
                        intent.putExtra("dayIndex",dayIndex);
                        intent.putExtra("month",month);
                        intent.putExtra("week",week);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getBaseContext(),"هذا اخر تمرين",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                if (itemIndex == 0) {
                    Log.d("onItemClick ", "" + itemIndex + " " + itemName);
                    if (exList.size() > exerciseP && exerciseP >= 0) {
                        Intent intent = new Intent(getApplicationContext(), ActivityInsideExercise.class);

                        intent.putExtra("exSets", exList.get(exerciseP).getSets());
                        intent.putExtra("exName", exList.get(exerciseP).getExername());
                        intent.putExtra("exImage", exList.get(exerciseP).getImage());
                        intent.putExtra("exNumber", String.valueOf(exerciseP));
                        intent.putExtra("exercise list", exList);
                        intent.putExtra("dayIndex", dayIndex);
                        intent.putExtra("month", month);
                        intent.putExtra("week", week);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "هذا اول تمرين", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Log.d("onItemReselected ", "" + itemIndex + " " + itemName);
                    if(exList.size()>exerciseN){
                        Intent intent = new Intent(getApplicationContext(), ActivityInsideExercise.class);

                        intent.putExtra("exSets", exList.get(exerciseN).getSets());
                        intent.putExtra("exName", exList.get(exerciseN).getExername());
                        intent.putExtra("exImage", exList.get(exerciseN).getImage());
                        intent.putExtra("exNumber",String.valueOf(exerciseN));
                        intent.putExtra("exercise list", exList);
                        intent.putExtra("dayIndex",dayIndex);
                        intent.putExtra("month",month);
                        intent.putExtra("week",week);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getBaseContext(),"هذا اخر تمرين",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//
//        spaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
//            @Override
//            public void onCentreButtonLongClick() {
////                Toast.makeText(MainActivity.this, "onCentreButtonLongClick", Toast.LENGTH_SHORT).show();
//                // Intent intent = new Intent(this, ActivityWithBadge.class);
//                // startActivity(intent);
//            }
//
//            @Override
//            public void onItemLongClick(int itemIndex, String itemName) {
//                Toast.makeText(ActivityInsideExercise.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
//            }
//        });





        //showPic();






//        calVAndR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                calculateVolumeAndRM1();
//
//                if(checker == false){
//                    calVAndR.setVisibility(View.VISIBLE);
//                }
//                else {
//                   // calVAndR.setVisibility(View.INVISIBLE);
//
//                }
//           }
//        });




        Intent intent = this.getIntent();

        exList = (ArrayList<exercise>) intent.getSerializableExtra("exercise list");
        //here we receive array of objects from daysAdapter
        //because daysAdapter has an object of DietDay which contains DayMeals array of objects
        setsList = (ArrayList<sets>) intent.getSerializableExtra("exSets");
        //get day number from الكلاس المعضل (daysAdapter)
        exNumber = intent.getStringExtra("exNumber");
        String exName = intent.getStringExtra("exName");
        exImage = intent.getStringExtra("exImage");
        Log.d("test", "this is image   " + exImage);
        exerciseN = Integer.parseInt(exNumber) + 1;
        exerciseP = Integer.valueOf(exNumber) - 1;

        exNumberTextView.setText(" التمرين " + String.valueOf(exerciseN));
        exNameTextView.setText(exName);

//        Picasso.get()
//                .load(exImage)
//                .placeholder(R.drawable.ic_loading)
//                .fit()
//                .centerCrop()
//                .into(imageViewEx);

        Ion.with(this)
                .load(exImage)
                .progressBar(progressBar)
                .withBitmap()
                // .placeholder(R.drawable.ic_loading)
                .intoImageView(imageViewEx)
        .setCallback(new FutureCallback<ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {
                progressBar.setVisibility(View.GONE);
            }
        });


        for(int i =0; i < setsList.size(); i++){
            addNewSetBtn.setVisibility(View.VISIBLE);
            if(setsList.get(i).getReps().equals("1234")){
                //hide keyboard focus
                getWindow().setSoftInputMode(WindowManager.
                        LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                btnNext.setVisibility(View.GONE);
//                btnPrevious.setVisibility(View.GONE);
                adapterFree = new ExerciseDetailsAdapterFree(setsArrayList, this);
                recyclerView.setAdapter(adapterFree);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false));
                adapterFree.notifyDataSetChanged();
                getExerciseFree();
                recyclerViewHistory.setVisibility(View.GONE);
                historyText.setVisibility(View.GONE);
                rvHistory.setVisibility(View.GONE);
                rvHistory2.setVisibility(View.GONE);

            }else {
                addNewSetBtn.setVisibility(View.INVISIBLE);
                //calVAndR.setVisibility(View.VISIBLE);
                setsList.get(i).setWeight("");
                setsList.get(i).setRM1("");
                setsList.get(i).setVolume("");
                adapter = new ExerciseDetailsAdapter(setsList, this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                getMonthAndWeekNum();
                getExercises();
                getSetsHistory();
            }
        }

    }

    private void getExerciseFree() {
        exList.get(Integer.valueOf(exNumber));
    }

    private void nextAndPreviousExercise() {
        //moving to next meal
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                //go to the child where you want to retreive values of
//                DatabaseReference usersRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Diet")
//                        .child(chosenMonth).child(chosenWeek);

                if(exList.size()>exerciseN){
                    Intent intent = new Intent(getApplicationContext(), ActivityInsideExercise.class);

                    intent.putExtra("exSets", exList.get(exerciseN).getSets());
                    intent.putExtra("exName", exList.get(exerciseN).getExername());
                    intent.putExtra("exImage", exList.get(exerciseN).getImage());
                    intent.putExtra("exNumber",String.valueOf(exerciseN));
                    intent.putExtra("exercise list", exList);
                    intent.putExtra("dayIndex",dayIndex);
                    intent.putExtra("month",month);
                    intent.putExtra("week",week);
                    v.getContext().startActivity(intent);
                    finish();}
                else {
                    Toast.makeText(getBaseContext(),"هذا اخر تمرين",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //moving to previous meal
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exList.size() > exerciseP && exerciseP >= 0){
                    Intent intent = new Intent(getApplicationContext(), ActivityInsideExercise.class);

                    intent.putExtra("exSets", exList.get(exerciseP).getSets());
                    intent.putExtra("exName", exList.get(exerciseP).getExername());
                    intent.putExtra("exImage", exList.get(exerciseP).getImage());
                    intent.putExtra("exNumber",String.valueOf(exerciseP));
                    intent.putExtra("exercise list", exList);
                    intent.putExtra("dayIndex",dayIndex);
                    intent.putExtra("month",month);
                    intent.putExtra("week",week);
                    v.getContext().startActivity(intent);
                    finish();}
                else {
                    Toast.makeText(getBaseContext(),"هذا اول تمرين",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showCalBtn() {
        calculateVolumeAndRM1();
        //calVAndR.setVisibility(View.VISIBLE);
    }

    private ArrayList<sets> populateList() {


        addNewSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sets s2 = new sets();
                list.add(s2);
                adapterFree.notifyDataSetChanged();
                //calVAndR.setVisibility(View.VISIBLE);
                sumVolume = 0;
            }
        });
        return list;
    }




    public void calculateVolumeAndRM1() {
        max = Double.MIN_VALUE;
        sumVolume = 0;
        sumWeight = 0;

        if(adapterFree != null){
            for (int i = 0; i < adapterFree.getSetsArrayFree().size(); i++) {
                if(adapterFree.getSetsArrayFree().get(i).getWeight() == null){
                    checker = false;
                    Toast.makeText
                            (this, "الرجاء ادخال الوزن و عدد مرات التكرار لكل الجلسات", Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (!adapterFree.getSetsArrayFree().get(i).getWeight().isEmpty()) {
                    if(!adapterFree.getSetsArrayFree().get(i).getReps().isEmpty())
                    checker = true;
                    if (!adapterFree.getSetsArrayFree().get(i).getVolume().isEmpty() &&
                            !adapterFree.getSetsArrayFree().get(i).getRM1().isEmpty() && checker) {
                        //calculate total volume
                        totalVolume = Double.valueOf(adapterFree.getSetsArrayFree().get(i).getVolume());
                        sumVolume = sumVolume + totalVolume;

                        //maximum of RM1 here
                        if (Double.valueOf(setsArrayList.get(i).getRM1()) > max) {
                            max = Double.valueOf(setsArrayList.get(i).getRM1());
                        }
                    }

                } else {
                    checker = false;
                    sumVolume =0;
                    max = 0;
                    Toast.makeText
                            (this, "الرجاء ادخال الوزن وعدد مرات التكرار", Toast.LENGTH_SHORT).show();
                    break;

                }
            }
        }
        if(checker) {
            tvTotalVolume.setText(String.valueOf(sumVolume));
            tvMaxRM1.setText(String.valueOf(max));
        }
        if (adapterFree == null) {
            for (int i = 0; i < adapter.getSetsArray().size(); i++) {
                if (!adapter.getSetsArray().get(i).getWeight().isEmpty()) {
                    checker = true;
                    if (!adapter.getSetsArray().get(i).getVolume().isEmpty() &&
                            !adapter.getSetsArray().get(i).getRM1().isEmpty() && checker) {
                        //calculate total volume and weight
                        totalVolume = Double.valueOf(adapter.getSetsArray().get(i).getVolume());
                        totalWeight = Double.valueOf(adapter.getSetsArray().get(i).getWeight());
                        sumVolume = sumVolume + totalVolume;
                        sumWeight = sumWeight + totalWeight;

                        //maximum of RM1 here
                        if (Double.valueOf(setsList.get(i).getRM1()) > max) {
                            max = Double.valueOf(setsList.get(i).getRM1());
                        }
                    }

                } else {
                    checker = false;
                    sumVolume =0;
                    max = 0;
                    sumWeight = 0;
                    Toast.makeText
                            (this, "الرجاء ادخال الوزن و عدد مرات التكرار لكل الجلسات", Toast.LENGTH_SHORT).show();
                    break;

                }
            }

            if(checker) {
                tvTotalVolume.setText(String.valueOf(sumVolume));
                tvMaxRM1.setText(String.valueOf(max));
            }
        }
        uploadVolumeAnd1RM();
    }


    private void clickToEnlarge() {
        imageViewEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPic();
            }
        });
    }
    private void showPic() {
        final Dialog nagDialog = new Dialog(ActivityInsideExercise.this
                ,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // nagDialog.setCancelable(false);
        nagDialog.setCanceledOnTouchOutside(true);
        nagDialog.setContentView(R.layout.preview_image);
        Button btnClose = nagDialog.findViewById(R.id.btnIvClose);
        ImageView ivPreview = nagDialog.findViewById(R.id.iv_preview_image);
        final ProgressBar progressBar = nagDialog.findViewById(R.id.progressBar2);


        Ion.with(this)
                .load(exImage)
                .progressBar(progressBar)
                .withBitmap()
                // .placeholder(R.drawable.ic_loading)
                .intoImageView(ivPreview)
                .setCallback(new FutureCallback<ImageView>() {
                    @Override
                    public void onCompleted(Exception e, ImageView result) {
                        progressBar.setVisibility(View.GONE);
                    }
                });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                nagDialog.dismiss();
            }
        });
        nagDialog.show();
    }
    protected void getMonthAndWeekNum(){
        Intent intent = this.getIntent();

        month = intent.getStringExtra("month");
        week = intent.getStringExtra("week");
        day = intent.getStringExtra("day");
        dayIndex = intent.getStringExtra("dayIndex");



        day = String.valueOf(dayIndex);
    }
    public void getExercises(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        //go to the child where you want to retreive values of
        DatabaseReference usersRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Exercises")
                .child(month).child(week).child(day).child("exercise").child(exNumber);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                volume1RmEx = dataSnapshot.getValue(exercise.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void uploadVolumeAnd1RM() {
        if(volume1RmEx != null && checker) {
            ArrayList<sets> setsToUpdate = volume1RmEx.getSets();

            setsList.size();
            setsToUpdate.size();


            setsToUpdate.get(setsToUpdate.size() - 1).setVolume(String.valueOf(sumVolume));
            setsToUpdate.get(setsToUpdate.size() - 1).setRM1(String.valueOf(max));
            setsToUpdate.get(setsToUpdate.size() - 1).setWeight(String.valueOf(sumWeight));

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            //go to the child where you want to retreive values of
            DatabaseReference rm1Ref = rootRef.child("users").child(firebaseAuth.getUid()).child("Exercises")
                    .child(month).child(week).child(day).child("exercise").child(exNumber)
                    .child("sets").child(String.valueOf(setsToUpdate.size() - 1)).child("rm1");

            DatabaseReference volumeRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Exercises")
                    .child(month).child(week).child(day).child("exercise").child(exNumber)
                    .child("sets").child(String.valueOf(setsToUpdate.size() - 1)).child("volume");

            DatabaseReference weightRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Exercises")
                    .child(month).child(week).child(day).child("exercise").child(exNumber)
                    .child("sets").child(String.valueOf(setsToUpdate.size() - 1)).child("weight");

            rm1Ref.setValue(String.valueOf(max));
            volumeRef.setValue(String.valueOf(sumVolume));
            weightRef.setValue(String.valueOf(sumWeight)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    getSetsHistory();
                    Toast.makeText(ActivityInsideExercise.this, "تم التحديث", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }
    private void getSetsHistory(){
        setsHistory = new ArrayList<>();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        //go to the child where you want to retreive values of
        DatabaseReference setsRef = rootRef.child("users").child(firebaseAuth.getUid()).child("Exercises")
                .child(month).child(week).child(day).child("exercise").child(exNumber)
                .child("sets");

        setsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    setsHistory.add(ds.getValue(sets.class));


                }
                if(setsHistory.get(0).getVolume().isEmpty() && setsHistory.get(0).getWeight().isEmpty()
                        && setsHistory.get(0).getRM1().isEmpty()){
                    historyText.setVisibility(View.VISIBLE);
                    recyclerViewHistory.setVisibility(View.GONE);

                }else {
                    historyText.setVisibility(View.GONE);
                    historyAdapter = new HistoryAdapter(setsHistory, getApplicationContext());
                    recyclerViewHistory.setAdapter(historyAdapter);
                    recyclerViewHistory.setHasFixedSize(true);
                    recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                    historyAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
