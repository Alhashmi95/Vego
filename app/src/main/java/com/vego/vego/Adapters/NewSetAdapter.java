package com.vego.vego.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        this.mContext = mContext;
    }
    public ArrayList<sets> getSetsArray() {
        return setList;
    }


    @Override
    public NewSetAdapter.SetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_sets_add, null);
        NewSetAdapter.SetViewHolder holder = new NewSetAdapter.SetViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NewSetAdapter.SetViewHolder holder, final int position) {

        exListTest = setList.get(position);
        int setsNo = position + 1;
        String StringSetsNo = String.valueOf(setsNo);
        holder.txtSets.setText(StringSetsNo);

        holder.txtReps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setList.get(position).setReps(holder.txtReps.getText().toString());
                setList.get(position).setRM1("");
                setList.get(position).setWeight("");
                setList.get(position).setVolume("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return setList.size();
    }

    class SetViewHolder extends RecyclerView.ViewHolder {

        EditText txtReps;
        TextView txtSets;
        TextView mealCount;


        public SetViewHolder(final View itemView) {
            super(itemView);

            txtReps = itemView.findViewById(R.id.txtReps);
            txtSets = itemView.findViewById(R.id.txtSet);
            mealCount = itemView.findViewById(R.id.mealCount);


        }
    }
}
