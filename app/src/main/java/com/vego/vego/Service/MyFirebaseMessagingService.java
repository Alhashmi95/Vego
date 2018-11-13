//package com.vego.vego.Service;
//
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.IBinder;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//import com.vego.vego.Activity.MainActivity;
//import com.vego.vego.R;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
//import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
//import static android.media.RingtoneManager.TYPE_NOTIFICATION;
//import static com.facebook.AccessTokenManager.TAG;
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        // ...
//
//        // TODO(developer): Handle FCM messages here.
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
////        Log.d(TAG, "From: " + remoteMessage.getFrom());
//
////        // Check if message contains a data payload.
////        if (remoteMessage.getData().size() > 0) {
////            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//////
////            if (/* Check if data needs to be processed by long running job */ true) {
////                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
////              //  scheduleJob();
////            } else {
////                // Handle message within 10 seconds
////              //  handleNow();
////            }
//            // Check if message contains a notification payload.
////            if (remoteMessage.getNotification() != null) {
//               Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
////            }
////            if (!AlakatkHostActivity2.IS_IN_CHAT) {
//        try {
//            this.sendNotification(remoteMessage.getNotification().getTitle(),
//                    remoteMessage.getNotification().getBody(),
//                    this.notificationData(new JSONObject(remoteMessage.getData())));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
////            }
//
////
////        }
////
////        // Check if message contains a notification payload.
////        if (remoteMessage.getNotification() != null) {
////         //   Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
////        }
////
////        // Also if you intend on generating your own notifications as a result of a received FCM
////        // message, here is where that should be initiated. See sendNotification method below.
////        }
//    }
//    @Override
//    public void onDeletedMessages() {
//        super.onDeletedMessages();
//
//        Log.e(toString(), "onDeletedMessages");
//    }
//   private Intent notificationData(JSONObject data) throws JSONException {
//        Intent intent =new Intent(this, MainActivity.class);
//        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
//        if (data.has("channelId")) {
//            intent.putExtra("NEW_NOTIFICATIONS", true);
//            intent.putExtra("userId", data.getString("userId"));
//            intent.putExtra("channelId", data.getString("channelId"));
//            intent.putExtra("customerName", data.getString("customerName"));
//        }
//
//        return intent;
//    }
//    private void sendNotification(String messageTitle,String messageBody ,Intent intent) {
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_UPDATE_CURRENT);
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder =new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.logo_welcome_screen)
//                .setContentTitle(messageTitle)
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager)
//                getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
//
//    private void sendNotification(String message){
//
//    }
//
//
//
//
//}
