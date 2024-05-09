package com.devx.streaming.utils

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : NetworkResult<T>()
    class Success<T>(data: T?): NetworkResult<T>(data = data)
    class Error<T>(message: String? = null) : NetworkResult<T>(message = message)
}