package com.example.movieapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapplication.data.local.entity.MovieEntity
import com.example.movieapplication.data.local.entity.PlaylistEntity
import com.example.movieapplication.data.local.entity.PlaylistMovieCrossRef

@Database(
    entities = [
        MovieEntity::class,
        PlaylistEntity::class,
        PlaylistMovieCrossRef::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val dao : MovieDao
}