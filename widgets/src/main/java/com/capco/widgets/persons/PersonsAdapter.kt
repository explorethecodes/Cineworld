package com.capco.widgets.persons

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.capco.support.images.loadImage
import com.capco.widgets.R
import com.capco.widgets.databinding.AdapterPersonsBinding
import kotlin.collections.ArrayList

class PersonsAdapter(
    private var context: Context,
    private var data: ArrayList<PersonsItem>,
    private var onClickListener: PersonsOnClickListener
) : RecyclerView.Adapter<PersonsViewHolder>() {

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PersonsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_persons,
                parent,
                false
            )
        )

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<PersonsItem>?) {
        this.data.clear()
        data?.let {
            this.data.addAll(it)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setSelected(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    private var selectedPosition = 0
    override fun onBindViewHolder(holder: PersonsViewHolder, position: Int) {
        val data = data[position]
        holder.bindData(data)
    }

    private fun PersonsViewHolder.bindData(data: PersonsItem) {
        binding.data = data

        binding.root.setOnClickListener {
            onClickListener.onClick(data, position)
        }

        data.imageUrl?.let {
            val placeHolder = ContextCompat.getDrawable(context, R.drawable.ic_user)
            binding.idArtistImage.loadImage(it, placeHolder) {
            }
        }
    }

}

data class PersonsItem(
    var id: String? = null,
    var name: String? = null,
    var imageUrl: String? = null,
    var description: String? = null
)

class PersonsViewHolder(
    val binding: AdapterPersonsBinding
) : RecyclerView.ViewHolder(binding.root)

interface PersonsOnClickListener {
    fun onClick(personItem: PersonsItem, position: Int)
}