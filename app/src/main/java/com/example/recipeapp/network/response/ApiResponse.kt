package com.example.recipeapp.network.response

import com.example.recipeapp.data.model.ResponseObject
import java.io.IOException

sealed class ApiResponse {
    data class Success(val data: ResponseObject): ApiResponse()
    object Loading: ApiResponse()
    sealed class Error {
        data class HttpError(val code: Int, val message: String): Error()
        data class NetworkError(val exception: IOException): Error()
        data object NoInternetError: Error()
        data class UnknownError(val exception: Throwable) : Error()
    }
}

