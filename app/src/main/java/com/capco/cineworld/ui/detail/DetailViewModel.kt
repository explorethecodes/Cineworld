package com.capco.cineworld.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capco.cineworld.data.network.api.NetworkCallListener
import com.capco.cineworld.data.network.models.movie.MovieResponse
import com.capco.cineworld.data.network.models.movie.MovieRequest
import com.capco.cineworld.data.network.models.articles.ArticlesRequest
import com.capco.cineworld.data.network.models.articles.ArticlesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) : ViewModel() {

    //------------------------------------------- NETWORK ------------------------------------------//
    var networkCallListener : NetworkCallListener? =null

    //------------------------------------------- MOVIE -------------------------------------------//
    var movieRequest = MovieRequest()
    val movie: LiveData<MovieResponse> get() = _movie
    private var _movie = MutableLiveData<MovieResponse>()
    fun requestMovie(movieRequest: MovieRequest) {
        if (validateMovie()){
            repository.requestMovie(movieRequest,networkCallListener) {
                _movie.value = it
            }
        }
    }
    private fun validateMovie(): Boolean{
        return movieRequest.id != null
    }

    //------------------------------------------- NEWS -------------------------------------------//
    var articlesRequest = ArticlesRequest()
    val articles: LiveData<ArticlesResponse> get() = _articles
    private var _articles = MutableLiveData<ArticlesResponse>()
    fun requestArticles(articlesRequest: ArticlesRequest) {
        if (validateArticles()){
            repository.requestArticles(articlesRequest,networkCallListener) {
                _articles.value = it
            }
        }
    }
    private fun validateArticles(): Boolean {
        return articlesRequest.search !=null
    }
}