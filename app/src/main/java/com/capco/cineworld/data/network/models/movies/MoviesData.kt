package com.capco.cineworld.data.network.models.movies

import com.capco.cineworld.data.network.AppNetwork.BASE_URL_IMAGE
import com.capco.widgets.carousel.InputItem
import com.capco.widgets.movies.MoviesItem

class MoviesData(private var response: MoviesResponse){

    fun isSuccess(): Boolean {
        return true
    }

    fun getError() : Exception {
        response.statusMessage?.let {
            return Exception(it)
        }
        return Exception("Unknown error")
    }

    fun getMovies() : List<MoviesItem>? {
        return response.resultMovies?.map {
            it.toMoviesItem()
        }
    }

    fun getMoviesCarousel() : List<InputItem>? {
        return response.resultMovies?.map {
            it.toCarouselItem()
        }
    }
}

fun ResultMovies.toMoviesItem(): MoviesItem {
    return MoviesItem(
        id = id.toString(),
        title = title,
        poster = BASE_URL_IMAGE + posterPath,
    )
}

fun ResultMovies.toCarouselItem(): InputItem {
    return InputItem(
        id = id.toString(),
        imageUrl = BASE_URL_IMAGE + posterPath,
        caption = title,
    )
}