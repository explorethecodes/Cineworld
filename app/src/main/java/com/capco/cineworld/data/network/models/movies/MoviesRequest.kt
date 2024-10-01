package com.capco.cineworld.data.network.models.movies

import com.capco.cineworld.data.network.CallCode

data class MoviesRequest(
    var callCode: CallCode?=null,
    var limit: String?=null
)