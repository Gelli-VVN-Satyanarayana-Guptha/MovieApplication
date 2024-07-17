package com.example.movieapplication.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithPlaylists(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "playlistId",
        associateBy = Junction(PlaylistMovieCrossRef::class)
    )
    val playlists: List<PlaylistEntity>
){
    fun toListOfPlaylists() : List<PlaylistEntity> {
        return playlists
    }
}
