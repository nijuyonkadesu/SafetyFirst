package com.njk.safetyfirst

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad

class MapActivity :AppCompatActivity() {
    companion object SmsLocation {
        var latitude: Double = 0.0
        var longitude: Double = 0.0
        var emergencyLocation: LatLng = LatLng(0.0,0.0)
    }

    private lateinit var mMap: GoogleMap
    private var circle: Circle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        latitude = intent?.extras?.getString("lat")?.toDouble() ?: 0.0
        longitude = intent?.extras?.getString("long")?.toDouble() ?: 0.0
        val emergencyLocation = LatLng(latitude, longitude)
        Log.d("intent", "intent received $latitude, $longitude")

        // TODO: fix findFragmentById cannot detect view
        val mapFragment = (supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?)
        lifecycleScope.launchWhenCreated {
            // get map
            val googleMap = mapFragment?.awaitMap()
            // wait for map to finish loading
            googleMap?.awaitMapLoad()
            val bounds = LatLngBounds.builder()
            bounds.include(emergencyLocation)
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))

            if (googleMap != null) {
                marker(googleMap)
            }
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
}