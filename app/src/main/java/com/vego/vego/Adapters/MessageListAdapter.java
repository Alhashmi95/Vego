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
import com.mikepenz.iconics.utils.Utils;
import com.sendbird.android.SendBird;
import com.vego.vego.R;
import com.vego.vego.model.Chat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        if (message.getUserUID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
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

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);

        }

        void bind(Chat message, int position) {
            messageText.setText(message.getMessageChat());
             // get date and assign it to text view holder
                if(position != 0) {
                    processDate(tvDate, message.getDateChat()
                            , mMessageList.get(position - 1).getDateChat()
                            , false)
                    ;
                }else {
                    processDate(tvDate, message.getDateChat()
                            , null
                            , true)
                    ;
                }

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getCreatedAt());
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText, tvDate;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);

            //profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(Chat message, int position) {
            // get date and assign it to text view holder
            if(position != 0) {
                processDate(tvDate, message.getDateChat()
                        , mMessageList.get(position - 1).getDateChat()
                        , false)
                ;
            }else {
                processDate(tvDate, message.getDateChat()
                        , null
                        , true)
                ;
            }
            messageText.setText(message.getMessageChat());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getCreatedAt());

            nameText.setText(message.getnameChat());

            // Insert the profile image from the URL into the ImageView.
            //Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }

    private class DateShowHolder extends RecyclerView.ViewHolder {
        TextView tvDate;

        DateShowHolder(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }

        void bind(Chat message, int position) {
            // get date and assign it to text view holder
            if(position != 0) {
                processDate(tvDate, message.getDateChat()
                        , mMessageList.get(position - 1).getDateChat()
                        , false)
                ;
            }else {
                processDate(tvDate, message.getDateChat()
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
                tv.setVisibility(View.INVISIBLE);
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
                    tv.setVisibility(View.INVISIBLE);
                }
            } else {
                tv.setVisibility(View.INVISIBLE);
            }
        }
    }
}
