package com.example.pokemon.model

sealed class ApiState<out T> {
    data class Success<out T>(val value: T) : ApiState<T>()

    data object Loading : ApiState<Nothing>()

    // you can pass if the error network error or not and pass custom
    // error code to handle any custom exception with it as you see error body it Any
    // so you can pass any thing and cast it
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: ErrorCodes?,
        val errorBody: Any?
    ) :
        ApiState<Nothing>()

}


enum class ErrorCodes {
    EXCEPTION,
    HTTP,
    JSON_EXP
}