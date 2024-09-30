package com.capco.widgets.persons

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capco.widgets.R
import com.capco.widgets.databinding.WidgetPersonsBinding

class PersonsWidget : LinearLayout {

    private var binding: WidgetPersonsBinding

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.widget_persons,
            this,
            true
        )
    }
    constructor(context: Context, data: PersonsWidgetData, onClickListener: PersonsWidgetOnClickListener) : this(context, null){
        construct(data,onClickListener)
    }

    fun construct(data: PersonsWidgetData, onClickListener: PersonsWidgetOnClickListener){
        setData(data)
        setOnClickListener(onClickListener)
        bind()
        create()
    }

    private var data: PersonsWidgetData? = null
    private fun setData(personsData: PersonsWidgetData){
        this.data = personsData
    }

    private var onClickListener : PersonsWidgetOnClickListener? = null
    private fun setOnClickListener(movieCardOnClickListener: PersonsWidgetOnClickListener){
        this.onClickListener = movieCardOnClickListener
    }

    private fun bind() {
        binding.data = data
    }

    private fun create() {
        persons()
    }

    @SuppressLint("StaticFieldLeak")
    var recyclerView: RecyclerView? = null
    private var personsAdapter: PersonsAdapter? = null
    private fun persons() {
        recyclerView = binding.recyclerView

        personsAdapter = PersonsAdapter(context,arrayListOf(),object : PersonsOnClickListener {
            override fun onClick(personItem: PersonsItem, position: Int) {
                onClickListener?.onClickPerson(personItem)
            }
        })

        data?.let { personsWidgetData ->
            personsWidgetData.persons?.let { personsItems ->
                personsAdapter?.setData(personsItems)
                if (personsItems.size>6)
                    personsWidgetData.orientation = PersonsWidgetOrientation.HORIZONTAL_GRID
                else
                    personsWidgetData.orientation = PersonsWidgetOrientation.HORIZONTAL
            }
        }

        recyclerView?.apply {
            layoutManager = when(data?.orientation){

                PersonsWidgetOrientation.HORIZONTAL -> {
                    LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
                }

                else -> {
                    GridLayoutManager(context!!, 2, GridLayoutManager.HORIZONTAL, false)
                }
            }
            this.adapter = personsAdapter
        }
    }
}

enum class PersonsWidgetOrientation {
    HORIZONTAL_GRID,
    HORIZONTAL
}

interface PersonsWidgetOnClickListener{
    fun onClickPerson(personsItem: PersonsItem)
}

data class PersonsWidgetData(
    var orientation: PersonsWidgetOrientation? = null,
    var title: String? = null,
    var persons: List<PersonsItem>? = null
)