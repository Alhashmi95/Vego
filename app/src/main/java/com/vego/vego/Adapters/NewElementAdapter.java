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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vego.vego.Activity.MealIngrActivity;
import com.vego.vego.R;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.elements;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.meal;

import java.util.ArrayList;
import java.util.List;

public class NewElementAdapter extends RecyclerView.Adapter<NewElementAdapter.ElementViewHolder> {

    private ArrayList<elements> eleList;
    private Context mContext;
    private CardView cardViewMeals;
    private LayoutInflater inflater;


    public NewElementAdapter(ArrayList<elements> eleList, Context mContext) {
        this.eleList = eleList;
            inflater = LayoutInflater.from(mContext);
    }


    @Override
    public NewElementAdapter.ElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.meal_card_view, parent, false);
        ElementViewHolder holder = new ElementViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(ElementViewHolder holder, final int position) {
        holder.txtQuan.setText(eleList.get(position).getAmount());
        holder.txtIngr.setText(eleList.get(position).getName());
        Log.d("print","yes");

        //holder.mealCount.setText("."+String.valueOf(position+1));

        holder.btn_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eleList.size()==1){
                    Toast.makeText(mContext,"يجب ان تحتوي على عنصر واحد على الاقل",Toast.LENGTH_SHORT).show();
                }else {
                eleList.remove(position);
                notifyDataSetChanged();}
            }
        });
    }

    @Override
    public int getItemCount() {

        return eleList.size();
    }
    public ArrayList<elements> getElementssArray() {
        return eleList;
    }
    class ElementViewHolder extends RecyclerView.ViewHolder{

        EditText txtIngr, txtQuan;
        TextView mealCount;
        Button btn_delet ;



        public ElementViewHolder(final View itemView) {
            super(itemView);
            btn_delet=itemView.findViewById(R.id.btn_delet);

            txtIngr = itemView.findViewById(R.id.txtIngr);
            txtQuan = itemView.findViewById(R.id.txtQuantity);
//            mealCount = itemView.findViewById(R.id.mealCount);

            txtIngr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



                    eleList.get(getAdapterPosition()).setName(txtIngr.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            txtQuan.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    eleList.get(getAdapterPosition()).setAmount(txtQuan.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }

    }

}
