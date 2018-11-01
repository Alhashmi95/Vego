package com.vego.vego.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vego.vego.R;
import com.vego.vego.model.exercise;
import com.vego.vego.model.sets;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private ArrayList<sets> exListHistory;
    private Context mContext;
    sets exListTest;
    int setsNo;

    public HistoryAdapter(ArrayList<sets> exList, Context mContext) {
        this.exListHistory = exList;
        this.mContext = mContext;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.sets_show_history, null);
        HistoryAdapter.ViewHolder historyViewHolder = new HistoryAdapter.ViewHolder(view);
        return historyViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //holder.txtSets.setText(exList.get(position).g);
        exListTest = exListHistory.get(position);
        holder.txtReps.setText(exListTest.getReps());
        holder.textViewRM1.setText(exListTest.getRM1());
        holder.textViewVolume.setText(exListTest.getVolume());
        holder.etWeight.setText(exListTest.getWeight());
    }

    @Override
    public int getItemCount() {

        return exListHistory.size();
    }
    public ArrayList<sets> getSetsArrayHistory() {
        return exListHistory ;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSets;
        TextView textViewVolume, textViewRM1;
        TextView txtReps;
        ImageView exImage;
        TextView etWeight;


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
