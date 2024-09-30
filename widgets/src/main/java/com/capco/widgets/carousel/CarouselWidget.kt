package com.capco.widgets.carousel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.capco.support.context.lifecycleOwner
import com.capco.widgets.R
import com.capco.widgets.databinding.WidgetCarouselBinding
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.io.Serializable

class CarouselWidget : LinearLayout {

    private var binding : WidgetCarouselBinding

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.widget_carousel,
            this,
            true
        )
    }
    constructor(context: Context, data: CarouselWidgetData, onClickListener: CarouselWidgetOnClickListener) : this(context, null){
        construct(data,onClickListener)
    }

    fun construct(data: CarouselWidgetData, onClickListener: CarouselWidgetOnClickListener) {
        setData(data)
        setOnClickListener(onClickListener)
        bind()
        create()
    }

    private var data: CarouselWidgetData? = null
    fun setData(data: CarouselWidgetData){
        this.data = data
    }

    var onClickListener : CarouselWidgetOnClickListener? = null
    @JvmName("setOnClickListener1")
    fun setOnClickListener(onClickListener: CarouselWidgetOnClickListener){
        this.onClickListener = onClickListener
    }

    private fun bind() {
        binding.data = data
    }

    private fun create() {
        val carousel: ImageCarousel = findViewById(R.id.id_carousal)
        this.context.lifecycleOwner()?.lifecycle?.let {
            carousel.registerLifecycle(it)
        }
        carousel.autoPlay = true

        data?.items?.let {
            carousel.setData(it.toCarouselItems())
        }

        carousel.carouselListener = object :CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                carouselItem.let { onClickListener?.onClickCarousel(it.toInputItem()) }
            }
        }
    }

}

fun List<InputItem>.toCarouselItems() : List<CarouselItem> {
    return map {
        it.toCarouselItem()
    }
}

fun InputItem.toCarouselItem() : CarouselItem {
    val map = HashMap<String,String>()
    imageUrl?.let { imageUrl->
        id?.let { id->
            map[imageUrl] = id
        }
    }
    return CarouselItem(
        imageUrl = imageUrl,
        caption = caption,
        headers = map
    )
}

fun CarouselItem.toInputItem() : InputItem {
    return InputItem(
        id = headers?.get(imageUrl),
        imageUrl = imageUrl,
        caption = caption
    )
}
data class InputItem(
    var id: String?=null,
    var imageUrl: String?=null,
    var caption: String?=null
)

interface CarouselWidgetOnClickListener {
    fun onClickCarousel(item: InputItem)
}

data class CarouselWidgetData(
    var title: String,
    var items: List<InputItem>
) : Serializable