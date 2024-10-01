package com.capco.cineworld.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capco.cineworld.data.network.CallCode
import com.capco.cineworld.data.network.api.NetworkCallListener
import com.capco.cineworld.data.network.models.artists.ArtistsRequest
import com.capco.cineworld.data.network.models.artists.ArtistsResponse
import com.capco.cineworld.data.network.models.movies.MoviesRequest
import com.capco.cineworld.data.network.models.movies.MoviesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    //------------------------------------------- NETWORK ------------------------------------------//
    var networkCallListener : NetworkCallListener? =null

    //------------------------------------------- MOVIES NOW PLAYING -------------------------------------------//
    var moviesNowPlayingRequest = MoviesRequest()
    val moviesNowPlaying: LiveData<MoviesResponse> get() = _moviesNowPlaying
    private var _moviesNowPlaying = MutableLiveData<MoviesResponse>()
    fun requestMoviesNowPlaying(moviesNowPlayingRequest: MoviesRequest) {
        if (validateMoviesNowPlaying()){
            repository.requestNowPlayingMovies(moviesNowPlayingRequest,networkCallListener) {
                _moviesNowPlaying.value = it
            }
        }
    }
    private fun validateMoviesNowPlaying(): Boolean{
        moviesNowPlayingRequest.callCode = CallCode.MOVIES_NOW_PLAYING
        return true
    }

    //------------------------------------------- MOVIES TOP RATED -------------------------------------------//
    var moviesTopRatedRequest = MoviesRequest()
    val moviesTopRated: LiveData<MoviesResponse> get() = _moviesTopRated
    private var _moviesTopRated = MutableLiveData<MoviesResponse>()
    fun requestMoviesTopRated(moviesTopRatedRequest: MoviesRequest) {
        if (validateMoviesTopRated()){
            repository.requestTopRatedMovies(moviesTopRatedRequest,networkCallListener) {
                _moviesTopRated.value = it
            }
        }
    }
    private fun validateMoviesTopRated(): Boolean{
        moviesTopRatedRequest.callCode = CallCode.MOVIES_TOP_RATED
        return true
    }

    //------------------------------------------- MOVIES TRENDING -------------------------------------------//
    var moviesTrendingRequest = MoviesRequest()
    val moviesTrending: LiveData<MoviesResponse> get() = _moviesTrending
    private var _moviesTrending = MutableLiveData<MoviesResponse>()
    fun requestMoviesTrending(moviesTrendingRequest: MoviesRequest) {
        if (validateMoviesTrending()){
            repository.requestTrendingMovies(moviesTrendingRequest,networkCallListener) {
                _moviesTrending.value = it
            }
        }
    }
    private fun validateMoviesTrending(): Boolean{
        moviesTrendingRequest.callCode = CallCode.MOVIES_TRENDING
        return true
    }

    //------------------------------------------- MOVIES UPCOMING -------------------------------------------//
    var moviesUpcomingRequest = MoviesRequest()
    val moviesUpcoming: LiveData<MoviesResponse> get() = _moviesUpcoming
    private var _moviesUpcoming = MutableLiveData<MoviesResponse>()
    fun requestMoviesUpcoming(moviesUpcomingRequest: MoviesRequest) {
        if (validateMoviesUpcoming()){
            repository.requestMoviesUpcoming(moviesUpcomingRequest,networkCallListener) {
                _moviesUpcoming.value = it
            }
        }
    }
    private fun validateMoviesUpcoming(): Boolean{
        moviesUpcomingRequest.callCode = CallCode.MOVIES_UPCOMING
        return true
    }

    //------------------------------------------- ARTISTS -------------------------------------------//
    var artistsRequest = ArtistsRequest()
    val artists: LiveData<ArtistsResponse> get() = _artists
    private var _artists = MutableLiveData<ArtistsResponse>()
    fun requestArtists(artistsRequest: ArtistsRequest) {
        if (validateArtists()){
            repository.requestArtists(artistsRequest,networkCallListener) {
                _artists.value = it
            }
        }
    }
    private fun validateArtists(): Boolean{
        artistsRequest.page = (0..5).random().toString()
        return true
    }
}