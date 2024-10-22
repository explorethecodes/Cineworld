package com.capco.widgets.articles

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.capco.support.images.loadImage
import com.capco.widgets.R
import com.capco.widgets.databinding.AdapterArticlesBinding
import com.capco.widgets.flips.FlipsItem
import kotlin.collections.ArrayList

class ArticlesAdapter(private var articles: ArrayList<FlipsItem>, var onClickListener : ArticlesOnClickListener)
    : RecyclerView.Adapter<ArticlesViewHolder>() {

    override fun getItemCount() = articles.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArticlesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_articles,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    private fun ArticlesViewHolder.bind(flipsItem: FlipsItem) {
        binding.data = flipsItem

        flipsItem.image?.let { thumbnail ->
            binding.idImage.loadImage(thumbnail){
                it?.printStackTrace()
            }
        }

        binding.root.setOnClickListener { onClickListener.onClick(flipsItem) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setArticles(articles: List<FlipsItem>) {
        this.articles.clear()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    private var selectedPosition = 0
    @SuppressLint("NotifyDataSetChanged")
    private fun setSelected(position: Int){
        selectedPosition = position
        notifyDataSetChanged()
    }
}

class ArticlesViewHolder(
    val binding: AdapterArticlesBinding
) : RecyclerView.ViewHolder(binding.root)

interface ArticlesOnClickListener {
    fun onClick(item : FlipsItem)
}