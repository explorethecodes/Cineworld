package com.capco.widgets.flips

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.capco.support.images.loadImage
import com.capco.support.views.hide
import com.capco.support.views.show
import com.capco.widgets.R
import com.capco.widgets.databinding.FragmentFlipsBinding
import com.capco.widgets.image.ImageActivity
import java.io.Serializable
import com.google.gson.annotations.SerializedName


class FlipsAdapter(context: Context?) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val items: MutableList<FlipsItem> = ArrayList()

    private var callback: FlipsCallback? = null
    fun setCallback(callback: FlipsCallback?) {
        this.callback = callback
    }

    private lateinit var onClickListener : FlipsOnClickListener
    fun setOnClickListener(flipsAdapterListener: FlipsOnClickListener){
        this.onClickListener = flipsAdapterListener
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View?

        val holder : FlipsViewHolder

        if (convertView == null) {
            view = DataBindingUtil.inflate<FragmentFlipsBinding>(LayoutInflater.from(parent.context),
                R.layout.fragment_flips,parent,false).root
            holder = FlipsViewHolder(DataBindingUtil.bind(view)!!)

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as FlipsViewHolder
        }

        holder.bind(items[position],position)

        return view
    }

    fun FlipsViewHolder.bind(item: FlipsItem, position: Int){

        binding.data = item

        if (item.source.isNullOrEmpty()) binding.idSource.hide()

        if (item.time.isNullOrEmpty()) binding.idTime.hide()

        if (item.source.isNullOrEmpty() && item.time.isNullOrEmpty()) binding.idSourceTimeLayout.hide()

        if (position == 0)
            binding.idScrollToTop.hide()
        else
            binding.idScrollToTop.show()

//        binding.root.clearData()

        item.image?.let {
            binding.image.loadImage(it){}
            binding.image.setOnClickListener {view->
                val intent = Intent(view.context, ImageActivity::class.java)
                intent.putExtra("image_url",it)
                view.context.startActivity(intent)
            }
        }

        binding.idScrollToTop.setOnClickListener {
            onClickListener.onCLickScrollToTop()
        }

        binding.idShare.setOnClickListener{
            onClickListener.onClickShare(item, binding.content)
        }

        binding.idReadMore.setOnClickListener{
            onClickListener.onClickReadMore(item)
        }
    }

    fun setItems(items : MutableList<FlipsItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}

data class FlipsItem(
    var id: String? = null,
    var image: String? = null,
    var video: String? = null,
    var title: String? = null,
    var description: String? = null,
    var time: String? = null,
    var source: String? = null,
    var url: String? = null
) : Serializable

class FlipsViewHolder(
    val binding: FragmentFlipsBinding
)

interface FlipsOnClickListener{
    fun onCLickScrollToTop()
    fun onClickReadMore(item: FlipsItem)
    fun onClickShare(item: FlipsItem, view: View)
}

interface FlipsCallback {
    fun onPageRequested(page: Int)
}

data class FlipsWidgetData(
    val flipsItems: MutableList<FlipsItem>
) : Serializable