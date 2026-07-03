package com.mahdi.assignment.shoppingapp.core.network

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object NetworkErrorHandler {
    fun getErrorMessage(e: Throwable): String {
        return when (e) {
            is SocketTimeoutException -> "Connection timed out. Please try again."
            is IOException -> "No internet connection. Please check your network."
            is HttpException -> {
                when (e.code()) {
                    404 -> "The requested products could not be found."
                    500 -> "Server error. Please try again later."
                    503 -> "Service unavailable. The server is under maintenance."
                    else -> "Something went wrong. Error code: ${e.code()}"
                }
            }
            else -> e.localizedMessage ?: "An unexpected error occurred."
        }
    }
}
