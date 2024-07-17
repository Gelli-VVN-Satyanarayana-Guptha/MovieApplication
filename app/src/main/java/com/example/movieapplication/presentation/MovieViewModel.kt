package com.example.movieapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.movieapplication.data.local.entity.MovieEntity
import com.example.movieapplication.data.repository.MovieRepositoryImpl
import com.example.movieapplication.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val pager : Pager<Int, MovieEntity>,
    private val movieRepo : MovieRepositoryImpl
) : ViewModel() {

    val moviePagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toMovie() }
        }
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(GuideState())
    private val _playlists = movieRepo.getPlaylists()
    private val _moviesWithPlaylists = movieRepo.moviesWithPlaylists()
    val state = combine(_state, _playlists, _moviesWithPlaylists) { state, listOfPlaylists, moviesWithPlaylists ->
        state.copy(
            playlists = listOfPlaylists,
            moviesWithPlaylists = moviesWithPlaylists
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GuideState())

    fun onEvent(event: GuideEvent){
        when(event) {

            is GuideEvent.AddPlaylist -> {
                viewModelScope.launch {
                    movieRepo.addPlaylist(playlistName = event.playlistName)
                }
                _state.update {
                    it.copy(
                        addPlaylist = false
                    )
                }
            }
            is GuideEvent.DeletePlaylist -> {
                viewModelScope.launch {
                    movieRepo.deletePlaylist(playlistId = event.playlistId)
                }
            }

            is GuideEvent.ShowPlaylistMovies -> {
                var playlistMovies = listOf<Movie>()
                viewModelScope.launch {
                    playlistMovies = movieRepo.getMoviesOfPlaylist(playlistId = event.playlistId)
                }
                _state.update {
                    it.copy(
                        playlistMovies = playlistMovies,
                        filterApplied = true
                    )
                }
            }

            is GuideEvent.SaveToPlaylist -> {
                viewModelScope.launch {
                    movieRepo.addMovieToPlaylist(
                        movieId = event.movieId,
                        playlistId = event.playlistId
                    )
                }
                _state.update {
                    it.copy(
                        openBottomSheet = !state.value.openBottomSheet
                    )
                }
            }

            GuideEvent.ShowFilter -> {
                _state.update{
                    it.copy(
                        openBottomSheet = !state.value.openBottomSheet,
                        filterState = true
                    )
                }
            }

            GuideEvent.CloseFilterEffect -> {
                _state.update {
                    it.copy(
                        filterApplied = false
                    )
                }
            }

            is GuideEvent.ShowBottomSheet -> {
                _state.update {
                    it.copy(
                        movieId = event.movieId,
                        openBottomSheet = !state.value.openBottomSheet
                    )
                }
            }

            GuideEvent.HideBottomSheet -> {
                _state.update {
                    it.copy(
                        filterState = false,
                        openBottomSheet = !state.value.openBottomSheet
                    )
                }
            }

            GuideEvent.ShowAddPlaylistDialog -> {
                _state.update {
                    it.copy(
                        addPlaylist = !state.value.addPlaylist
                    )
                }
            }
        }
    }
}