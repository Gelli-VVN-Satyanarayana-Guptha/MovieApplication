package com.example.movieapplication.presentation.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.movieapplication.R
import com.example.movieapplication.domain.model.Movie
import com.example.movieapplication.presentation.GuideEvent
import com.example.movieapplication.presentation.GuideState


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun Guide(
    movies : LazyPagingItems<Movie>,
    state : GuideState,
    onEvent : (GuideEvent) -> Unit
) {
    val context = LocalContext.current

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded  = true)

    LaunchedEffect (key1 = movies.loadState) {
        if(movies.loadState.refresh is LoadState.Error){
            Toast.makeText(
                context,
                "Error: " + (movies.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    MaterialTheme {
        Scaffold (
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Popular Movies",
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Cyan),
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    shape = FloatingActionButtonDefaults.largeShape,
                    onClick = {
                        onEvent(GuideEvent.ShowFilter)
                    }
                ) {
                    Icon(Icons.Filled.FilterAlt, contentDescription = "")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ){
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 70.dp, start = 8.dp, end = 8.dp)
            ){
                if(state.filterApplied){
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(state.playlistMovies){
                            MovieCard(
                                movie = it,
                                state = state,
                                onEvent = onEvent
                            )
                        }
                    }
                }else {

                    if ( movies.loadState.refresh is LoadState.Loading ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else{
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(movies) {
                                if(it != null) {
                                    MovieCard (
                                        movie = it,
                                        state = state,
                                        onEvent = onEvent
                                    )
                                }
                            }
                            item {
                                if(movies.loadState.append is LoadState.Loading){
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }

            }

            if (state.openBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        onEvent(GuideEvent.HideBottomSheet)
                    },
                    sheetState = bottomSheetState,
                ) {
                    if(!state.filterState){

                        Column (
                            modifier = Modifier
                                .padding(start = 15.dp, bottom = 10.dp, end = 15.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            LazyColumn {
                                items(state.playlists.size){

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(35.dp)
                                            .clickable {
                                                onEvent(
                                                    GuideEvent.SaveToPlaylist(
                                                        state.movieId,
                                                        state.playlists[it].playlistId
                                                    )
                                                )
                                            },
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text (
                                            text = state.playlists[it].playlistName,
                                            modifier = Modifier.padding(start = 5.dp)
                                        )

                                        Icon(
                                            Icons.Filled.Delete,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(end = 5.dp)
                                                .clickable {
                                                    onEvent(GuideEvent.DeletePlaylist(state.playlists[it].playlistId))
                                                }
                                        )

                                    }
                                }
                            }

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .clickable {
                                        onEvent(GuideEvent.ShowAddPlaylistDialog)
                                    },
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = null,
                                )
                                Text(
                                    text = "Add a playlist",
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                            }
                        }
                    } else {
                        Column (
                            modifier = Modifier
                                .padding(start = 15.dp, bottom = 10.dp, end = 15.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            LazyColumn {
                                items(state.playlists.size) {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(35.dp)
                                            .clickable {
                                                onEvent(GuideEvent.ShowPlaylistMovies(state.playlists[it].playlistId))
                                            },
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = state.playlists[it].playlistName,
                                            modifier = Modifier.padding(start = 5.dp)
                                        )
                                    }

                                }

                                item {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(35.dp)
                                            .clickable {
                                                onEvent(GuideEvent.CloseFilterEffect)
                                            },
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Filled.Search,
                                            contentDescription = null,
                                        )
                                        Text(
                                            text = "Discover Popular Movies",
                                            modifier = Modifier.padding(start = 5.dp)
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }

            if(state.addPlaylist){
                AddPlaylistDialog(state = state, onEvent = onEvent)
            }

        }
    }
}

@Composable
fun MovieCard(movie: Movie, state: GuideState, onEvent : (GuideEvent) -> Unit ) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
            .background(color = Color.LightGray)
    ) {
        Column {
            Box (
                modifier = Modifier
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)),
            ){
                if(movie.imageUrl != ""){

                    SubcomposeAsyncImage(
                        model = movie.imageUrl,
                        modifier = Modifier.fillMaxWidth(),
                        contentDescription = null
                    ){
                        val state = painter.state
                        if(state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error){
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ){
                                CircularProgressIndicator()
                            }
                        }else {
                            SubcomposeAsyncImageContent()
                        }
                    }

                }else {
                    Image(
                        painter = painterResource(id = R.drawable.not_found),
                        contentDescription = null,
                    )
                }

            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 15.dp, end = 15.dp, top = 2.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 2.dp),
                ) {
                    Text(
                        text = movie.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = FontFamily.Serif,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.padding(1.dp))
                    Text(
                        text = "Rating: " + movie.rating,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                Icon(
                    if(state.moviesWithPlaylists[movie.movieId]?.isEmpty() == true) Icons.Outlined.FavoriteBorder else Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onEvent(GuideEvent.ShowBottomSheet(movie.movieId))
                        }
                )

            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                items(state.moviesWithPlaylists[movie.movieId]!!.size){
                    Box(
                        modifier = Modifier
                            .background(color = Color.Cyan, shape = RoundedCornerShape(15))
                            .padding(5.dp)
                    ){
                        Text(
                            text = state.moviesWithPlaylists[movie.movieId]!![it].playlistName,
                            modifier = Modifier.padding(2.dp),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

        }
    }

}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddPlaylistDialog(
    state: GuideState,
    onEvent: (GuideEvent) -> Unit
){
    AlertDialog(
        onDismissRequest = {
            onEvent(GuideEvent.ShowAddPlaylistDialog)
        },
        modifier = Modifier
            .background(color = Color.White)
            .clip(RoundedCornerShape(50))
    ){
        var text by remember { mutableStateOf("") }

        Column (
            modifier = Modifier.padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "New Playlist")

            OutlinedTextField(
                modifier = Modifier.padding(top = 15.dp),
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter playlist name") }
            )

            Button(
                modifier = Modifier
                    .width(100.dp)
                    .padding(top = 20.dp),
                onClick = {
                    onEvent(GuideEvent.AddPlaylist(text))
                },
                shape = RoundedCornerShape(10),
            ) {
                Text(
                    text = "Create"
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ToDoPreview() {

}



