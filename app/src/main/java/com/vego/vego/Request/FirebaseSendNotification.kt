package com.vego.vego.Request

import android.util.Log
import android.app.Activity
import com.vego.vego.APIs.FirebaseAPI
import com.vego.vego.Service.ServiceGenerator2
import com.vego.vego.Storage.UserSharedPreferences
import com.vego.vego.response.ResponseFcmToken
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.simple.JSONObject
import java.io.IOException

/**
 *
 * Requested class to send notification message to the target device by firebase token
 *
 * @property title: for notification
 * @property message:  the reason for the notification
 * @property delegatorName: to handel chat parameters
 * @property channelId: to handel chat parameters
 * @property toFCMToken: to send notification you must get the target deveice firebase token
 * @property deviceToken: deviceToken firebase token
 *
 */
class FirebaseSendNotification(val activity: Activity,
                               val title: String,
                               val message: String,
                               val toFCMToken: String,
                               val channelId: String,
                               val delegatorName: String,
                               val deviceToken: String)
    {

    private var firebaseAPI: FirebaseAPI? = null
          var responseFcmToken: ResponseFcmToken?=null

    init {
        this.firebaseAPI = ServiceGenerator2().createService(FirebaseAPI::class.java)

    }

    private fun setUpBody(): org.json.simple.JSONObject {

        val notifyData = org.json.simple.JSONObject()
        notifyData["title"] = this.title
        notifyData["body"] = this.message
        /**
         * Need to send this key and value for android configuration
         * */
        notifyData["click_action"] = "OPEN_ACTIVITY_1"
        notifyData["sound"] = "default"


        val additionalData = org.json.simple.JSONObject()
        additionalData["userId"] = UserSharedPreferences.fetch(activity).uid
        additionalData["orderId"] = this.channelId
        additionalData["userName"] = UserSharedPreferences.fetch(activity).name


        val notifiMessage = org.json.simple.JSONObject()
        notifiMessage["notification"] = notifyData
        notifiMessage["to"] = this.toFCMToken
        notifiMessage["data"] = additionalData
        return notifiMessage
    }


        fun onResponse() {

            val serverKey = "AAAARF_r6uI:APA91bFF8DZ1mn2fz034P4QjqTiCVwYmR1jS89p_aMrQuNzeMah_5Bp4J8oajGsj6jEXMnVKOuY3FsekPte7sxH9k1KiZlUveut3SZ-UWLtWf1v6sePkgTFDkxDpFMtxNS9xf7V4McJHquW4BK-XrHIFXgbtM2VbZw"

            val call = this.firebaseAPI!!.sendNotifi(
                    authorization =serverKey, body = this.setUpBody())
            call.enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(call: retrofit2.Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                    try {
                        Log.d("test2", "" + response.code())
                        if (response.isSuccessful) {
                            responseFcmToken!!.isSuccessfulSendNotification()
                        } else {

                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    //   Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();


                }

                override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {

                    //  Toast.makeText(getApplicationContext(),"Fail", Toast.LENGTH_LONG).show();


                }


            })
        }

}