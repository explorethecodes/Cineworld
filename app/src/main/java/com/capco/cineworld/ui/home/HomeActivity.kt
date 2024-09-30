package com.capco.cineworld.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capco.cineworld.data.network.CallInfo
import com.capco.cineworld.data.network.NoInternetException
import com.capco.cineworld.data.network.api.NetworkCallListener
import com.capco.cineworld.data.network.models.artists.ArtistsData
import com.capco.cineworld.data.network.models.movies.MoviesData
import com.capco.cineworld.databinding.ActivityHomeBinding
import com.capco.cineworld.utils.startDetailActivity
import com.capco.cineworld.utils.startImageActivity
import com.capco.support.views.hide
import com.capco.support.views.show
import com.capco.widgets.R
import com.capco.widgets.alerts.AlertCallbacks
import com.capco.widgets.alerts.noInternetAlert
import com.capco.widgets.alerts.somethingWentWrongAlert
import com.capco.widgets.carousel.CarouselWidgetData
import com.capco.widgets.carousel.CarouselWidgetOnClickListener
import com.capco.widgets.carousel.InputItem
import com.capco.widgets.persons.personsWidgetDummy
import com.capco.widgets.carousel.carouselWidgetDummy
import com.capco.widgets.movies.MoviesItem
import com.capco.widgets.movies.MoviesWidgetData
import com.capco.widgets.movies.MoviesWidgetOnClickListener
import com.capco.widgets.movies.MoviesWidgetType
import com.capco.widgets.movies.moviesWidgetDummy
import com.capco.widgets.movies.moviesWidgetDummy2
import com.capco.widgets.persons.PersonsItem
import com.capco.widgets.persons.PersonsWidgetData
import com.capco.widgets.persons.PersonsWidgetOnClickListener
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NetworkCallListener {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding : ActivityHomeBinding

    companion object Constants {
        const val CAROUSEL_MAX_LIMIT = 7

        const val NOW_PLAYING = "Now Playing"
        const val TOP_RATED = "Top Rated"
        const val TRENDING = "Trending"
        const val UPCOMING = "Upcoming"
        const val SPOTLIGHT = "Spotlight"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        create()
    }

    private fun create() {
        init()
        //showcase()
        set()
    }

    @SuppressLint("RestrictedApi")
    private fun init() {
        viewModel.networkCallListener = this
        binding.idSwipeRefreshLayout.setOnRefreshListener {
            requestAll()
        }

        val wave : Sprite = DoubleBounce()
        wave.color = resources.getColor(R.color.white)
        binding.progress.indeterminateDrawable = wave
    }

    private fun set() {
        moviesNowPlaying()
        moviesTopRated()
        moviesTrending()
        moviesUpcoming()
        artists()
    }

    //---------------------------------------------- MOVIES NOW PLAYING -------------------------------------------//
    private fun moviesNowPlaying() {
        requestMoviesNowPlaying()
        observeMoviesNowPlaying()
    }
    private fun requestMoviesNowPlaying() {
        viewModel.requestMoviesNowPlaying(viewModel.moviesNowPlayingRequest)
    }
    private fun observeMoviesNowPlaying() {
        viewModel.moviesNowPlaying.observe(this) { moviesNowPlaying ->
            val moviesData = MoviesData(moviesNowPlaying)
            if (moviesData.isSuccess()){
                moviesData.getMoviesCarousel()?.let {
                    val moviesFiltered: List<InputItem>
                    = if (it.size< CAROUSEL_MAX_LIMIT) it else it.take(CAROUSEL_MAX_LIMIT)

                    binding.moviesNowPlayingWidget.show()
                    binding.moviesNowPlayingWidget.construct(
                        CarouselWidgetData(TOP_RATED,moviesFiltered),
                        object : CarouselWidgetOnClickListener {
                            override fun onClickCarousel(item: InputItem) {
                                item.id?.let { id ->
                                    startDetailActivity(id)
                                }
                            }
                    })
                }
            }
        }
    }

    //---------------------------------------------- MOVIES TOP RATED-------------------------------------------//
    private fun moviesTopRated() {
        requestMoviesTopRated()
        observeMoviesTopRated()
    }
    private fun requestMoviesTopRated() {
        viewModel.requestMoviesTopRated(viewModel.moviesTopRatedRequest)
    }
    private fun observeMoviesTopRated() {
        viewModel.moviesTopRated.observe(this) { moviesTopRated ->
            val moviesData = MoviesData(moviesTopRated)
            if (moviesData.isSuccess()){
                moviesData.getMovies()?.let {
                    binding.moviesTopRatedWidget.show()
                    binding.moviesTopRatedWidget.construct(
                        MoviesWidgetData(MoviesWidgetType.TOP_RATED, TOP_RATED,it),
                        object : MoviesWidgetOnClickListener {
                            override fun onClickTitle(title: String) {
                            }

                            override fun onClickMovie(movie: MoviesItem) {
                                movie.id?.let { id ->
                                    startDetailActivity(id)
                                }
                            }
                        })
                }
            } else {
                somethingWentWrongAlert (moviesData.getError()){
                    alerts(it)
                }
            }
        }
    }

    //---------------------------------------------- MOVIES TRENDING -------------------------------------------//
    private fun moviesTrending() {
        requestMoviesTrending()
        observeMoviesTrending()
    }
    private fun requestMoviesTrending() {
        viewModel.requestMoviesTrending(viewModel.moviesTrendingRequest)
    }
    private fun observeMoviesTrending() {
        viewModel.moviesTrending.observe(this) { moviesTrending ->
            val moviesData = MoviesData(moviesTrending)
            if (moviesData.isSuccess()){
                moviesData.getMovies()?.let {
                    binding.moviesTrendingWidget.show()
                    binding.moviesTrendingWidget.construct(
                        MoviesWidgetData(MoviesWidgetType.TRENDING, TRENDING,it),
                        object : MoviesWidgetOnClickListener {
                            override fun onClickTitle(title: String) {
                            }

                            override fun onClickMovie(movie: MoviesItem) {
                                movie.id?.let {
                                    startDetailActivity(it)
                                }
                            }
                        })
                }
            }
        }
    }

    //---------------------------------------------- MOVIES UPCOMING -------------------------------------------//
    private fun moviesUpcoming() {
        requestMoviesUpcoming()
        observeMoviesUpcoming()
    }
    private fun requestMoviesUpcoming() {
        viewModel.requestMoviesUpcoming(viewModel.moviesUpcomingRequest)
    }
    private fun observeMoviesUpcoming() {
        viewModel.moviesUpcoming.observe(this) { moviesUpcoming ->
            val moviesData = MoviesData(moviesUpcoming)
            if (moviesData.isSuccess()){
                moviesData.getMovies()?.let {
                    binding.moviesUpcomingWidget.show()
                    binding.moviesUpcomingWidget.construct(
                        MoviesWidgetData(MoviesWidgetType.TOP_RATED, UPCOMING,it),
                        object : MoviesWidgetOnClickListener {
                            override fun onClickTitle(title: String) {
                            }

                            override fun onClickMovie(movie: MoviesItem) {
                                movie.id?.let {
                                    startDetailActivity(it)
                                }
                            }
                        })
                }
            }
        }
    }

    //---------------------------------------------- ARTISTS -------------------------------------------//
    private fun artists() {
        requestArtists()
        observeArtists()
    }
    private fun requestArtists() {
        viewModel.requestArtists(viewModel.artistsRequest)
    }
    private fun observeArtists() {
        viewModel.artists.observe(this) { artists ->
            val artistsData = ArtistsData(artists)
            if (artistsData.isSuccess()){
                artistsData.getArtists()?.let {
                    binding.artistsWidget.show()
                    binding.artistsWidget.construct(
                        PersonsWidgetData(title = SPOTLIGHT, persons = it),
                        object : PersonsWidgetOnClickListener {
                            override fun onClickPerson(item: PersonsItem) {
                                item.imageUrl?.let { imageUrl ->
                                    startImageActivity(imageUrl)
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    //--------------------------------------- COMMON ------------------------------//
    private fun requestAll() {
        requestMoviesNowPlaying()
        requestMoviesTopRated()
        requestMoviesTrending()
        requestMoviesUpcoming()
        requestArtists()
    }

    private fun moviesProgress(isLoading: Boolean) {
        if (isLoading){
            binding.progress.show()
        } else{
            binding.progress.hide()
            binding.idSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun alerts(alertCallbacks: AlertCallbacks) {
        when(alertCallbacks){
            AlertCallbacks.TRY_AGAIN -> {
                requestAll()
            }
            AlertCallbacks.QUIT -> {
                finish()
            }
        }
    }

    //----------------------------------------------- NETWORK --------------------------------------------//
    override fun onNetworkCallStarted(callInfo: CallInfo) {
        moviesProgress(true)
    }

    override fun onNetworkCallSuccess(callInfo: CallInfo) {
        moviesProgress(false)
    }

    override fun onNetworkCallFailure(callInfo: CallInfo) {
        moviesProgress(false)
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
        moviesProgress(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Showcase
    private fun showcase() {
        binding.contents.addView(carouselWidgetDummy(this))
        binding.contents.addView(moviesWidgetDummy(this))
        binding.contents.addView(moviesWidgetDummy2(this){
            startDetailActivity(it)
        })
        binding.contents.addView(personsWidgetDummy(this))

        binding.idSwipeRefreshLayout.setOnRefreshListener {
            binding.idSwipeRefreshLayout.isRefreshing = false
        }
    }
}