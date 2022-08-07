package com.idzayu.kinoline.model.movies.Repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("api/v2.2/films/{Id}")
    fun getCommitsByName(@Path("Id") id: Int): Call<MovieModel>
}