package com.example.movieapplication.data.repository

import com.example.movieapplication.data.local.MovieDao
import com.example.movieapplication.data.local.entity.MovieEntity
import com.example.movieapplication.data.local.entity.PlaylistEntity
import com.example.movieapplication.data.local.entity.PlaylistMovieCrossRef
import com.example.movieapplication.domain.model.Movie
import com.example.movieapplication.domain.model.Playlist
import com.example.movieapplication.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl (
    private val dao : MovieDao
): MovieRepository {

    override suspend fun insertMovies(movies: List<MovieEntity>) {
        dao.upsertAll(movies)
    }

    override suspend fun getMovies() {
        TODO("Not yet implemented")
    }

    override suspend fun addPlaylist(playlistName: String) {
        dao.upsertPlaylist(
            PlaylistEntity(playlistName = playlistName)
        )
    }

    override suspend fun deletePlaylist(playlistId: Long) {
        dao.deletePlaylist(
            dao.getPlaylist(playlistId)
        )
    }

    override fun getPlaylists(): Flow<List<Playlist>> {
        return dao.getAllPlaylists().map {
            it.map { pl ->
                pl.toPlaylist()
            }
        }
    }

    override suspend fun addMovieToPlaylist(movieId: Long, playlistId: Long) {
        dao.addMovieToPlaylist(
            PlaylistMovieCrossRef(
                movieId = movieId,
                playlistId = playlistId
            )
        )
    }

    override fun moviesWithPlaylists(): Flow<Map<Long,List<Playlist>>> {
//        return dao.getPlaylistsOfMovies().map{
//            it.map { movieWithPlaylists ->
//                movieWithPlaylists.toMapOfPlaylists().map { playlistEntity ->
//                    playlistEntity.toPlaylist()
//                }
//            }
//        }
        return dao.getPlaylistsOfMovies().map {
            it.associate { movieWithPlaylists ->
                movieWithPlaylists.movie.movieId to movieWithPlaylists.playlists.map { playlistEntity ->
                    playlistEntity.toPlaylist()
                }
            }
        }
    }

    override suspend fun getMoviesOfPlaylist(playlistId: Long): List<Movie> {
        return dao.getMoviesOfPlaylist(playlistId).movies.map {
            it.toMovie()
        }
    }


}