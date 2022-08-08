package com.idzayu.kinoline.model.movies.Repository.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        MovieEntity::class
        ,MovieFavoriteEntity::class
               ],
    version = 1
)
abstract class AppDb: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}