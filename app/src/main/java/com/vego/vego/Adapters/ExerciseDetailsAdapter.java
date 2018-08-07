package com.vego.vego.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vego.vego.Activity.ActivityWorkoutUser;
import com.vego.vego.Fragment.Add_workout2user;
import com.vego.vego.R;
import com.vego.vego.model.exercise;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.sets;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDetailsAdapter  extends RecyclerView.Adapter<ExerciseDetailsAdapter.ViewHolder> {


    private ArrayList<sets> exList;
    private Context mContext;
    sets exListTest;

    public ExerciseDetailsAdapter(ArrayList<sets> exList, Context mContext) {
        this.exList = exList;
        this.mContext = mContext;
    }

    @Override
    public ExerciseDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_sets_show, null);
        ExerciseDetailsAdapter.ViewHolder exerciseDetailsViewHolder = new ExerciseDetailsAdapter.ViewHolder(view);
        return exerciseDetailsViewHolder;
    }


    @Override
    public void onBindViewHolder(final ExerciseDetailsAdapter.ViewHolder holder, final int position) {


          exListTest = exList.get(position);
        int setsNo = position+1;
        holder.txtSets.setText(String.valueOf(setsNo));
        holder.txtReps.setText(exListTest.getReps());

        holder.etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(holder.etWeight.getText().toString() != null && holder.etWeight.getText().length() > 0) {
                    exList.get(position).setWeight(holder.etWeight.getText().toString());

                    double Volume = Double.valueOf(exList.get(position).getWeight()) * exList.size() *
                            Double.valueOf(exListTest.getReps());

                    double RM1 = Double.valueOf(exList.get(position).getWeight()) * (36 / (37
                            - Double.valueOf(exListTest.getReps())));


                    holder.textViewRM1.setText(String.format("%.1f", RM1));
                    holder.textViewVolume.setText(String.valueOf(Volume));
                }else {
                    holder.textViewRM1.setText("");
                    holder.textViewVolume.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        Picasso.get()
//                .load(dayMeals.getImage())
//                .placeholder(R.drawable.ic_loading)
//                .fit()
//                .centerCrop()
//                .into(holder.imageView);


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ActivityWorkoutUser.class);
//
//                intent.putExtra("exSets", exList.get(position).getSets());
//                //intent.putExtra("day",daysList.get(position).getDay());
//                v.getContext().startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {

        return exList.size();
    }
    public ArrayList<sets> getSetsArray() {
        return exList ;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSets;
        TextView textViewVolume, textViewRM1;
         TextView txtReps;
         ImageView exImage;
         EditText etWeight;


        public ViewHolder(View itemView) {
            super(itemView);

            txtSets = itemView.findViewById(R.id.txtSet);
            textViewVolume = itemView.findViewById(R.id.textViewVolume);
            txtReps = itemView.findViewById(R.id.txtReps);
            exImage = itemView.findViewById(R.id.imageViewMu);
            etWeight = itemView.findViewById(R.id.txtWeight);
            textViewRM1 = itemView.findViewById(R.id.txtRM1);




        }
    }
}
