package com.vego.vego.APIs;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FirebaseAPI {
    @POST("fcm/send")
    Call<okhttp3.ResponseBody> sendNotifi(@Body HashMap<String, Object> body,
                                                 @Header("Authorization") String authorization);
}
