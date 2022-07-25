package com.idzayu.kinoline.model.movies.Repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.idzayu.kinoline.model.movies.Movie
import java.lang.Exception

typealias MoviePageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<Movie>


@Suppress("UnnecessaryVariable")
class MoviePagingSource(
    private val loader: MoviePageLoader,
    private val pageSize: Int
): PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = MovieList.getPageIndex()
        return try {
            val movieList =  loader.invoke(pageIndex , params.loadSize)
            return LoadResult.Page(
                data = movieList
                ,prevKey = if(pageIndex == MovieList.getPageIndex() - pageSize * 3) null
                else pageIndex - pageSize
                ,nextKey =  pageIndex + pageSize
                ,0
                ,10
            )
        } catch (e: Exception){
            LoadResult.Error(
                throwable  = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        return 301
    }
}

