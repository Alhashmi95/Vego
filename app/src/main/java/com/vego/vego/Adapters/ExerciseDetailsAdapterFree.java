package com.vego.vego.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vego.vego.Activity.ActivityInsideExercise;
import com.vego.vego.Fragment.FragmentAddMealDetailes;
import com.vego.vego.R;
import com.vego.vego.model.sets;

import java.util.ArrayList;

public class ExerciseDetailsAdapterFree extends RecyclerView.Adapter<ExerciseDetailsAdapterFree.ViewHolder> {
    private ArrayList<sets> exList;
    private Context mContext;
    sets exListTest;
    int setsNo;
    DataTransferInterface dtInterface;
    //step 3
    DataTransferInterface listner;



    boolean checker = false;
    private boolean activate;

    public ExerciseDetailsAdapterFree(ArrayList<sets> exList, Context mContext) {
        this.exList = exList;
        this.mContext = mContext;

    }

    @Override
    public ExerciseDetailsAdapterFree.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_sets_show_free, null);
        ExerciseDetailsAdapterFree.ViewHolder exerciseDetailsViewHolder = new ExerciseDetailsAdapterFree.ViewHolder(view);
        return exerciseDetailsViewHolder;
    }
    public interface DataTransferInterface {
        public void setValues(boolean ch);
    }


    @Override
    public void onBindViewHolder(final ExerciseDetailsAdapterFree.ViewHolder holder, final int position) {


        exListTest = exList.get(position);
        setsNo = position+1;
        holder.txtSets.setText(String.valueOf(setsNo));

        holder.txtReps.setText(exList.get(position).getReps());
        holder.etWeight.setText(exList.get(position).getWeight());

        holder.btn_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exList.size()==1){
                    Toast.makeText(mContext,"يجب ان تحتوي على عنصر واحد على الاقل",Toast.LENGTH_SHORT).show();
                }else {
                    exList.remove(position);
                    notifyDataSetChanged();
                    checker = true;
                    if(mContext instanceof ActivityInsideExercise){
                        ((ActivityInsideExercise)mContext).showCalBtn();
                    }
                }
            }
        });
//        holder.txtReps.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(holder.etWeight.getText().toString() != null && holder.etWeight.getText().length() > 0
//                        && holder.txtReps.getText().toString() != null && holder.txtReps.getText().length() > 0) {
//                    exList.get(position).setWeight(holder.etWeight.getText().toString());
//                    exList.get(position).setReps(holder.txtReps.getText().toString());
//
//                    double Volume = Double.valueOf(exList.get(position).getWeight()) *
//                            Double.valueOf(position+1) *
//                            Double.valueOf(exList.get(position).getReps());
//
//                    double RM1 = Double.valueOf(exList.get(position).getWeight()) * (36 / (37
//                            - Double.valueOf(exList.get(position).getReps())));
//
//
//                    holder.textViewRM1.setText(String.format("%.1f", RM1));
//                    holder.textViewVolume.setText(String.valueOf(Volume));
//
//
//                    //get text of text views here to use it in the activity using (getSetsArray) method
//                    exList.get(position).setVolume(holder.textViewVolume.getText().toString());
//                    exList.get(position).setRM1(holder.textViewRM1.getText().toString());
//                }else {
//                    holder.textViewRM1.setText("");
//                    holder.textViewVolume.setText("");
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


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
    public ArrayList<sets> getSetsArrayFree() {
        return exList ;
    }

    public boolean getCheckerValue() {
        return checker;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSets;
        TextView textViewVolume, textViewRM1;
        EditText txtReps;
        ImageView exImage;
        EditText etWeight;
        Button btn_delet ;



        public ViewHolder(View itemView) {
            super(itemView);

            txtSets = itemView.findViewById(R.id.txtSet);
            textViewVolume = itemView.findViewById(R.id.textViewVolume);
            txtReps = itemView.findViewById(R.id.etReps);
            exImage = itemView.findViewById(R.id.imageViewMu);
            etWeight = itemView.findViewById(R.id.txtWeight);
            textViewRM1 = itemView.findViewById(R.id.txtRM1);
            btn_delet=itemView.findViewById(R.id.btn_delet);

            etWeight.setEnabled(false);
            etWeight.setFocusable(false);



            etWeight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    double Volume = 0;
                    if(etWeight.getText().toString() != null && !etWeight.getText().toString().isEmpty()
                            && txtReps.getText().toString() != null && !txtReps.getText().toString().isEmpty()) {
//                    exList.get(position).setWeight(holder.etWeight.getText().toString());
//                    exList.get(position).setReps(holder.txtReps.getText().toString());

                        exList.get(getAdapterPosition()).setReps(txtReps.getText().toString());
                        exList.get(getAdapterPosition()).setWeight(etWeight.getText().toString());




                        Volume = Double.valueOf(exList.get(getAdapterPosition()).getWeight()) *
                                Double.valueOf(getAdapterPosition()+1) *
                                Double.valueOf(exList.get(getAdapterPosition()).getReps());

                        double RM1 = Double.valueOf(exList.get(getAdapterPosition()).getWeight()) * (36 / (37
                                - Double.valueOf(exList.get(getAdapterPosition()).getReps())));


                        textViewRM1.setText(String.format("%.1f", RM1));
                        textViewVolume.setText(String.valueOf(Volume));


                        //get text of text views here to use it in the activity using (getSetsArray) method
                        exList.get(getAdapterPosition()).setVolume(textViewVolume.getText().toString());
                        exList.get(getAdapterPosition()).setRM1(textViewRM1.getText().toString());
                    }else {
                        textViewRM1.setText("");
                        textViewVolume.setText("");
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            txtReps.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!txtReps.getText().toString().isEmpty() && !txtReps.getText().equals("")){
                    etWeight.setEnabled(true);
                    etWeight.setFocusableInTouchMode(true);
                }else {
                    etWeight.setEnabled(false);
                    etWeight.setFocusable(false);
                }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



        }
    }
}
