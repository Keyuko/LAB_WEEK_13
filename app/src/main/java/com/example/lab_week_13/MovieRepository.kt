package com.example.lab_week_13

import com.example.lab_week_13.api.MovieService
import com.example.lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(private val movieService: MovieService) {
    private val apiKey = "e9f18cdceafead39e191d68fc85aa127"
    fun fetchMovies(): Flow<List<Movie>> = flow {
        val sorted = movieService.getPopularMovies(apiKey)
            .results
            .sortedByDescending { it.popularity }

        sorted.take(5).forEach {
            println("SORT CHECK -> ${it.title} | popularity = ${it.popularity}")
        }

        emit(sorted)
    }.flowOn(Dispatchers.IO)

}
