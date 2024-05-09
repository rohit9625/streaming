package com.devx.streaming.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devx.streaming.utils.NetworkResult
import com.devx.streaming.viewmodels.MainViewModel

@Composable
fun HomeScreen(
    onVideoClick : (Int)-> Unit
) {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CustomSearchBar(
                query = mainViewModel.query,
                onQueryChange = { mainViewModel.updateQuery(it) },
                onSearch = { mainViewModel.searchQuery() },
                modifier = Modifier
                    .padding(vertical = 32.dp, horizontal = 48.dp)
                    .fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when(uiState) {
                is NetworkResult.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is NetworkResult.Error -> {
                    uiState.message?.let {
                        ErrorBox(
                            message = it,
                            onRetry = { mainViewModel.getVideos() })
                    }
                }
                is NetworkResult.Success -> {
                    uiState.data?.let {videos->
                        LazyColumn {
                            items(videos) {
                                VideoCard(
                                    video = it,
                                    modifier = Modifier.clickable {
                                        onVideoClick(it.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(onVideoClick = {})
}