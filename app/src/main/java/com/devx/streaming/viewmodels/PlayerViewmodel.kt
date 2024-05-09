package com.devx.streaming.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.devx.streaming.models.Video
import com.devx.streaming.utils.Constants
import com.devx.streaming.utils.NetworkResult
import com.devx.streaming.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewmodel @Inject constructor(
    val player: Player,
    private val supabaseClient: SupabaseClient,
    private val networkUtils: NetworkUtils
): ViewModel() {
    var currentVideo = MutableStateFlow<NetworkResult<Video>>(NetworkResult.Loading())
        private set
    var otherVideos = MutableStateFlow<NetworkResult<List<Video>>>(NetworkResult.Loading())
        private set

    init {
        player.prepare()
    }

    fun playVideo(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = async { fetchVideo(id) }.await()
            currentVideo.value = result

            result.data?.let {
                player.setMediaItem(MediaItem.fromUri(it.url))
                player.play()
                getOtherVideos(id)
            }
        }
    }

    private suspend fun fetchVideo(id: Int) : NetworkResult<Video> {
        return try {
            val response = supabaseClient.from("videos")
                .select {
                    filter { eq("id", id) }
                }.decodeSingleOrNull<Video>()

            if(response != null) {
                NetworkResult.Success(response)
            } else {
                NetworkResult.Error("Video not found")
            }

        }catch (e: Exception) {
            Log.e(Constants.TAG, "Error playing video, Error: ${e.message}")
            NetworkResult.Error(e.message)
        }
    }

    private fun getOtherVideos(currentVideoId: Int) {
        if(!networkUtils.isNetworkAvailable()) {
            otherVideos.value = NetworkResult.Error("Network Unavailable")
            return
        }
        try {
            viewModelScope.launch {
                val result = supabaseClient.from("videos").select{
                    filter { neq("id", currentVideoId) }
                }.decodeList<Video>()

                otherVideos.value = NetworkResult.Success(result)
            }
        }catch (e: Exception) {
            Log.e(Constants.TAG, "Error retrieving other videos, Error: ${e.message}")
            otherVideos.value = NetworkResult.Error("Internal Server Error")
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}