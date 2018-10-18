package com.vego.vego.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vego.vego.Activity.ActivityWorkoutUser;
import com.vego.vego.Activity.DayMealsActivity;
import com.vego.vego.R;
import com.vego.vego.model.Exercises;

import java.util.List;

public class DaysAdapterExercise extends RecyclerView.Adapter<DaysAdapterExercise.ViewHolder> {
    private List<Exercises> daysList;
    private Context mContext;
    public FragmentManager f_manager;
    String month,week;

        //moving month and week values ==========1=========
    public DaysAdapterExercise(List<Exercises> daysList, Context mContext, FragmentManager f_manager
    ,String month, String week)
    {
        this.f_manager = f_manager;
        this.daysList = daysList;
        this.mContext = mContext;
        this.month = month;
        this.week = week;
    }

    @Override
    public DaysAdapterExercise.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_adapter_exercise, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final DaysAdapterExercise.ViewHolder holder, final int position) {


        final Exercises dayList = daysList.get(position);
        holder.txtDay.setText("اليوم :"+dayList.getDay());
        holder.txtMuscl.setText(dayList.getTargetedmuscles());
        holder.txtCount.setText("عدد التمارين :"+dayList.getExercisecount());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, ActivityWorkoutUser.class);
                intent.putExtra("DayExercise",daysList.get(position).getExercise());
                intent.putExtra("day",daysList.get(position).getDay());

                //putExtra is all u have to do to move the values u want (remember the values will be assigined in the fragment)
                //go to fragment and see the adapter declaration
                intent.putExtra("month",month);
                intent.putExtra("week",week);
              //  intent.putExtra("day",daysList.get(position).getExercise().);

                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {

        return daysList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtDay;
        public TextView txtMuscl;
        public TextView txtCount;
        final Context context;


        public ViewHolder(View itemView) {
            super(itemView);

            txtDay =  itemView.findViewById(R.id.txtDay);
            txtMuscl = itemView.findViewById(R.id.txtCals);
            txtCount = itemView.findViewById(R.id.txtCount);

            context = itemView.getContext();


        }
    }


}
