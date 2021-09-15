package com.maxab.currencyconverter.manager.connection

import retrofit2.Response
import java.lang.Exception


abstract class BaseDataSource {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {

        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Resource.Success(body)
                }
            }
            return Resource.Failed("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return Resource.Failed(e.message ?: e.toString())
        }
    }

}