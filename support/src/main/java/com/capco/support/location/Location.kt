package com.capco.support.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import java.io.IOException
import java.util.*

/*---------- Location Listener ------------- */
class LocationListener(var context : Context, var callback : (Location) -> Unit) : LocationListener {
    override fun onLocationChanged(loc: android.location.Location) {
        var city: String? =null
        var district: String? = null
        var state: String? = null

        val gcd = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>?
        try {
            addresses = gcd.getFromLocation(loc.latitude,
                    loc.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                city = addresses[0].locality
                district = addresses[0].subAdminArea
                state = addresses[0].adminArea
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val location = Location(loc.latitude,loc.longitude,city,district,state)
        Log.d("ncm", "Found location $location")

        callback(location)
    }

    @Deprecated("Deprecated in Java")
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}


    override fun onProviderEnabled(provider: String) {}

    override fun onProviderDisabled(provider: String) {}

}

data class Location(
        var latitude: Double? = null,
        var longitude: Double? = null,
        var city: String? = null,
        var district: String? = null,
        var state: String? = null
)