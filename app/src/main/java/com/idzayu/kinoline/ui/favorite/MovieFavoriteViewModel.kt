package com.idzayu.kinoline.ui.favorite
import androidx.lifecycle.ViewModel
import com.idzayu.kinoline.model.movies.Movie
import com.idzayu.kinoline.model.movies.Repository.MovieList

class MovieFavoriteViewModel:  ViewModel() {
    val movieList = MovieList.getMovieFavoriteList()

    fun onFavoriteClick(movie: Movie, position: Int) {
        movieList.remove(movie)
        movie.isLike = false
        movie.isfavorite = false
    }
}