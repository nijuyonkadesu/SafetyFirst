package com.njk.safetyfirst

import android.os.Bundle
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
    private lateinit var mMap: GoogleMap
    private var circle: Circle? = null
    val emergencyLocation = LatLng(13.0327, 80.23) // TODO: load from sms

    override fun onCreate(savedInstanceState: Bundle?) { // TODO: launch with intent
        super.onCreate(savedInstanceState)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
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
}