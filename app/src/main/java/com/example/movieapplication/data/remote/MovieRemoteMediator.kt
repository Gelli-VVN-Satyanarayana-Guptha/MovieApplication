package com.example.movieapplication.data.remote

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapplication.data.local.MovieDatabase
import com.example.movieapplication.data.local.entity.MovieEntity
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator (
    private val movieDb : MovieDatabase,
    private val movieApi: MovieApi
) : RemoteMediator<Int, MovieEntity>() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {

        return try {

            val loadKey = when(loadType){
                    LoadType.REFRESH -> 1
                    LoadType.PREPEND -> return  MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    LoadType.APPEND -> {

                        (movieDb.dao.getCount() / 20) + 1

                    }
            }

            val movies = movieApi.getMovies( page = loadKey )

            movieDb.withTransaction {
                if(loadType == LoadType.REFRESH){
                    movieDb.dao.clearAll()
                }
                val movieEntities = movies.results.map{ it.toMovieEntity() }
                movieDb.dao.upsertAll(movieEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = movies.results.isEmpty()
            )

        }catch (e : IOException){
            MediatorResult.Error(e)
        }catch (e : HttpException){
             MediatorResult.Error(e)
        }
    }
}

