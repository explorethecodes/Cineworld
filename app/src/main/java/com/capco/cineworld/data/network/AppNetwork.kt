package com.capco.cineworld.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.io.IOException
import kotlin.Exception

object AppNetwork{
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_URL_IMAGE = "https://image.tmdb.org/3/t/p/original"
}

enum class CallCode {
    MOVIES_NOW_PLAYING,
    MOVIES_TOP_RATED,
    MOVIES_TRENDING,
    MOVIES_UPCOMING,
    ARTISTS,
    MOVIE,
    ARTICLES,
}

data class CallInfo(
    var callCode: CallCode?=null,
    var callStatus: CallStatus?=null,
    val exception: Exception? = null
)

data class CallSuccess(var message: String) : CallStatus()
data class CallFailed(var message: String) : CallStatus()

open class CallStatus

class ApiException(message: String) : IOException(message)

class NoInternetException(message: String) : IOException(message)

fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    connectivityManager?.let {
        it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
    }
    return result
}