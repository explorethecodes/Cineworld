package com.capco.cineworld.data.network.api

import android.content.Context
import com.capco.cineworld.data.network.NoInternetException
import com.capco.cineworld.data.network.isInternetAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
    @ApplicationContext context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable(applicationContext))
            throw NoInternetException("Make sure you have an active data connection !")

        var request = chain.request()
        request = request.newBuilder()
                .addHeader("Content-Type", "application/json")
            .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0Mjg4MmE5NzhiOGU3ZWMzYmY5NmE4ZTliNjA2OWU4NyIsIm5iZiI6MTcyNzM1NDgwMy45OTA5MTksInN1YiI6IjY2ZjIzMjA4MDMxNWI5MWY0NjNiNDk2NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.zhV69fjk2z1A1UyR9nW5k_h0RXw8S1piQIvw6Arvvmg")
                .build()

        return chain.proceed(request)
    }
}