package com.capco.widgets.carousel

import android.content.Context
import android.content.Intent
import com.capco.support.json.getJsonString
import com.capco.widgets.image.ImageActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

object Constants {
    const val TITLE = "Latest release"
    const val JSON = "json/carousels.json"
}

fun carouselWidgetDummy(context: Context) : CarouselWidget {
    val carouselWidgetData = CarouselWidgetData(Constants.TITLE,getInputItems(context))
    val carouselWidget = CarouselWidget(context,carouselWidgetData,object : CarouselWidgetOnClickListener {
        override fun onClickCarousel(item: InputItem) {
            val intent = Intent(Intent(context, ImageActivity::class.java))
            intent.putExtra("image_url",item.imageUrl)
            context.startActivity(intent)
        }
    })

    return carouselWidget
}

fun getInputItems(context: Context) : List<InputItem>{
    val jsonString = getJsonString(context, Constants.JSON)

    if(!jsonString.isNullOrEmpty()) {
        val gson = Gson()
        val type = object : TypeToken<List<InputItem>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    return listOf()
}