package com.example.movieapplication.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


class PreferencesDataStore(context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "page")
    private var pref = context.dataStore

    companion object {
        var page = 0
    }

    suspend fun increasePage(){
        pref.edit {
            page += 1
        }
    }

    suspend fun getTask() = pref.data.map {
        page
    }
}