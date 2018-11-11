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

import com.vego.vego.Activity.ActivityWorkoutUser;
import com.vego.vego.R;
import com.vego.vego.model.Mucsle;

import java.util.List;

public class MusclesAdapter extends RecyclerView.Adapter<MusclesAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Mucsle> mData ;


    public MusclesAdapter(Context mContext, List<Mucsle> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_view_mu_name,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {



        holder.tv_book_title.setText(mData.get(position).getMucsleName());
        holder.img_book_thumbnail.setImageResource(mData.get(position).getMucsleImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),ActivityWorkoutUser.class);

                // passing data to the book activity
                intent.putExtra("DayExercise2",mData.get(position).getExerciseArrayList());
                intent.putExtra("mucsleName",mData.get(position).getMucsleName());
                // start the activity
                v.getContext().startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView ;


        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.book_title_id) ;
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }


}