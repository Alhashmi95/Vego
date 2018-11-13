package com.vego.vego.APIs

import okhttp3.ResponseBody
import org.json.simple.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FirebaseAPI {
    @POST("fcm/send")
    fun sendNotifi(@Body body: org.json.simple.JSONObject, @Header("Authorization") authorization: String): Call<ResponseBody>
}