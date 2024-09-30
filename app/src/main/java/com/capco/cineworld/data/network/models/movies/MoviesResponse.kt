package com.capco.cineworld.data.network.models.movies
import com.google.gson.annotations.SerializedName


data class MoviesResponse(
    @SerializedName("success")
    var success: Boolean? = null,
    @SerializedName("status_code")
    var statusCode: Int? = null,
    @SerializedName("status_message")
    var statusMessage: String? = null,
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var resultMovies: List<ResultMovies>? = null,
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null
)

data class ResultMovies(
    @SerializedName("adult")
    var adult: Boolean? = null,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("genre_ids")
    var genreIds: List<Int?>? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("popularity")
    var popularity: Double? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("video")
    var video: Boolean? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null
)