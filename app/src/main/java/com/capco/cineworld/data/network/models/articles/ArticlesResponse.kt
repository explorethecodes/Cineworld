package com.capco.cineworld.data.network.models.articles
import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("success")
    var success: Boolean? = null,
    @SerializedName("status_code")
    var statusCode: Int? = null,
    @SerializedName("status_message")
    var statusMessage: String? = null,
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var results: List<ArticlesResults>? = null,
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null
)

data class ArticlesResults(
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("media_type")
    var mediaType: String? = null,
    @SerializedName("adult")
    var adult: Boolean? = null,
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("genre_ids")
    var genreIds: List<Int?>? = null,
    @SerializedName("popularity")
    var popularity: Double? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @SerializedName("video")
    var video: Boolean? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("original_name")
    var originalName: String? = null,
    @SerializedName("first_air_date")
    var firstAirDate: String? = null,
    @SerializedName("origin_country")
    var originCountry: List<String>? = null
)