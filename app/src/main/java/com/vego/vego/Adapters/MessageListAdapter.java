package com.vego.vego.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.iconics.utils.Utils;
import com.sendbird.android.SendBird;
import com.sendbird.android.User;
import com.squareup.picasso.Picasso;
import com.vego.vego.R;
import com.vego.vego.model.Chat;
import com.vego.vego.model.UserInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEWTYPE_ITEM_DATE_CONTAINER = 3;


    private Context mContext;
    private List<Chat> mMessageList;

    public MessageListAdapter(Context context, List<Chat> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Chat message = (Chat) mMessageList.get(position);

        if (message.getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
       // return 3;
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_show, parent, false);

        switch (viewType) {
            case VIEW_TYPE_MESSAGE_SENT:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.my_message, parent, false);
                return new SentMessageHolder(view);
            case VIEW_TYPE_MESSAGE_RECEIVED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.their_message, parent, false);
                return new ReceivedMessageHolder(view);
        }
        // code to populate type 2 view here

        return new DateShowHolder(view);
      //  return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Chat message = (Chat) mMessageList.get(position);



        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message, position);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message, position);
                break;
            case VIEWTYPE_ITEM_DATE_CONTAINER:
                ((DateShowHolder) holder).bind(message, position);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, tvDate;
        String date,datePrev,time;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);

        }

        void bind(Chat message, int position) {
            messageText.setText(message.getMsg());

            //set date to default pattern
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy",Locale.ENGLISH);
            try {
                Date dw = dateFormat.parse(message.getDate());
                date = dateFormat.format(dw);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            //here we get the whole pattern then extract what we need (which is in this case TIME)
                    try {
                        Date dw = new SimpleDateFormat("MMM d, yyyy HH:mm:ss",Locale.ENGLISH)
                                .parse(message.getDate());
                        time = new SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(dw);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
             // get date and assign it to text view holder
                if(position != 0) {
                    //for previous message
                    SimpleDateFormat dateFormatPrev = new SimpleDateFormat("MMM d, yyyy",Locale.ENGLISH);
                    try {
                        Date dw = dateFormatPrev.parse(mMessageList.get(position-1).getDate());
                        datePrev = dateFormatPrev.format(dw);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    processDate(tvDate, date
                            , datePrev
                            , false)
                    ;
                }else {
                    processDate(tvDate, date
                            , null
                            , true)
                    ;
                }

            // Format the stored timestamp into a readable String using method.
            timeText.setText(time);
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText, tvDate;
        CircleImageView profileImage;
        UserInfo userInfo;

        String date,datePrev,time;


        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);

            profileImage = itemView.findViewById(R.id.avatar);
        }

        void bind(Chat message, int position) {
            // get date and assign it to text view holder

            //set date to default pattern
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy",Locale.ENGLISH);
            try {
                Date dw = dateFormat.parse(message.getDate());
                date = dateFormat.format(dw);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            //here we get the whole pattern then extract what we need (which is in this case TIME)
            try {
                Date dw = new SimpleDateFormat("MMM d, yyyy HH:mm:ss",Locale.ENGLISH)
                        .parse(message.getDate());
                time = new SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(dw);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(position != 0) {
                //for previous message
                SimpleDateFormat dateFormatPrev = new SimpleDateFormat("MMM d, yyyy",Locale.ENGLISH);
                try {
                    Date dw = dateFormatPrev.parse(mMessageList.get(position-1).getDate());
                    datePrev = dateFormatPrev.format(dw);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                processDate(tvDate, date
                        , datePrev
                        , false)
                ;
            }else {
                processDate(tvDate, date
                        , null
                        , true)
                ;
            }
            messageText.setText(message.getMsg());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(time);

            nameText.setText(message.getSendername());

            //get the pic url from firebase
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

            DatabaseReference databaseReference = firebaseDatabase.getReference().child("users")
                    .child(message.getSenderId()).child("Profile");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userInfo = dataSnapshot.getValue(UserInfo.class);

                    // Insert the profile image from the URL into the ImageView.
                    assert userInfo != null;
                    if(userInfo.getImage() != null) {
                        Picasso.get()
                                .load(userInfo.getImage())
                                .fit()
                                .into(profileImage);
                    }else {
                        profileImage.setImageResource(R.drawable.profile_pic_large);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private class DateShowHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        String date,datePrev,time;


        DateShowHolder(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }

        void bind(Chat message, int position) {
            // get date and assign it to text view holder

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy",Locale.ENGLISH);
            try {
                Date dw = dateFormat.parse(message.getDate());
                date = dateFormat.format(dw);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            if(position != 0) {
                //for previous message
                SimpleDateFormat dateFormatPrev = new SimpleDateFormat("MMM d, yyyy",Locale.ENGLISH);
                try {
                    Date dw = dateFormatPrev.parse(mMessageList.get(position-1).getDate());
                    datePrev = dateFormatPrev.format(dw);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                processDate(tvDate, date
                        , datePrev
                        , false)
                ;
            }else {
                processDate(tvDate, date
                        , null
                        , true)
                ;
            }
            //tvDate.setText(message.getDateChat());
        }
    }
    private void processDate(@NonNull TextView tv, String dateAPIStr
            , String dateAPICompareStr
            , boolean isFirstItem) {

        DateFormat f = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
        if (isFirstItem) {
            //first item always got date/today to shows
            //and overkill to compare with next item flow
            Date dateFromAPI = null;
            try {
                dateFromAPI = f.parse(dateAPIStr);
                if (DateUtils.isToday(dateFromAPI.getTime())) tv.setText("today");
                else if (DateUtils.isToday(dateFromAPI.getTime() + DateUtils.DAY_IN_MILLIS)) tv.setText("yesterday");
                else tv.setText(dateAPIStr);
                tv.setIncludeFontPadding(false);
                tv.setVisibility(View.VISIBLE);
            } catch (ParseException e) {
                e.printStackTrace();
                tv.setVisibility(View.GONE);
            }
        } else {
            if (!dateAPIStr.equalsIgnoreCase(dateAPICompareStr)) {
                try {
                    Date dateFromAPI = f.parse(dateAPIStr);
                    if (DateUtils.isToday(dateFromAPI.getTime())) tv.setText("today");
                    else if (DateUtils.isToday(dateFromAPI.getTime() + DateUtils.DAY_IN_MILLIS)) tv.setText("yesterday");
                    else tv.setText(dateAPIStr);
                    tv.setIncludeFontPadding(false);
                    tv.setVisibility(View.VISIBLE);
                } catch (ParseException e) {
                    e.printStackTrace();
                    tv.setVisibility(View.GONE);
                }
            } else {
                tv.setVisibility(View.GONE);
            }
        }
    }
}
