package com.idzayu.kinoline.model.movies.Repository

import android.content.Context
import android.util.Log
import com.idzayu.kinoline.model.movies.Movie
import com.idzayu.kinoline.model.movies.Repository.room.AppDataBase
import com.idzayu.kinoline.model.movies.Repository.room.MovieEntity
import com.idzayu.kinoline.model.movies.Repository.room.MovieFavoriteEntity
import java.util.concurrent.Executors

object MovieList {

    val movieList = ArrayList<Movie>()
    private var movieFavoriteList = ArrayList<Movie>()
    private var positionSelectedMovie = 0
    private var pageIndex = 301


    fun setPositionSelectedMovie(position: Int) {
        positionSelectedMovie = position
        }

    fun getPositionSelectedMovie(): Int{
        return  positionSelectedMovie
    }


    fun getMovieFavoriteList(): ArrayList<Movie> {

        return movieFavoriteList
    }

    fun getMovieEntity(): ArrayList<MovieEntity> {
        val list = ArrayList<MovieEntity>()

        movieList.forEach {
            if (!it.isDbSave) {
                list.add(
                    MovieEntity(
                        it.id,
                        it.nameFilm,
                        it.imageUrl,
                        it.description,
                        it.isSelected,
                        it.comment,
                        it.isLike,
                        it.isfavorite
                    )
                )
            }
        }

        return list
    }

    fun getMovieFavoriteEntity(): ArrayList<MovieFavoriteEntity> {
        val list = ArrayList<MovieFavoriteEntity>()

        movieFavoriteList.forEach {
            if (!it.isDbSave) {
                list.add(
                    MovieFavoriteEntity(
                        it.id,
                        it.nameFilm,
                        it.imageUrl,
                        it.description,
                        it.isSelected,
                        it.comment,
                        it.isLike,
                        it.isfavorite
                    )
                )
            }
        }

        return list
    }


    private fun getMovieFavorite(list: List<MovieEntity>?){

        list?.forEach {
            movieFavoriteList.add(Movie(
                it.id
                ,it.imageUrl
                ,it.nameFilm
                ,it.description
                ,it.isSelected
                ,it.comment
                ,it.isLike
                ,it.isfavorite
                ,true
            ))
        }

    }

    private fun getMovie(list: List<MovieEntity>?){
        list?.forEach {
            movieList.add(Movie(
                it.id
                ,it.imageUrl
                ,it.nameFilm
                ,it.description
                ,it.isSelected
                ,it.comment
                ,it.isLike
                ,it.isfavorite
                ,true
            ))
        }

    }

    fun addMovieDb(context: Context){
        val appDb = AppDataBase.getInstance(context)?.getMovieDao()
        Executors.newSingleThreadExecutor().execute {
            Runnable {
                getMovie(appDb?.selectMovie())
                getMovieFavorite(appDb?.selectFavoriteMovie())
            }.run()
        }
    }

    fun getPageIndex(): Int{
        return if (pageIndex!=0) pageIndex else 301
    }

    fun setPageIndex(index: Int){
        pageIndex = index
    }

    fun setPageIndex(context: Context){
        val appDb = AppDataBase.getInstance(context)?.getMovieDao()
        Executors.newSingleThreadExecutor().execute {
            Runnable {
                pageIndex = appDb?.selectMaxIndexMovie() ?: 301
            }.run()
        }
    }
}