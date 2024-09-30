package com.capco.support.json

import android.content.Context
import java.io.IOException

//fun Context.parseJson(fileName: String, type : Any){
//    val gson = Gson()
//    val jsonString = getJsonDataFromAsset(fileName)
//
//    val listPersonType = object : TypeToken<Any>() {}.type
//    var charts: Any = gson.fromJson(jsonString, listPersonType)
//}

fun Context.getJsonDataFromAsset(fileName: String): String? {
    val jsonString: String
    try {
        jsonString = assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun getJsonString(context: Context, fileName: String): String?{
    return context.getJsonDataFromAsset(fileName)
}
