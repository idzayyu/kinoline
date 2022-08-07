package com.idzayu.kinoline.model.movies.Repository.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.idzayu.kinoline.model.movies.Movie

@Entity(
    indices = [
        Index(value = ["id"])
    ]
)
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val nameFilm: String,
    val imageUrl: String,
    val description: String,
    var isSelected: Boolean = false,
    var comment: String = "",
    var isLike: Boolean = false,
    var isfavorite: Boolean = false
)