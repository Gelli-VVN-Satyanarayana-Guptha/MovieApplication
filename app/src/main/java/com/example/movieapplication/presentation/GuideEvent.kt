package com.example.movieapplication.presentation

sealed interface GuideEvent{
    data class ShowPlaylistMovies(val playlistId : Long) : GuideEvent
    data class DeletePlaylist(val playlistId: Long) : GuideEvent
    data class AddPlaylist(val playlistName: String) : GuideEvent
    data class SaveToPlaylist(val movieId: Long,val playlistId: Long) : GuideEvent
    data class ShowBottomSheet(val movieId: Long) : GuideEvent
    object ShowFilter : GuideEvent
    object HideBottomSheet : GuideEvent
    object ShowAddPlaylistDialog : GuideEvent
    object CloseFilterEffect : GuideEvent
}