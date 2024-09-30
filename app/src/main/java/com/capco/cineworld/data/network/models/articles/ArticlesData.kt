package com.capco.cineworld.data.network.models.articles

import com.capco.cineworld.data.network.AppNetwork.BASE_URL_IMAGE
import com.capco.widgets.flips.FlipsItem

class ArticlesData(private var response: ArticlesResponse){

    fun isSuccess(): Boolean {
        return true
    }

    fun getError() : Exception {
        response.statusMessage?.let {
            return Exception(it)
        }
        return Exception("Unknown error")
    }

    fun getArticles() : List<FlipsItem>? {
        return response.results?.map {
            it.toFlipsItem()
        }
    }
}

fun ArticlesResults.toFlipsItem(): FlipsItem {
    return FlipsItem(
        id = id.toString(),
        image = BASE_URL_IMAGE + posterPath,
        title = title,
        description = overview,
        time = firstAirDate,
        source = mediaType,
        url = "https://www.google.com/"
    )
}