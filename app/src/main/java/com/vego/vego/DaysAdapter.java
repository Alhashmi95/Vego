package com.vego.vego;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



    public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {


        private List<ModelDay> daysList;
        private Context mContext;

        public DaysAdapter(List<ModelDay> daysList, Context mContext) {
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

            final ModelDay dayList = daysList.get(position);
            holder.txtDay.setText(dayList.getDay());
            holder.txtCals.setText(dayList.getCalories());
            holder.txtCount.setText(dayList.getMealsCount());

        }

        @Override
        public int getItemCount() {
            return daysList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView txtDay;
            public TextView txtCals;
            public TextView txtCount;

            public ViewHolder(View itemView) {
                super(itemView);
                txtDay = (TextView) itemView.findViewById(R.id.txtDay);
                txtCals = (TextView) itemView.findViewById(R.id.txtCals);
                txtCount = (TextView) itemView.findViewById(R.id.txtCount);
            }
        }
    }
