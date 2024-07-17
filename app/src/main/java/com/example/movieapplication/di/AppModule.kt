package com.example.movieapplication.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.movieapplication.data.local.MovieDatabase
import com.example.movieapplication.data.local.entity.MovieEntity
import com.example.movieapplication.data.remote.MovieApi
import com.example.movieapplication.data.remote.MovieRemoteMediator
import com.example.movieapplication.data.repository.MovieRepositoryImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn( SingletonComponent::class )
object AppModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context) : MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movies.db"
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideMovieApi() : MovieApi {

        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMDY0ZTM0YWQ3ODU3YTNjZjAwNDkzNDUwMGFjMGY3MSIsInN1YiI6IjY0ZGY3YzEzZDEwMGI2MTRiMzAwZTA2MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xeXlHe0SSIu3wQBAz_ByxoyTK4Ty0qhMDxsq_-eBnDE")
                .build()
            chain.proceed(newRequest)
        }).build()

        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(MovieApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMoviePager(movieDb : MovieDatabase, movieApi: MovieApi) : Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 1),
            remoteMediator = MovieRemoteMediator(
                movieDb = movieDb,
                movieApi = movieApi
            ),
            pagingSourceFactory = {
                movieDb.dao.pagingSource()
            }
        )
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieDb: MovieDatabase) : MovieRepositoryImpl {
        return MovieRepositoryImpl(movieDb.dao)
    }
}