package com.capco.cineworld.data.network.models.artists

import com.capco.cineworld.data.network.CallCode

data class ArtistsRequest(
    var callCode: CallCode?=CallCode.ARTISTS,
    var page: String?=null,
    var limit: String?=null,
)