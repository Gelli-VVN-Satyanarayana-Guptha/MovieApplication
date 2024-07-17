package com.example.movieapplication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.movieapplication.R

/*
ClassicCard(
modifier = Modifier
.fillMaxWidth()
.safeContentPadding(),
onClick = { },
image = {
    Box (
        modifier = Modifier
            .padding(10.dp),
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
                modifier = Modifier.clickable{
                    //
                }
            )
        }

    }



},
title = {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column {
            Text(text = movie.title)
            Spacer(modifier = Modifier.padding(1.dp))
            Text(
                text = "Rating: " + movie.rating,
                style = MaterialTheme.typography.labelSmall
            )
        }
        Icon(
            Icons.Outlined.FavoriteBorder,
            contentDescription = null
        )
    }
},
colors = CardDefaults.colors(containerColor = Color(0xFF03DAC5))
)
 */