package com.idzayu.kinoline.model.movies.Repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert
    fun insert(entity: MovieEntity)

    @Insert
    fun insert(list: List<MovieEntity>)

    @Insert
    fun insertFavorite(list: List<MovieFavoriteEntity>)

    @Query("SELECT * FROM MovieEntity ")
    fun selectMovie(): List<MovieEntity>

    @Query("SELECT * FROM MovieFavoriteEntity ")
    fun selectFavoriteMovie(): List<MovieEntity>

    @Query("SELECT MAX(id) FROM MovieEntity ")
    fun selectMaxIndexMovie(): Int

}