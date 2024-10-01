package com.capco.widgets.movies

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.capco.support.images.loadImage
import com.capco.widgets.R
import com.capco.widgets.databinding.AdapterMoviesBinding
import kotlin.collections.ArrayList

class MoviesAdapter(var context : Context, var movies: ArrayList<MoviesItem>, private var onClickListener : MoviesOnClickListener)
    : RecyclerView.Adapter<MoviesViewHolder>() {

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MoviesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_movies,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    private fun MoviesViewHolder.bind(movie: MoviesItem) {
        binding.movie = movie
        binding.root.setOnClickListener { onClickListener.onClick(movie) }

        movie.poster?.let {
//            val placeHolder = ContextCompat.getDrawable(context,R.drawable.ic_camera_movie)
            binding.movieImage.loadImage(it) {
            }
        }

        if (!movie.rating.isNullOrEmpty()){
            try {
                binding.idRating.rating = movie.rating!!.toFloat()
                binding.idRatingText.text = "${movie.rating!!}/5"
                binding.idRatingText.setTextColor(Color.parseColor("#000000"))
            } catch (e:Exception){
                e.printStackTrace()
            }
        }

        movie.title?.let { binding.idTitle.text = it }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(movies: List<MoviesItem>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(movies: List<MoviesItem>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    private var selectedPosition = 0
    @SuppressLint("NotifyDataSetChanged")
    private fun setSelected(position: Int){
        selectedPosition = position
        notifyDataSetChanged()
    }
}

data class MoviesItem(
    var id: String? = null,
    var title: String? = null,
    var language: String? = null,
    var certificate: String? = null,
    var filmType: String? = null,
    var genres: List<String>? = null,
    var poster: String? = null,
    var rating: String? = null,
    var ratingCount: String?=null,
    var releaseDate: String? = null,
    var runningLength: String? = null,
    var slug: String? = null,
    var synopsis: String? = null,
    var trailer: String? = null,
    var yearOfRelease: String? = null,
    var movieDetails: String? = null
)

class MoviesViewHolder(
    val binding: AdapterMoviesBinding
) : RecyclerView.ViewHolder(binding.root)


interface MoviesOnClickListener {
    fun onClick(moviesItem : MoviesItem)
}