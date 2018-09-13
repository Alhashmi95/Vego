package com.vego.vego.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vego.vego.R;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.sets;

import java.util.ArrayList;
import java.util.List;

public class NewSetAdapter extends RecyclerView.Adapter<NewSetAdapter.SetViewHolder> {
    private ArrayList<sets> setList;
    private LayoutInflater inflater;
    private Context mContext;
    sets exListTest;

    public NewSetAdapter(ArrayList<sets> setList, Context mContext) {
        this.setList = setList;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public NewSetAdapter.SetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_sets_add, parent, false);
        SetViewHolder holder = new SetViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NewSetAdapter.SetViewHolder holder, final int position) {

        exListTest = setList.get(position);
        int setsNo = position + 1;
        String StringSetsNo = String.valueOf(setsNo);
        holder.txtSets.setText(StringSetsNo);

        //dont forget this (to maintain order of arraylist      IIIDDDDDIIIOOOTT
        holder.txtReps.setText(setList.get(position).getReps());
        holder.btn_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return setList.size();
    }

    public ArrayList<sets> getSetsArray() {
        return setList;
    }


    class SetViewHolder extends RecyclerView.ViewHolder {

        EditText txtReps;
        TextView txtSets;
        TextView setCount;
        Button btn_delet ;

        public SetViewHolder(final View itemView) {
            super(itemView);

            txtReps = itemView.findViewById(R.id.txtReps);
            txtSets = itemView.findViewById(R.id.txtSet);
          //  setCount = itemView.findViewById(R.id.mealCount);
            btn_delet=itemView.findViewById(R.id.btn_delet);

            txtReps.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    setList.get(getAdapterPosition()).setReps(txtReps.getText().toString());
                    setList.get(getAdapterPosition()).setRM1("");
                    setList.get(getAdapterPosition()).setWeight("");
                    setList.get(getAdapterPosition()).setVolume("");

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        }
    }
}
