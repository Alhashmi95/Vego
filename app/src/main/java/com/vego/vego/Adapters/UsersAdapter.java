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
import com.vego.vego.Activity.AdminActivity;
import com.vego.vego.Activity.InsideChatActivity;
import com.vego.vego.Activity.MealIngrActivity;
import com.vego.vego.R;
import com.vego.vego.model.Chat;
import com.vego.vego.model.DayMeals;
import com.vego.vego.model.UserInfo;
import com.vego.vego.model.meal;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private ArrayList<UserInfo> userList;
    private Context mContext;
    private ArrayList<Chat> chat;
    String time="";

    ProgressBar progressBar;


    public UsersAdapter(ArrayList<UserInfo> userList, Context mContext,ArrayList<Chat> chat) {
        this.userList = userList;
        this.mContext = mContext;
        this.chat = chat;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        progressBar = parent.findViewById(R.id.progressBar);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.users_view_holder, null);
        UsersViewHolder usersViewHolder = new UsersViewHolder(view);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(final UsersViewHolder holder, final int position) {

        //getting the product of the specified position

        Chat c = chat.get(position);


        if(!c.getMsg().equals("") && c.getMsg() != null){
            holder.textViewLastMessage.setText(c.getMsg());
        }else{
            holder.textViewLastMessage.setText("أبدا الدردشة مع هذا المتدرب بالضغط هنا");
        }

        //binding the data with the viewholder views
        holder.textViewUsername.setText(userList.get(position).getName());
        holder.textViewEmail.setText(userList.get(position).getUserEmail());

        if(!c.getDate().equals("") && c.getDate() != null) {
            Date dw = null;
            try {
                dw = new SimpleDateFormat("MMM d, yyyy HH:mm:ss", Locale.ENGLISH)
                        .parse(c.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            time = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dw);


        }else{
            holder.textViewTime.setVisibility(View.GONE);
        }

        holder.textViewTime.setText(time);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, InsideChatActivity.class);
                intent.putExtra("userList", (Serializable) userList.get(position));
 //               intent.putExtra("DayMealsElements",mealsList.get(position).getElements());
//                intent.putExtra("name",mealsList.get(position).getName());
//                intent.putExtra("position",String.valueOf(position));
//                intent.putExtra("image",mealsList.get(position).getImage());
//                intent.putExtra("meals list",mealsList);
                v.getContext().startActivity(intent);
            }
        });

//        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(dayMeals.getImg()));



    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView textViewUsername, textViewLastMessage,textViewTime,textViewEmail;
        ImageView imageView;
        ProgressBar progressBar;


        public UsersViewHolder(final View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.tv_userName);
            textViewLastMessage = itemView.findViewById(R.id.tv_lastMessage);
            textViewTime = itemView.findViewById(R.id.tv_display_time);
            imageView = itemView.findViewById(R.id.iv_profilePicSupport);
            textViewEmail = itemView.findViewById(R.id.tv_userEmail);



            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    itemView.getContext().startActivity(new Intent(itemView.getContext(),MealIngrActivity.class));

                }
            });


        }
    }
}
