package com.vego.vego.Adapters;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vego.vego.R;

import com.vego.vego.model.elements;


import java.util.List;

public class MealDetailesAdapter extends  RecyclerView.Adapter<MealDetailesAdapter.ViewHolder> {


    private List<elements> elementList;
    private Context mContext;



    public MealDetailesAdapter(List<elements> elementList, Context mContext) {
        this.elementList = elementList;
        this.mContext = mContext;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_cardview_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // هنا تربط المتغيرات حقتك "النصوص" مع الاوبجكت من الكلاس اللي تبغى تنزله على الكارد
        final elements element = elementList.get(position);
        holder.txtType.setText(element.getName());
        holder.txtQuantity.setText(element.getAmount());



    }

    @Override
    public int getItemCount() {
        return elementList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        // تعريف للاشياء اللي حتظهر في الكارد
        public TextView txtType;
        public TextView txtQuantity;
        final Context context;
        private CardView cardViewIngs;



        public ViewHolder(View itemView) {
            super(itemView);

            //ربط الكارد والمتغيرات بالاشياء في الـ XML
            cardViewIngs = itemView.findViewById(R.id.cardViewIngs);
            txtType =  itemView.findViewById(R.id.txtIngr);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            context = itemView.getContext();


        }
    }
}
