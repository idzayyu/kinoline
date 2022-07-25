package com.idzayu.kinoline.ui.detail

import androidx.lifecycle.ViewModel
import com.idzayu.kinoline.model.movies.Repository.MovieList

class DetailFilmViewModel :  ViewModel() {

    var flag = false
    private var position = MovieList.getPositionSelectedMovie()
    private val movieList = MovieList.movieList
    private val isFavorite = movieList[position].isfavorite
    private val movieFavoriteList = MovieList.getMovieFavoriteList()

    fun getDescriptionText(): String {
        return movieList[position].description
    }
    fun getImageId(): String {
        return movieList[position].imageUrl
    }
    fun onClickLike(){
        flag = !flag
        if (isFavorite) {
            movieFavoriteList[position].isLike = flag
        }
        else{
            movieList[position].isLike = flag
        }
    }
    fun addComment(comment: String){
        movieList[position].comment = comment
    }
}