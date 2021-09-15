package com.maxab.currencyconverter.manager.connection


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Failed<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
    class NoConnection<T>:Resource<T>()
    class HideLoading<T>:Resource<T>()
}