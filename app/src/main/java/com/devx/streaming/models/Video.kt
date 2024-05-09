package com.devx.streaming.models

import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val id: Int,
    val title: String,
    val description: String,
    val url: String,
    val thumbnail: String,
    val channel_name: String,
    val likes: Int,
    val duration: String
)