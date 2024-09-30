package com.capco.cineworld.data.network.models.movie

import com.capco.cineworld.data.network.CallCode

data class MovieRequest(
    var callCode: CallCode?=CallCode.MOVIE,
    var id: String?=null
)