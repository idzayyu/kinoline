package com.idzayu.kinoline

import android.app.Application
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

    lateinit var api: MovieApi

    override fun onCreate() {
        super.onCreate()
        instance = this

        val client = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(MovieApi::class.java)
    }

    companion object{
        const val BASE_URL = "http://t9385154.beget.tech/"
        lateinit var instance: App
            private set
    }
}