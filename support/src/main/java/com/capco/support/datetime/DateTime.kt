package com.bits.support.datetime

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.text.SimpleDateFormat
import java.util.*

const val HOUR_12 = "hh"
const val HOUR_24 = "HH"
const val DAY_NUMBER = "dd"
const val MONTH_NAME = "MMM"
const val MONTH_NUMBER = "MM"
const val YEAR_NUMBER = "yyyy"

const val FORMAT_BASIC = "$DAY_NUMBER/$MONTH_NAME/$YEAR_NUMBER hh:mm:ss"

enum class DateTimeValues{
    TIME,
    DAY,
    MONTH,
    YEAR
}

//--------------------- Stand Alone ---------------------------//
@SuppressLint("SimpleDateFormat")
fun now(dateFormat: String): String {
    val sdf = SimpleDateFormat(dateFormat)
    return sdf.format(Date())
}

fun now() : String{
    return now(FORMAT_BASIC)
}

//----------------------------- String --------------------------------//
@SuppressLint("SimpleDateFormat")
fun String.toDate(dateFormatString : String) : Date? {
    val date = try {
        SimpleDateFormat(dateFormatString).parse(this)
    } catch (e:Exception){
        null
    }
    return date
}

//---------------------- Fragment Activity ------------------------//
fun FragmentActivity.now() : String{
    return now()
}

fun FragmentActivity.now(dateFormatString: String) : String{
    return now(dateFormatString)
}

@SuppressLint("SimpleDateFormat")
fun FragmentActivity.now(dateFormat: DateTimeValues): String {
    val dateFormatString = when (dateFormat) {
        DateTimeValues.TIME -> HOUR_24
        DateTimeValues.DAY -> DAY_NUMBER
        DateTimeValues.MONTH -> MONTH_NAME
        DateTimeValues.YEAR -> YEAR_NUMBER
    }
    return now(dateFormatString)
}

fun FragmentActivity.counterTimer(expireOn : String, lifecycle: Lifecycle, callback: (Boolean,String, String, String, String) -> Unit){

    val currentDateTime = now(FORMAT_BASIC).toDate(FORMAT_BASIC)
    val endDateTime = expireOn.toDate(FORMAT_BASIC)

    if (currentDateTime!==null && endDateTime!=null){

        if (endDateTime.after(currentDateTime)){
            val dateDifference = getDateDifference(currentDateTime,endDateTime)

            val timer = object: CountDownTimer(dateDifference, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    var diff = millisUntilFinished
                    val secondsInMilli: Long = 1000
                    val minutesInMilli = secondsInMilli * 60
                    val hoursInMilli = minutesInMilli * 60
                    val daysInMilli = hoursInMilli * 24

                    val elapsedDays = diff / daysInMilli
                    diff %= daysInMilli

                    val elapsedHours = diff / hoursInMilli
                    diff %= hoursInMilli

                    val elapsedMinutes = diff / minutesInMilli
                    diff %= minutesInMilli

                    val elapsedSeconds = diff / secondsInMilli

                    var days = elapsedDays.toString()
                    var hours = elapsedHours.toString()
                    var minutes = elapsedMinutes.toString()
                    var seconds = elapsedSeconds.toString()

                    if (elapsedDays<10){
                        days = "0$days"
                    }
                    if (elapsedHours<10){
                        hours = "0$hours"
                    }
                    if (elapsedMinutes<10){
                        minutes = "0$minutes"
                    }
                    if (elapsedSeconds<10){
                        seconds = "0$seconds"
                    }
                    callback(false,days,hours,minutes,seconds)
                }

                override fun onFinish() {

                }
            }
            timer.start()

            lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_START)
                fun onStart(){
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
                fun onCreate(){
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                fun onPause(){
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                fun onResume(){
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
                fun onStop(){
                    timer.onFinish()
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy(){
                    timer.onFinish()
                }
            })
        } else {
            callback(true,"","","","")
        }
    }
}

//----------------------------- Dates -----------------------------------------//
fun getDateDifference(startDate: Date,endDate: Date): Long {
    return endDate.time - startDate.time
}
fun getDateDifferenceInDays(startDate: Date, endDate: Date): Long {

    val different: Long = endDate.time - startDate.time

    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    return different / daysInMilli
}

fun printDifference(startDate: Date, endDate: Date) {
    //milliseconds
    var different: Long = endDate.time - startDate.time
    println("startDate : $startDate")
    println("endDate : $endDate")
    println("different : $different")

    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays = different / daysInMilli

    different %= daysInMilli
    val elapsedHours = different / hoursInMilli
    different %= hoursInMilli
    val elapsedMinutes = different / minutesInMilli
    different %= minutesInMilli
    val elapsedSeconds = different / secondsInMilli
    System.out.printf(
            "%d days, %d hours, %d minutes, %d seconds%n",
            elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds)
}