package com.capco.cineworld.data.network.models.movie

import com.capco.cineworld.data.network.AppNetwork.BASE_URL_IMAGE
import com.capco.widgets.chips.ChipsItem

class MovieData(private var response: MovieResponse){

    fun isSuccess(): Boolean {
        return true
    }

    fun getError() : Exception {
        response.statusMessage?.let {
            return Exception(it)
        }
        return Exception("Unknown error")
    }

    fun getImageUrl() : String? {
        response.posterPath?.let {
            return "${BASE_URL_IMAGE+it}"
        }
        return null
    }

    fun getGenres() : List<ChipsItem>? {
        val chipsItems = mutableListOf<ChipsItem>()
        response.genres?.forEach {genre->
            genre?.name?.let { genreName ->
                chipsItems.add(ChipsItem(title = genreName))
            }
        }
        return chipsItems.toList()
    }

    fun getTitle() : String? {
        return response.title
    }

    fun getRating() : Float? {
        response.voteAverage?.let {
            return (it/2).toFloat()
        }
        return null
    }

    fun getRatingsText() : String? {
        getRating()?.let {
            return "$it Rating"
        }
        return null
    }

    fun getVotesText() : String? {
        response.voteCount?.let {
            return "$it Votes"
        }
        return null
    }

    fun getOverview() : String? {
        return response.overview
    }

    fun getTagline() : String? {
        return response.tagline
    }

    fun getReleaseDate() : String? {
        response.releaseDate?.let {
            return "Released on $it"
        }
        return null
    }

    fun getArticleSearchQuery(): String? {
        getTitle()?.let {
            return it
        }
        return null
    }
}