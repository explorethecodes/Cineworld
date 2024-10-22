package com.capco.widgets.articles

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.capco.support.views.hide
import com.capco.support.views.show
import com.capco.widgets.R
import com.capco.widgets.databinding.WidgetArticlesBinding
import com.capco.widgets.flips.FlipsItem

class ArticlesWidget : LinearLayout {

    companion object{
        const val ARTICLES_ADAPTER_MAX_LIMIT = 5
    }

    private var binding: WidgetArticlesBinding

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.widget_articles,
            this,
            true
        )
    }
    constructor(context: Context, data: ArticlesWidgetData, onClickListener: ArticlesWidgetOnClickListener) : this(context, null){
        construct(data,onClickListener)
    }

    fun construct(data: ArticlesWidgetData, onClickListener: ArticlesWidgetOnClickListener){
        setData(data)
        setOnClickListener(onClickListener)
        bind()
        create()
    }

    private var data: ArticlesWidgetData? = null
    private fun setData(articlesData: ArticlesWidgetData){
        this.data = articlesData
    }

    private var onClickListener : ArticlesWidgetOnClickListener? = null
    private fun setOnClickListener(movieCardOnClickListener: ArticlesWidgetOnClickListener){
        this.onClickListener = movieCardOnClickListener
    }

    private fun bind() {
        binding.data = data
    }

    private fun create() {
        articles()
    }

    @SuppressLint("StaticFieldLeak")
    private fun articles() {
        val unfilteredArticles = data?.articles
        val articles = unfilteredArticles?.filter { !it.title.isNullOrEmpty() and !it.description.isNullOrEmpty()}
        binding.articlesLayout.show()

        val articlesRecyclerView = binding.articles
        val articlesAdapter = ArticlesAdapter(arrayListOf(),object : ArticlesOnClickListener {
            override fun onClick(articlesItem: FlipsItem) {
                onClickListener?.onClickArticle(articlesItem)
            }
        })
        articlesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL,false)
            adapter = articlesAdapter
        }

        articles?.take(ARTICLES_ADAPTER_MAX_LIMIT)?.let { articlesAdapter.setArticles(it) }

        if (articles != null) {
            if (articles.size> ARTICLES_ADAPTER_MAX_LIMIT)
                binding.viewMore.show()
            else
                binding.viewMore.hide()
        }

        binding.viewMore.setOnClickListener {
            onClickListener?.onClickViewMore()
        }
    }
}

interface ArticlesWidgetOnClickListener{
    fun onClickArticle(articlesItem: FlipsItem)
    fun onClickViewMore()
}

data class ArticlesWidgetData(
    var title: String? = null,
    var articles: List<FlipsItem>? = null
)