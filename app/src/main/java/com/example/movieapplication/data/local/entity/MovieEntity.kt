package com.example.movieapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapplication.domain.model.Movie

@Entity(tableName = "movies")
data class MovieEntity (
    @PrimaryKey
    val movieId : Long,
    val title : String,
    val rating : String,
    val imageUrl : String

){
    fun toMovie() : Movie {
        return Movie(
            movieId = movieId,
            title = title,
            rating = rating,
            imageUrl = imageUrl
        )
    }
}
