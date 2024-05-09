package com.devx.streaming.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.ui.PlayerView
import com.devx.streaming.R
import com.devx.streaming.models.Video
import com.devx.streaming.utils.NetworkResult
import com.devx.streaming.viewmodels.PlayerViewmodel

@Composable
fun VideoScreen(videoId: Int) {
    val playerViewmodel: PlayerViewmodel = hiltViewModel<PlayerViewmodel>()
    LaunchedEffect(key1 = videoId) {
        playerViewmodel.playVideo(videoId)
    }
    val uiState by playerViewmodel.currentVideo.collectAsStateWithLifecycle()
    val otherVideoState by playerViewmodel.otherVideos.collectAsStateWithLifecycle()
    var descriptionExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = {context->
                PlayerView(context).also {
                    it.player = playerViewmodel.player
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )
        when (uiState) {
            is NetworkResult.Loading -> {

            }
            is NetworkResult.Success -> {
                uiState.data?.let {
                    VideoDetails(
                        video = it,
                        onMoreClick = {
                            descriptionExpanded = !descriptionExpanded
                        },
                        modifier = Modifier.padding(16.dp),
                        isExpanded = descriptionExpanded
                    )
                }
            }
            is NetworkResult.Error -> {
                otherVideoState.message?.let {
                    Text(text = it)
                }
            }
        }
        HorizontalDivider()
        Text(
            text = "More From Us",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )
        HorizontalDivider()
        when(otherVideoState) {
            is NetworkResult.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is NetworkResult.Success -> {
                otherVideoState.data?.let {videos->
                    LazyColumn {
                        items(videos) {
                            VideoCard(
                                video = it,
                                modifier = Modifier.clickable {
                                    playerViewmodel.playVideo(it.id)
                                }
                            )

                        }
                    }
                }
            }
            is NetworkResult.Error -> {
                otherVideoState.message?.let {
                    Text(text = it)
                }
            }
        }
    }
}

@Composable
fun VideoDetails(
    video: Video,
    onMoreClick: ()-> Unit,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = video.title,
            style = MaterialTheme.typography.titleMedium
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = video.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = if (isExpanded) "less" else "more",
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable {
                        onMoreClick()
                    },
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChannelNameIcon(
                channelName = video.channel_name,
                logo = R.drawable.channel_icon,
            )
            LikeButton(count = video.likes)
        }
    }
}

@Composable
fun ChannelNameIcon(
    channelName: String,
    @DrawableRes logo: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(logo),
            contentDescription = channelName,
            modifier = Modifier
                .clip(CircleShape)
                .size(48.dp)
        )
        Text(
            text = channelName,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun LikeButton(
    modifier: Modifier = Modifier,
    count: Int = 0
) {
    OutlinedCard(
        onClick = { /*TODO*/ },
        modifier = modifier,
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.DarkGray
        )
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.likes_icon),
                contentDescription = "Like Button",
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = "$count",
                style = MaterialTheme.typography.labelLarge
            )

        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun VideoPlayerScreenPreview() {
//    VideoScreen(videoUrl = "")
}