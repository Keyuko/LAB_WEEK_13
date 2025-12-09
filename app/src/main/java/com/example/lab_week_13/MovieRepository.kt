package com.example.lab_week_13

import com.example.lab_week_13.api.MovieService
import com.example.lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.example.lab_week_13.database.MovieDatabase
import com.example.lab_week_13.database.MovieDao

class MovieRepository(private val movieService: MovieService, private val movieDatabase: MovieDatabase) {
    private val apiKey = "e9f18cdceafead39e191d68fc85aa127"
    fun fetchMovies(): Flow<List<Movie>> {
        return flow {
            // Check if there are movies saved in the database
            val movieDao: MovieDao = movieDatabase.movieDao()
            val savedMovies = movieDao.getMovies()
            // If there are no movies saved in the database,
            // fetch the list of popular movies from the API
            if(savedMovies.isEmpty()) {
                val movies = movieService.getPopularMovies(apiKey).results
                // save the list of popular movies to the database
                movieDao.addMovies(movies)
                // emit the list of popular movies from the API
                emit(movies)
            } else {
                // If there are movies saved in the database,
                // emit the list of saved movies from the database
                emit(savedMovies)
            }
        }.flowOn(Dispatchers.IO)
    }
}
