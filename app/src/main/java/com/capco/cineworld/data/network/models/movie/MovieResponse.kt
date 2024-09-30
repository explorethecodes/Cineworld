package com.capco.cineworld.data.network.models.movie
import com.capco.cineworld.data.network.AppNetwork.BASE_URL
import com.capco.widgets.movies.MoviesItem
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("success")
    var success: Boolean? = null,
    @SerializedName("status_code")
    var statusCode: Int? = null,
    @SerializedName("status_message")
    var statusMessage: String? = null,
    @SerializedName("adult")
    var adult: Boolean? = null,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("belongs_to_collection")
    var belongsToCollection: Any? = null,
    @SerializedName("budget")
    var budget: Int? = null,
    @SerializedName("genres")
    var genres: List<Genre?>? = null,
    @SerializedName("homepage")
    var homepage: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("imdb_id")
    var imdbId: String? = null,
    @SerializedName("origin_country")
    var originCountry: List<String?>? = null,
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
    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompany?>? = null,
    @SerializedName("production_countries")
    var productionCountries: List<ProductionCountry?>? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @SerializedName("revenue")
    var revenue: Int? = null,
    @SerializedName("runtime")
    var runtime: Int? = null,
    @SerializedName("spoken_languages")
    var spokenLanguages: List<SpokenLanguage?>? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("tagline")
    var tagline: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("video")
    var video: Boolean? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null
)

data class Genre(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null
)

data class ProductionCompany(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("logo_path")
    var logoPath: Any? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("origin_country")
    var originCountry: String? = null
)

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    var iso31661: String? = null,
    @SerializedName("name")
    var name: String? = null
)

data class SpokenLanguage(
    @SerializedName("english_name")
    var englishName: String? = null,
    @SerializedName("iso_639_1")
    var iso6391: String? = null,
    @SerializedName("name")
    var name: String? = null
)

fun MovieResponse.toMoviesItem() : MoviesItem {
    return MoviesItem(
        id = id.toString(),
        title = title,
        poster = BASE_URL + posterPath,
        rating = voteAverage.toString(),
        ratingCount = voteCount.toString()
    )
}