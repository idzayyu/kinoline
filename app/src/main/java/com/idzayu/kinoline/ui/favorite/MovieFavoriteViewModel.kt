package com.idzayu.kinoline.ui.favorite
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.idzayu.kinoline.Movie
import com.idzayu.kinoline.MovieList

class MovieFavoriteViewModel:  ViewModel() {
    val movieList = MovieList.getMovieFavoriteList()

    fun onFavoriteClick(movie: Movie, position: Int) {
        movieList.remove(movie)
        movie.isLike = false
        movie.isfavorite = false
    }
}