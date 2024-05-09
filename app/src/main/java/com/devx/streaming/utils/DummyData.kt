package com.devx.streaming.utils

import com.devx.streaming.models.Video

object DummyData {
    val sampleVideos = listOf(
        Video(
            id = 1,
            title = "Kotlin Tutorial for Beginners",
            description = "Learn the basics of Kotlin, a modern programming language for Android development.",
            url = "https://www.youtube.com/watch?v=F9UC9DY-v3g",
            thumbnail = "https://i.ytimg.com/vi/F9UC9DY-v3g/hqdefault.jpg",
            channel_name = "Google Developers",
            likes = 10000,
            duration = "01:20"
        ),
        Video(
            id = 2,
            title = "How to Build a Simple Android App",
            description = "Follow this step-by-step tutorial to create your first Android app.",
            url = "https://www.youtube.com/watch?v=shDB5c0N3bo",
            thumbnail = "https://i.ytimg.com/vi/shDB5c0N3bo/hqdefault.jpg",
            channel_name = "Android Developers",
            likes = 5000,
            duration = "01:20"
        ),
        Video(
            id = 3,
            title = "Jetpack Compose Tutorial",
            description = "Learn how to use Jetpack Compose, a modern UI toolkit for Android development.",
            url = "https://www.youtube.com/watch?v=0-S_88GH28M",
            thumbnail = "https://i.ytimg.com/vi/0-S_88GH28M/hqdefault.jpg",
            channel_name = "Android Developers",
            likes = 8000,
            duration = "01:20"
        ),
        Video(
            id = 4,
            title = "MVVM Architecture for Android",
            description = "Understand the Model-View-ViewModel (MVVM) architectural pattern for Android development.",
            url = "https://www.youtube.com/watch?v=X-K_7LhL3IY",
            thumbnail = "https://i.ytimg.com/vi/X-K_7LhL3IY/hqdefault.jpg",
            channel_name = "Android Developers",
            likes = 6000,
            duration = "01:20"
        ),
        Video(
            id = 5,
            title = "Kotlin Coroutines Tutorial",
            description = "Learn how to use Kotlin coroutines to write asynchronous code in a simple and efficient way.",
            url = "https://www.youtube.com/watch?v=q_R0g8ZOxWg",
            thumbnail = "https://i.ytimg.com/vi/q_R0g8ZOxWg/hqdefault.jpg",
            channel_name = "Android Developers",
            likes = 7000,
            duration = "01:20"
        ),
        Video(
            id = 6,
            title = "Dagger Hilt Tutorial",
            description = "Learn how to use Dagger Hilt, a dependency injection framework for Android development.",
            url = "https://www.youtube.com/watch?v=sl55q34858M",
            thumbnail = "https://i.ytimg.com/vi/sl55q34858M/hqdefault.jpg",
            channel_name = "Android Developers",
            likes = 4000,
            duration = "01:20"
        ),
        Video(
            id = 7,
            title = "Room Database Tutorial",
            description = "Learn how to use Room, a database library for Android development.",
            url = "https://www.youtube.com/watch?v=5_sI6o08rWg",
            thumbnail = "https://i.ytimg.com/vi/5_sI6o08rWg/hqdefault.jpg",
            channel_name = "Android Developers",
            likes = 5000,
            duration = "01:20"
        ),
        Video(
            id = 8,
            title = "Firebase Realtime Database Tutorial",
            description = "Learn how to use Firebase Realtime Database, a NoSQL database for Android development.",
            url = "https://www.youtube.com/watch?v=3_d63g9iK9Y",
            thumbnail = "https://i.ytimg.com/vi/3_d63g9iK9Y/hqdefault.jpg",
            channel_name = "Firebase",
            likes = 4500,
            duration = "01:20"
        )
    )
}