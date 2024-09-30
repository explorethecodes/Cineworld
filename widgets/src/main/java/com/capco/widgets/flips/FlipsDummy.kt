package com.capco.widgets.flips

import android.content.Context
import com.capco.support.json.getJsonString
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Constants {
    const val JSON = "json/flips.json"
}

fun getFlips(context: Context): List<FlipsItem> {
    val jsonString = getJsonString(context, Constants.JSON)

    if (!jsonString.isNullOrEmpty()) {
        val gson = Gson()
        val type = object : TypeToken<List<FlipsItem>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    return listOf()
}