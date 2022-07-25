package com.idzayu.kinoline.model.movies.Repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.idzayu.kinoline.model.movies.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiMovieRepository(
    private val ioDispatcher: CoroutineDispatcher
) : MovieRepositories {

    private val enableErrorsFlow = MutableStateFlow(false)

    override fun isErrorsEnabled(): Flow<Boolean> = enableErrorsFlow

    override fun setErrorsEnabled(value: Boolean) {
        enableErrorsFlow.value = value
    }

    override fun getPagedMovie(): Flow<PagingData<Movie>> {
        val loader: MoviePageLoader = { pageIndex, pageSize ->
            getMovies(pageIndex, pageSize)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(loader, PAGE_SIZE) }
        ).flow    }

    private suspend fun getMovies(pageIndex: Int, pageSize: Int)
            = withContext(ioDispatcher) {

        if (enableErrorsFlow.value) throw IllegalStateException("Error!")
        var pageIndexV = pageIndex
        while (pageIndexV < pageIndex + pageSize) {
            getPageMovie(pageIndexV)
            pageIndexV++
        }
        Log.d("ssasdas", pageIndexV.toString())
        Log.d("ssasdas", pageIndex.toString())
        Log.d("ssasdas", (MovieList.movieList.size - (pageIndexV - pageIndex - 1)).toString())
        Log.d("ssasdas", MovieList.movieList.size.toString())
        Log.d("ssasdas", MovieList.movieList.subList(
            MovieList.movieList.size - (pageIndexV - pageIndex - 1),
            MovieList.movieList.size
        ).size.toString())

        MovieList.setPageIndex(pageIndexV)
        return@withContext MovieList.movieList.subList(
            MovieList.movieList.size - (pageIndexV - pageIndex - 1),
            MovieList.movieList.size
        )
    }

    private suspend fun getPageMovie(pageIndex: Int){
        delay(100)
        App.instance.api.getCommitsByName(pageIndex)
            .enqueue(object : Callback<MovieModel> {
                override fun onResponse(
                    call: Call<MovieModel>,
                    response: Response<MovieModel>
                ) {
                    if (response.isSuccessful) {
                        MovieList.movieList.add(response.body()?.let {
                            Movie(
                                pageIndex,it.posterUrl, it.nameFilm, it.description
                            )
                        } ?: Movie(pageIndex,"","",""))
                    }
                }
                override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                }
            })
    }

    private companion object {
        const val PAGE_SIZE = 5
    }
}