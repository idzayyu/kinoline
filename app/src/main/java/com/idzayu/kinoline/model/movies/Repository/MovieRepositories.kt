package com.idzayu.kinoline.model.movies.Repository

import androidx.paging.PagingData
import com.idzayu.kinoline.model.movies.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepositories {

    fun isErrorsEnabled(): Flow<Boolean>

    fun setErrorsEnabled(value: Boolean)

    fun getPagedMovie(): Flow<PagingData<Movie>>
}