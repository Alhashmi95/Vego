package com.vego.vego.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vego.vego.Activity.MealIngrActivity;
import com.vego.vego.R;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.meal;

import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealsViewHolder> {

    private ArrayList<meal> mealsList;
    private Context mContext;
    private CardView cardViewMeals;

    ProgressBar progressBar;


    public MealsAdapter(ArrayList<meal> mealsList, Context mContext) {
        this.mealsList = mealsList;
        this.mContext = mContext;
    }

    @Override
    public MealsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        progressBar = parent.findViewById(R.id.progressBar);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.meals_list, null);
        MealsViewHolder mealsViewHolder = new MealsViewHolder(view);
                return mealsViewHolder;
    }

    @Override
    public void onBindViewHolder(final MealsViewHolder holder, final int position) {

        //getting the product of the specified position
        final meal dayMeals = mealsList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(dayMeals.getName());
        holder.textViewShortDesc.setText(dayMeals.getCal() + " سعرة");
        Picasso.get()
                .load(dayMeals.getImage())
//                .placeholder(R.drawable.progress)
                .fit()

                .into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {

                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        //holder.imageView

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, MealIngrActivity.class);
                intent.putExtra("DayMeals",mealsList.get(position).getingredients());
                intent.putExtra("DayMealsElements",mealsList.get(position).getElements());
                intent.putExtra("name",mealsList.get(position).getName());
                intent.putExtra("position",String.valueOf(position));
                intent.putExtra("image",mealsList.get(position).getImage());
                intent.putExtra("meals list",mealsList);
                v.getContext().startActivity(intent);
            }
        });

//        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(dayMeals.getImg()));



    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }

    class MealsViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewShortDesc;
        ImageView imageView;
        ProgressBar progressBar;


        public MealsViewHolder(final View itemView) {
           super(itemView);

           textViewTitle = itemView.findViewById(R.id.textViewTitle);
           textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
           cardViewMeals = itemView.findViewById(R.id.cardViewMeals);
           imageView = itemView.findViewById(R.id.imageView);

            progressBar = itemView.findViewById(R.id.progressBar);


            itemView.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view){
                   itemView.getContext().startActivity(new Intent(itemView.getContext(),MealIngrActivity.class));

               }
           });


       }
   }
}
