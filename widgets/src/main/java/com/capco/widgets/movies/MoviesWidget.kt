package com.capco.widgets.movies

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capco.support.views.hide
import com.capco.support.views.show
import com.capco.widgets.R
import com.capco.widgets.databinding.WidgetMoviesBinding

class MoviesWidget : LinearLayout {

    private var binding: WidgetMoviesBinding

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        binding = WidgetMoviesBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

        val typedArray =
            context.obtainStyledAttributes(
                attrs,
                R.styleable.MoviesWidget,
                defStyleAttr,
                0
            )

        val title= typedArray.getString(R.styleable.MoviesWidget_title)

    }
    constructor(context: Context) : this(context,null)
    constructor(context: Context, data: MoviesWidgetData, onClickListener: MoviesWidgetOnClickListener) : this(context, null){
        construct(data,onClickListener)
    }

    fun construct(data: MoviesWidgetData, onClickListener: MoviesWidgetOnClickListener) {
        setData(data)
        setOnClickListener(onClickListener)
        show()
        create()
    }

    private var data: MoviesWidgetData? = null
    private fun setData(data: MoviesWidgetData){
        this.data = data
    }

    var onClickListener : MoviesWidgetOnClickListener? = null
    @JvmName("setOnClickListener1")
    fun setOnClickListener(onClickListener: MoviesWidgetOnClickListener){
        this.onClickListener = onClickListener
    }

    private fun show(){
        binding.root.show()
        binding.contents.show()
    }

    private fun create() {
        title()
        movies()
    }

    private fun title() {
        data?.title?.also { title ->
            binding.idTitle.text = title
            binding.idTitle.setOnClickListener {
                onClickListener?.onClickTitle(title)
            }
        }
    }

    private fun movies(){
        moviesRecyclerView()
    }

    @SuppressLint("StaticFieldLeak")
    var moviesRecyclerView: RecyclerView? = null
    private fun moviesRecyclerView() {
        moviesRecyclerView = binding.idMoviesRecycler
        moviesRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL,false)
        }

        when(data?.type){
            MoviesWidgetType.TOP_RATED-> {
                moviesTopRatedAdapter()
            }
            MoviesWidgetType.TRENDING -> {
                moviesTrendingAdapter()
            }
            else -> {
                //Return
            }
        }
    }

    private fun moviesTopRatedAdapter() {
        val adapter = MoviesAdapter(this.context,arrayListOf(),object : MoviesOnClickListener {
            override fun onClick(moviesItem: MoviesItem) {
                onClickListener?.onClickMovie(moviesItem)
            }
        })
        data?.movies?.let { adapter.setMovies(it) }
        moviesRecyclerView?.apply {
            this.adapter = adapter
        }
    }

    private fun moviesTrendingAdapter() {
        val adapter = MoviesAdapterAlpha(this.context,arrayListOf(),
            object : MoviesAlphaOnClickListener {
                override fun onClick(moviesItem: MoviesItem) {
                    onClickListener?.onClickMovie(moviesItem)
                }
            })
        data?.movies?.let { adapter.setMovies(it) }
        moviesRecyclerView?.apply {
            this.adapter = adapter
        }
    }
}

enum class MoviesWidgetType {
    TRENDING,
    TOP_RATED
}

interface MoviesWidgetOnClickListener {
    fun onClickTitle(title: String)
    fun onClickMovie(movie: MoviesItem)
}

data class MoviesWidgetData(
    val type : MoviesWidgetType,
    var title: String,
    val movies: List<MoviesItem>
)