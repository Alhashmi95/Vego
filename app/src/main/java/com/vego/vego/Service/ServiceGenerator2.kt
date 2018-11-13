package com.vego.vego.Service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator2 {

    private var API_BASE_URL = "https://fcm.googleapis.com"

    private val httpClient = OkHttpClient.Builder()
//            .readTimeout(60, TimeUnit.SECONDS)
//            .connectTimeout(60, TimeUnit.SECONDS)

    private val builder = Retrofit.Builder()

            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = this.builder.client(this.httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}