package com.mahdi.assignment.shoppingapp.core.network

import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.test.assertEquals

class NetworkErrorHandlerTest {

    @Test
    fun `getErrorMessage returns timeout message for SocketTimeoutException`() {
        val exception = SocketTimeoutException()
        val result = NetworkErrorHandler.getErrorMessage(exception)
        assertEquals("Connection timed out. Please try again.", result)
    }

    @Test
    fun `getErrorMessage returns internet message for IOException`() {
        val exception = IOException()
        val result = NetworkErrorHandler.getErrorMessage(exception)
        assertEquals("No internet connection. Please check your network.", result)
    }

    @Test
    fun `getErrorMessage returns specific message for 404 error`() {
        val errorResponse = Response.error<Any>(404, "".toResponseBody())
        val exception = HttpException(errorResponse)

        val result = NetworkErrorHandler.getErrorMessage(exception)
        assertEquals("The requested products could not be found.", result)
    }

    @Test
    fun `getErrorMessage returns fallback message for unknown HTTP code`() {
        val errorResponse = Response.error<Any>(418, "".toResponseBody()) // I'm a teapot
        val exception = HttpException(errorResponse)

        val result = NetworkErrorHandler.getErrorMessage(exception)
        assertEquals("Something went wrong. Error code: 418", result)
    }
}