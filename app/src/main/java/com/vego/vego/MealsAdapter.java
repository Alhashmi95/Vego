package com.vego.vego;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vego.vego.model.DayMeals;
import com.vego.vego.model.DietDay;

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
    public void onBindViewHolder(MealsViewHolder holder, int position) {
        //getting the product of the specified position
        final DayMeals dayMeals = mealsList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(dayMeals.getMealName());
        holder.textViewShortDesc.setText(dayMeals.getMealCal());

//        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(dayMeals.getImg()));



    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }

    class MealsViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewShortDesc;
        ImageView imageView;

       public MealsViewHolder(View itemView) {
           super(itemView);

           textViewTitle = itemView.findViewById(R.id.textViewTitle);
           textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            cardViewMeals = itemView.findViewById(R.id.cardViewMeals);
           imageView = itemView.findViewById(R.id.imageView);

           itemView.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view){

                   AppCompatActivity activity = (AppCompatActivity) view.getContext();
                   Fragment myFragment = new FragmentMealsDetails();
                //   activity.getSupportFragmentManager().beginTransaction().replace(R.id.viewpager_id, myFragment).addToBackStack(null).commit();


               }
           });


       }
   }
}
