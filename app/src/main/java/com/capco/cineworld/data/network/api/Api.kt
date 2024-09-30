package com.capco.cineworld.data.network.api

import com.capco.cineworld.data.network.AppNetwork.BASE_URL
import com.capco.cineworld.data.network.models.movie.MovieResponse
import com.capco.cineworld.data.network.models.movies.MoviesResponse
import com.capco.cineworld.data.network.models.articles.ArticlesResponse
import com.capco.cineworld.data.network.models.artists.ArtistsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface Api {

    //-------------------------------------- MOVIE -----------------------------------------//
    @GET("movie/{id}")
    suspend fun movie(
        @Path("id",encoded = true) id : String
    ) : Response<MovieResponse>

    //------------------------------ MOVIES NOW PLAYING-----------------------------------------//
    @GET("movie/now_playing")
    suspend fun moviesNowPlaying(
        @Query("page",encoded = true) page : String
    ) : Response<MoviesResponse>

    //------------------------------ MOVIES TOP RATED -----------------------------------------//
    @GET("movie/top_rated")
    suspend fun moviesTopRated(
        @Query("page",encoded = true) page : String
    ) : Response<MoviesResponse>

    //------------------------------ MOVIES TRENDING -----------------------------------------//
    @GET("movie/popular")
    suspend fun moviesTrending(
        @Query("page",encoded = true) page : String
    ) : Response<MoviesResponse>

    //------------------------------- MOVIES UPCOMING-----------------------------------------//
    @GET("movie/upcoming")
    suspend fun moviesUpcoming(
        @Query("page",encoded = true) page : String
    ) : Response<MoviesResponse>

    //--------------------------------- PERSONS -----------------------------------------//
    @GET("person/popular")
    suspend fun artists(
        @Query("page",encoded = true) page : String
    ) : Response<ArtistsResponse>

    //-------------------------------- ARTICLES -----------------------------------------//
    @GET("search/multi")
    suspend fun articles(
        @Query("query",encoded = true) query : String
    ) : Response<ArticlesResponse>

    companion object{
        operator fun invoke(
            networkInterceptor: NetworkInterceptor
        ) : Api {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okkHttpclient = OkHttpClient.Builder()
                    .readTimeout(10000, TimeUnit.SECONDS)
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .writeTimeout(10000, TimeUnit.SECONDS)
                    .addInterceptor(networkInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}