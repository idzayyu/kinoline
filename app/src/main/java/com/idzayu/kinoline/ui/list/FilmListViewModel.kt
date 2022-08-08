package com.idzayu.kinoline.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.idzayu.kinoline.model.movies.Movie
import com.idzayu.kinoline.model.movies.Repository.ApiMovieRepository
import com.idzayu.kinoline.model.movies.Repository.MovieList
import com.idzayu.kinoline.model.movies.Repository.MovieRepositories
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class FilmListViewModel(): ViewModel() {
    private val movieFavoriteList = MovieList.getMovieFavoriteList()
    private val movieList = MovieList.movieList
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val movieRepository : MovieRepositories = ApiMovieRepository(ioDispatcher)
    val moviesFlow: Flow<PagingData<Movie>> = movieRepository.getPagedMovie().cachedIn(viewModelScope)

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

    fun refresh() {
        this.refresh()
    }
}