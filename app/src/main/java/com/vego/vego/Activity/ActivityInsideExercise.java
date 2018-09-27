package com.vego.vego.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.vego.vego.Adapters.ExerciseAdapter;
import com.vego.vego.Adapters.ExerciseDetailsAdapter;
import com.vego.vego.Adapters.ExerciseDetailsAdapterFree;
import com.vego.vego.Adapters.NewSetAdapter;
import com.vego.vego.R;
import com.vego.vego.model.elements;
import com.vego.vego.model.exercise;
import com.vego.vego.model.sets;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityInsideExercise extends AppCompatActivity {
    private ExerciseDetailsAdapter adapter;
    private ExerciseDetailsAdapterFree adapterFree;
    RecyclerView recyclerView;
    TextView exNumberTextView, exNameTextView, tvTotalVolume,tvMaxRM1;
    ImageView imageViewEx;
    ProgressBar progressBar;
    double totalVolume, sumVolume = 0;
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




        setsArrayList = populateList();



        clickToEnlarge();


        nextAndPreviousExercise();




        //showPic();






        calVAndR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateVolumeAndRM1();

                if(checker == false){
                    calVAndR.setVisibility(View.VISIBLE);
                }
                else {
                    calVAndR.setVisibility(View.INVISIBLE);

                }
           }
        });




        Intent intent = this.getIntent();

        exList = (ArrayList<exercise>) intent.getSerializableExtra("exercise list");
        //here we receive array of objects from daysAdapter
        //because daysAdapter has an object of DietDay which contains DayMeals array of objects
        setsList = (ArrayList<sets>) intent.getSerializableExtra("exSets");
        //get day number from الكلاس المعضل (daysAdapter)
        String exNumber = intent.getStringExtra("exNumber");
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
                btnNext.setVisibility(View.GONE);
                btnPrevious.setVisibility(View.GONE);
                adapterFree = new ExerciseDetailsAdapterFree(setsArrayList, this);
                recyclerView.setAdapter(adapterFree);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false));
                adapterFree.notifyDataSetChanged();
            }else {
                addNewSetBtn.setVisibility(View.INVISIBLE);
                calVAndR.setVisibility(View.VISIBLE);
                adapter = new ExerciseDetailsAdapter(setsList, this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }



    }

    private void nextAndPreviousExercise() {
        //moving to next meal
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exList.size()>exerciseN){
                    Intent intent = new Intent(getApplicationContext(), ActivityInsideExercise.class);

                    intent.putExtra("exSets", exList.get(exerciseN).getSets());
                    intent.putExtra("exName", exList.get(exerciseN).getExername());
                    intent.putExtra("exImage", exList.get(exerciseN).getImage());
                    intent.putExtra("exNumber",String.valueOf(exerciseN));
                    intent.putExtra("exercise list", exList);
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
                calVAndR.setVisibility(View.VISIBLE);
                sumVolume = 0;
            }
        });
        return list;
    }




    public void calculateVolumeAndRM1() {
        max = Double.MIN_VALUE;
        sumVolume = 0;

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
                        //calculate total volume
                        totalVolume = Double.valueOf(adapter.getSetsArray().get(i).getVolume());
                        sumVolume = sumVolume + totalVolume;

                        //maximum of RM1 here
                        if (Double.valueOf(setsList.get(i).getRM1()) > max) {
                            max = Double.valueOf(setsList.get(i).getRM1());
                        }
                    }

                } else {
                    checker = false;
                    sumVolume =0;
                    max = 0;
                    Toast.makeText
                            (this, "please enter all weights for all sets", Toast.LENGTH_SHORT).show();
                    break;

                }
            }

            if(checker) {
                tvTotalVolume.setText(String.valueOf(sumVolume));
                tvMaxRM1.setText(String.valueOf(max));
            }
        }
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


}
