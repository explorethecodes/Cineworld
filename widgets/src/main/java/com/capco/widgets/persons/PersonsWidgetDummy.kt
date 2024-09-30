package com.capco.widgets.persons

import android.content.Context
import android.content.Intent
import com.capco.support.json.getJsonString
import com.capco.widgets.flips.FlipsActivity
import com.capco.widgets.flips.FlipsWidgetData
import com.capco.widgets.flips.getFlips
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Constants {
    const val TITLE = "Spotlight"
    const val JSON = "json/persons.json"
}

fun personsWidgetDummy(context: Context): PersonsWidget {
    val personsWidgetData = PersonsWidgetData(
        title = Constants.TITLE,
        persons = getPersonsItems(context)
    )
    val personsWidget =
        PersonsWidget(context, personsWidgetData, object : PersonsWidgetOnClickListener {
            override fun onClickPerson(personsItem: PersonsItem) {
                val intent = Intent(context, FlipsActivity::class.java)
                intent.putExtra("data", FlipsWidgetData(getFlips(context).toMutableList()))
                intent.putExtra("id",0)
                context.startActivity(intent)
            }
        })

    return personsWidget
}

fun getPersonsItems(context: Context): List<PersonsItem> {
    val jsonString = getJsonString(context, Constants.JSON)

    if (!jsonString.isNullOrEmpty()) {
        val gson = Gson()
        val type = object : TypeToken<List<PersonsItem>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    return listOf()
}