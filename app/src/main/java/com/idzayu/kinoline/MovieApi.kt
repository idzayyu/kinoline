package com.idzayu.kinoline

import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {

    @GET("movieList.gson")
    fun getMovie(): Call<List<MovieModel>>
}