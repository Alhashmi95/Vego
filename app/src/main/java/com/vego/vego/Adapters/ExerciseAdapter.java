package com.vego.vego.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vego.vego.Activity.ActivityInsideExercise;
import com.vego.vego.Activity.ActivityWorkoutUser;
import com.vego.vego.Activity.DayMealsActivity;
import com.vego.vego.R;
import com.vego.vego.model.exercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {


    private List<exercise> exList;
    private Context mContext;
    public FragmentManager f_manager;


    public ExerciseAdapter(List<exercise> exList, Context mContext) {
        this.exList = exList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
                .into(holder.exImage);
        String mu = exListTest.getTargetedmuscle();

        if(mu.equals("صدر")){
            Picasso.get()
                    .load(R.drawable.balance)
                    //.placeholder(R.drawable.ic_loading)
                    .fit()
                    .centerCrop()
                    .into(holder.muImage);
        }
        if(mu.equals("ظهر")){
            Picasso.get()
                    .load(R.drawable.age)
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
        final Context context;


        public ViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtNameExercise);
            txtMu = itemView.findViewById(R.id.txtMu);
            exImage = itemView.findViewById(R.id.imageViewMu);
            muImage = itemView.findViewById(R.id.ivtargtedMu);
            context = itemView.getContext();


        }
    }
}
