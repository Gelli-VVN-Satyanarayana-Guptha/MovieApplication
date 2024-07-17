package com.example.movieapplication.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PlaylistWithMovies(
    @Embedded val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "movieId",
        associateBy = Junction(PlaylistMovieCrossRef::class)
    )
    val movies: List<MovieEntity>
)

