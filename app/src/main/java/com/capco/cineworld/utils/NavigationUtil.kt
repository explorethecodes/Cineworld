package com.capco.cineworld.utils

import android.app.Activity
import android.content.Intent
import com.capco.cineworld.ui.detail.DetailActivity
import com.capco.widgets.flips.FlipsActivity
import com.capco.widgets.flips.FlipsWidgetData
import com.capco.widgets.image.ImageActivity

fun Activity.startFlipsActivity(data: FlipsWidgetData, id : String?) {
    val intent = Intent(this, FlipsActivity::class.java)
    intent.putExtra("data",data)
    intent.putExtra("id",id)
    startActivity(intent)
}

fun Activity.startImageActivity(imageUrl : String){
    val intent = Intent(this, ImageActivity::class.java)
    intent.putExtra("image_url",imageUrl)
    startActivity(intent)
}

fun Activity.startDetailActivity(id : String){
    val intent = Intent(this, DetailActivity::class.java)
    intent.putExtra("id",id)
    startActivity(intent)
}