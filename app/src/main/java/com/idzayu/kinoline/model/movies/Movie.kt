package com.idzayu.kinoline.model.movies

data class Movie(
    val id: Int,
    val imageUrl: String,
    val nameFilm: String,
    val description: String,
    var isSelected: Boolean = false,
    var comment: String = "",
    var isLike: Boolean = false,
    var isfavorite: Boolean = false,
    var isDbSave: Boolean = false
    ) {

}









