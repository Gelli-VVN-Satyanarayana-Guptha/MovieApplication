package com.example.movieapplication.domain.repository

import com.example.movieapplication.data.local.entity.MovieEntity
import com.example.movieapplication.domain.model.Movie
import com.example.movieapplication.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun insertMovies(movies : List<MovieEntity>)

    suspend fun getMovies()

    suspend fun addPlaylist(playlistName: String)

    suspend fun deletePlaylist(playlistId: Long)

    fun getPlaylists() : Flow<List<Playlist>>

    fun moviesWithPlaylists() : Flow<Map<Long,List<Playlist>>>

    suspend fun addMovieToPlaylist(movieId: Long, playlistId: Long)

    suspend fun getMoviesOfPlaylist(playlistId: Long) : List<Movie>

}