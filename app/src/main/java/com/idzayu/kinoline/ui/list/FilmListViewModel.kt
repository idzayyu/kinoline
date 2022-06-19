package com.idzayu.kinoline.ui.list

import androidx.lifecycle.ViewModel
import com.idzayu.kinoline.Movie
import com.idzayu.kinoline.MovieList

class FilmListViewModel: ViewModel() {
    private val movieFavoriteList = MovieList.getMovieFavoriteList()
    private val movieList = MovieList.getMovieList()


    fun onFavoriteClick(movie: Movie, position: Int): Boolean {
        return if (!movie.isfavorite){
            movie.isfavorite = true
            movieFavoriteList.add(movie)
            if (movieList.size > 0){
                movieList[position].isLike = true
            }
            true
        } else{
            false
        }
    }

}