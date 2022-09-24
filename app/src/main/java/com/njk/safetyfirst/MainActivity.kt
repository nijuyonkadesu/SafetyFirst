package com.njk.safetyfirst

import android.graphics.Color.red
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var circle: Circle? = null
    val emergencyLocation = LatLng(13.0327, 80.23)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapFragment.getMapAsync { googleMap ->
            // Ensure all places are visible in the map.
            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()
                bounds.include(emergencyLocation)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
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

// TODO: info adapter https://developers.google.com/codelabs/maps-platform/maps-platform-101-android?authuser=0#6 
// TODO: get vicinity from google maps https://developers.google.com/maps/documentation/places/android-sdk/place-details