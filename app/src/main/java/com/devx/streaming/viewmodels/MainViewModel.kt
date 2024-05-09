package com.devx.streaming.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devx.streaming.models.Video
import com.devx.streaming.utils.Constants
import com.devx.streaming.utils.NetworkResult
import com.devx.streaming.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val networkUtils: NetworkUtils
): ViewModel() {
    var uiState = MutableStateFlow<NetworkResult<List<Video>>>(NetworkResult.Loading())
        private set
    var query by mutableStateOf("")
        private set

    init {
        getVideos()
    }

    fun updateQuery(query: String) {
        this.query = query
    }

    fun searchQuery(){
        uiState.value = NetworkResult.Loading()
        try {
            viewModelScope.launch {
                val queryPatterns = query.split(" ")
                val resultByTitle = async {
                    supabaseClient.from("videos").select {
                        filter {
                            ilikeAny("title", patterns = queryPatterns.map {
                                "%${it.trim()}%"
                            })
                        }
                    }.decodeList<Video>()
                }.await()

                val resultByDescription = async {
                    supabaseClient.from("videos").select {
                        filter {
                            ilikeAll("description", patterns = queryPatterns.map {
                                "%${it.trim()}%"
                            })
                        }
                        }.decodeList<Video>()
                }.await()

                val resultByChannelName = async {
                    supabaseClient.from("videos").select {
                        filter {
                            ilikeAll("channel_name", queryPatterns.map {
                                "%${it.trim()}%"
                            })
                        }
                    }.decodeList<Video>()
                }.await()

                val result = resultByTitle + resultByDescription + resultByChannelName
                val videos = result.distinct()

                uiState.value = NetworkResult.Success(videos)
            }
        } catch (e: Exception) {
            Log.e(Constants.TAG, "Error searching videos, Error: ${e.message}")
        }

    }

    fun getVideos() {
        uiState.value = NetworkResult.Loading()
        //check internet connection
        if(!networkUtils.isNetworkAvailable()) {
            uiState.value = NetworkResult.Error(message = "Network Unavailable")
            return
        }
        try {
            viewModelScope.launch {
                val response = supabaseClient.from("videos").select().decodeList<Video>()
                uiState.value = NetworkResult.Success(response)
            }
        }catch (e: Exception) {
            Log.e(Constants.TAG, "Error retrieving videos from the database, Error: ${e.message}")
            uiState.value = NetworkResult.Error("Internal Server Error")
        }
    }
}