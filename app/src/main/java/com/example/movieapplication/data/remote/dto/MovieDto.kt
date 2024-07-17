package com.example.movieapplication.data.remote.dto

import com.example.movieapplication.data.local.entity.MovieEntity
import com.google.gson.annotations.SerializedName

data class MovieDto (

    @SerializedName("adult"             ) var adult            : Boolean?       = null,
    @SerializedName("backdrop_path"     ) var backdropPath     : String?        = null,
    @SerializedName("genre_ids"         ) var genreIds         : ArrayList<Int> = arrayListOf(),
    @SerializedName("id"                ) var id               : Int?           = null,
    @SerializedName("original_language" ) var originalLanguage : String?        = null,
    @SerializedName("original_title"    ) var originalTitle    : String?        = null,
    @SerializedName("overview"          ) var overview         : String?        = null,
    @SerializedName("popularity"        ) var popularity       : Double?        = null,
    @SerializedName("poster_path"       ) var posterPath       : String?        = null,
    @SerializedName("release_date"      ) var releaseDate      : String?        = null,
    @SerializedName("title"             ) var title            : String?        = null,
    @SerializedName("video"             ) var video            : Boolean?       = null,
    @SerializedName("vote_average"      ) var voteAverage      : Double?        = null,
    @SerializedName("vote_count"        ) var voteCount        : Int?           = null

){

    fun toMovieEntity() : MovieEntity {
        return MovieEntity(
            movieId = id?.toLong() ?: 0,
            rating = voteAverage.toString(),
            imageUrl = "https://image.tmdb.org/t/p/original$posterPath",
            title = title ?: "Not Found",
        )
    }

}
