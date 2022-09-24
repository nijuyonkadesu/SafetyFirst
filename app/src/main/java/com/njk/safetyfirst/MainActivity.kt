package com.njk.safetyfirst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad

class MainActivity : AppCompatActivity() {
    private lateinit var mMap: GoogleMap
    private var circle: Circle? = null
    val emergencyLocation = LatLng(13.0327, 80.23)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.maps) as SupportMapFragment
        lifecycleScope.launchWhenCreated {
            // get map
            val googleMap = mapFragment.awaitMap()
            // wait for map to finish loading
            googleMap.awaitMapLoad()
            val bounds = LatLngBounds.builder()
            bounds.include(emergencyLocation)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))

            marker(googleMap)
        }
    }

    private fun marker(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.addMarker(
            MarkerOptions()
                .position(emergencyLocation)
                .title("Marker in Emergency!")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(emergencyLocation))
        addCircle(mMap, emergencyLocation)
    }

    private fun addCircle(googleMap: GoogleMap, item: LatLng) {
        circle?.remove()
        circle = googleMap.addCircle(
            CircleOptions()
                .center(item)
                .radius(50.0)
                .fillColor(ContextCompat.getColor(this, R.color.red_translucent))
                .strokeColor(ContextCompat.getColor(this, R.color.red))
        )
    }

    fun parseSms(){
        // TODO: https://www.youtube.com/watch?v=y-UXGMZk92E
        // TODO: NotifListener, Broadcast Receiver, Notif Listener Service, on notif posted getNotifcations(), sendBroadcast(msgrcv)
    }
}

// TODO: info adapter https://developers.google.com/codelabs/maps-platform/maps-platform-101-android?authuser=0#6 
// TODO: get vicinity from google maps https://developers.google.com/maps/documentation/places/android-sdk/place-details