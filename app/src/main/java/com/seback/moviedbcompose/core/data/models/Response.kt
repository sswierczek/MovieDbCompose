package com.seback.moviedbcompose.core.data.models

sealed class Response<T> {

    data class Loading<T>(
        val initialData: T
    ) : Response<T>()

    data class Success<T>(
        val data: T
    ) : Response<T>()

    data class Error<T>(
        val message: String?,
        val throwable: Throwable?
    ) : Response<T>()
}

fun <T> unknownError() = Response.Error<T>("Unknown error", Throwable("Unknown error"))