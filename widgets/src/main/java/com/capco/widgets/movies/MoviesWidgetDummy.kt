package com.capco.widgets.movies

import android.content.Context
import com.capco.support.json.getJsonString
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Constants {
    const val TITLE = "Latest Releases"
    const val TITLE_2 = "Trending World Wide"
    const val JSON = "json/movies.json"
}

fun moviesWidgetDummy(context: Context): MoviesWidget {
    val moviesWidgetData = MoviesWidgetData(
        type = MoviesWidgetType.TRENDING,
        title = Constants.TITLE,
        movies = getInputItems(context).reversed()
    )

    val moviesWidget =
        MoviesWidget(context, moviesWidgetData, object : MoviesWidgetOnClickListener {
            override fun onClickTitle(title: String) {
            }

            override fun onClickMovie(movie: MoviesItem) {
            }

        })

    return moviesWidget
}

fun moviesWidgetDummy2(context: Context, callback : (String)->Unit): MoviesWidget {
    val moviesWidgetData = MoviesWidgetData(
        type = MoviesWidgetType.TOP_RATED,
        title = Constants.TITLE_2,
        movies = getInputItems(context).shuffled()
    )

    val moviesWidget =
        MoviesWidget(context, moviesWidgetData, object : MoviesWidgetOnClickListener {
            override fun onClickTitle(title: String) {
            }

            override fun onClickMovie(movie: MoviesItem) {
                movie.id?.let { callback(it) }
            }

        })

    return moviesWidget
}

fun getInputItems(context: Context): List<MoviesItem> {
    val jsonString = getJsonString(context, Constants.JSON)

    if (!jsonString.isNullOrEmpty()) {
        val gson = Gson()
        val type = object : TypeToken<List<MoviesItem>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    return listOf()
}