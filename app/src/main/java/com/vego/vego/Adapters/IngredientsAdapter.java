package com.vego.vego.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vego.vego.Activity.MealIngrActivity;
import com.vego.vego.R;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.meal;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private ArrayList<ingredients> ingrList;
    private Context mContext;


    public IngredientsAdapter(ArrayList<ingredients> ingrList, Context mContext) {
        this.ingrList = ingrList;
        this.mContext = mContext;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.meal_card_view_text_view, null);
        IngredientsViewHolder ingredientsViewHolder = new IngredientsViewHolder(view);
        return ingredientsViewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, final int position) {
        //getting the product of the specified position
        ingredients dayIngr =ingrList.get(position);

        //binding the data with the viewholder views
        Log.d("test","this is ingrType"+dayIngr.getType());

        holder.textViewTitle.setText(dayIngr.getQuantity());
        holder.textViewShortDesc.setText(dayIngr.getType());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(mContext, MealIngrActivity.class);
//                intent.putExtra("DayMeals",ingrList.get(position).getQuantity());
//                v.getContext().startActivity(intent);
//            }
//        });

//        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(dayMeals.getImg()));

//        holder.btn_delet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ingrList.size()==1){
//                    Toast.makeText(mContext,"يجب ان تحتوي على عنصر واحد على الاقل",Toast.LENGTH_SHORT).show();
//                }else {
//                ingrList.remove(position);
//                notifyDataSetChanged();
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return ingrList.size();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewShortDesc;
        ImageView imageView;
        Button btn_delet ;


        public IngredientsViewHolder(final View itemView) {
            super(itemView);
            btn_delet=itemView.findViewById(R.id.btn_delet);

            textViewTitle = itemView.findViewById(R.id.txtQuantity);
            textViewShortDesc = itemView.findViewById(R.id.txtIngr);
//            cardViewMeals = itemView.findViewById(R.id.cardViewMeals);
//            imageView = itemView.findViewById(R.id.imageView);

//            itemView.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view){
//                    itemView.getContext().startActivity(new Intent(itemView.getContext(),MealIngrActivity.class));
//
//                }
//            });


        }
    }
}
