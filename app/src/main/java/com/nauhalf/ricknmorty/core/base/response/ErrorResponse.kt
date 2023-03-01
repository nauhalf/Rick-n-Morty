package com.nauhalf.ricknmorty.core.base.response

import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NoInternetError : RickMortyResponse.Error(message = "No Internet")

object TimeOutError : RickMortyResponse.Error(message = "Time Out")

data class HttpError(
    override val message: String,
    val messageTitle: String,
    val code: Int,
    val data: Any?,
) : RickMortyResponse.Error(message = message)

fun Exception.toError(): RickMortyResponse.Error {
    return try {
        when {
            this is IOException && message == "No Internet" -> NoInternetError
            this is HttpException -> {
                val error = Gson().fromJson(
                    response()?.errorBody()?.string().orEmpty(),
                    RawHttpError::class.java,
                )
                HttpError(
                    message = error.error ?: message(),
                    messageTitle = error.error ?: message(),
                    code = response()?.code() ?: 400,
                    data = null
                )
            }
            this is SocketTimeoutException -> {
                TimeOutError
            }
            this is UnknownHostException -> {
                NoInternetError
            }
            else -> {
                RickMortyResponse.Error(message = message.orEmpty())
            }
        }
    } catch (e: Exception) {
        RickMortyResponse.Error(message = e.message.orEmpty())
    }
}
