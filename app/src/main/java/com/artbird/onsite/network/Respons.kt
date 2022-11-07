package com.artbird.onsite.network

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val error: Throwable) : Resource<T>()
    class Loading<T> : Resource<T>()
}