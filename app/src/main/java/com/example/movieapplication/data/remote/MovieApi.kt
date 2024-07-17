package com.example.movieapplication.data.remote

import com.example.movieapplication.data.remote.dto.PageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("popular")
    suspend fun getMovies(
        @Query("page") page: Long
    ) : PageDto

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    }
}
