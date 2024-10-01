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
import com.capco.widgets.databinding.AdapterMoviesAlphaBinding
import com.capco.widgets.databinding.AdapterMoviesBinding
import kotlin.collections.ArrayList

class MoviesAdapterAlpha(var context : Context, var movies: ArrayList<MoviesItem>, private var onClickListener : MoviesAlphaOnClickListener)
    : RecyclerView.Adapter<MoviesAlphaViewHolder>() {

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MoviesAlphaViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_movies_alpha,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MoviesAlphaViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    private fun MoviesAlphaViewHolder.bind(movie: MoviesItem) {
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

class MoviesAlphaViewHolder(
    val binding: AdapterMoviesAlphaBinding
) : RecyclerView.ViewHolder(binding.root)


interface MoviesAlphaOnClickListener {
    fun onClick(item : MoviesItem)
}