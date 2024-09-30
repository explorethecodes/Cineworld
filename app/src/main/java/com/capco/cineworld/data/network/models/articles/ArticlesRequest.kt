package com.capco.cineworld.data.network.models.articles

import com.capco.cineworld.data.network.CallCode

data class ArticlesRequest(
    var callCode: CallCode?=CallCode.ARTICLES,
    var page: String?=null,
    var limit: String?=null,
    var search: String?=null
)