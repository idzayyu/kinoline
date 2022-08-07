package com.idzayu.kinoline.model.movies.Repository.room

import android.content.Context
import androidx.room.Room

private const val DATABASE_NAME = "MOVIEBD"
object AppDataBase {
    private var INSTANCE: AppDb? = null

    fun getInstance(context: Context): AppDb?{
        if (INSTANCE == null){
            synchronized(AppDataBase){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDb::class.java,
                    DATABASE_NAME
                    )
                    .build()
            }
        }

        return  INSTANCE
    }
    fun destroyInstance(){
        INSTANCE?.close()
        INSTANCE = null
    }
}