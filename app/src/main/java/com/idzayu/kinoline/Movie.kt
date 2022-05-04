package com.idzayu.kinoline


class Movie(imageId: Int, nameFilm: String, description: String) {
    val imageId: Int = imageId
    val nameFilm: String = nameFilm
    var isSelected: Boolean = false
    var description: String = description
    var comment: String = ""
    var isLike = false
    var isfavorite = false
}