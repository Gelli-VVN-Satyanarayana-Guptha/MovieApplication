package com.example.movieapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapplication.domain.model.Playlist

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val playlistId : Long = 0,
    val playlistName : String
){
    fun toPlaylist() : Playlist{
        return Playlist(
            playlistId = playlistId,
            playlistName = playlistName
        )
    }
}
