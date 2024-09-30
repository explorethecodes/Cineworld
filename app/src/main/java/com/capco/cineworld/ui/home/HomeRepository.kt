package com.capco.cineworld.ui.home

import com.capco.cineworld.data.local.preference.AppPreference
import com.capco.cineworld.data.network.ApiException
import com.capco.cineworld.data.network.CallInfo
import com.capco.cineworld.data.network.NoInternetException
import com.capco.cineworld.data.network.api.Api
import com.capco.cineworld.data.network.api.NetworkCall
import com.capco.cineworld.data.network.api.NetworkCallListener
import com.capco.cineworld.data.network.models.artists.ArtistsRequest
import com.capco.cineworld.data.network.models.artists.ArtistsResponse
import com.capco.cineworld.data.network.models.movies.MoviesRequest
import com.capco.cineworld.data.network.models.movies.MoviesResponse
import com.capco.support.location.Location
import com.capco.support.logger.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: Api, private val appPreference: AppPreference) : NetworkCall() {

    fun requestNowPlayingMovies(request: MoviesRequest, networkCallListener: NetworkCallListener?, callback : (MoviesResponse?) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                networkCallListener?.onNetworkCallStarted(CallInfo(callCode = request.callCode))

                val moviesResponse = apiRequest {
                    api.moviesNowPlaying(request.page!!)}
                callback(moviesResponse)

                networkCallListener?.onNetworkCallSuccess(CallInfo(callCode = request.callCode))

            } catch (e: ApiException) {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            } catch (e: NoInternetException) {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            } catch (e : Exception){
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
                log("Api | Exception | $e")
            }
        }
    }

    fun requestTopRatedMovies(request: MoviesRequest, networkCallListener: NetworkCallListener?, callback : (MoviesResponse?) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                networkCallListener?.onNetworkCallStarted(CallInfo(callCode = request.callCode))

                val moviesResponse = apiRequest { api.moviesTopRated(request.page!!)}
                callback(moviesResponse)

                networkCallListener?.onNetworkCallSuccess(CallInfo(callCode = request.callCode))

            } catch (e: ApiException) {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            } catch (e: NoInternetException) {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            } catch (e : Exception){
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
                log("Api | Exception | $e")
            }
        }
    }

    fun requestTrendingMovies(request: MoviesRequest, networkCallListener: NetworkCallListener?, callback : (MoviesResponse?) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                networkCallListener?.onNetworkCallStarted(CallInfo(callCode = request.callCode))

                val moviesResponse = apiRequest { api.moviesTrending(request.page!!)}
                callback(moviesResponse)

                networkCallListener?.onNetworkCallSuccess(CallInfo(callCode = request.callCode))

            } catch (e: ApiException) {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            } catch (e: NoInternetException) {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            } catch (e : Exception){
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
                log("Api | Exception | $e")
            }
        }
    }

    fun requestMoviesUpcoming(request: MoviesRequest, networkCallListener: NetworkCallListener?, callback : (MoviesResponse?) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                networkCallListener?.onNetworkCallStarted(CallInfo(callCode = request.callCode))

                val moviesResponse = apiRequest { api.moviesUpcoming(request.page!!)}
                callback(moviesResponse)

                networkCallListener?.onNetworkCallSuccess(CallInfo(callCode = request.callCode))

            } catch (e: ApiException) {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            } catch (e: NoInternetException) {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            } catch (e : Exception){
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
                log("Api | Exception | $e")
            }
        }
    }

    fun requestArtists(request: ArtistsRequest, networkCallListener: NetworkCallListener?, callback : (ArtistsResponse?) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                networkCallListener?.onNetworkCallStarted(CallInfo(callCode = request.callCode))

                val personsResponse = apiRequest { api.artists(request.page!!)}
                callback(personsResponse)

                networkCallListener?.onNetworkCallSuccess(CallInfo(callCode = request.callCode))

            } catch (e: ApiException) {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            } catch (e: NoInternetException) {
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
            } catch (e : Exception){
                networkCallListener?.onNetworkCallFailure(CallInfo(callCode = request.callCode,exception = e))
                log("Api | Exception | $e")
            }
        }
    }

    fun setLocation(location: Location){
        appPreference.setLocation(location)
    }

    fun getLocation() : Location {
        return appPreference.getLocation()
    }

    fun clearLocation() {
        appPreference.clearLocation()
    }
}