package com.example.recipeapp.util

import com.example.recipeapp.data.model.ResponseObject
import com.example.recipeapp.data.network.response.ApiResponse

fun handleApiResponseUiLogic(
    apiResponse: ApiResponse,
    onSuccess: (data: ResponseObject) -> Unit = {},
    onHttpError: (code: Int, message: String) -> Unit = {code, message -> },
    onUnknownError: (exception: Exception) -> Unit = {},
    onSomethingElseHappen: () -> Unit = {}
) {
    when(apiResponse) {
        is ApiResponse.Success -> onSuccess(apiResponse.data)
        is ApiResponse.Error.HttpError -> onHttpError(apiResponse.code, apiResponse.message)
        is ApiResponse.Error.UnknownError -> onUnknownError(apiResponse.exception)
        else -> onSomethingElseHappen()
    }
}