package com.idzayu.kinoline.model.movies.Repository

import android.app.Application
import com.idzayu.kinoline.model.movies.Repository.room.AppDataBase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class App: Application() {

    lateinit var api: MovieApi

    override fun onCreate() {
        super.onCreate()
        instance = this

        Executors.newSingleThreadExecutor().execute {
            Runnable {
                AppDataBase.getInstance(context = this)
            }.run()
        }

        val client = OkHttpClient.Builder()

        client.addInterceptor() { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("X-API-KEY", "5639732c-5158-4165-a184-78bc62fd9fe2")
                .build()
            // 0 - d75c0856-d389-403e-b789-dc35213e8be4
            // 1 - 5639732c-5158-4165-a184-78bc62fd9fe2
            // 2 - c49a6ade-d9d5-492f-a8c0-b2f2a70e52a1
            // 3 - 4f23ff84-ea68-4d3b-bace-b245e0ddc275
            chain.proceed(request)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        api = retrofit.create(MovieApi::class.java)
    }

    companion object{
        const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
        lateinit var instance: App
            private set
    }
}