package com.devx.streaming.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.devx.streaming.R
import com.devx.streaming.models.Video
import com.devx.streaming.utils.DummyData.sampleVideos

@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String)-> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = {onQueryChange(it)},
        modifier = modifier.height(50.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        placeholder = {
            Text(
                text = "Search: PUBG",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        maxLines = 1,
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        shape = CircleShape,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onSearch()
                    }
            )
        }
    )
}

@Composable
fun VideoCard(
    video: Video,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(video.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "Video Thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f),
                contentScale = ContentScale.Crop
            )
            ElevatedCard(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.LightGray,
                )
            ) {
                Text(
                    text = video.duration,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 2.dp)

                )
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 32.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(0.80f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = video.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = video.channel_name,
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
            Likes(
                count = video.likes,
            )
        }
    }
}

@Composable
fun Likes(
    modifier: Modifier = Modifier,
    count: Int = 0,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.likes_icon),
            contentDescription = "Icon",
            modifier = Modifier
                .size(22.dp)
                .clickable { }
        )
        Text(
            text = "$count",
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun ErrorBox(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.network_error),
                contentDescription = "Error Icon",
                modifier = Modifier.size(56.dp)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Retry",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                modifier = Modifier.padding(top = 8.dp).clickable {
                    onRetry()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorBoxPreview() {
    ErrorBox(message = "Network Unavailable", onRetry = {})
}

@Preview(showBackground = true)
@Composable
private fun VideoCardPreview() {
    val sampleVideo = sampleVideos.first()
    VideoCard(sampleVideo)
}


@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    CustomSearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        modifier = Modifier.padding(16.dp)
    )
}