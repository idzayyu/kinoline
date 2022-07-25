package com.idzayu.kinoline.model.movies.Repository

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("posterUrl") val posterUrl : String,
    @SerializedName("nameRu") val nameFilm : String,
    @SerializedName("description") val description : String
)
