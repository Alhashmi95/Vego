package com.vego.vego.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vego.vego.Activity.MealIngrActivity;
import com.vego.vego.R;
import com.vego.vego.model.DayMeals;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealsViewHolder> {

    private List<DayMeals> mealsList;
    private Context mContext;
    private CardView cardViewMeals;

    public MealsAdapter(List<DayMeals> mealsList, Context mContext) {
        this.mealsList = mealsList;
        this.mContext = mContext;
    }

    @Override
    public MealsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.meals_list, null);
        MealsViewHolder mealsViewHolder = new MealsViewHolder(view);
                return mealsViewHolder;
    }

    @Override
    public void onBindViewHolder(MealsViewHolder holder, final int position) {
        //getting the product of the specified position
        final DayMeals dayMeals = mealsList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(dayMeals.getMealName());
        holder.textViewShortDesc.setText(dayMeals.getMealCal());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, MealIngrActivity.class);
                intent.putExtra("DayMeals",mealsList.get(position).getIngredients());
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

       public MealsViewHolder(final View itemView) {
           super(itemView);

           textViewTitle = itemView.findViewById(R.id.textViewTitle);
           textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
           cardViewMeals = itemView.findViewById(R.id.cardViewMeals);
           imageView = itemView.findViewById(R.id.imageView);

           itemView.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view){
                   itemView.getContext().startActivity(new Intent(itemView.getContext(),MealIngrActivity.class));

               }
           });


       }
   }
}
