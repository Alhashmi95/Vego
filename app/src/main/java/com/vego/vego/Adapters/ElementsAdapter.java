package com.vego.vego.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vego.vego.Activity.MealIngrActivity;
import com.vego.vego.R;
import com.vego.vego.model.elements;
import com.vego.vego.model.ingredients;
import com.vego.vego.model.meal;

import java.util.ArrayList;
import java.util.List;

public class ElementsAdapter extends RecyclerView.Adapter<ElementsAdapter.ElementsViewHolder> {

    private ArrayList<elements> eleList;
    private Context mContext;


    public ElementsAdapter(ArrayList<elements> ingrList, Context mContext) {
        this.eleList = ingrList;
        this.mContext = mContext;
    }

    @Override
    public ElementsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.meal_card_view_text_view, null);
        ElementsViewHolder elementViewHolder = new ElementsViewHolder(view);
        return elementViewHolder;
    }

    @Override
    public void onBindViewHolder(ElementsViewHolder holder, final int position) {
        //getting the product of the specified position
        elements dayIngr =eleList.get(position);

        //binding the data with the viewholder views
       // Log.d("test","this is ingrType"+dayIngr.getType());

        holder.textViewTitle.setText(dayIngr.getAmount());
        holder.textViewShortDesc.setText(dayIngr.getName());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(mContext, MealIngrActivity.class);
//                intent.putExtra("DayMeals",ingrList.get(position).getQuantity());
//                v.getContext().startActivity(intent);
//            }
//        });

//        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(dayMeals.getImg()));



    }

    @Override
    public int getItemCount() {
        return eleList.size();
    }

    class ElementsViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewShortDesc;
        ImageView imageView;

        public ElementsViewHolder(final View itemView) {
            super(itemView);

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
