package com.vego.vego.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vego.vego.Activity.DayMealsActivity;
import com.vego.vego.R;
import com.vego.vego.model.DietDay;

import java.util.List;



    public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {




        private List<DietDay> daysList;
        private Context mContext;
        public FragmentManager f_manager;


        public DaysAdapter(List<DietDay> daysList, Context mContext, FragmentManager f_manager)
        {
            this.f_manager = f_manager;
            this.daysList = daysList;
            this.mContext = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_meals, parent, false);
            return new ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {


            final DietDay dayList = daysList.get(position);
            holder.txtDay.setText("اليوم :"+dayList.getDay());
            holder.txtCals.setText(dayList.getTotalCals()+" سعرة");
            holder.txtCount.setText("عدد الوجبات :"+dayList.getMealsCount());



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(mContext, DayMealsActivity.class);

                    intent.putExtra("DayMeals",daysList.get(position).getDayMeals());
                    intent.putExtra("day",daysList.get(position).getDay());
                    if(dayList.getDay().isEmpty()){
                        Toast.makeText(mContext,
                                "الرجاء الانتظار لحين وضع الجدول الغذائي الخاص بك",Toast.LENGTH_SHORT).show();
                    }else {
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }


        @Override
        public int getItemCount() {

            return daysList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView txtDay;
            public TextView txtCals;
            public TextView txtCount;
            final Context context;


            public ViewHolder(View itemView) {
                super(itemView);

                txtDay =  itemView.findViewById(R.id.txtDay);
                txtCals = itemView.findViewById(R.id.txtCals);
                txtCount = itemView.findViewById(R.id.txtCount);
                context = itemView.getContext();


            }
        }
    }
