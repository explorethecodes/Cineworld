package com.capco.cineworld.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.capco.cineworld.data.network.CallInfo
import com.capco.cineworld.data.network.api.NetworkCallListener
import com.capco.cineworld.data.network.models.movies.MoviesRequest
import com.capco.cineworld.ui.home.HomeRepository
import com.capco.cineworld.ui.home.HomeViewModel
import com.capco.cineworld.utils.getOrAwaitValue
import com.capco.widgets.movies.MoviesItem
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    // Mock the repository
    @Mock
    lateinit var repository: HomeRepository

    // ViewModel under test
    private lateinit var movieViewModel: HomeViewModel

    // LiveData Test Rule
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // Initialize the ViewModel with the mocked repository
        movieViewModel = HomeViewModel(repository)
    }

    @Test
    fun `fetchLatestHomes updates LiveData with movie list`() {
        // Arrange: Define a mock movie list and make the repository return it
        val mockMoviesList = listOf(MoviesItem(title = "Movie 1"), MoviesItem(title = "Movie 2"))
        val networkCallListener = object : NetworkCallListener {
            override fun onNetworkCallStarted(callInfo: CallInfo) {
                TODO("Not yet implemented")
            }

            override fun onNetworkCallSuccess(callInfo: CallInfo) {
                TODO("Not yet implemented")
            }

            override fun onNetworkCallFailure(callInfo: CallInfo) {
                TODO("Not yet implemented")
            }

            override fun onNetworkCallCancel(callInfo: CallInfo) {
                TODO("Not yet implemented")
            }
        }
        //`when`(repository.requestTrendingMovies(MoviesRequest(),networkCallListener){}).thenReturn(mockMoviesList)

        // Act: Trigger the fetchTrendingMovies function
        movieViewModel.requestMoviesTrending(MoviesRequest())

        // Assert: Verify that LiveData is updated correctly
        val liveData = movieViewModel.moviesTrending.getOrAwaitValue()
        assertEquals(mockMoviesList, liveData)
    }
}