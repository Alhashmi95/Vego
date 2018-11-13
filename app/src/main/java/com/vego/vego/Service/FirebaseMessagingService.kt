package com.vego.vego.Service

/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent

import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.vego.vego.Activity.LoginActivity
import com.vego.vego.Activity.MainActivity
import com.vego.vego.R
import org.json.JSONObject


class FirebaseMessagingService : FirebaseMessagingService() {

    /**
     * Called when message is received.

     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
//        Log.v(toString(), "remote message: ${remoteMessage!!.notification.clickAction}")
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the actionType
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage!!.from)

        // Check if message contains a data payload.
        if (remoteMessage!!.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            Log.d(TAG, "Message data notification: " + remoteMessage.notification!!.clickAction)

        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body)
        }


            this.sendNotification(remoteMessage.notification!!.title!!,
                    remoteMessage.notification!!.body!!,
                    this.notificationData(JSONObject(remoteMessage.data)))

    }


    override fun onDeletedMessages() {
        super.onDeletedMessages()

        Log.e(toString(), "onDeletedMessages")
    }

    private fun notificationData(data: JSONObject): Intent {
        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        if (data.has("channelId")) {
            intent.putExtra("NEW_NOTIFICATIONS", true)
            intent.putExtra("userId", data.getString("userId"))
            intent.putExtra("channelId", data.getString("channelId"))
            intent.putExtra("customerName", data.getString("customerName"))
        }

        return intent
    }

    private fun sendNotification(messageTitle: String, messageBody: String, intent: Intent) {
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val defaultSoundUri = RingtoneManager.getDefaultUri(TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    companion object {
        private val TAG = "MyFirebaseMsgService"
    }
}