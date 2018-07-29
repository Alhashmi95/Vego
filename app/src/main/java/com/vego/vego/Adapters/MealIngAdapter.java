package com.vego.vego.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vego.vego.R;

import com.vego.vego.model.ingredients;

import java.util.List;

public class MealIngAdapter extends  RecyclerView.Adapter<MealIngAdapter.ViewHolder> {


    private List<ingredients> ingsList;
    private Context mContext;
    public FragmentManager f_manager;


    public MealIngAdapter(List<ingredients> ingsList, Context mContext) {
        this.ingsList = ingsList;
        this.mContext = mContext;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_cardview_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ingredients ingList = ingsList.get(position);
        holder.txtType.setText(ingList.getType());
        holder.txtQuantity.setText(ingList.getQuantity());



    }

    @Override
    public int getItemCount() {
        return ingsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtType;
        public TextView txtQuantity;
        final Context context;
        private CardView cardViewIngs;



        public ViewHolder(View itemView) {
            super(itemView);

            cardViewIngs = itemView.findViewById(R.id.cardViewIngs);
            txtType =  itemView.findViewById(R.id.txtIngr);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            context = itemView.getContext();


        }
    }
}
