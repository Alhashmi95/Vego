package com.vego.vego.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vego.vego.Activity.ActivityInsideExercise;
import com.vego.vego.Activity.ActivityWorkoutUser;
import com.vego.vego.Activity.DayMealsActivity;
import com.vego.vego.R;
import com.vego.vego.model.exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {


    private ArrayList<exercise> exList;
    private Context mContext;
    public FragmentManager f_manager;
    String month,week,day;

    ProgressBar progressBar;




    public ExerciseAdapter(ArrayList<exercise> exList, Context mContext,String month, String week,String day) {
        this.exList = exList;
        this.mContext = mContext;
        this.month = month;
        this.week = week;
        this.day = day;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //progressBar = parent.findViewById(R.id.progressBar);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.cardview_addworkout, null);
        ExerciseAdapter.ViewHolder exerciseViewHolder = new ExerciseAdapter.ViewHolder(view);
        return exerciseViewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {




        final exercise exListTest = exList.get(position);
        holder.txtName.setText(exListTest.getExername());
        holder.txtMu.setText(exListTest.getTargetedmuscle());
        Picasso.get()
                .load(exListTest.getImage())
                //.placeholder(R.drawable.ic_loading)
                .fit()
                .centerCrop()
                .into(holder.exImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
        String mu = exListTest.getTargetedmuscle();

        if(mu.equals("صدر")){
            Picasso.get()
                    .load(R.drawable.mu_chest)
                    //.placeholder(R.drawable.ic_loading)
                    .fit()
                    .centerCrop()
                    .into(holder.muImage);
        }
        if(mu.equals("بطن")){
            Picasso.get()
                    .load(R.drawable.mu_abs)
                    //.placeholder(R.drawable.ic_loading)
                    .fit()
                    .centerCrop()
                    .into(holder.muImage);
        }
        if(mu.equals("قدم")){
            Picasso.get()
                    .load(R.drawable.mu_quadriceps)
                    //.placeholder(R.drawable.ic_loading)
                    .fit()
                    .centerCrop()
                    .into(holder.muImage);
        }
        if(mu.equals("تراي")){
            Picasso.get()
                    .load(R.drawable.mu_triceps)
                    //.placeholder(R.drawable.ic_loading)
                    .fit()
                    .centerCrop()
                    .into(holder.muImage);
        }
        if(mu.equals("ترابيس")){
            Picasso.get()
                    .load(R.drawable.mu_trabiz)
                    //.placeholder(R.drawable.ic_loading)
                    .fit()
                    .centerCrop()
                    .into(holder.muImage);
        }
        if(mu.equals("اكتاف")){
            Picasso.get()
                    .load(R.drawable.mu_shoulders)
                    //.placeholder(R.drawable.ic_loading)
                    .fit()
                    .centerCrop()
                    .into(holder.muImage);
        }
        if(mu.equals("باي")){
            Picasso.get()
                    .load(R.drawable.mu_biceps)
                    //.placeholder(R.drawable.ic_loading)
                    .fit()
                    .centerCrop()
                    .into(holder.muImage);
        }
        if(mu.equals("عضلة السمانة")){
            Picasso.get()
                    .load(R.drawable.mu_gastrocnemius)
                    //.placeholder(R.drawable.ic_loading)
                    .fit()
                    .centerCrop()
                    .into(holder.muImage);
        }
        if(mu.equals("ظهر")){
            Picasso.get()
                    .load(R.drawable.mu_bavk)
                    //.placeholder(R.drawable.ic_loading)
                    .fit()
                    .centerCrop()
                    .into(holder.muImage);
        }
        if(mu.equals("سواعد")){
            Picasso.get()
                    .load(R.drawable.mu_bavk)
                    //.placeholder(R.drawable.ic_loading)
                    .fit()
                    .centerCrop()
                    .into(holder.muImage);
        }if(mu.equals("كارديو")){
            Picasso.get()
                    .load(R.drawable.mu_bavk)
                    //.placeholder(R.drawable.ic_loading)
                    .fit()
                    .centerCrop()
                    .into(holder.muImage);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityInsideExercise.class);

                intent.putExtra("exSets", exList.get(position).getSets());
                intent.putExtra("exName", exList.get(position).getExername());
                intent.putExtra("exImage", exList.get(position).getImage());
                intent.putExtra("exNumber",String.valueOf(position));
                intent.putExtra("exercise list", exList);

                intent.putExtra("month",month);
                intent.putExtra("week",week);
                intent.putExtra("day",day);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {

        return exList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtMu;
        public ImageView exImage;
        public ImageView muImage;

        ProgressBar progressBar;

        final Context context;


        public ViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtNameExercise);
            txtMu = itemView.findViewById(R.id.txtMu);
            exImage = itemView.findViewById(R.id.imageViewMu);
            muImage = itemView.findViewById(R.id.ivtargtedMu);
            context = itemView.getContext();
            progressBar = itemView.findViewById(R.id.progressBar);



        }
    }
}
