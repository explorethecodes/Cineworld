package com.capco.cineworld.ui.detail

import com.capco.cineworld.data.network.ApiException
import com.capco.cineworld.data.network.CallInfo
import com.capco.cineworld.data.network.NoInternetException
import com.capco.cineworld.data.network.api.Api
import com.capco.cineworld.data.network.api.NetworkCall
import com.capco.cineworld.data.network.api.NetworkCallListener
import com.capco.cineworld.data.network.models.movie.MovieRequest
import com.capco.cineworld.data.network.models.movie.MovieResponse
import com.capco.cineworld.data.network.models.articles.ArticlesRequest
import com.capco.cineworld.data.network.models.articles.ArticlesResponse
import com.capco.support.logger.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api: Api) : NetworkCall() {
    
    fun requestMovie(request: MovieRequest, networkCallListener: NetworkCallListener?, callback : (MovieResponse?) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                networkCallListener?.onNetworkCallStarted(CallInfo(callCode = request.callCode))

                val movieResponse = apiRequest { api.movie(
                    request.id!!
                )}
                callback(movieResponse)

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

    fun requestArticles(request: ArticlesRequest, networkCallListener: NetworkCallListener?, callback : (ArticlesResponse?) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                networkCallListener?.onNetworkCallStarted(CallInfo(callCode = request.callCode))

                val articlesResponse = apiRequest { api.articles(
                    request.search!!
                )}
                callback(articlesResponse)

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
}