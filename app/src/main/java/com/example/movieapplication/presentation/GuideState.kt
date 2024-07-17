package com.example.movieapplication.presentation

import com.example.movieapplication.domain.model.Movie
import com.example.movieapplication.domain.model.Playlist

data class GuideState (
    val movieId: Long = 0,
    val playlistId: Long = 0,
    var playlistMovies: List<Movie> = listOf(),
    var moviesWithPlaylists: Map<Long,List<Playlist>> = mapOf(),
    var playlists: List<Playlist> = listOf(),
    var openBottomSheet: Boolean = false,
    var filterState: Boolean = false,
    var addPlaylist: Boolean = false,
    var filterApplied: Boolean = false
)
