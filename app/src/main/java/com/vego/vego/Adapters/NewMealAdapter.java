package com.vego.vego.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vego.vego.Activity.MealIngrActivity;
import com.vego.vego.R;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.meal;

import java.util.ArrayList;
import java.util.List;

public class NewMealAdapter extends RecyclerView.Adapter<NewMealAdapter.MealsViewHolder> {

    private List<ingredients> ingrList;
    private Context mContext;
    private CardView cardViewMeals;
    private LayoutInflater inflater;


    public NewMealAdapter(List<ingredients> ingrList, Context mContext) {
        this.ingrList = ingrList;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public NewMealAdapter.MealsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.meal_card_view, parent, false);
        MealsViewHolder holder = new MealsViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(NewMealAdapter.MealsViewHolder holder, final int position) {

        holder.txtQuan.setText(ingrList.get(position).getType());
        Log.d("print","yes");





    }

    @Override
    public int getItemCount() {

        return ingrList.size();
    }
    class MealsViewHolder extends RecyclerView.ViewHolder{

        EditText txtIngr, txtQuan;


        public MealsViewHolder(final View itemView) {
            super(itemView);

            txtIngr = itemView.findViewById(R.id.txtIngr);
            txtQuan = itemView.findViewById(R.id.txtQuantity);

            txtIngr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    ingrList.get(getAdapterPosition()).setType(txtIngr.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }

    }

        }
