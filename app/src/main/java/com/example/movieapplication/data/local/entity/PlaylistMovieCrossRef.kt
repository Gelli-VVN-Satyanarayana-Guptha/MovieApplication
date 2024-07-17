package com.example.movieapplication.data.local.entity

import androidx.room.Entity

@Entity(primaryKeys = ["playlistId", "movieId"])
data class PlaylistMovieCrossRef(
    val playlistId: Long,
    val movieId: Long
)
