package com.example.recipeapp.data.network.response

import com.example.recipeapp.data.model.ResponseObject
import retrofit2.HttpException
import java.io.IOException

sealed class ApiResponse {
    data class Success(val data: ResponseObject): ApiResponse()
    object Loading: ApiResponse()
    sealed class Error: ApiResponse() {
        data class HttpError(val code: Int, val message: String): Error()
        data class NetworkError(val exception: IOException): Error()
        data object NoInternetError: Error()
        data class UnknownError(val exception: Exception) : Error()
    }
}

// Extension function to handle API calls
suspend fun safeApiCall(apiCall: suspend () -> ResponseObject): ApiResponse {
    return try {
        ApiResponse.Success(apiCall.invoke())
    } catch (exception: Exception) {
        when (exception) {
            is IOException -> ApiResponse.Error.NetworkError(exception)
            is HttpException -> ApiResponse.Error.HttpError(exception.code(), exception.message())
            else -> ApiResponse.Error.UnknownError(exception)
        }
    }
}