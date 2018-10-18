package com.vego.vego.Request;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;

import com.vego.vego.APIs.APIUrl;
import com.vego.vego.APIs.FirebaseAPI;
//import com.vego.vego.APIs.FirebaseAPI;
//import com.vego.vego.APIs.FirebaseAPI.*;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;


/**
 * Created by devfatani on 9/25/17.
 *
 * Requested class to send notification message to the target device by firebase token
 *
 * @property title: for notification
 * @property message:  the reason for the notification
 * @property delegatorName: to handel chat parameters
 * @property channelId: to handel chat parameters
 * @property toFCMToken: to send notification you must get the target deveice firebase token
 * @property delegatorFCMToken: delegator firebase token
 *
 */
    public class FirebaseSendNotification {
    Activity activity;
    String title, message, toFCMToken, channelId, delegatorName, delegatorFCMToken;
    String authorization = "key=AAAAaByvXTw:APA91bGpORzbC24Ua_qkqfp5E4t2-UgPKyIT0JM3M3uawtlekUDRRHf3P65Si3NRQvNn9J-qzUyZY2PaaXpkJAPgsoFUsO9VOBU7TjPN_7SvGV1pptA4lWdM8Rs2GsyvrSo0tovBxm03";


    public FirebaseSendNotification(Activity activity, String title, String message, String toFCMToken, String channelID,
                                    String delegatorName, String delegatorFCMToken) {
        this.activity = activity;
        this.title = title;
        this.message = message;
        this.toFCMToken = toFCMToken;
        this.channelId = channelID;
        this.delegatorName = delegatorName;
        this.delegatorFCMToken = delegatorFCMToken;
        // this.firebaseAPI = new APIUrl().createService(FirebaseAPI::class.java)

    }

    // private FirebaseAPI firebaseAPI= null;

    //
//        init {
//        this.firebaseAPI = ServiceGenerator2().createService(FirebaseAPI::class.java)
//
//        }
//
    private HashMap<String, Object> setUpBody() {
        HashMap<String, Object> notifyData = new HashMap<>();
        notifyData.put("title", this.title);
        notifyData.put("body", this.message);

        notifyData.put("click_action", "OPEN_ACTIVITY_1");
        notifyData.put("sound", "default");


        HashMap<String, Object> additionalData = new HashMap<>();
        additionalData.put("channelId", this.channelId);
        additionalData.put("delegatorName", this.delegatorName);
        additionalData.put("your token", this.delegatorFCMToken);

        HashMap<String, Object> notifiMessage = new HashMap<>();
        notifiMessage.put("notification", notifyData);
        notifiMessage.put("to", this.toFCMToken);
        notifiMessage.put("data", additionalData);
        return notifiMessage;
    }

    public void onResponse() {

        String serverKey = "key=AAAARF_r6uI:APA91bFF8DZ1mn2fz034P4QjqTiCVwYmR1jS89p_aMrQuNzeMah_5Bp4J8oajGsj6jEXMnVKOuY3FsekPte7sxH9k1KiZlUveut3SZ-UWLtWf1v6sePkgTFDkxDpFMtxNS9xf7V4McJHquW4BK-XrHIFXgbtM2VbZw";

        FirebaseAPI api = APIUrl.retrofit.create(FirebaseAPI.class);
        retrofit2.Call<ResponseBody> call = api.sendNotifi(setUpBody(), serverKey);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.e("test2Ayman", "" + response.code());
                if (response.isSuccessful()) {



                } else {
              }


            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                //  Toast.makeText(getApplicationContext(),"Fail", Toast.LENGTH_LONG).show();


            }


        });
    }
}
