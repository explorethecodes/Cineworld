package com.capco.cineworld.data.network.models.artists

import com.capco.cineworld.data.network.AppNetwork.BASE_URL_IMAGE
import com.capco.widgets.persons.PersonsItem

class ArtistsData(private var response: ArtistsResponse){
    fun isSuccess(): Boolean {
        return true
    }
    fun getError() : Exception {
        response.statusMessage?.let {
            return Exception(it)
        }
        return Exception("Unknown error")
    }
    fun getArtists() : List<PersonsItem>? {
        return response.results?.map {
            it.toArtistsItem()
        }
    }
}

fun ResultArtists.toArtistsItem(): PersonsItem {
    return PersonsItem(
        id = id.toString(),
        name = name,
        imageUrl = BASE_URL_IMAGE + profilePath,
        description = knownForDepartment,
    )
}