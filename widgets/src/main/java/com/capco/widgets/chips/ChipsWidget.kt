package com.capco.widgets.chips

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.capco.widgets.R
import com.capco.widgets.databinding.WidgetChipsBinding
import com.google.android.material.chip.Chip

class ChipsWidget : LinearLayout {

    private var binding: WidgetChipsBinding

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.widget_chips,
            this,
            true
        )
    }
    constructor(context: Context, data: ChipsWidgetData,onClickListener: ChipsWidgetOnClickListener) : this(context, null){
        construct(data,onClickListener)
    }

    fun construct(data: ChipsWidgetData, onClickListener: ChipsWidgetOnClickListener) {
        setData(data)
        setOnCLickListener(onClickListener)
        create()
    }

    private lateinit var data: ChipsWidgetData
    private fun setData(chipsData: ChipsWidgetData){
        this.data = chipsData
    }

    private lateinit var onClickListener: ChipsWidgetOnClickListener
    private fun setOnCLickListener(onClickListener: ChipsWidgetOnClickListener){
        this.onClickListener = onClickListener
    }

    private fun create() {
        chips()
    }

    private fun chips() {
        data.chips?.let { chipsItems ->
            for (chipsItem in chipsItems) {
                val chip = Chip(context)
                chip.text = chipsItem.title
                val color = ContextCompat.getColor(context, R.color.black)
                chip.chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.grey_900)

                chip.setTextColor(resources.getColor(R.color.grey_20))
                chip.setOnCloseIconClickListener {
                    binding.chips.removeView(chip)
                }

                chip.setOnClickListener {
                    onClickListener.onClick(chipsItem)
                }

                binding.chips.addView(chip)
            }

        }
    }
}

data class ChipsItem(
    var title : String?=null
)

data class ChipsWidgetData(
    var chips: List<ChipsItem>? = null,
    var onClickListener: ChipsWidgetOnClickListener? = null
)

interface ChipsWidgetOnClickListener {
    fun onClick(item : ChipsItem)
}