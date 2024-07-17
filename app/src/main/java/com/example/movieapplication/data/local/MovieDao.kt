package com.example.movieapplication.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.movieapplication.data.local.entity.MovieEntity
import com.example.movieapplication.data.local.entity.MovieWithPlaylists
import com.example.movieapplication.data.local.entity.PlaylistEntity
import com.example.movieapplication.data.local.entity.PlaylistMovieCrossRef
import com.example.movieapplication.data.local.entity.PlaylistWithMovies
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Upsert(entity = MovieEntity::class)
    suspend fun upsertAll(movies : List<MovieEntity>)

    @Query("SELECT * FROM movies")
    fun pagingSource() : PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM movies")
    fun getCount() : Long

    @Upsert(entity = PlaylistEntity::class)
    suspend fun upsertPlaylist(playlist : PlaylistEntity)

    @Delete(entity = PlaylistEntity::class)
    suspend fun deletePlaylist(playlist : PlaylistEntity)

    @Query("SELECT * FROM playlists WHERE playlistId = :id")
    suspend fun getPlaylist(id: Long): PlaylistEntity

    @Query("SELECT * FROM playlists")
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>

    @Transaction
    @Query("SELECT * FROM playlists WHERE playlistId = :id")
    fun getMoviesOfPlaylist(id: Long): PlaylistWithMovies

    @Transaction
    @Query("SELECT * FROM movies")
    fun getPlaylistsOfMovies(): Flow<List<MovieWithPlaylists>>

    @Insert(entity = PlaylistMovieCrossRef::class,onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovieToPlaylist(playlistMovieCrossRef: PlaylistMovieCrossRef)

}