package com.capco.cineworld.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capco.cineworld.data.network.CallInfo
import com.capco.cineworld.data.network.NoInternetException
import com.capco.cineworld.data.network.api.NetworkCallListener
import com.capco.cineworld.data.network.models.articles.ArticlesData
import com.capco.cineworld.data.network.models.movie.MovieData
import com.capco.cineworld.databinding.ActivityDetailBinding
import com.capco.cineworld.utils.startFlipsActivity
import com.capco.cineworld.utils.startImageActivity
import com.capco.support.images.loadImage
import com.capco.support.views.hide
import com.capco.support.views.show
import com.capco.widgets.R
import com.capco.widgets.alerts.AlertCallbacks
import com.capco.widgets.alerts.noInternetAlert
import com.capco.widgets.alerts.somethingWentWrongAlert
import com.capco.widgets.chips.ChipsWidgetData
import com.capco.widgets.flips.FlipsItem
import com.capco.widgets.flips.FlipsWidgetData
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), NetworkCallListener {

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var binding : ActivityDetailBinding

    companion object{
        const val ARTICLES_ADAPTER_MAX_LIMIT = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        create()
    }

    private fun create() {
        receive()
        init()
        set()
    }

    private fun receive() {
        viewModel.movieRequest.id = intent.getStringExtra("id").toString()
    }

    @SuppressLint("RestrictedApi")
    private fun init() {
        viewModel.networkCallListener = this

        val wave : Sprite = DoubleBounce()
        wave.color = resources.getColor(R.color.white)
        binding.progress.indeterminateDrawable = wave
    }

    private fun set() {
        movie()
        articles()
    }

    //---------------------------------------------- MOVIE -------------------------------------------//
    private fun movie() {
        requestMovie()
        observeMovie()
    }
    private fun requestMovie() {
        viewModel.requestMovie(viewModel.movieRequest)
    }
    private fun observeMovie() {
        viewModel.movie.observe(this) { movie ->
            movieLoading(false)

            val movieData = MovieData(movie)
            if (movieData.isSuccess()){
                binding.contents.show()

                movieData.getImageUrl()?.let {
                    binding.imageLayout.show()
                    binding.image.loadImage(it)

                    binding.image.setOnClickListener {view->
                        startImageActivity(it)
                    }
                }
                movieData.getGenres()?.let {
                    binding.genres.construct(ChipsWidgetData(chips = it))
                }

                movieData.getTitle()?.let {
                    supportActionBar?.title= it
                    binding.titleLayout.show()
                    binding.title.text = it
                }
                movieData.getRating()?.let {
                    binding.ratingBar.rating = it
                }
                movieData.getRatingsText()?.let {
                    binding.ratingText.text = it
                }
                movieData.getVotesText()?.let {
                    binding.votesText.text = it
                }

                movieData.getOverview()?.let {
                    binding.overviewLayout.show()
                    binding.overview.text = it
                }
                movieData.getTagline()?.let {
                    binding.tagline.text = it
                }
                movieData.getReleaseDate()?.let {
                    binding.releasedDate.text = it
                }

                movieData.getArticleSearchQuery()?.let {
                    viewModel.articlesRequest.search = it
                    requestArticles()
                }

            } else {
                somethingWentWrongAlert (movieData.getError()){
                    alerts(it)
                }
            }
        }
    }

    private fun movieLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progress.show()
        } else{
            binding.progress.hide()
        }
    }

    private fun alerts(alertCallbacks: AlertCallbacks) {
        when(alertCallbacks){
            AlertCallbacks.TRY_AGAIN -> {
                requestMovie()
            }
            AlertCallbacks.QUIT -> {
                finish()
            }
        }
    }

    //------------------------- ARTICLES ---------------------------//
    private fun articles() {
        observeArticles()
    }
    private fun requestArticles() {
        viewModel.requestArticles(viewModel.articlesRequest)
    }
    private fun observeArticles() {
        viewModel.articles.observe(this) { articles ->
            val articlesData = ArticlesData(articles)
            if (articlesData.isSuccess()){
                articlesData.getArticles()?.let {
                    articlesRecyclerView(it)
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private fun articlesRecyclerView(unfilteredArticles: List<FlipsItem>) {
        val articles = unfilteredArticles.filter { !it.title.isNullOrEmpty() and !it.description.isNullOrEmpty()}
        binding.articlesLayout.show()

        val articlesRecyclerView = binding.articles
        val articlesAdapter = ArticlesAdapter(arrayListOf(),object : ArticlesOnClickListener {
            override fun onClick(articlesItem: FlipsItem) {
                startFlipsActivity(
                    FlipsWidgetData(articles!!.toMutableList())
                    ,articlesItem.id
                )
            }
        })
        articlesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL,false)
            adapter = articlesAdapter
        }

        articlesAdapter.setArticles(articles.take(ARTICLES_ADAPTER_MAX_LIMIT))

        if (articles.size> ARTICLES_ADAPTER_MAX_LIMIT)
            binding.viewMore.show()
        else
            binding.viewMore.hide()

        binding.viewMore.setOnClickListener {
            startFlipsActivity(FlipsWidgetData(articles.toMutableList()),null)
        }
    }

    //----------------------------------------------- NETWORK --------------------------------------------//
    override fun onNetworkCallStarted(callInfo: CallInfo) {
        movieLoading(true)
    }

    override fun onNetworkCallSuccess(callInfo: CallInfo) {
        movieLoading(false)
    }

    override fun onNetworkCallFailure(callInfo: CallInfo) {
        movieLoading(false)
        when(callInfo.exception){
            is NoInternetException -> {
                noInternetAlert {
                    alerts(it)
                }
            }
            else -> {
                somethingWentWrongAlert(callInfo.exception!!){
                    alerts(it)
                }
            }
        }
    }

    override fun onNetworkCallCancel(callInfo: CallInfo) {
        movieLoading(false)
        finish()
    }

}