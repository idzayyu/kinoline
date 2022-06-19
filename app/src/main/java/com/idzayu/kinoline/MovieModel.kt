package com.idzayu.kinoline

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("imageUrl") val imageUrl : String,
    @SerializedName("nameFilm") val nameFilm : String,
    @SerializedName("description") val description : String
)
